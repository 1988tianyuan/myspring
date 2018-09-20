package com.liugeng.myspring.test.v2;


import com.liugeng.myspring.context.ApplicationContext;
import com.liugeng.myspring.context.support.ClassPathXmlApplicationContext;
import com.liugeng.myspring.dao.v2.AccountDao;
import com.liugeng.myspring.dao.v2.ItemDao;
import com.liugeng.myspring.service.v2.PetStoreService;
import com.liugeng.myspring.util.ClassUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class ApplicationContextTestV2 {
    private Properties paths;

    @Before
    public void setup() throws IOException{
        this.paths = new Properties();
        paths.load(new InputStreamReader(ClassUtils.getDefaultClassLoader().getResourceAsStream("path-v2.properties"), "GBK"));
    }

    @Test
    public void testGetBeanProperty() throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(paths.getProperty("classpath"));
        PetStoreService petStoreService = (PetStoreService) applicationContext.getBean("petStore");
        Assert.assertNotNull(petStoreService);
        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertTrue(petStoreService.getAccountDao() instanceof AccountDao);
        Assert.assertNotNull(petStoreService.getItemDao());
        Assert.assertTrue(petStoreService.getItemDao() instanceof ItemDao);
        Assert.assertEquals("test", petStoreService.getEnv());
        Assert.assertEquals(1,  petStoreService.getVersion());
    }
}
