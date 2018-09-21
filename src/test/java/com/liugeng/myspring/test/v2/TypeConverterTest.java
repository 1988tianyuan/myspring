package com.liugeng.myspring.test.v2;

import com.liugeng.myspring.beans.SimpleTypeConverter;
import com.liugeng.myspring.beans.TypeConverter;
import com.liugeng.myspring.beans.TypeMismatchException;
import org.junit.Assert;
import org.junit.Test;

public class TypeConverterTest {

    @Test
    public void testConvertStringToInt() throws TypeMismatchException{
        TypeConverter converter = new SimpleTypeConverter();
        Integer i = converter.convertIfNecessary("2", Integer.class);
        Assert.assertEquals(2, i.intValue());
        try {
            converter.convertIfNecessary("2.34", Integer.class);
        } catch (TypeMismatchException e) {
            return;
        }
        Assert.fail();
    }

    @Test
    public void testConvertStringToBoolean() throws TypeMismatchException{
        TypeConverter converter = new SimpleTypeConverter();
        Boolean b = converter.convertIfNecessary("no", Boolean.class);
        Assert.assertEquals(false, b.booleanValue());
        try {
            converter.convertIfNecessary("dsada", Boolean.class);
        } catch (TypeMismatchException e) {
            return;
        }
        Assert.fail();
    }
}
