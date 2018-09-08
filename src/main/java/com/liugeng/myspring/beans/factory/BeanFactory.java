package com.liugeng.myspring.beans.factory;

import com.liugeng.myspring.beans.BeanDefinition;

public interface BeanFactory {
    Object getBean(String beanId);
}
