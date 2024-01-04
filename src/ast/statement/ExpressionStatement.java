package ast.statement;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.MethodVisitor;

import ast.expression.Expression;

import semantics.table.SymbolTable;

public class ExpressionStatement extends Statement {
    public Expression expression;
    public Boolean isAssign;

    public ExpressionStatement(Expression expr, Boolean assign)
    {
        expression = expr;
        isAssign = assign;
    }
    
    @Override public String toString()
    {
        return String.format("\n\tExpStmt (Assign %s) %s", isAssign, expression.toString());
    }

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        expression.GenerateBytecode(mv);
        
        if (!isAssign) {
            // If the program calls a function, which returns void, do nothing. Otherwise, pop from stack 
            if (expression.expression_type != null)
            {
                mv.visitInsn(POP);
            }
        }
    }
    public void verifyType(SymbolTable symbolTable) {
        expression.validateType(symbolTable);
    }
}
