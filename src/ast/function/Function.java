package ast.function;

import java.util.ArrayList;

import ast.ASTNode;
import ast.statement.CompoundStatement;
import ast.type.TypeSpecifier;
import codegen.CodeGenerator;
import semantics.table.SymbolTable;
import util.Reporter;

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
            "%s FUNC %s %s %s", 
            r, 
            identifier,
            parameters.toString(),
            body.toString()
        );
    }

    public void validate(SymbolTable symbolTable) {

        CodeGenerator.CurrentReturnType = return_type;
        
        body.validate(symbolTable);

        if (!body.includesJump && return_type != null)
        {
            Reporter.ReportErrorAndExit("Function " + identifier + " must include a return statement in its main body.");
        }
    }
}
