package io.github.fearnlang.codegen;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.MethodVisitor;

/**
 * This is a derived class of ASM's MethodVisitor, used to resolver issues
 * related specific to the FearnC compiler..
 * 
 */
public class FearnMethodVisitor extends MethodVisitor {

    /**
     * Constructor for the FearnMethodVisitor class
     * 
     * @param api The ASM API version
     * @param mv  The MethodVisitor object to be used
     */
    public FearnMethodVisitor(int api, MethodVisitor mv) {
        super(api, mv);
    }

    /**
     * This method is called whenever a label is visited, and defines the state of
     * the frame at that jump location. To ensure no confusion or loss of data, I
     * often use 'F_FULL' by default, defining the exact state of local variable in
     * the frame, using the local variables from the function's symbol table. The
     * issue is that, if the method is called twice in a row (by different AST
     * nodes), an IllegalStateException is raised. This visitor catches that
     * exception, and replaces the call with an F_SAME call, instructing the JVM
     * that the state of the frame has not changed - this suppresses the error.
     * 
     * You can read more about this here:
     * https://asm.ow2.io/javadoc/org/objectweb/asm/MethodVisitor.html#visitFrame(int,int,java.lang.Object%5B%5D,int,java.lang.Object%5B%5D)
     * 
     * @param type     The type of frame to be visited
     * @param numLocal The number of local variables in the frame
     * @param local    The local variables in the frame
     * @param numStack The number of stack elements in the frame
     * @param stack    The stack elements in the frame
     */
    @Override
    public void visitFrame(
            final int type,
            final int numLocal,
            final Object[] local,
            final int numStack,
            final Object[] stack) {

        try {
            super.visitFrame(type, numLocal, local, numStack, stack);
        } catch (IllegalStateException e) {
            super.visitFrame(F_SAME, 0, null, 0, null);
        }

    }

    /**
     * Corrects the un-escaping of escape characters in strings read in by the
     * parser. This method re-escapes the escape characters, so that they are
     * correctly interpreted by the JVM.
     * 
     * @param obj The constant to be loaded
     */
    @Override
    public void visitLdcInsn(Object obj) {
        if (obj instanceof String) {
            String input_str = String.valueOf(obj);

            input_str = input_str
                    .replace("\\n", "\n")
                    .replace("\\t", "\t")
                    .replace("\\b", "\b");

            super.visitLdcInsn(input_str);
            return;

        } else
            super.visitLdcInsn(obj);
    }

}
