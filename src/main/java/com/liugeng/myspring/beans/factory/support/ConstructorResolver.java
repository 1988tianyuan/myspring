package com.liugeng.myspring.beans.factory.support;

import com.liugeng.myspring.beans.BeanDefinition;
import com.liugeng.myspring.beans.ConstructorArgument;
import com.liugeng.myspring.beans.SimpleTypeConverter;
import com.liugeng.myspring.beans.factory.BeanCreationException;
import com.liugeng.myspring.beans.factory.config.ConfigurableBeanFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ConstructorResolver {
    protected final Log logger = LogFactory.getLog(ConstructorResolver.class);

    private final ConfigurableBeanFactory factory;

    public ConstructorResolver(DefaultBeanFactory factory) {
        this.factory = factory;
    }

    /**
     * 获取当前bean中跟配置文件所匹配的Constructor并生成bean
     * @param beanDefinition
     * @return
     */
    public Object autowireConstructor(final BeanDefinition beanDefinition) {
        Constructor<?> constructorToUse = null;
        Class<?> beanClass;
        Object[] argsToUse = null;
        try {
            beanClass = this.factory.getBeanClassLoader().loadClass(beanDefinition.getBeanClassName());
        } catch (ClassNotFoundException e){
            throw new BeanCreationException("Instantiation of bean: " + beanDefinition.getId() + " failed, can't find class");
        }
        Constructor<?>[] candidates = beanClass.getConstructors();
        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory);
        ConstructorArgument constructorArgument = beanDefinition.getConstructorArgument();
        SimpleTypeConverter converter = new SimpleTypeConverter();
        //找到合适的constructor
        for(int i = 0; i<candidates.length; i++){
            Class<?>[] paramTypes = candidates[i].getParameterTypes();
            if(paramTypes.length != constructorArgument.getArgumentCount()){
                continue;
            }
            argsToUse = new Object[paramTypes.length];
            //看看是不是合适的constructor，如果是的话同时生成构造参数列表：argsToUse
            boolean result = this.valuesMatchTypes(paramTypes, constructorArgument.getArgumentValues(), argsToUse, resolver, converter);
            if(result){
                constructorToUse = candidates[i];
                break;
            }
        }
        //没找到合适的constructor
        if(constructorToUse == null){
            throw new BeanCreationException("can't find a appropriate constructor in bean: " + beanDefinition.getId());
        }
        try {
            //找到了合适的constructor，使用准备好的参数构造实例
            return constructorToUse.newInstance(argsToUse);
        } catch (Exception e) {
            throw new BeanCreationException("can't create bean by constructor: " + beanDefinition.getId() + ", error message: " + e.getMessage());
        }
    }

    /**
     * 遍历constructor的参数列表，并将ConstructorArgument中的value依次转换为对应类型的值
     * 如果出错则说明该constructor不匹配
     * @param paramTypes
     * @param argumentValues
     * @param argsToUse
     * @param resolver
     * @param converter
     * @return
     */
    private boolean valuesMatchTypes(Class<?>[] paramTypes, List<ConstructorArgument.ValueHolder> argumentValues, Object[] argsToUse,
                                     BeanDefinitionValueResolver resolver, SimpleTypeConverter converter) {
        for(int i = 0; i < paramTypes.length; i++){
            ConstructorArgument.ValueHolder valueHolder = argumentValues.get(i);
            Object value = valueHolder.getValue();
            try {
                Object resolvedValue = resolver.resolveValueIfNecessary(value);
                Object convertedValue = converter.convertIfNecessary(resolvedValue, paramTypes[i]);
                argsToUse[i] = convertedValue;
            } catch (Exception e) {
                logger.error(e.getMessage());
                return false;
            }
        }
        return true;
    }
}
