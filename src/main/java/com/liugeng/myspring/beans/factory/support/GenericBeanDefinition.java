package com.liugeng.myspring.beans.factory.support;

import com.liugeng.myspring.beans.BeanDefinition;

public class GenericBeanDefinition implements BeanDefinition {

    private String id;
    private String beanClassName;
    private String scope = SCOPE_DEFAULT;

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    @Override
    public String getBeanClassName() {
        return this.beanClassName;
    }

    @Override
    public boolean isSingleton() {
        return scope.equals(SCOPE_SINGLETON) || scope.equals(SCOPE_DEFAULT);
    }

    @Override
    public boolean isPrototype() {
        return scope.equals(SCOPE_PROTOTYPE);
    }

    @Override
    public String getScope() {
        return scope;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
    }
}
