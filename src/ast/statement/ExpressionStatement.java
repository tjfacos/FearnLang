package ast.statement;

import ast.expression.Expression;

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
}
