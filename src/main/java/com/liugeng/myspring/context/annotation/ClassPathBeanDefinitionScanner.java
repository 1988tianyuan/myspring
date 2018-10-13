package com.liugeng.myspring.context.annotation;

import com.liugeng.myspring.beans.BeanDefinition;
import com.liugeng.myspring.beans.ConstructorArgument;
import com.liugeng.myspring.beans.PropertyValue;
import com.liugeng.myspring.beans.factory.support.GenericBeanDefinition;

import java.util.List;

public class ClassPathBeanDefinitionScanner extends GenericBeanDefinition{
    @Override
    public String getBeanClassName() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public boolean isPrototype() {
        return false;
    }

    @Override
    public String getScope() {
        return null;
    }

    @Override
    public void setScope(String scope) {

    }

    @Override
    public List<PropertyValue> getPropertyValues() {
        return null;
    }

    @Override
    public ConstructorArgument getConstructorArgument() {
        return null;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public boolean hasConstructorArgumentValues() {
        return false;
    }
}
