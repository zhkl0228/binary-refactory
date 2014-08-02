package org.hydra.renamer.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.Remapper;

public class ClassForNameFixVisitor extends ClassVisitor {
    private Remapper remapper;

    public ClassForNameFixVisitor(ClassVisitor cv, Remapper mapper) {
        super(Opcodes.ASM5, cv);
        this.remapper = mapper;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
    	MethodVisitor visitor = super.visitMethod(access, name, desc, signature, exceptions);
        return new ClassForNameFixMethodVistor(visitor, remapper);
    }

}
