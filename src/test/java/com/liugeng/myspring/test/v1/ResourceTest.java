package com.liugeng.myspring.test.v1;

import com.liugeng.myspring.core.io.ClassPathResource;
import com.liugeng.myspring.core.io.FileSystemResource;
import com.liugeng.myspring.util.ClassUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class ResourceTest {
    private Properties paths;

    @Before
    public void setup() throws IOException {
        this.paths = new Properties();
        paths.load(new InputStreamReader(ClassUtils.getDefaultClassLoader().getResourceAsStream("path.properties"), "GBK"));
    }

    @Test
    public void classPathXmlResourceTest() throws IOException {
        ClassPathResource resource = new ClassPathResource(paths.getProperty("classpath"));
        InputStream is = null;
        try{
            is = resource.getInputStream();
            Assert.assertNotNull(is);
        }finally {
            if(is != null){
                is.close();
            }
        }
    }

    @Test
    public void fileSystemResourceTest() throws IOException {
        FileSystemResource resource = new FileSystemResource(paths.getProperty("file_system_path"));
        InputStream is = null;
        try{
            is = resource.getInputStream();
            Assert.assertNotNull(is);
        }finally {
            if(is != null){
                is.close();
            }
        }
    }
}
