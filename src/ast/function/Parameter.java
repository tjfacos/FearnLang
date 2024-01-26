package ast.function;

import ast.ASTNode;
import ast.type.TypeSpecifier;

public class Parameter extends ASTNode {
    public String identifier;
    public TypeSpecifier type;
    
    public Parameter(String id, TypeSpecifier t)
    {
       identifier = id;
       type = t;
    }

    @Override public String toString()
    {
        return String.format("%s : %s", type.toString(), identifier);
    }
    
}
