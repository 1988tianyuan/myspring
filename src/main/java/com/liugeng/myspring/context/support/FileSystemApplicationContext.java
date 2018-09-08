package com.liugeng.myspring.context.support;

import com.liugeng.myspring.core.io.FileSystemResource;
import com.liugeng.myspring.core.io.Resource;

import java.io.FileNotFoundException;

public class FileSystemApplicationContext extends AbstractApplicationContext {

    public FileSystemApplicationContext(String config) throws FileNotFoundException {
        super(config);
    }

    @Override
    public Resource getResource(String config) {
        return new FileSystemResource(config);
    }
}
