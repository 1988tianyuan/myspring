package com.liugeng.myspring.core.io;

import com.liugeng.myspring.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ClassPathXmlResource implements Resource{
    private String classPath;
    private ClassLoader loader;

    public ClassPathXmlResource(String classPath) {
        this(classPath, (ClassLoader)null);
    }

    public ClassPathXmlResource(String classPath, ClassLoader loader) {
        this.classPath = classPath;
        this.loader = loader != null ? loader : ClassUtils.getDefaultClassLoader();
    }

    public InputStream getInputStream() throws FileNotFoundException {
        InputStream is = this.loader.getResourceAsStream(this.classPath);
        if(is == null){
            throw new FileNotFoundException("file: " + this.classPath +" not found");
        }
        return is;
    }

    public String getDescription() {
        return this.classPath;
    }
}
