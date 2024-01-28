package codegen;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.MethodVisitor;

public class FearnMethodVisitor extends MethodVisitor{
    
    public FearnMethodVisitor(int api, MethodVisitor mv)
    {
        super(api, mv);
    }

    @Override
    public void visitFrame(
        final int type,
        final int numLocal,
        final Object[] local,
        final int numStack,
        final Object[] stack
    )
    {
        
        try {
            super.visitFrame(type, numLocal, local, numStack, stack);
        } catch (IllegalStateException e) {
            super.visitFrame(F_SAME, 0, null, 0, null);
        }

    }

    @Override
    public void visitLdcInsn(Object obj)
    {
        if (obj instanceof String)
        {
            String input_str = String.valueOf(obj);

            input_str = input_str
                .replace("\\n", "\n")
                .replace("\\t", "\t")
                .replace("\\b", "\b")
            ;

            super.visitLdcInsn(input_str);
            return;

        } else super.visitLdcInsn(obj);
    }

}
