package com.liugeng.myspring.beans.factory.support;

import com.liugeng.myspring.beans.BeanDefinition;
import com.liugeng.myspring.beans.factory.config.ConfigurableBeanFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConstructorResolver {
    protected final Log logger = LogFactory.getLog(ConstructorResolver.class);

    private final ConfigurableBeanFactory factory;

    public ConstructorResolver(DefaultBeanFactory factory) {
        this.factory = factory;
    }

    public Object autowireConstructor(BeanDefinition beanDefinition) {

        return null;
    }
}
