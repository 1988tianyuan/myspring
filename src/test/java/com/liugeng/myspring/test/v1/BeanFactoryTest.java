package com.liugeng.myspring.test.v1;

import com.liugeng.myspring.beans.BeanDefinition;
import com.liugeng.myspring.beans.factory.BeanCreationException;
import com.liugeng.myspring.beans.factory.BeanDefinitionStoreException;
import com.liugeng.myspring.beans.factory.support.DefaultBeanFactory;
import com.liugeng.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import com.liugeng.myspring.core.io.ClassPathResource;
import com.liugeng.myspring.core.io.Resource;
import com.liugeng.myspring.service.v1.PetStoreService;
import com.liugeng.myspring.util.ClassUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class BeanFactoryTest {

    private DefaultBeanFactory factory;
    private XmlBeanDefinitionReader reader;
    private Properties paths;

    @Before
    public void setup() throws IOException {
        this.factory = new DefaultBeanFactory();
        this.reader = new XmlBeanDefinitionReader(factory);
        this.paths = new Properties();
        paths.load(new InputStreamReader(ClassUtils.getDefaultClassLoader().getResourceAsStream("path.properties"), "GBK"));
    }

    @Test
    public void testGetBean() throws FileNotFoundException {
        Resource resource = new ClassPathResource(paths.getProperty("classpath"));
        reader.loadBeanDefinition(resource);
        BeanDefinition bd = factory.getBeanDefinition("petStore");

        Assert.assertFalse(bd.isPrototype());
        Assert.assertTrue(bd.isSingleton());
        Assert.assertEquals(BeanDefinition.SCOPE_SINGLETON, bd.getScope());
        Assert.assertEquals("com.liugeng.myspring.service.v1.PetStoreService", bd.getBeanClassName());

        PetStoreService petStoreService = (PetStoreService)factory.getBean("petStore");
        Assert.assertNotNull(petStoreService);

        PetStoreService petStoreService1 = (PetStoreService)factory.getBean("petStore");
        Assert.assertTrue(petStoreService == petStoreService1 );
    }

    @Test
    public void testInvalidBean() {
        Resource resource = new ClassPathResource(paths.getProperty("classpath"));
        reader.loadBeanDefinition(resource);
        try {
            factory.getBean("invalidBean");
        } catch (BeanCreationException e) {
            return;
        }
        Assert.fail("期望该代码抛出BeanCreationException");
    }

    @Test
    public void testInvalidBeanDefinition() {
        try {
            Resource resource = new ClassPathResource(paths.getProperty("classpath1"));
            reader.loadBeanDefinition(resource);
        } catch (BeanDefinitionStoreException e) {
            return;
        }
        Assert.fail("期望该代码抛出BeanDefinitionStoreException");
    }


}
