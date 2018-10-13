package com.liugeng.myspring.core.type.classreading;

import com.liugeng.myspring.core.io.Resource;
import com.liugeng.myspring.core.type.AnnotationMetadata;
import com.liugeng.myspring.core.type.ClassMetadata;

public interface MetadataReader {
    Resource getResource();

    ClassMetadata getClassMetadata();

    AnnotationMetadata getAnnotationMetadata();
}
