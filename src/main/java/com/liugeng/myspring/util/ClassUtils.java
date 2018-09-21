package com.liugeng.myspring.util;

import java.util.IdentityHashMap;
import java.util.Map;

//这个Util是Spring默认的ClassUtils
public class ClassUtils {
    /**
     * 通过基本数据类型获取包装类型， for example: int.class -> Integer.class.
     */
    private static final Map<Class<?>, Class<?>> primitiveTypeToWrapperMap = new IdentityHashMap<>(8);
    /**
     * 通过包装类型获取基本数据类型， for example: Integer.class -> int.class.
     */
    private static final Map<Class<?>, Class<?>> primitiveWrapperTypeMap = new IdentityHashMap<>(8);

    static {
        primitiveWrapperTypeMap.put(Boolean.class, boolean.class);
        primitiveWrapperTypeMap.put(Byte.class, byte.class);
        primitiveWrapperTypeMap.put(Character.class, char.class);
        primitiveWrapperTypeMap.put(Double.class, double.class);
        primitiveWrapperTypeMap.put(Float.class, float.class);
        primitiveWrapperTypeMap.put(Integer.class, int.class);
        primitiveWrapperTypeMap.put(Long.class, long.class);
        primitiveWrapperTypeMap.put(Short.class, short.class);
        primitiveWrapperTypeMap.forEach((key, value) -> {
            primitiveTypeToWrapperMap.put(value, key);
        });
    }

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

    public static <T> boolean isAssignableValue(Class<T> type, Object value) {
        Assert.notNull(type, "Type must not be null");
        return (value != null ? isAssignable(type, value.getClass()) : !type.isPrimitive());
    }

    /**
     * 判断两个类型是否分别是相互的基本类型和包装类型
     * @param lhsType
     * @param rhsType
     * @return
     */
    public static boolean isAssignable(Class<?> lhsType, Class<?> rhsType) {
        Assert.notNull(lhsType, "Left-hand side type must not be null");
        Assert.notNull(rhsType, "Right-hand side type must not be null");
        if (lhsType.isAssignableFrom(rhsType)) {
            return true;
        }
        if (lhsType.isPrimitive()) {
            Class<?> resolvedPrimitive = primitiveWrapperTypeMap.get(rhsType);
            if (lhsType == resolvedPrimitive) {
                return true;
            }
        }
        else {
            Class<?> resolvedWrapper = primitiveTypeToWrapperMap.get(rhsType);
            if (resolvedWrapper != null && lhsType.isAssignableFrom(resolvedWrapper)) {
                return true;
            }
        }
        return false;
    }
}
