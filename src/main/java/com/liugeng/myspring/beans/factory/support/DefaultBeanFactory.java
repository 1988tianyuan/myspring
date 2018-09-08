package com.liugeng.myspring.beans.factory.support;

import com.liugeng.myspring.beans.BeanDefinition;
import com.liugeng.myspring.beans.factory.BeanCreationException;
import com.liugeng.myspring.beans.factory.BeanDefinitionStoreException;
import com.liugeng.myspring.beans.factory.BeanFactory;
import com.liugeng.myspring.beans.factory.config.ConfigurableBeanFactory;
import com.liugeng.myspring.util.ClassUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory, BeanDefinitionRegister {

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
    private ClassLoader beanClassLoader;

    public DefaultBeanFactory() {
    }

    public void registerBeanDefinition(String id, BeanDefinition bd) {
        this.beanDefinitionMap.put(id, bd);
    }

    /**
     * 通过组件id获取相应的BeanDefinition对象，
     * @param beanId
     * @return
     */
    public BeanDefinition getBeanDefinition(String beanId) {
        BeanDefinition db = this.beanDefinitionMap.get(beanId);
        if(db == null){
            throw new BeanCreationException("Bean definition does not exist !");
        }
        return db;
    }

    /**
     * 通过组件id获取组件实例，如果是singleton则S生成一个实例放入singleton列表
     * @param beanId
     * @return
     */
    public Object getBean(String beanId) {
        BeanDefinition bd = this.beanDefinitionMap.get(beanId);
        if(bd == null){
            throw new BeanCreationException("Bean definition does not exist !");
        }
        if(bd.isSingleton()) {
            Object bean = this.getSingleton(beanId);
            if (bean == null) {
                bean = this.createBean(bd);
                this.registerSingleton(beanId, bean);
            }
            return bean;
        }
        return createBean(bd);
    }

    /**
     * 通过反射创建实例
     * @param bd
     * @return
     */
    private Object createBean(BeanDefinition bd) {
        String beanClassName = bd.getBeanClassName();
        try {
            ClassLoader cl = getBeanClassLoader();
            Class<?> clazz = cl.loadClass(beanClassName);
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for name: " + beanClassName + " failed, ", e);
        }
    }

    @Override
    public void setBeanClassLoader(ClassLoader classloader) {
        this.beanClassLoader = classloader;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader();
    }
}
















