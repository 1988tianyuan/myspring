package com.liugeng.myspring.beans;

import java.util.LinkedList;
import java.util.List;

/**
 * 用来保存某个类的构造函数参数的定义信息
 */
public class ConstructorArgument {
    private final List<ValueHolder> argumentValues = new LinkedList<>();

    public ConstructorArgument() {
    }

    public void addArgumentValue(ValueHolder valueHolder){
        argumentValues.add(valueHolder);
    }

    public List<ValueHolder> getArgumentValues(){
        return this.argumentValues;
    }

    public void clear(){
        argumentValues.clear();
    }

    public int getArgumentCount(){
        return argumentValues.size();
    }

    public boolean isEmpty(){
        return argumentValues.isEmpty();
    }

    public static class ValueHolder{
        private String name;
        private String type;
        private Object value;

        public ValueHolder(Object value) {
            this.value = value;
        }

        public ValueHolder(String type, Object value) {
            this.type = type;
            this.value = value;
        }

        public ValueHolder(String name, String type, Object value) {
            this.name = name;
            this.type = type;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}
