package ast.expression;

import org.objectweb.asm.MethodVisitor;

public class AssignExpression extends Expression {
    public static enum AssignmentOperator {
        Equals,
        AddEquals,
        SubEquals,
        MultEquals,
        DivEquals,
        ModEquals
    }

    public Expression target;
    public Expression expression;
    public AssignmentOperator operator;


    public AssignExpression(Expression t, Expression e, AssignmentOperator op)
    {
        target = t;
        expression = e;
        operator = op;
    }

    @Override 
    public String toString()
    {
        return target.toString() + " " + operator.name() + " ( " + expression.toString() + " ) ";
    }

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        expression.GenerateBytecode(mv);
    }

}
