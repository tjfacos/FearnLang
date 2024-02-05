package ast;

import java.util.ArrayList;

import semantics.table.SymbolTable;
import util.Reporter;

/* Struct.java
 * 
 * Represents struct definitions in the AST.
 *  ->  It contains the struct's identifier, and attributes (as declarations)
 *  ->  It's validate method checks the the user hasn't attempted to initialise
 *      an attribute to a default value
 *      ->  This is raised as invalid, simply because, when a struct is 
 *          instantiated, the user must define the initial value of every 
 *          attribute of that instance
 */

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
        return String.format("struct %s {...}", identifier);
    }

    public void validate(SymbolTable symbolTable) {
        for (Declaration decl : declarations)
        {
            if (decl.init_expression != null)
            {
                Reporter.ReportErrorAndExit(decl.toString() + ": Cannot assign default values to struct attributes.", null);
            }
        }
    }
    
}
