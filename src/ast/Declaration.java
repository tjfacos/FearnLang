package ast;

import static org.objectweb.asm.Opcodes.ASTORE;

import org.objectweb.asm.MethodVisitor;

import ast.expression.Expression;
import ast.type.TypeSpecifier;
import codegen.CodeGenerator;

public class Declaration extends ASTNode {
    
    public String identifier;
    public TypeSpecifier type;
    public Expression init_expression;
    
    public Declaration(String id, TypeSpecifier t, Expression e)
    {
        identifier = id;
        type = t;
        init_expression = e;
    }

    @Override public String toString()
    {
        if (!(init_expression == null)) {
            return "Declare " + type.toString() + " " + identifier + " = " + init_expression.toString();
        } else {
            return "Declare " + type.toString() + " " + identifier ;
        }
    }

    public void GenerateBytecode(MethodVisitor mv) 
    {
        // This is ONLY called to generate local variables within functions, so no need to handle global variables
        if (init_expression != null) {
            init_expression.GenerateBytecode(mv);
            mv.visitVarInsn(ASTORE, CodeGenerator.LocalSymbolTable.GetIndex(identifier));
        }

    }

}
