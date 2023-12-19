package ast;

import java.util.ArrayList;

public class Program {
    
    private ArrayList<Declaration> global_declarations = new ArrayList<Declaration>(); 
    private ArrayList<Function> functions = new ArrayList<Function>();
    
    public void addGlobalDeclaration(Declaration decl)
    {
        global_declarations.add(decl);
    }

    public void addFunction(Function func)
    {
        functions.add(func);
    }

    public ArrayList<Declaration> getGlobalDeclarations()
    {
        return this.global_declarations;
    }

    public ArrayList<Function> getFunctions()
    {
        return this.functions;
    }

}
