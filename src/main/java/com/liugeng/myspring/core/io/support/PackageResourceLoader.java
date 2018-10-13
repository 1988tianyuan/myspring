package com.liugeng.myspring.core.io.support;

import com.liugeng.myspring.core.io.FileSystemResource;
import com.liugeng.myspring.core.io.Resource;
import com.liugeng.myspring.util.Assert;
import com.liugeng.myspring.util.ClassUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.net.www.ParseUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class PackageResourceLoader {

    private final ClassLoader classLoader;
    private static final Log logger = LogFactory.getLog(PackageResourceLoader.class);

    public PackageResourceLoader() {
        this.classLoader = ClassUtils.getDefaultClassLoader();
    }

    public PackageResourceLoader(ClassLoader classLoader) {
        Assert.notNull(classLoader, "ResourceLoader should not be null!");
        this.classLoader = classLoader;
    }

    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    public Resource[] getResources(String basePackage) {
        Assert.notNull(basePackage, "basePackage should not be null!");
        String location = ClassUtils.convertClassNameToResourcePath(basePackage);
        ClassLoader classLoader = getClassLoader();
        URL url = classLoader.getResource(location);
        // need to decode the url which has non-latin character
        String filePath = ParseUtil.decode(url.getFile());
        File rootDir = new File(filePath);
        Set<File> matchingFiles = retrieveMatchingFiles(rootDir);
        Resource[] resource = new Resource[matchingFiles.size()];
        int i = 0;
        for(File file : matchingFiles){
            resource[i] = new FileSystemResource(file);
            i++;
        }
        return resource;
    }

    protected Set<File> retrieveMatchingFiles(File rootDir) {
        if(!rootDir.exists()){
            if(logger.isDebugEnabled()){
                logger.debug("Skipping [" + rootDir.getAbsolutePath() + "] because it doesn't exist.");
            }
            return Collections.emptySet();
        }
        if(!rootDir.isDirectory()){
            if(logger.isWarnEnabled()){
                logger.warn("Skipping [" + rootDir.getAbsolutePath() + "] because it is not a directory.");
            }
            return Collections.emptySet();
        }
        if(!rootDir.canRead()){
            if (logger.isWarnEnabled()) {
                logger.warn("Cannot search for matching files underneath directory [" + rootDir.getAbsolutePath() +
                        "] because the application is not allowed to read the directory");
            }
            return Collections.emptySet();
        }
        Set<File> fileSet = new LinkedHashSet<>();
        doRetrieveMatchingFiles(rootDir, fileSet);
        return fileSet;
    }

    protected void doRetrieveMatchingFiles(File rootDir, Set<File> fileSet) {
        File[] dirContents = rootDir.listFiles();
        if(dirContents == null){
            if (logger.isWarnEnabled()) {
                logger.warn("Cannot retrieve contents of directory: [" + rootDir.getAbsolutePath() + "]");
            }
            return;
        }
        for(File file : dirContents){
            if(file.isDirectory()){
                if(!file.canRead()){
                    if (logger.isDebugEnabled()) {
                        logger.debug("Skipping subdirectory [" + rootDir.getAbsolutePath() +
                                "] because the application is not allowed to read the directory");
                    }else {
                        doRetrieveMatchingFiles(file, fileSet);
                    }
                }
            }else {
                fileSet.add(file);
            }
        }
    }
}
