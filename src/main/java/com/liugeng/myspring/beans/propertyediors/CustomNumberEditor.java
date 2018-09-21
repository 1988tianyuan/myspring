package com.liugeng.myspring.beans.propertyediors;

import com.liugeng.myspring.util.NumberUtils;
import com.liugeng.myspring.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;

/**
 * 用于将字符串转换为指定的Number类型
 */
public class CustomNumberEditor extends PropertyEditorSupport{
    private final Class<? extends Number> numberClass;
    private final NumberFormat numberFormat;
    private final boolean allowEmpty;

    public CustomNumberEditor(Class<? extends Number> numberClass, boolean allowEmpty){
        this(numberClass, null, allowEmpty);
    }

    public CustomNumberEditor(Class<? extends Number> numberClass, NumberFormat numberFormat, boolean allowEmpty) throws IllegalArgumentException{
        if(numberClass == null || !Number.class.isAssignableFrom(numberClass)){
            throw new IllegalArgumentException("Property class must be a subClass of Number.");
        }
        this.numberClass = numberClass;
        this.numberFormat = numberFormat;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if(this.allowEmpty && !StringUtils.hasText(text)){
            this.setValue(null);
        } else if (this.numberFormat != null){
            this.setValue(NumberUtils.parseNumber(text, this.numberClass, this.numberFormat));
        } else {
            this.setValue(NumberUtils.parseNumber(text, this.numberClass));
        }
    }

}
