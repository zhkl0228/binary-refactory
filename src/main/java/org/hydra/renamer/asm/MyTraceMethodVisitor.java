/**
 * 
 */
package org.hydra.renamer.asm;

import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.util.AbstractVisitor;
import org.objectweb.asm.util.TraceMethodVisitor;

/**
 * @author zhkl0228
 *
 */
@SuppressWarnings("unchecked")
public class MyTraceMethodVisitor extends TraceMethodVisitor {
	
	private String getOpcode(int opcode) {
		String code = OPCODES[opcode];
		return code == null ? null : code.toLowerCase();
	}

	@Override
	public void visitInsn(int opcode) {
        buf.setLength(0);
        buf.append(tab2).append(getOpcode(opcode)).append('\n');
        text.add(buf.toString());

        if (mv != null) {
            mv.visitInsn(opcode);
        }
	}

	@Override
	public void visitIntInsn(int opcode, int operand) {
        buf.setLength(0);
        buf.append(tab2)
                .append(getOpcode(opcode))
                .append(' ')
                .append(opcode == Opcodes.NEWARRAY
                        ? TYPES[operand]
                        : Integer.toString(operand))
                .append('\n');
        text.add(buf.toString());

        if (mv != null) {
            mv.visitIntInsn(opcode, operand);
        }
	}

	@Override
	public void visitVarInsn(int opcode, int var) {
        buf.setLength(0);
        buf.append(tab2)
                .append(getOpcode(opcode))
                .append(' ')
                .append(var)
                .append('\n');
        text.add(buf.toString());

        if (mv != null) {
            mv.visitVarInsn(opcode, var);
        }
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
        buf.setLength(0);
        buf.append(tab2).append(getOpcode(opcode)).append(' ');
        appendDescriptor(INTERNAL_NAME, type);
        buf.append('\n');
        text.add(buf.toString());

        if (mv != null) {
            mv.visitTypeInsn(opcode, type);
        }
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name,
			String desc) {
        buf.setLength(0);
        buf.append(tab2).append(getOpcode(opcode)).append(' ');
        appendDescriptor(INTERNAL_NAME, owner);
        buf.append('.').append(name).append(" : ");
        appendDescriptor(FIELD_DESCRIPTOR, desc);
        buf.append('\n');
        text.add(buf.toString());

        if (mv != null) {
            mv.visitFieldInsn(opcode, owner, name, desc);
        }
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name,
			String desc) {
        buf.setLength(0);
        buf.append(tab2).append(getOpcode(opcode)).append(' ');
        appendDescriptor(INTERNAL_NAME, owner);
        buf.append('.').append(name).append(' ');
        appendDescriptor(METHOD_DESCRIPTOR, desc);
        buf.append('\n');
        text.add(buf.toString());

        if (mv != null) {
            mv.visitMethodInsn(opcode, owner, name, desc);
        }
	}

	@Override
	public void visitJumpInsn(int opcode, Label label) {
        buf.setLength(0);
        buf.append(tab2).append(getOpcode(opcode)).append(' ');
        appendLabel(label);
        buf.append('\n');
        text.add(buf.toString());

        if (mv != null) {
            mv.visitJumpInsn(opcode, label);
        }
	}

	@Override
	public void visitLdcInsn(Object cst) {
        buf.setLength(0);
        buf.append(tab2).append("ldc ");
        if (cst instanceof String) {
            AbstractVisitor.appendString(buf, (String) cst);
        } else if (cst instanceof Type) {
            buf.append(((Type) cst).getDescriptor()).append(".class");
        } else {
            buf.append(cst);
        }
        buf.append('\n');
        text.add(buf.toString());

        if (mv != null) {
            mv.visitLdcInsn(cst);
        }
	}

	@Override
	public void visitIincInsn(int var, int increment) {
        buf.setLength(0);
        buf.append(tab2)
                .append("iinc ")
                .append(var)
                .append(' ')
                .append(increment)
                .append('\n');
        text.add(buf.toString());

        if (mv != null) {
            mv.visitIincInsn(var, increment);
        }
	}

}
