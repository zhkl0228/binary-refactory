package org.hydra.renamer.asm;

import org.hydra.util.Log;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.Remapper;

public class ClassForNameFixMethodVistor extends MethodVisitor {

    private Remapper remapper;

    public ClassForNameFixMethodVistor(MethodVisitor mv, Remapper remapper) {
        super(Opcodes.ASM5, mv);
        
        this.remapper = remapper;
    }

    @Override
    public void visitLdcInsn(Object cst) {
        if (String.class.isInstance(cst)) {
            // 如果是装载一个常量的字符串，检查在不在我们的类名列表里，如果是，就修改掉
            String oldName = String.class.cast(cst).replace('.', '/');
            // Log.debug("visitLdcInsn %s", oldName);
            String newName = remapper.map(oldName);
            if (newName != null && !oldName.equals(newName)) {
                newName = newName.replace('/', '.');
                Log.debug("change constant loading from %s to %s", cst, newName);
                super.visitLdcInsn(newName);
                return;
            }
        }
        super.visitLdcInsn(cst);
    }

}
