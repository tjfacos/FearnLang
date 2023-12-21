package ast.expression;

public class UnaryExpression extends Expression {

    public Expression Op;
    public ExprType Operator;
    
    public UnaryExpression(Expression op, ExprType operator)
    {
        Op = op;
        Operator = operator;
    }

    @Override
    public String toString()
    {
        return '(' + Operator.name() + ' ' + Op.toString() + ')';
    }

}
