package ast;

import java.util.ArrayList;

public class Struct extends ASTNode {

    public String identifer;
    public ArrayList<Declaration> declarations;

    public Struct(String id, ArrayList<Declaration> decl) 
    {
        identifer = id;
        declarations = decl;
    }

    @Override 
    public String toString()
    {
        return String.format("STRUCT %s \n %s \n", identifer, declarations.toString());
    }
    
}
