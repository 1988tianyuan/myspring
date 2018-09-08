package com.liugeng.myspring.beans.factory.config;

public interface SingletonBeanRegistry {
    void registerSingleton(String beanName, Object bean);
    Object getSingleton(String beanName);
}
