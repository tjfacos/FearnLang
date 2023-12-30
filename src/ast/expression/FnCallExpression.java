package ast.expression;

import java.util.ArrayList;

import org.objectweb.asm.MethodVisitor;

public class FnCallExpression extends Expression {
    
    public String identifier;
    public ArrayList<Expression> arguements;
    
    public FnCallExpression(String fn_name, ArrayList<Expression> args)
    {
        identifier = fn_name;
        arguements = args;
    }

    @Override
    public String toString()
    {
        return "FCALL "+ identifier + "( " + arguements.toString() + " )";
    }

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GenerateBytecode'");
    }

}
