package com.liugeng.myspring.util;

public class Assert {
    public static void notNull(Object object, String msg){
        if(object == null){
            throw new IllegalArgumentException(msg);
        }
    }
}
