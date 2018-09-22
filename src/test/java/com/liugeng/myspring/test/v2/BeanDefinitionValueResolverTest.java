package com.liugeng.myspring.test.v2;


import com.liugeng.myspring.beans.SimpleTypeConverter;
import com.liugeng.myspring.beans.TypeConverter;
import com.liugeng.myspring.beans.factory.config.RuntimeBeanReference;
import com.liugeng.myspring.beans.factory.config.TypeStringValue;
import com.liugeng.myspring.beans.factory.support.BeanDefinitionValueResolver;
import com.liugeng.myspring.beans.factory.support.DefaultBeanFactory;
import com.liugeng.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import com.liugeng.myspring.core.io.ClassPathXmlResource;
import com.liugeng.myspring.dao.v2.AccountDao;
import com.liugeng.myspring.util.ClassUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class BeanDefinitionValueResolverTest {
    private Properties paths;

    @Before
    public void setup() throws IOException {
        this.paths = new Properties();
        paths.load(new InputStreamReader(ClassUtils.getDefaultClassLoader().getResourceAsStream("path-v2.properties"), "GBK"));
    }

    @Test
    public void resolveRuntimeBeanReference() throws Exception{
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinition(new ClassPathXmlResource(paths.getProperty("classpath")));

        //将BeanFactory装载到resolver中，使其可以将某个PropertyValue的value引用直接转换为具体的Bean引用
        TypeConverter converter = new SimpleTypeConverter();
        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory, converter);
        RuntimeBeanReference reference = new RuntimeBeanReference("accountDao");
        Object value = resolver.resolveValueIfNecessary(reference, AccountDao.class);
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof AccountDao);
    }

    @Test
    public void resolveTypeStringValue() throws Exception{
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinition(new ClassPathXmlResource(paths.getProperty("classpath")));

        TypeConverter converter = new SimpleTypeConverter();
        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory, converter);
        TypeStringValue stringValue = new TypeStringValue("test");
        Object value = resolver.resolveValueIfNecessary(stringValue, String.class);
        Assert.assertNotNull(value);
        Assert.assertTrue("test".equals(value));
    }
}
