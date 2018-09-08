package com.liugeng.myspring.beans.factory.config;

import com.liugeng.myspring.beans.factory.BeanFactory;

/**
 * 让用户可以自由配置ClassLoader
 */
public interface ConfigurableBeanFactory extends BeanFactory{
    void setBeanClassLoader(ClassLoader classloader);
    ClassLoader getBeanClassLoader();
}
