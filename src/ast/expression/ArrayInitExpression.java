package ast.expression;

import java.util.ArrayList;

import ast.type.TypeSpecifier;

public class ArrayInitExpression extends Expression {
    
    public TypeSpecifier type;
    public ArrayList<Expression> dimenisons;
    public ArrayBody init_elements;

    public ArrayInitExpression(TypeSpecifier t, ArrayList<Expression> dims, ArrayBody ele)
    {
        type = t;
        dimenisons = dims;
        init_elements = ele;
    }

    @Override
    public String toString()
    {
        return type.toString() + "(dims: " + dimenisons.toString() + ")" + init_elements.toString();
    }
    

}
