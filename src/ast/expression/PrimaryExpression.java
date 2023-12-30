package ast.expression;

import org.objectweb.asm.MethodVisitor;

public class PrimaryExpression<T> extends Expression {
    public T value;
    public ExprType type;

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

    public void GenerateBytecode(MethodVisitor mv)
    {
        if (type == ExprType.VariableReference)
        {

        } else {
            mv.visitLdcInsn(value);
        }
    }

}
