/**
 * 
 */
package org.hydra.renamer.asm;

import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;

/**
 * @author zhkl0228
 *
 */
@SuppressWarnings("unchecked")
public class LowercaseOpcodeTextifier extends Textifier {

	public LowercaseOpcodeTextifier() {
		super(Opcodes.ASM5);
	}

	@Override
	public void visitVarInsn(int opcode, int var) {
        buf.setLength(0);
        buf.append(tab2).append(getOpcode(opcode)).append(' ').append(var)
                .append('\n');
        text.add(buf.toString());
	}

	@Override
	public void visitJumpInsn(int opcode, Label label) {
        buf.setLength(0);
        buf.append(tab2).append(getOpcode(opcode)).append(' ');
        appendLabel(label);
        buf.append('\n');
        text.add(buf.toString());
	}

	private Object getOpcode(int opcode) {
		String code = OPCODES[opcode];
		return code == null ? null : code.toLowerCase();
	}

	@Override
	public void visitLdcInsn(Object cst) {
        buf.setLength(0);
        buf.append(tab2).append("ldc ");
        if (cst instanceof String) {
            Printer.appendString(buf, (String) cst);
        } else if (cst instanceof Type) {
            buf.append(((Type) cst).getDescriptor()).append(".class");
        } else {
            buf.append(cst);
        }
        buf.append('\n');
        text.add(buf.toString());
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
	}

	@Override
	public void visitInsn(int opcode) {
        buf.setLength(0);
        buf.append(tab2).append(getOpcode(opcode)).append('\n');
        text.add(buf.toString());
	}

	@Override
	public void visitIntInsn(int opcode, int operand) {
        buf.setLength(0);
        buf.append(tab2)
                .append(getOpcode(opcode))
                .append(' ')
                .append(opcode == Opcodes.NEWARRAY ? TYPES[operand] : Integer
                        .toString(operand)).append('\n');
        text.add(buf.toString());
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
        buf.setLength(0);
        buf.append(tab2).append(getOpcode(opcode)).append(' ');
        appendDescriptor(INTERNAL_NAME, type);
        buf.append('\n');
        text.add(buf.toString());
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name,
			String desc, boolean itf) {
        if (api < Opcodes.ASM5) {
            super.visitMethodInsn(opcode, owner, name, desc, itf);
            return;
        }
        doVisitMethodInsn(opcode, owner, name, desc, itf);
	}

    private void doVisitMethodInsn(final int opcode, final String owner,
            final String name, final String desc, final boolean itf) {
        buf.setLength(0);
        buf.append(tab2).append(getOpcode(opcode)).append(' ');
        appendDescriptor(INTERNAL_NAME, owner);
        buf.append('.').append(name).append(' ');
        appendDescriptor(METHOD_DESCRIPTOR, desc);
        buf.append('\n');
        text.add(buf.toString());
    }

	@Override
	protected Textifier createTextifier() {
		return new LowercaseOpcodeTextifier();
	}

}
