package io.github.fearnlang.ast;

import java.util.ArrayList;

import io.github.fearnlang.ast.function.Function;
import io.github.fearnlang.semantics.table.SymbolTable;

/* Program.java
 * 
 * The root node of the AST.
 *  ->  It contains global declarations, structs, and functions.
 *  ->  Its validate() method simply calls the validate() methods for 
 *      its children
 *      ->  Function validations are have their local symbol table 
 *          passed to them, from the global symbol table
 * 
 */
public class Program extends ASTNode {
    

    public ArrayList<Declaration> global_declarations; 
    public ArrayList<Struct> structs; 
    public ArrayList<Function> functions;

    public Program(ArrayList<Declaration> global_decl, ArrayList<Function> funcs, ArrayList<Struct> s) 
    {
        global_declarations = global_decl;
        functions = funcs;
        structs = s;
    }
    
    @Override 
    public String toString()
    {
        return String.format("%s%s%s", global_declarations, structs, functions);
    }

    public void validate(SymbolTable symbolTable) {
        
        for (Declaration d : global_declarations) d.validate(symbolTable); 
        for (Struct s : structs) s.validate(symbolTable);
        for (Function f : functions) f.validate(symbolTable.GetFuncSymbolTable(f.identifier)); 
        
    }
    

}