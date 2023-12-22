package ast.expression;

public class AssignExpression extends Expression {
    public static enum AssignmentOperator {
        Equals,
        AddEquals,
        SubEquals,
        MultEquals,
        DivEquals,
        ModEquals
    }

    public Expression Target;
    public Expression Expr;
    public AssignmentOperator Operator;


    public AssignExpression(Expression t, Expression e, AssignmentOperator op)
    {
        Target = t;
        Expr = e;
        Operator = op;
    }

    @Override 
    public String toString()
    {
        return Target.toString() + " " + Operator.name() + " ( " + Expr.toString() + " ) ";
    }

}
