package com.liugeng.myspring.test.v2;

import com.liugeng.myspring.beans.propertyediors.CustomBooleanEditor;
import org.junit.Assert;
import org.junit.Test;

public class CustomBooleanEditorTest {

    @Test
    public void test(){
        CustomBooleanEditor editor = new CustomBooleanEditor(true);

        editor.setAsText("true");
        Assert.assertEquals(true, ((Boolean) editor.getValue()).booleanValue());
        editor.setAsText("false");
        Assert.assertEquals(false, ((Boolean) editor.getValue()).booleanValue());
        editor.setAsText("on");
        Assert.assertEquals(true, ((Boolean) editor.getValue()).booleanValue());
        editor.setAsText("off");
        Assert.assertEquals(false, ((Boolean) editor.getValue()).booleanValue());
        editor.setAsText("yes");
        Assert.assertEquals(true, ((Boolean) editor.getValue()).booleanValue());
        editor.setAsText("no");
        Assert.assertEquals(false, ((Boolean) editor.getValue()).booleanValue());

        try{
            editor.setAsText("we21");
        } catch (IllegalArgumentException e){
            return;
        }
        Assert.fail();
    }
}
