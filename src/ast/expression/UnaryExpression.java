package ast.expression;

import org.objectweb.asm.MethodVisitor;

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

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GenerateBytecode'");
    }

}
