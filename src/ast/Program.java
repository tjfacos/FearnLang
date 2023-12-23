package ast;

import java.util.ArrayList;

import ast.function.Function;

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
    

}
