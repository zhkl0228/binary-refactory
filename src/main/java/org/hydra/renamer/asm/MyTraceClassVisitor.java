/**
 * 
 */
package org.hydra.renamer.asm;

import java.io.PrintWriter;

import org.objectweb.asm.util.TraceClassVisitor;
import org.objectweb.asm.util.TraceMethodVisitor;

/**
 * @author zhkl0228
 *
 */
public class MyTraceClassVisitor extends TraceClassVisitor {

	public MyTraceClassVisitor(PrintWriter pw) {
		super(pw);
	}

	@Override
	protected TraceMethodVisitor createTraceMethodVisitor() {
		return new MyTraceMethodVisitor();
	}

}
