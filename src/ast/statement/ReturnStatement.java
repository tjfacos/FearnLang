package ast.statement;

import org.objectweb.asm.MethodVisitor;

import ast.expression.Expression;

public class ReturnStatement extends JumpStatement {
    Expression expression;

    public ReturnStatement(JumpType t, Expression e)
    {
        super(t);
        expression = e;
    }

    @Override public String toString()
    {
        return "\n\t" + type.name() + " " + expression.toString() + ";";
    }

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GenerateBytecode'");
    }
}
