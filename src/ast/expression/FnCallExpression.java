package ast.expression;

import java.util.ArrayList;

public class FnCallExpression extends Expression {
    
    public String name;
    public ArrayList<Expression> arguements;
    
    public FnCallExpression(String fn_name, ArrayList<Expression> args)
    {
        name = fn_name;
        arguements = args;
    }

    @Override
    public String toString()
    {
        return name + "( " + arguements.toString() + " )";
    }

}
