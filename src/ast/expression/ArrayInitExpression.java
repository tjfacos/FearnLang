package ast.expression;

import java.util.ArrayList;

import ast.type.TypeSpecifier;

public class ArrayInitExpression extends Expression {
    
    public TypeSpecifier type;
    public Expression length;
    public ArrayList<Expression> init_elements;

    public ArrayInitExpression(TypeSpecifier t, Expression len, ArrayList<Expression> ele)
    {
        type = t;
        length = len;
        init_elements = ele;
    }

    @Override
    public String toString()
    {
        return type.toString() + "(len: " + length.toString() + ")" + init_elements.toString();
    }
    

}
