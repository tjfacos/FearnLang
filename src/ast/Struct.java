package ast;

import java.util.ArrayList;

import semantics.table.SymbolTable;
import util.Reporter;

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

    public void validate(SymbolTable symbolTable) {
        for (Declaration decl : declarations)
        {
            if (decl.init_expression != null)
            {
                Reporter.ReportErrorAndExit(decl.toString() + ": Cannot assign default values to struct attributes.");
            }
        }
    }
    
}
