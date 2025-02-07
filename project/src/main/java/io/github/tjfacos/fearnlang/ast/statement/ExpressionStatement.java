package io.github.tjfacos.fearnlang.ast.statement;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.MethodVisitor;

import io.github.tjfacos.fearnlang.ast.expression.Expression;
import io.github.tjfacos.fearnlang.ast.expression.FnCallExpression;
import io.github.tjfacos.fearnlang.ast.expression.IncrExpression;
import io.github.tjfacos.fearnlang.semantics.table.SymbolTable;
import io.github.tjfacos.fearnlang.util.Reporter;

/* ExpressionStatement.java
 * 
 * Represents an Expression Statement in the AST. It contains
 *  ->  The expression
 *  ->  isAssign: a boolean value indicating if the expression 
 *      is an assignment
 */

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
        return expression.toString() + ";";
    }

    /* The GenerateBytecode method simply generates the bytecode for the expression.
     * 
     * If the expression doesn't evaluate to null (to evaluates to some data, still on 
     * the stack), a POP instruction is added on the end to remove the value.
     * 
     */
    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        
        expression.GenerateBytecode(mv);
        
        // If the expression evaluates to a value, pop from operand stack            
        if (expression.expression_type != null) mv.visitInsn(POP);
        
    }

    /* To validate, the expression is validated. The expression is also 
     * checked to ensure it's valid as a statement, on its own. This means 
     * it is either
     *  ->  An Assignment
     *  ->  A Function Call
     *  ->  An Increment Expression
     */
    public void validate(SymbolTable symbolTable) {
        
        if(!isAssign && !(expression instanceof FnCallExpression || expression instanceof IncrExpression))        
            Reporter.ReportErrorAndExit("Invalid Statement.", this);
        
        expression.validate(symbolTable);
    }
}
