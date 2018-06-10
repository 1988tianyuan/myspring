package com.liugeng.myspring.beans.factory;

import com.liugeng.myspring.beans.BeanDefinition;

public interface BeanFactory {

    BeanDefinition getBeanDefinition(String petstore);

    Object getBean(String beanId);
}
