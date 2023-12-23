package ast.function;

import java.util.ArrayList;

import ast.ASTNode;
import ast.statement.CompoundStatement;
import ast.type.TypeSpecifier;

public class Function extends ASTNode {
    
    public String identifier;
    public ArrayList<Parameter> parameters;

    public TypeSpecifier return_type;
    public Boolean is_void;

    public CompoundStatement body;

    
    public Function(String id, ArrayList<Parameter> params, TypeSpecifier rt, Boolean _void, CompoundStatement bod)
    {
       identifier = id;
       parameters = params;
       return_type = rt;
       is_void = _void;
       body = bod;

    }

    @Override public String toString()
    {
        String r = "void";
        if (return_type != null) {r = return_type.toString();}
        return String.format(
            "%s FUNC %s %s \n %s", 
            r, 
            identifier,
            parameters.toString(),
            body.toString()
        );
    }
}
