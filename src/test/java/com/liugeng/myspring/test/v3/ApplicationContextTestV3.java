package com.liugeng.myspring.test.v3;

import com.liugeng.myspring.util.ClassUtils;
import org.junit.Before;
import org.junit.Test;

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
    public void testGetBean(){

    }
}
