package com.liugeng.myspring.beans.factory.xml;

import com.liugeng.myspring.beans.BeanDefinition;
import com.liugeng.myspring.beans.PropertyValue;
import com.liugeng.myspring.beans.factory.BeanDefinitionStoreException;
import com.liugeng.myspring.beans.factory.config.RuntimeBeanReference;
import com.liugeng.myspring.beans.factory.config.TypeStringValue;
import com.liugeng.myspring.beans.factory.support.BeanDefinitionRegister;
import com.liugeng.myspring.beans.factory.support.GenericBeanDefinition;
import com.liugeng.myspring.core.io.Resource;
import com.liugeng.myspring.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.print.attribute.standard.NumberUp;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.logging.Logger;

public class XmlBeanDefinitionReader {
    private static final String ID_ATTRIBUTE = "id";
    private static final String CLASS_ATTRIBUTE = "class";
    private static final String SCOPE_ATTRIBUTE = "scope";
    private static final String PROPERTY_ELEMENT = "property";
    private static final String REF_ATTRIBUTE ="ref";
    private static final String VALUE_ATTRIBUTE = "value";
    private static final String NAME_ATTRIBUTE = "name";
    private BeanDefinitionRegister register;

    public XmlBeanDefinitionReader(BeanDefinitionRegister register) {
        this.register = register;
    }

    protected final Log logger = LogFactory.getLog(XmlBeanDefinitionReader.class);

    /**
     * 通过xml配置文件解析BeanDefinition
     */
    public void loadBeanDefinition(Resource resource) throws RuntimeException {
        InputStream is = null;
        try {
            is = resource.getInputStream();
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);
            Element root = doc.getRootElement();    //获取根元素（也就是beans列表）
            Iterator<Element> iter = root.elementIterator();
            while (iter.hasNext()){
                Element element = iter.next();
                String id = element.attributeValue(ID_ATTRIBUTE);
                String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);
                String scope = element.attributeValue(SCOPE_ATTRIBUTE);
                BeanDefinition bd = new GenericBeanDefinition(id, beanClassName);
                if(scope != null) {
                    bd.setScope(scope);
                }   //读取scope属性并存入beanDefinition中
                parsePropertyValue(element, bd);
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

    //解析当前Element节点下面的property子节点，处理两种不同的propertyValue类型：ref和StringValue
    public void parsePropertyValue(Element element, BeanDefinition beanDefinition) throws Exception{
        Iterator iterator = element.elementIterator(PROPERTY_ELEMENT);
        while (iterator.hasNext()){
            Element propElement = (Element) iterator.next();
            String propName = propElement.attributeValue(NAME_ATTRIBUTE);

            if(!StringUtils.hasText(propName)){
                logger.fatal("property name must not be null");
            }
            // 通过属性name，构造一个PropertyValue，放入当前beanDefinition中
            // 其中实际保存的value值要么是RuntimeBeanReference，对应某个Bean
            // 要么是TypeStringValue，对应一个具体的String值
            Object propValue = parsePropertyValue(propElement, beanDefinition, propName);
            PropertyValue property = new PropertyValue(propName, propValue);
            beanDefinition.getPropertyValues().add(property);
        }
    }

    //解析property节点，处理两种不同的propertyValue类型：ref和StringValue
    private Object parsePropertyValue (Element propElement, BeanDefinition beanDefinition, String propName) throws Exception{
        String elementName = propName != null ? "<property> element for property '" + propName + "'" : "<constructor-arg> element";
        boolean hasRefAttribute = propElement.attribute(REF_ATTRIBUTE) != null;
        boolean hasValueAttribute = propElement.attribute(VALUE_ATTRIBUTE) != null;

        if(hasRefAttribute){
            String refName = propElement.attributeValue(REF_ATTRIBUTE);
            if(StringUtils.hasText(refName)){
                logger.fatal(elementName + " contains empty 'ref' attribute");
            }
            return new RuntimeBeanReference(refName);
        } else if (hasValueAttribute){
            String value = propElement.attributeValue(VALUE_ATTRIBUTE);
            return new TypeStringValue(value);
        } else {
            throw new Exception(elementName + " must specify a ref or value");
        }
    }

}
