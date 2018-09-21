package com.liugeng.myspring.test.v2;

import com.liugeng.myspring.beans.propertyediors.CustomNumberEditor;
import org.junit.Assert;
import org.junit.Test;

public class CustomNumberEditorTest {

    @Test
    public void test(){
        CustomNumberEditor editor = new CustomNumberEditor(Integer.class, true);
        editor.setAsText("3");
        Object value = editor.getValue();
        Assert.assertTrue(value instanceof  Integer);
        Assert.assertEquals(3, ((Integer)editor.getValue()).intValue());

        editor.setAsText("");
        Assert.assertNull(editor.getValue());

        try {
            editor.setAsText("3.1");
            editor.setAsText("3daqf");
        } catch (IllegalArgumentException e) {
            return;
        }
        Assert.fail();
    }
}
