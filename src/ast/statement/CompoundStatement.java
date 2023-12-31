package ast.statement;

import java.util.ArrayList;

import org.objectweb.asm.MethodVisitor;

import ast.Declaration;

public class CompoundStatement extends Statement {
    public ArrayList<Declaration> declarations;
    public ArrayList<Statement> statements;

    public CompoundStatement(ArrayList<Declaration> decls, ArrayList<Statement> stmts)
    {
        declarations = decls;
        statements = stmts;
    }

    @Override public String toString()
    {
        return "BLOCK " + this.ID + " {\n\t" + declarations.toString() + "\n\t" + statements.toString() + "\n}";
    }

    public void GenerateBytecode(MethodVisitor mv) 
    {
        for (Declaration decl : declarations)
        {
            decl.GenerateBytecode(mv);
        }

        for (Statement stmt : statements)
        {
            stmt.GenerateBytecode(mv);
        }
        
    }
}
