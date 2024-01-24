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
        String ret_type_str = "void";
        if (return_type != null) {ret_type_str = return_type.toString();}
        return String.format(
            "fn %s%s => %s {...}",  
            identifier,
            parameters.toString().replace("[", "(").replace("]", ")"),
            ret_type_str
        );
    }

    /* No Function.GenerateBytecode is provided, as the generation of 
     * functions using the method visitor is performed by CodeGenerator,
     * as the program's Class Writer is needed to add the method to the 
     * main program class (see codegen/CodeGenerator.java).
     */

    public void validate(SymbolTable symbolTable) {
        /* Sets Current Return Type to the return type specifier
         * for this function. This is used during the treversal of 
         * the body, as return statements must return an expression 
         * the evaluates to the right type.
         * 
         * The body is validated, and (assuming it is not void), must include
         * a return statement (the type of expression returned is validated by 
         * the return statement itself, using CurrentReturnType).
         */

        CodeGenerator.CurrentReturnType = return_type;
        
        body.validate(symbolTable);

        if (!body.includesReturn && return_type != null)
        {
            Reporter.ReportErrorAndExit("Function " + identifier + " must include a return statement in its main body.", null);
        }
    }
}
