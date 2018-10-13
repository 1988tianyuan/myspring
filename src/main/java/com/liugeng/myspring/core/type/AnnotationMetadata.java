package com.liugeng.myspring.core.type;

import com.liugeng.myspring.stereotype.AnnotationAttributes;

import java.util.Set;

public interface AnnotationMetadata extends ClassMetadata{

    boolean hasAnnotation(String annotation);

    Set<String> getAnnotationTypes();

    AnnotationAttributes getAnnotationAttributes(String annotationName);
}
