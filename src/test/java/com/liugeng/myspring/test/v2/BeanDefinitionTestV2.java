package com.liugeng.myspring.test.v2;

import com.liugeng.myspring.beans.BeanDefinition;
import com.liugeng.myspring.beans.PropertyValue;
import com.liugeng.myspring.beans.factory.BeanFactory;
import com.liugeng.myspring.beans.factory.config.RuntimeBeanReference;
import com.liugeng.myspring.beans.factory.support.DefaultBeanFactory;
import com.liugeng.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import com.liugeng.myspring.core.io.ClassPathXmlResource;
import com.liugeng.myspring.util.ClassUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

public class BeanDefinitionTestV2 {
    private Properties paths;

    @Before
    public void setup() throws IOException {
        this.paths = new Properties();
        paths.load(new InputStreamReader(ClassUtils.getDefaultClassLoader().getResourceAsStream("path-v2.properties"), "GBK"));
    }

    @Test
    public void testGetBeanDefinition() throws Exception{
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinition(new ClassPathXmlResource(paths.getProperty("classpath")));
        BeanDefinition beanDefinition = factory.getBeanDefinition("petStore");

        List<PropertyValue> propertyValueList = beanDefinition.getPropertyValues();

        Assert.assertTrue(propertyValueList.size() == 4);

        PropertyValue propertyValue1 = this.getPropertyValue(propertyValueList, "accountDao");
        Assert.assertNotNull(propertyValue1);
        Assert.assertNotNull(propertyValue1.getValue() instanceof RuntimeBeanReference);

        PropertyValue propertyValue2 = this.getPropertyValue(propertyValueList, "itemDao");
        Assert.assertNotNull(propertyValue2);
        Assert.assertNotNull(propertyValue2.getValue() instanceof RuntimeBeanReference);
    }

    private PropertyValue getPropertyValue(List<PropertyValue> propertyValueList, String propertyName) {
        for(PropertyValue propertyValue : propertyValueList){
            if(propertyValue.getName().equals(propertyName)){
                return propertyValue;
            }
        }
        return null;
    }
}
