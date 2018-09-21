package com.liugeng.myspring.beans;

import com.liugeng.myspring.beans.propertyediors.CustomBooleanEditor;
import com.liugeng.myspring.beans.propertyediors.CustomNumberEditor;
import com.liugeng.myspring.util.ClassUtils;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

public class SimpleTypeConverter implements TypeConverter {

    private final Map<Class<?>, PropertyEditor> defaultEditors = new HashMap<>();

    public SimpleTypeConverter(){

    }

    @Override
    public <T> T convertIfNecessary(Object value, Class<T> requireType) throws TypeMismatchException {
        if(ClassUtils.isAssignableValue(requireType, value)){
            return (T)value;
        } else {
            if(value instanceof String){
                PropertyEditor editor = this.findDefaultEditor(requireType);
                try {
                    editor.setAsText((String) value);
                } catch (IllegalArgumentException e) {
                    throw new TypeMismatchException(value, requireType);
                }
                return (T)editor.getValue();
            }else {
                throw new TypeMismatchException(value, requireType);
            }
        }
    }

    private PropertyEditor findDefaultEditor(Class<?> requireType){
        PropertyEditor editor = this.getDefaultEditor(requireType);
        if (editor == null){
            throw new RuntimeException("Editor for " + requireType + " has not been implemented.");
        }
        return editor;
    }

    private PropertyEditor getDefaultEditor(Class<?> requireType){
        if(this.defaultEditors.isEmpty()){
            this.initDefaultEditors();
        }
        return this.defaultEditors.get(requireType);
    }

    private void initDefaultEditors(){
        defaultEditors.put(Integer.class, new CustomNumberEditor(Integer.class, true));
        defaultEditors.put(int.class, new CustomNumberEditor(Integer.class, true));
        defaultEditors.put(Long.class, new CustomNumberEditor(Long.class, true));
        defaultEditors.put(long.class, new CustomNumberEditor(Long.class, true));
        defaultEditors.put(Double.class, new CustomNumberEditor(Double.class, true));
        defaultEditors.put(double.class, new CustomNumberEditor(Double.class, true));
        defaultEditors.put(Float.class, new CustomNumberEditor(Float.class, true));
        defaultEditors.put(float.class, new CustomNumberEditor(Float.class, true));
        defaultEditors.put(Short.class, new CustomNumberEditor(Short.class, true));
        defaultEditors.put(short.class, new CustomNumberEditor(Short.class, true));
        defaultEditors.put(Boolean.class, new CustomBooleanEditor(true));
    }
}
