package com.liugeng.myspring.core.type.classreading;

import com.liugeng.myspring.stereotype.AnnotationAttributes;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.SpringAsmInfo;

import java.util.Map;

public class AnnotationAttributeReadingVisitor extends AnnotationVisitor{

    private final String annotationType;
    private final Map<String, AnnotationAttributes> attributesMap;
    private final AnnotationAttributes attributes = new AnnotationAttributes();

    public AnnotationAttributeReadingVisitor(String annotationType, Map<String, AnnotationAttributes> attributesMap){
        super(SpringAsmInfo.ASM_VERSION);
        this.annotationType = annotationType;
        this.attributesMap = attributesMap;
    }

    @Override
    public void visit(String attributeName, Object attributeValue) {
        attributes.put(attributeName, attributeValue);
    }

    @Override
    public void visitEnd() {
        attributesMap.put(annotationType, attributes);
    }
}
