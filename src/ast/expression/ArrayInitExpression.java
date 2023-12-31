package ast.expression;

import java.util.ArrayList;

import org.objectweb.asm.MethodVisitor;

import ast.type.TypeSpecifier;
import semantics.table.SymbolTable;

public class ArrayInitExpression extends Expression {
    
    public TypeSpecifier type;
    public ArrayList<Expression> dimenisons;
    public ArrayBody init_elements;

    public ArrayInitExpression(TypeSpecifier t, ArrayList<Expression> dims, ArrayBody ele)
    {
        type = t;
        dimenisons = dims;
        init_elements = ele;
    }

    @Override
    public String toString()
    {
        return type.toString() + "(dims: " + dimenisons.toString() + ")" + init_elements.toString();
    }

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        // TODO GenerateBytecode ArrayInitExpression

        // Just Generate the Body, if provided

        // Otherwise
        /*
        
        methodVisitor.visitInsn(ICONST_2); <- Length
        methodVisitor.visitTypeInsn(ANEWARRAY, "java/lang/Integer"); <- Create Array of Length

        OR 

        methodVisitor.visitInsn(ICONST_2);
        methodVisitor.visitInsn(ICONST_3);
        methodVisitor.visitMultiANewArrayInsn("[[Ljava/lang/Integer;", 2);

        */

        throw new UnsupportedOperationException("Unimplemented method 'GenerateBytecode'");
    }

    @Override
    public TypeSpecifier validateType(SymbolTable symTable) {
        // TODO Auto-generated method stub

        // Check the Type of the body (if present) matches what's expected.
        throw new UnsupportedOperationException("Unimplemented method 'validateType'");
    }
    

}
