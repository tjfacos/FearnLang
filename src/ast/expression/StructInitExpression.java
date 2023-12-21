package ast.expression;

import java.util.ArrayList;

public class StructInitExpression extends Expression {
    
    public String name;
    public ArrayList<Expression> arguements;
    
    public StructInitExpression(String n, ArrayList<Expression> args)
    {
        name = n;
        arguements = args;
    }

    @Override
    public String toString()
    {
        return "$" + name + "( " + arguements.toString() + " )";
    }

}
