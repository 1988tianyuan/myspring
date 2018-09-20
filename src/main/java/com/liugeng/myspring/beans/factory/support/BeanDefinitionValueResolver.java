package com.liugeng.myspring.beans.factory.support;

import com.liugeng.myspring.beans.factory.config.RuntimeBeanReference;
import com.liugeng.myspring.beans.factory.config.TypeStringValue;

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
