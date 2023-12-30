package ast.expression;

import org.objectweb.asm.MethodVisitor;

public class StructAttrExpression extends Expression {
    
    public Expression instance;
    public Expression attribute;
    
    public StructAttrExpression(Expression n, Expression attr)
    {
        instance = n;
        attribute = attr;
    }

    @Override
    public String toString()
    {
        return  "$" + instance.toString() + "." + attribute.toString();
    }

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GenerateBytecode'");
    }
}
