package ast.expression;

import java.util.ArrayList;

import ast.type.PrimitiveDataType;

public class ArrayInitExpression extends Expression {
    public PrimitiveDataType type;
    public Expression length;
    public ArrayList<Expression> init_elements;

    public ArrayInitExpression(PrimitiveDataType t, Expression len, ArrayList<Expression> ele)
    {
        type = t;
        length = len;
        init_elements = ele;
    }

    @Override
    public String toString()
    {
        return type.name() + "(len: " + length.toString() + ")" + init_elements.toString();
    }
    

}
