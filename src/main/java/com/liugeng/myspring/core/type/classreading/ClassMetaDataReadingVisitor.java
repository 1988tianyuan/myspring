package com.liugeng.myspring.core.type.classreading;

import com.liugeng.myspring.core.type.ClassMetadata;
import com.liugeng.myspring.util.ClassUtils;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.Opcodes;
import org.springframework.asm.SpringAsmInfo;

public class ClassMetaDataReadingVisitor extends ClassVisitor implements ClassMetadata{

    private boolean isAbstract;
    private boolean isInterface;
    private boolean isFinal;
    private boolean isAnnotation;
    private String className;
    private String superClassName;
    private String[] interfaces;

    public ClassMetaDataReadingVisitor(){
        super(SpringAsmInfo.ASM_VERSION);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className = ClassUtils.convertResourcePathtoClassName(name);
        this.isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
        this.isAbstract = (access & Opcodes.ACC_ABSTRACT) != 0;
        this.isFinal = (access & Opcodes.ACC_FINAL) != 0;
        this.isAnnotation = (access & Opcodes.ACC_ANNOTATION) != 0;

        if(superName != null){
            this.superClassName = ClassUtils.convertResourcePathtoClassName(superName);
        }
        this.interfaces = new String[interfaces.length];
        for(int i = 0; i < interfaces.length; i++){
            this.interfaces[i] = ClassUtils.convertResourcePathtoClassName(interfaces[i]);
        }
    }



    @Override
    public boolean isAbstract() {
        return isAbstract;
    }

    @Override
    public boolean isInterface() {
        return isInterface;
    }

    @Override
    public boolean isAnnotation() {
        return isAnnotation;
    }

    public void setInterface(boolean anInterface) {
        isInterface = anInterface;
    }

    @Override
    public boolean isFinal() {
        return isFinal;
    }

    @Override
    public boolean hasSuperClass() {
        return superClassName != null;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public String getSuperClassName() {
        return superClassName;
    }

    @Override
    public String[] getInterfaceNames() {
        return interfaces;
    }




}
