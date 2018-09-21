package com.liugeng.myspring.beans;

public interface TypeConverter {
    <T> T convertIfNecessary(Object value, Class<T> requireType) throws TypeMismatchException;
}
