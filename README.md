# myspring
spring源码从0开始解析，从构建最简单的BeanFactory入手，一步步逼近真正意义上的IOC和AOP

# Chapter One
### 构建BeanFactory接口
首先创建maven接口，在beans.factory包下创建BeanFactory接口如下，这是spring的核心bean容器接口，用于初始化组件定义和组件生命周期管理：
```
public interface BeanFactory {

    BeanDefinition getBeanDefinition(String petstore);

    Object getBean(String beanId);
}
```
### 构建BeanDefinition接口
BeanDefinition用于管理组件的各项定义信息，包括id、类名、依赖关系等：
```
public interface BeanDefinition {

    String getBeanClassName();
}
```
目前初步只定义了获取类名这个方法，该方法用于后续通过反射生成类实例.

实现该接口：GenericBeanDefinition，该实现类只包含了id和ClassName，用于基本需求
```
public class GenericBeanDefinition implements BeanDefinition {

    private String id;
    private String beanClassName;

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    public String getBeanClassName() {
        return this.beanClassName;
    }
}
```


### 实现BeanFactory
实现一个DefaultBeanFactory，该实现包含了通过xml配置文件加载BeanDefinition这个方法，将解析得到的BeanDefinition存入一个ConcurrentHashMap中：
```
public class DefaultBeanFactory implements BeanFactory {

    private static final String ID_ATTRIBUTE = "id";
    private static final String CLASS_ATTRIBUTE = "class";
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

    public DefaultBeanFactory(String configFile) {
        loadBeanDefinition(configFile);
    }

    /**
     * 通过xml配置文件解析BeanDefinition
     * @param configFile
     */
    private void loadBeanDefinition(String configFile){
        //1.通过dom4j解析xml配置文件，得到Bean元素
        //2.获取每个Bean的id和ClassName，生成具体的BeanDefinition
        //3.这里的BeanDefinition实现类是GenericBeanDefinition
        //4.将生成的BeanDefinition保存到Map中
    }

    /**
     * 通过组件id获取相应的BeanDefinition对象，
     * @param beanId
     * @return
     */
    public BeanDefinition getBeanDefinition(String beanId) {
        BeanDefinition db = this.beanDefinitionMap.get(beanId);
        if(db == null){
            throw new BeanCreationException("Bean definition does not exist !");
        }
        return db;
    }

    /**
     * 通过组件id获取组件实例，如果获取失败则抛出相应的BeanCreationException
     * @param beanId
     * @return
     */
    public Object getBean(String beanId) {
        BeanDefinition bd = this.beanDefinitionMap.get(beanId);
        //...
        //通过反射获取类实例
        //...
    }
}
```

### 测试
xml定义一个Bean，只有id和类名，其他啥也没有：
```
<bean id="petStore" class="com.liugeng.myspring.service.v1.PetStoreService"></bean>
```
```
    @Test
    public void testGetBean(){
        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
        BeanDefinition bd = factory.getBeanDefinition("petStore");
        Assert.assertEquals("com.liugeng.myspring.service.v1.PetStoreService", bd.getBeanClassName());

        PetStoreService petStoreService = (PetStoreService)factory.getBean("petStore");
        Assert.assertNotNull(petStoreService);
    }
```
可见，通过这个基本的BeanFactory实现，就能完成最基本的组件管理功能，但离完全意义的IOC还差很远

# Chapter Two
- 在之前的基础上增加了XmlBeanDefinitionReader对xml配置文件进行统一读取
- 让DefaultBeanFactory实现BeanDefinitionRegister接口进行BeanDefinition注册
- 增加ApplicationContext接口及多个针对不同资源获取方式的实现类，实现对ClassLoader的读取
- 增加Resource接口，对不同资源（Classpath或者filesystem）进行输入流获取

