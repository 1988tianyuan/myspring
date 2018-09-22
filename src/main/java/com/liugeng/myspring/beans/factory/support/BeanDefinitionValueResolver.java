package com.liugeng.myspring.beans.factory.support;

import com.liugeng.myspring.beans.TypeConverter;
import com.liugeng.myspring.beans.factory.config.RuntimeBeanReference;
import com.liugeng.myspring.beans.factory.config.TypeStringValue;

public class BeanDefinitionValueResolver {
    private final DefaultBeanFactory factory;
    private final TypeConverter converter;

    public BeanDefinitionValueResolver(DefaultBeanFactory factory, TypeConverter converter) {
        this.factory = factory;
        this.converter = converter;
    }

    public Object resolveValueIfNecessary(Object value, Class<?> requireType) throws Exception{
        if(value instanceof RuntimeBeanReference){
            //如果property是ref类型，则从BeanFactory中取值并返回
            RuntimeBeanReference reference = (RuntimeBeanReference) value;
            return factory.getBean(reference.getBeanName());
        }else if (value instanceof TypeStringValue){
            TypeStringValue stringValue = (TypeStringValue) value;
            //如果property是value类型，则直接对其进行convert并返回convert后的值
            return converter.convertIfNecessary(stringValue.getValue(), requireType);
        }else {
            throw new Exception("this value "+value+ "has not beem implemented.");
        }
    }

}
