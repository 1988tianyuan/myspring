package com.liugeng.myspring.core.type.classreading;

import com.liugeng.myspring.core.type.AnnotationMetadata;
import com.liugeng.myspring.stereotype.AnnotationAttributes;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.SpringAsmInfo;
import org.springframework.asm.Type;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class AnnotationMetadataReadingVisitor extends ClassMetaDataReadingVisitor implements AnnotationMetadata {

    private final Set<String> annotationSet = new LinkedHashSet<>();
    private final Map<String, AnnotationAttributes> attributesMap= new LinkedHashMap<>();

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        String className = Type.getType(desc).getClassName();
        this.annotationSet.add(className);
        return new AnnotationAttributeReadingVisitor(className, attributesMap);
    }

    public AnnotationAttributes getAnnotationAttributes(String annotation){
        return attributesMap.get(annotation);
    }

    public boolean hasAnnotation(String annotation) {
        return annotationSet.contains(annotation);
    }

    @Override
    public Set<String> getAnnotationTypes() {
        return annotationSet;
    }

}
