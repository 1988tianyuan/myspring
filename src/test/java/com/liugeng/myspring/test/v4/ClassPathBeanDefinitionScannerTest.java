package com.liugeng.myspring.test.v4;

import com.liugeng.myspring.beans.BeanDefinition;
import com.liugeng.myspring.beans.factory.support.DefaultBeanFactory;
import com.liugeng.myspring.context.annotation.ClassPathBeanDefinitionScanner;
import com.liugeng.myspring.stereotype.Component;
import org.junit.Assert;
import org.junit.Test;

public class ClassPathBeanDefinitionScannerTest {

    @Test
    public void test(){
        DefaultBeanFactory factory = new DefaultBeanFactory();
        String basePackages = "com.liugeng.myspring.service.v4, com.liugeng.myspring.dao.v4";
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(factory);
        scanner.doScan(basePackages);

        String annotation = Component.class.getName();

        BeanDefinition beanDefinition = factory.getBeanDefinition("petStore");
        Assert.assertTrue(beanDefinition instanceof ScannedGenericBeanDefinition);
        ScannedGenericBeanDefinition scannedGenericBeanDefinition = (ScannedGenericBeanDefinition) beanDefinition;

    }
}
