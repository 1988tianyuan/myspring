package com.liugeng.myspring.test.v4;

import com.liugeng.myspring.core.io.ClassPathResource;
import com.liugeng.myspring.core.type.AnnotationMetadata;
import com.liugeng.myspring.core.type.classreading.MetadataReader;
import com.liugeng.myspring.core.type.classreading.SimpleMetadataReader;
import com.liugeng.myspring.stereotype.AnnotationAttributes;
import com.liugeng.myspring.stereotype.Component;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class MetadataReaderTest {

    @Test
    public void getMetadataTest() throws IOException{
        ClassPathResource resource = new ClassPathResource("com/liugeng/myspring/service/v4/PetStoreService.class");
        MetadataReader reader = new SimpleMetadataReader(resource);
        AnnotationMetadata annotationMetadata = reader.getAnnotationMetadata();

        String annotation = Component.class.getName();
        Assert.assertTrue(annotationMetadata.hasAnnotation(annotation));

        AnnotationAttributes annotationAttributes = annotationMetadata.getAnnotationAttributes(annotation);
        Assert.assertEquals("petStore", annotationAttributes.get("value"));
        Assert.assertFalse(annotationMetadata.isAbstract());
        Assert.assertFalse(annotationMetadata.isFinal());
        Assert.assertEquals("com.liugeng.myspring.service.v4.PetStoreService", annotationMetadata.getClassName());
    }
}
