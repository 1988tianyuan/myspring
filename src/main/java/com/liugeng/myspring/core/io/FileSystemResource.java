package com.liugeng.myspring.core.io;

import com.liugeng.myspring.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileSystemResource implements Resource{
    private final String fileSystemPath;
    private final File file;

    public FileSystemResource(String fileSystemPath) {
        Assert.notNull(fileSystemPath, "path must not be null");
        this.fileSystemPath = fileSystemPath;
        file = new File(fileSystemPath);
    }

    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(file);
    }

    public String getDescription() {
        return this.fileSystemPath;
    }
}
