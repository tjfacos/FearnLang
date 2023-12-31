package ast.expression;

import static org.objectweb.asm.Opcodes.AASTORE;
import static org.objectweb.asm.Opcodes.ANEWARRAY;
import static org.objectweb.asm.Opcodes.BIPUSH;
import static org.objectweb.asm.Opcodes.DUP;

import java.util.ArrayList;

import org.objectweb.asm.MethodVisitor;

import ast.type.TypeSpecifier;
import semantics.table.SymbolTable;

public class ArrayBody extends Expression {
    
    public ArrayList<Expression> elements;
    
    public ArrayBody(ArrayList<Expression> ele)
    {
        elements = ele;
    }

    @Override
    public String toString()
    {
        return "{" + elements.toString() + "}";
    }

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        
        /*  EXAMPLE: Recursive Method needed for N-D arrays 
        *  methodVisitor.visitInsn(ICONST_2);
        methodVisitor.visitTypeInsn(ANEWARRAY, "java/lang/Integer");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitInsn(ICONST_0);
        methodVisitor.visitInsn(ICONST_1);
        methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
        methodVisitor.visitInsn(AASTORE);
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitInsn(ICONST_1);
        methodVisitor.visitInsn(ICONST_2);
        methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
        methodVisitor.visitInsn(AASTORE);
        methodVisitor.visitVarInsn(ASTORE, 1);
        * 
        * 
        */
        
        mv.visitIntInsn(BIPUSH, elements.size());
        mv.visitTypeInsn(ANEWARRAY, SymbolTable.GenBasicDescriptor(elements.get(0).expression_type));
        mv.visitInsn(DUP);
        
        int i = 0;
        for (Expression e : elements)
        {
            mv.visitIntInsn(BIPUSH, i++);
            e.GenerateBytecode(mv);
            mv.visitInsn(AASTORE);
            
            if (i < elements.size())
            {
                mv.visitInsn(DUP);
            }
        }
        
    }

    @Override
    public TypeSpecifier validateType(SymbolTable symTable) {
        // TODO validateType ArrayBody

        // Check Dimensions and Element Types
        // Recursion needed for N-D Arrays
        throw new UnsupportedOperationException("Unimplemented method 'validateType'");
    }


}
