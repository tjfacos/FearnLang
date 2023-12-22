package ast;

import ast.expression.Expression;
import ast.type.TypeSpecifier;

public class Declaration extends ASTNode {
    
    public String identifer;
    public TypeSpecifier type;
    public Expression init_expression;
    
    public Declaration(String id, TypeSpecifier t, Expression e)
    {
        identifer = id;
        type = t;
        init_expression = e;
    }

    @Override public String toString()
    {
        if (!(init_expression == null)) {
            return "Declare " + type.toString() + " " + identifer + " = " + init_expression.toString() + "\n";
        } else {
            return "Declare " + type.toString() + " " + identifer ;
        }
    }

}
