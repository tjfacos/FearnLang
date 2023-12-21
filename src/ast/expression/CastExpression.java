package ast.expression;

import ast.type.PrimitiveDataType;

public class CastExpression extends Expression {
    
    public PrimitiveDataType target;
    public Expression Op;
    
    public CastExpression(Expression operand, PrimitiveDataType targetType)
    {
        target = targetType;
        Op = operand;
    }

    @Override
    public String toString()
    {
        return "( " + Op.toString() + " -> " + target.name() + " )";
    }

}
