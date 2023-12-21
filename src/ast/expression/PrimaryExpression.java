package ast.expression;

public class PrimaryExpression<T> extends Expression {
    T value;
    ExprType type;

    public PrimaryExpression(T val, ExprType t)
    {
        this.value = val;
        this.type = t;

    }

    @Override
    public String toString()
    {
        return value.toString();
    }

}
