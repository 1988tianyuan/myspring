package com.liugeng.myspring.beans.factory.xml;

import com.liugeng.myspring.beans.BeanDefinition;
import com.liugeng.myspring.beans.factory.BeanDefinitionStoreException;
import com.liugeng.myspring.beans.factory.support.BeanDefinitionRegister;
import com.liugeng.myspring.beans.factory.support.GenericBeanDefinition;
import com.liugeng.myspring.core.io.Resource;
import com.liugeng.myspring.util.ClassUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class XmlBeanDefinitionReader {
    private static final String ID_ATTRIBUTE = "id";
    private static final String CLASS_ATTRIBUTE = "class";
    private static final String SCOPE_ATTRIBUTE = "scope";
    private BeanDefinitionRegister register;

    public XmlBeanDefinitionReader(BeanDefinitionRegister register) {
        this.register = register;
    }

    /**
     * 通过xml配置文件解析BeanDefinition
     */
    public void loadBeanDefinition(Resource resource) throws FileNotFoundException {
        InputStream is = resource.getInputStream();
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(is);
            Element root = doc.getRootElement();    //获取根元素（也就是beans列表）
            Iterator<Element> iter = root.elementIterator();
            while (iter.hasNext()){
                Element element = iter.next();
                String id = element.attributeValue(ID_ATTRIBUTE);
                String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);
                String scope = element.attributeValue(SCOPE_ATTRIBUTE);
                BeanDefinition bd = new GenericBeanDefinition(id, beanClassName);
                if(scope != null) bd.setScope(scope);   //读取scope属性并存入beanDefinition中
                register.registerBeanDefinition(id, bd);
            }
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("load bean definition for XML document: "+resource.getDescription()+" failed !", e);
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

}
