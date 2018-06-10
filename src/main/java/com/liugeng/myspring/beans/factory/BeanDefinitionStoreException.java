package com.liugeng.myspring.beans.factory;

import com.liugeng.myspring.beans.BeansException;

public class BeanDefinitionStoreException extends BeansException{

    public BeanDefinitionStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
