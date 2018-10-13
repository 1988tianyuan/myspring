package com.liugeng.myspring.test.v4;

import com.liugeng.myspring.context.ApplicationContext;
import com.liugeng.myspring.context.support.ClassPathXmlApplicationContext;
import com.liugeng.myspring.service.v4.PetStoreService;
import com.liugeng.myspring.util.ClassUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class ApplicationContextTestV4 {
    private Properties paths;

    @Before
    public void setup() throws IOException {
        this.paths = new Properties();
        paths.load(new InputStreamReader(ClassUtils.getDefaultClassLoader().getResourceAsStream("path-v4.properties"), "GBK"));
    }

    @Test
    public void testGetBeanProperty() throws FileNotFoundException{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(paths.getProperty("classpath"));
        PetStoreService petStoreService = (PetStoreService) applicationContext.getBean("petStore");
        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertNotNull(petStoreService.getItemDao());
    }
}
