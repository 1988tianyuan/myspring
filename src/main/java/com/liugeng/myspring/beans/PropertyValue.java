package com.liugeng.myspring.beans;

public class PropertyValue {
    private final String name;
    private final Object value;
    private boolean converted = false;
    private Object convertValue;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public synchronized boolean isConverted() {
        return converted;
    }

    public synchronized void setConverted(boolean converted) {
        this.converted = converted;
    }

    public Object getConvertValue() {
        return convertValue;
    }

    public void setConvertValue(Object convertValue) {
        this.convertValue = convertValue;
    }
}
