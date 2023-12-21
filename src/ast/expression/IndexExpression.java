package ast.expression;

public class IndexExpression extends Expression {
    
    public Expression array_name;
    public Expression index;
    
    public IndexExpression(Expression id, Expression i)
    {
        array_name = id;
        index = i;
    }

    @Override
    public String toString()
    {
        return array_name.toString() + '[' + index.toString() + ']';
    }
}
