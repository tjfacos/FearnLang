package ast.expression;

public class BinaryExpression extends Expression {
    
    public Expression Op1;
    public Expression Op2;
    public ExprType Operator;
    
    public BinaryExpression(Expression op1, Expression op2, ExprType op)
    {
        Op1 = op1;
        Op2 = op2;
        Operator = op;
    }

    @Override
    public String toString()
    {
        return '(' + Op1.toString() + ' ' + Operator.name() + ' ' + Op2.toString() + ')';
    }
}
