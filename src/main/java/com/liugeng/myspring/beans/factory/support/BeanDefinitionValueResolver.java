package com.liugeng.myspring.beans.factory.support;

import com.liugeng.myspring.beans.TypeConverter;
import com.liugeng.myspring.beans.factory.config.RuntimeBeanReference;
import com.liugeng.myspring.beans.factory.config.TypeStringValue;

public class BeanDefinitionValueResolver {
    private final DefaultBeanFactory factory;

    public BeanDefinitionValueResolver(DefaultBeanFactory factory) {
        this.factory = factory;
    }

    public Object resolveValueIfNecessary(Object value) throws Exception{
        if(value instanceof RuntimeBeanReference){
            //如果property是ref类型，则从BeanFactory中取值并返回
            RuntimeBeanReference reference = (RuntimeBeanReference) value;
            return factory.getBean(reference.getBeanName());
        }else if (value instanceof TypeStringValue){
            TypeStringValue stringValue = (TypeStringValue) value;
            //如果property是value类型，则直接对其进行convert并返回convert后的值
            return stringValue.getValue();
        }else {
            throw new Exception("this value "+value+ "has not beem implemented.");
        }
    }

}
