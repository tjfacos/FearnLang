package ast.statement;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.MethodVisitor;

import ast.expression.Expression;
import ast.expression.FnCallExpression;
import semantics.table.SymbolTable;
import util.Reporter;

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
        return expression.toString();
    }

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        expression.GenerateBytecode(mv);
        
        if (!isAssign) {
            // If the expression evaluates to a value, pop from operand stack            
            if (expression.expression_type != null)
            {
                mv.visitInsn(POP);
            }
        }
    }
    public void validate(SymbolTable symbolTable) {
        
        if(!isAssign && !(expression instanceof FnCallExpression))
        {
            Reporter.ReportErrorAndExit(toString() + ": Invalid Statement.");
        }

        expression.validate(symbolTable);
    }
}
