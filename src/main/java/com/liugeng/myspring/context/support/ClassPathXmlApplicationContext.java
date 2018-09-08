package com.liugeng.myspring.context.support;

import com.liugeng.myspring.core.io.ClassPathXmlResource;
import com.liugeng.myspring.core.io.Resource;

import java.io.FileNotFoundException;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    public ClassPathXmlApplicationContext(String config) throws FileNotFoundException {
        super(config);
    }

    @Override
    public Resource getResource(String config) {
        return new ClassPathXmlResource(config, getBeanClassLoader());
    }
}
