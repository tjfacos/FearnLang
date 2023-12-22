package ast.expression;

import java.util.ArrayList;

public class ArrayBody extends Expression {
    
    public ArrayList<Expression> elements;
    
    public ArrayBody(ArrayList<Expression> ele)
    {
        elements = ele;
    }

    @Override
    public String toString()
    {
        return "{" + elements.toString() + "}";
    }

}
