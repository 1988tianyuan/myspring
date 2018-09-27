package com.liugeng.myspring.test.v3;

import com.liugeng.myspring.beans.BeanDefinition;
import com.liugeng.myspring.beans.ConstructorArgument;
import com.liugeng.myspring.beans.factory.config.RuntimeBeanReference;
import com.liugeng.myspring.beans.factory.config.TypeStringValue;
import com.liugeng.myspring.beans.factory.support.DefaultBeanFactory;
import com.liugeng.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import com.liugeng.myspring.core.io.ClassPathXmlResource;
import com.liugeng.myspring.dao.v3.AccountDao;
import com.liugeng.myspring.dao.v3.ItemDao;
import com.liugeng.myspring.util.ClassUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

public class BeanDefinitionTestV3 {
    private Properties paths;

    @Before
    public void setup() throws IOException {
        this.paths = new Properties();
        paths.load(new InputStreamReader(ClassUtils.getDefaultClassLoader().getResourceAsStream("path-v3.properties"), "GBK"));
    }

    @Test
    public void testConstructorArgument(){
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinition(new ClassPathXmlResource(paths.getProperty("classpath")));
        BeanDefinition beanDefinition = factory.getBeanDefinition("petStore");
        Assert.assertEquals("com.liugeng.myspring.service.v3.PetStoreService", beanDefinition.getBeanClassName());

        ConstructorArgument args = beanDefinition.getConstructorArgument();
        List<ConstructorArgument.ValueHolder> valueHolders = args.getArgumentValues();
        Assert.assertEquals(valueHolders.size(), 4);

        RuntimeBeanReference reference1 = (RuntimeBeanReference) valueHolders.get(0).getValue();
        Assert.assertEquals(reference1.getBeanName(), "accountDao");
        RuntimeBeanReference reference2 = (RuntimeBeanReference) valueHolders.get(1).getValue();
        Assert.assertEquals(reference2.getBeanName(), "itemDao");
        TypeStringValue strValue1 = (TypeStringValue) valueHolders.get(2).getValue();
        Assert.assertEquals(strValue1.getValue(), "test");
        TypeStringValue strValue2 = (TypeStringValue) valueHolders.get(3).getValue();
        Assert.assertEquals(strValue2.getValue(), "1");
    }


}
