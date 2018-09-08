package com.liugeng.myspring.test.v1;

import com.liugeng.myspring.context.ApplicationContext;
import com.liugeng.myspring.context.support.ClassPathXmlApplicationContext;
import com.liugeng.myspring.context.support.FileSystemApplicationContext;
import com.liugeng.myspring.service.v1.PetStoreService;
import com.liugeng.myspring.util.ClassUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class ApplicationContextTest {
    private Properties paths;

    @Before
    public void setup() throws IOException {
        this.paths = new Properties();
        paths.load(new InputStreamReader(ClassUtils.getDefaultClassLoader().getResourceAsStream("path.properties"), "GBK"));
    }

    @Test
    public void testGetBean() throws FileNotFoundException {
        ApplicationContext context = new ClassPathXmlApplicationContext(paths.getProperty("classpath"));
        PetStoreService service = (PetStoreService) context.getBean("petStore");
        Assert.assertNotNull(service);
    }

    @Test
    public void testGetBean2() throws FileNotFoundException {
        ApplicationContext context = new FileSystemApplicationContext(paths.getProperty("file_system_path"));
        PetStoreService service = (PetStoreService) context.getBean("petStore");
        Assert.assertNotNull(service);
    }
}
