package com.liugeng.myspring.beans.factory.support;

import com.liugeng.myspring.beans.BeanDefinition;

public interface BeanDefinitionRegister {
    BeanDefinition getBeanDefinition(String id);
    public void registerBeanDefinition(String id, BeanDefinition bd);
}
