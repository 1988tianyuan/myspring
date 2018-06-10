package com.liugeng.myspring.util;

//这个Util是Spring默认的ClassUtils
public class ClassUtils {

    //获取类加载器
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader classLoader = null;
        try {
            classLoader = Thread.currentThread().getContextClassLoader();
        }catch (Throwable e){
            //无法获取当前线程的上下文ClassLoader，就回滚
            e.printStackTrace();
        }
        if(classLoader == null){
            classLoader = ClassUtils.class.getClassLoader();
            if(classLoader == null){
                try {
                    classLoader = ClassLoader.getSystemClassLoader();
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
        }
        return classLoader;
    }
}
