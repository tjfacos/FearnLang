package ast;

import java.util.ArrayList;

import ast.function.Function;
import semantics.table.SymbolTable;

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
        return String.format("%s\n\n%s\n\n%s", global_declarations, structs, functions);
    }

    public void verifyType(SymbolTable symbolTable) {
        
        for (Declaration d : global_declarations    ) 
        {
            d.verifyType(symbolTable); 
        }

        for (Struct s : structs                     ) { s.verifyType(symbolTable); }
        for (Function f : functions                 ) { f.verifyType(symbolTable); }
    }
    

}