package com.liugeng.myspring.test.v4;

import com.liugeng.myspring.core.io.ClassPathResource;
import com.liugeng.myspring.core.type.classreading.AnnotationAttributeReadingVisitor;
import com.liugeng.myspring.core.type.classreading.AnnotationMetadataReadingVisitor;
import com.liugeng.myspring.core.type.classreading.ClassMetaDataReadingVisitor;
import com.liugeng.myspring.stereotype.AnnotationAttributes;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.asm.ClassReader;

import java.io.IOException;

public class ClassReaderTest {

    @Test
    public void classMetaDataTest() throws IOException{
        ClassPathResource resource = new ClassPathResource("com/liugeng/myspring/service/v4/PetStoreService.class");
        ClassReader reader = new ClassReader(resource.getInputStream());
        ClassMetaDataReadingVisitor visitor = new ClassMetaDataReadingVisitor();
        reader.accept(visitor, ClassReader.SKIP_DEBUG);

        Assert.assertFalse(visitor.isAbstract());
        Assert.assertFalse(visitor.isInterface());
        Assert.assertEquals("com.liugeng.myspring.service.v4.PetStoreService", visitor.getClassName());
        Assert.assertEquals("java.lang.Object", visitor.getSuperClassName());
        Assert.assertEquals(0, visitor.getInterfaceNames().length);
    }

    @Test
    public void annotationTest() throws IOException{
        ClassPathResource resource = new ClassPathResource("com/liugeng/myspring/service/v4/PetStoreService.class");
        ClassReader reader = new ClassReader(resource.getInputStream());
        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
        reader.accept(visitor, ClassReader.SKIP_DEBUG);
        String annotation = "com.liugeng.myspring.stereotype.Component";
        Assert.assertTrue(visitor.hasAnnotation(annotation));

        AnnotationAttributes attributes = visitor.getAnnotationAttributes(annotation);
        Assert.assertEquals("petStore", attributes.get("value"));
    }
}
