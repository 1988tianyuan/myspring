package com.liugeng.myspring.beans.factory.support;

import com.liugeng.myspring.beans.factory.config.SingletonBeanRegistry;
import com.liugeng.myspring.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>();

    @Override
    public void registerSingleton(String beanName, Object bean) {
        Assert.notNull(beanName, "beanName must not be null !");
        if(this.singletonObjects.containsKey(beanName)){
            throw new IllegalStateException("Couldn't register bean:" + beanName + ", there is already an object.");
        }
        this.singletonObjects.put(beanName, bean);
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletonObjects.get(beanName);
    }
}
