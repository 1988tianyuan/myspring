package com.liugeng.myspring.test.v3;

import com.liugeng.myspring.context.ApplicationContext;
import com.liugeng.myspring.context.support.ClassPathXmlApplicationContext;
import com.liugeng.myspring.dao.v3.AccountDao;
import com.liugeng.myspring.service.v3.PetStoreService;
import com.liugeng.myspring.util.ClassUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class ApplicationContextTestV3 {
    private Properties paths;

    @Before
    public void setup() throws IOException{
        this.paths = new Properties();
        paths.load(new InputStreamReader(ClassUtils.getDefaultClassLoader().getResourceAsStream("path-v3.properties"), "GBK"));
    }

    @Test
    public void testGetBean() throws FileNotFoundException{
        ApplicationContext context = new ClassPathXmlApplicationContext(paths.getProperty("classpath"));
        PetStoreService petStoreService = (PetStoreService)context.getBean("petStore");
        Assert.assertNotNull(petStoreService);
        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertTrue(petStoreService.getVersion() == 1);
    }
}