当前项目类图如下：
![image](https://raw.githubusercontent.com/1988tianyuan/myspring/master/images/BeanFactory(%E4%B8%8B)%20-%20%E7%B1%BB%E5%9B%BE.jpg)

# Chapter Three
实现setter注入，这部分的难点在于：
- 如何将XML配置文件中Bean的Property取出来并进行解析，得到想要的类型的值（基本类型、字符串、对象引用）
- 得到需要的Property类型值后，将值注入到Bean中对应的属性上

## 解析Bean配置文件的Property
Property总体上分为两种类型：直接可以使用的值、对象引用， 前者可以直接注入到Bean中（非String类型需要进行某些转换），后者需要对其进行解析转换为需要的对象类型才能进行注入

- 使用两个包装类来对以上两种类型进行包装：RuntimeBeanReference和TypeStringValue， 前者包装引用类型，后者包装String和基本数据类型
```
// XmlBeanDefinitionReader解析Bean配置时，ref类型的信息放入RuntimeBeanReference中
<property name="accountDao" ref="accountDao"/>
```
```
public class RuntimeBeanReference {
    //将ref引用的Bean名称存储进来
    private final String beanName;
    public RuntimeBeanReference(String beanName) {
        this.beanName = beanName;
    }
    public String getBeanName() {
        return beanName;
    }
}
```
```
// value类型的信息放入TypeStringValue
<property name="env" value="test"/>
<property name="version" value="1"/>
```
```
public class TypeStringValue {
    private String value;
    public TypeStringValue(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
```
- 获得两种类型的包装对象后，以Object形式放入PropertyValue这个包装类，并作为Bean的Property存储在BeanDefinition中，到这里单个Bean的注册就算完成了
```
PropertyValue property = new PropertyValue(propName, propValue);
beanDefinition.getPropertyValues().add(property);
```
- 对引用数据类型进行解析

当构建Bean进行依赖注入时，如果是引用类型（由RuntimeBeanReference包装），则需要进行解析，获取实际的引用对象，这里用到了BeanDefinitionValueResolver这个类：

```
public class BeanDefinitionValueResolver {
    private final DefaultBeanFactory factory;
    public BeanDefinitionValueResolver(DefaultBeanFactory factory) {
        this.factory = factory;
    }
    public Object resolveValueIfNecessary(Object value) throws Exception{
        if(value instanceof RuntimeBeanReference){
            RuntimeBeanReference reference = (RuntimeBeanReference) value;
            return factory.getBean(reference.getBeanName());
        }else if (value instanceof TypeStringValue){
            TypeStringValue stringValue = (TypeStringValue) value;
            return stringValue.getValue();
        }else {
            throw new Exception("this value "+value+ "has not beem implemented.");
        }
    }
}
```
如上，如果property是ref类型，则从BeanFactory中取相应的Bean并返回，如果是value类型，需要对其进行进一步解析，得到String或者基本数据类型

- 对基本数据类型进行解析

这部分相对比较复杂，需要额外构建一个TypeConverter，将输入TypeStringValue中的String对象转换为需要的类型（int, boolean, long等），这里用到了JDK的PropertyEditor这个接口，能够将String转换为需要的其他基本类型，类图如下：
![image](https://raw.githubusercontent.com/1988tianyuan/myspring/4-setter-injection/images/TypeConverter.JPG)

TypeConvert中以下方法能够将String转换为需要的基本数据类型：
```
public <T> T convertIfNecessary(Object value, Class<T> requireType) throws TypeMismatchException {
    if(ClassUtils.isAssignableValue(requireType, value)){
        return (T)value;
    } else {
        if(value instanceof String){
            PropertyEditor editor = this.findDefaultEditor(requireType);
            try {
                editor.setAsText((String) value);
            } catch (IllegalArgumentException e) {
                throw new TypeMismatchException(value, requireType);
            }
            return (T)editor.getValue();
        }else {
            throw new TypeMismatchException(value, requireType);
        }
    }
}
```

## 依赖注入
在BeanFactory中，依赖注入发生在Bean实例化之后：
```
private Object createBean(BeanDefinition bd) {
    //初始化bean对象
    Object bean = instantiateBean(bd);
    //进行bean的依赖注入
    populateBean(bd, bean);
    return bean;
}
```
- 首先，获取当前BeanDefinition的propertyValues列表

```
List<PropertyValue> propertyValues = bd.getPropertyValues();
```
- 使用JDK的Introspector对bean的每个property实现setter注入
```
BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
for(PropertyValue pv : propertyValues){
    String propertyName = pv.getName();
    Object originalValue = pv.getValue();
    for(PropertyDescriptor descriptor : descriptors){
        if(descriptor.getName().equals(propertyName)){
            //将原始String转换为需要的基本数据类型
            Object convertedValue = converter.convertIfNecessary(resolvedValue, descriptor.getPropertyType());
            //实质上就是调用bean的setter方法
            descriptor.getWriteMethod().invoke(bean, convertedValue);
            break;
        }
    }
}
```
至此，整个依赖注入过程全部完成


## 依赖注入的简化
TypeConvert实现起来有点复杂，其实Commons包里有现成的工具用于将某个值装载到Bean中：
```
//通过BeanUtils将值装载到bean属性中，不需要自己实现TypeConvert
BeanUtils.setProperty(bean, propertyName, resolvedValue);
```

```
private void populateBeanUseCommonBeanUtils(BeanDefinition bd, Object bean){
    List<PropertyValue> propertyValues = bd.getPropertyValues();
    if(propertyValues == null || propertyValues.isEmpty()){
        return;
    }
    TypeConverter converter = new SimpleTypeConverter();
    BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);
    try {
        //使用BeanUtils对bean的每个property实现setter注入
        for(PropertyValue pv : propertyValues){
            String propertyName = pv.getName();
            Object originalValue = pv.getValue();
            //得到解析后的值，具体引用或者原始String
            Object resolvedValue = resolver.resolveValueIfNecessary(originalValue);
            //通过BeanUtils将值装载到bean属性中，不需要自己实现TypeConvert
            BeanUtils.setProperty(bean, propertyName, resolvedValue);
        }
    } catch (Exception e) {
        throw new BeanCreationException("Failed to obtain BeanInfo for class [" + bd.getBeanClassName() + "]");
    }
}
```

# Chapter Four
使用构造器进行注入
 