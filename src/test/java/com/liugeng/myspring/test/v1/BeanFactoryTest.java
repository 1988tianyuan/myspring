package com.liugeng.myspring.test.v1;

import com.liugeng.myspring.beans.BeanDefinition;
import com.liugeng.myspring.beans.BeansException;
import com.liugeng.myspring.beans.factory.BeanCreationException;
import com.liugeng.myspring.beans.factory.BeanDefinitionStoreException;
import com.liugeng.myspring.beans.factory.BeanFactory;
import com.liugeng.myspring.beans.factory.support.DefaultBeanFactory;
import com.liugeng.myspring.service.v1.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

public class BeanFactoryTest {

    @Test
    public void testGetBean(){
        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
        BeanDefinition bd = factory.getBeanDefinition("petStore");
        Assert.assertEquals("com.liugeng.myspring.service.v1.PetStoreService", bd.getBeanClassName());

        PetStoreService petStoreService = (PetStoreService)factory.getBean("petStore");
        Assert.assertNotNull(petStoreService);
    }

    @Test
    public void testInvalidBean(){
        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
        try {
            factory.getBean("invalidBean");
        } catch (BeanCreationException e) {
            return;
        }
        Assert.fail("期望该代码抛出BeanCreationException");
    }

    @Test
    public void testInvalidBeanDefinition(){
        try {
            new DefaultBeanFactory("dsadad.xml");
        } catch (BeanDefinitionStoreException e) {
            return;
        }
        Assert.fail("期望该代码抛出BeanDefinitionStoreException");
    }


}
