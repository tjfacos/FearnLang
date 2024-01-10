package ast.statement;

import java.util.ArrayList;

import org.objectweb.asm.MethodVisitor;

import ast.ASTNode;
import ast.Declaration;
import semantics.table.SymbolTable;

public class CompoundStatement extends Statement {

    public ArrayList<ASTNode> lines;
    public Boolean includesJump = false;

    public CompoundStatement(ArrayList<ASTNode> loc_lines)
    {
        lines = loc_lines;
    }

    @Override public String toString()
    {
        return "{" + lines.toString() + "}";
    }

    public void GenerateBytecode(MethodVisitor mv) 
    {

        for (ASTNode line : lines) 
        {
            if (line instanceof Statement)
            {
                Statement stmt = (Statement)line;
                stmt.GenerateBytecode(mv);

                // Dead-Code elimination
                if (stmt instanceof JumpStatement)
                {
                    return;
                }

            } else {
                Declaration decl = (Declaration)line;
                decl.GenerateBytecode(mv);
            }

        }
        
    }
    
    public void validate(SymbolTable symbolTable) {
        
        for (ASTNode line : lines)
        { 
            if (line instanceof Statement)
            {
                Statement stmt = (Statement)line;
                stmt.validate(symbolTable);

                // Dead-Code elimination
                if (stmt instanceof JumpStatement)
                {
                    includesJump = true;
                }

            } else {
                Declaration decl = (Declaration)line;
                decl.validate(symbolTable);
            }
        
        }
    }
}
