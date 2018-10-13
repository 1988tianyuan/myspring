package com.liugeng.myspring.test.v4;

import com.liugeng.myspring.core.io.Resource;
import com.liugeng.myspring.core.io.support.PackageResourceLoader;
import com.sun.javafx.scene.shape.PathUtils;
import org.junit.Assert;
import org.junit.Test;
import sun.net.www.ParseUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class PackageResourceLoaderTest {

    @Test
    public void testGetResource() {
        PackageResourceLoader loader = new PackageResourceLoader();
        Resource[] resources = loader.getResources("com.liugeng.myspring.dao.v4");
        Assert.assertEquals(2, resources.length);
    }
}
