package com.liugeng.myspring.test.v3;

import com.liugeng.myspring.beans.BeanDefinition;
import com.liugeng.myspring.beans.factory.support.ConstructorResolver;
import com.liugeng.myspring.beans.factory.support.DefaultBeanFactory;
import com.liugeng.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import com.liugeng.myspring.core.io.ClassPathXmlResource;
import com.liugeng.myspring.dao.v3.AccountDao;
import com.liugeng.myspring.dao.v3.ItemDao;
import com.liugeng.myspring.service.v3.PetStoreService;
import com.liugeng.myspring.util.ClassUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class ConstructorResolverTest {
    private Properties paths;

    @Before
    public void setup() throws IOException {
        this.paths = new Properties();
        paths.load(new InputStreamReader(ClassUtils.getDefaultClassLoader().getResourceAsStream("path-v3.properties"), "GBK"));
    }

    @Test
    public void testAutowireConstructor(){
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinition(new ClassPathXmlResource(paths.getProperty("classpath")));
        BeanDefinition beanDefinition = factory.getBeanDefinition("petStore");

        ConstructorResolver resolver = new ConstructorResolver(factory);
        PetStoreService service = (PetStoreService)resolver.autowireConstructor(beanDefinition);

        Assert.assertEquals(service.getVersion(), 1);
        Assert.assertTrue(service.getAccountDao() instanceof AccountDao);
        Assert.assertTrue(service.getItemDao() instanceof ItemDao);
        Assert.assertEquals(service.getEnv(), "test");
    }

}
