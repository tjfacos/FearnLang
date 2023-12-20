package ast.expression;

public class PrimaryExpression<T> extends Expression {
    T value;
    ExprType.Primary type;

    public PrimaryExpression(T val, ExprType.Primary t)
    {
        this.value = val;
        this.type = t;

        System.out.print(this.value);
        System.out.print(" ");
        System.out.print(this.type);
        System.out.println();
        
    }

}
