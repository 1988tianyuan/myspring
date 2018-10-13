package com.liugeng.myspring.context.support;

import com.liugeng.myspring.beans.factory.support.DefaultBeanFactory;
import com.liugeng.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import com.liugeng.myspring.context.ApplicationContext;
import com.liugeng.myspring.core.io.Resource;
import com.liugeng.myspring.util.ClassUtils;

import java.io.FileNotFoundException;

public abstract class AbstractApplicationContext implements ApplicationContext {
    private DefaultBeanFactory factory;
    private ClassLoader beanClassLoader;

    public AbstractApplicationContext(String config) throws FileNotFoundException {
        Resource resource = getResource(config);
        this.factory = new DefaultBeanFactory();
        new XmlBeanDefinitionReader(this.factory).loadBeanDefinition(resource);
    }

    public Object getBean(String beanId) {
        return factory.getBean(beanId);
    }

    //资源具体获取方式放到子类去实现
    public abstract Resource getResource(String config);

    @Override
    public void setBeanClassLoader(ClassLoader classloader) {
        this.beanClassLoader = classloader;
        this.factory.setBeanClassLoader(classloader);
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader();
    }
}
