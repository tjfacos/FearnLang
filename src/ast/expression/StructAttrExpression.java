package ast.expression;

public class StructAttrExpression extends Expression {
    
    public Expression name;
    public Expression attribute;
    
    public StructAttrExpression(Expression n, Expression attr)
    {
        name = n;
        attribute = attr;
    }

    @Override
    public String toString()
    {
        return  "$" + name.toString() + "." + attribute.toString();
    }
}
