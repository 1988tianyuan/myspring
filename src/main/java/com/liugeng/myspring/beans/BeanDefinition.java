package com.liugeng.myspring.beans;

public interface BeanDefinition {

    public static final String SCOPE_DEFAULT = "";
    public static final String SCOPE_SINGLETON = "singleton";
    public static final String SCOPE_PROTOTYPE = "prototype";

    String getBeanClassName();

    boolean isSingleton();

    boolean isPrototype();

    String getScope();

    void setScope(String scope);
}
