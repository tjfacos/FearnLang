package ast;

import java.util.ArrayList;

import semantics.table.SymbolTable;

public class Struct extends ASTNode {

    public String identifier;
    public ArrayList<Declaration> declarations;

    public Struct(String id, ArrayList<Declaration> decl) 
    {
        identifier = id;
        declarations = decl;
    }

    @Override 
    public String toString()
    {
        return String.format("STRUCT %s \n %s \n", identifier, declarations.toString());
    }

    public void verifyType(SymbolTable symbolTable) {
        // TODO VerifyType
    }
    
}
