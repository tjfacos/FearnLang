package ast.statement;

import ast.expression.Expression;

public class ReturnStatement extends JumpStatement {
    Expression expression;

    public ReturnStatement(JumpType t, Expression e)
    {
        super(t);
        expression = e;
    }

    @Override public String toString()
    {
        return "\n\t" + type.name() + " " + expression.toString() + ";";
    }
}
