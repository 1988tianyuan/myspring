package com.liugeng.myspring.beans.factory.support;

import com.liugeng.myspring.beans.BeanDefinition;
import com.liugeng.myspring.beans.factory.BeanCreationException;
import com.liugeng.myspring.beans.factory.BeanDefinitionStoreException;
import com.liugeng.myspring.beans.factory.BeanFactory;
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

public class DefaultBeanFactory implements BeanFactory {

    private static final String ID_ATTRIBUTE = "id";
    private static final String CLASS_ATTRIBUTE = "class";
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

    public DefaultBeanFactory(String configFile) {
        loadBeanDefinition(configFile);
    }

    /**
     * 通过xml配置文件解析BeanDefinition
     * @param configFile
     */
    private void loadBeanDefinition(String configFile){
        InputStream is = null;
        ClassLoader cl = ClassUtils.getDefaultClassLoader();
        is = cl.getResourceAsStream(configFile);    //由ClassLoader对象从classpath查找资源
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(is);
            Element root = doc.getRootElement();    //获取根元素（也就是beans列表）
            Iterator<Element> iter = root.elementIterator();
            while (iter.hasNext()){
                Element element = iter.next();
                String id = element.attributeValue(ID_ATTRIBUTE);
                String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition bd = new GenericBeanDefinition(id, beanClassName);
                this.beanDefinitionMap.put(id, bd);
            }
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("load bean definition for XML document: "+configFile+" failed !", e);
        } finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
     * 通过组件id获取组件实例，如果获取失败则抛出相应的BeanCreationException
     * @param beanId
     * @return
     */
    public Object getBean(String beanId) {
        BeanDefinition bd = this.beanDefinitionMap.get(beanId);
        if(bd == null){
            throw new BeanCreationException("Bean definition does not exist !");
        }
        String beanClassName = bd.getBeanClassName();
        try {
            Class<?> clazz = Class.forName(beanClassName);
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for name: " + beanId + " failed", e);
        }
    }
}
















