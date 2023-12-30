package ast.expression;

import org.objectweb.asm.MethodVisitor;

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
        return "{" + array_name.toString() + "}" + '[' + index.toString() + ']';
    }

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GenerateBytecode'");
    }
}
