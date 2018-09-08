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

当前项目类图如下：https://raw.githubusercontent.com/1988tianyuan/myspring/master/images/BeanFactory(%E4%B8%8B)%20-%20%E7%B1%BB%E5%9B%BE.jpg
