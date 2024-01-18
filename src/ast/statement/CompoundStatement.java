package ast.statement;

import java.util.ArrayList;

import org.objectweb.asm.MethodVisitor;

import ast.Declaration;
import semantics.table.SymbolTable;

public class CompoundStatement extends Statement {
    public ArrayList<Declaration> declarations;
    public ArrayList<Statement> statements;
    public Boolean includesJump = false;
    public Boolean includesReturn = false;

    public CompoundStatement(ArrayList<Declaration> decls, ArrayList<Statement> stmts)
    {
        declarations = decls;
        statements = stmts;
    }

    @Override public String toString()
    {
        return "{" + declarations.toString() + statements.toString() + "}";
    }

    public void GenerateBytecode(MethodVisitor mv) 
    {
        /* Iterates through declarations, then statements, generating their bytecode
         * by calling the common GenerateBytecode method
         * 
         * A small optimisation is performed here: if a JumpStatement is encountered,
         * the code beyond is unreachable, and so is not generated.
         * 
         */
        for (Declaration decl : declarations) decl.GenerateBytecode(mv);

        for (Statement stmt : statements) 
        {
            stmt.GenerateBytecode(mv);

            if (stmt instanceof JumpStatement)
            {
                return;
            }

        }
        
    }
    
    public void validate(SymbolTable symbolTable) {
        /* First, the declaration (which always come first) are validated.
         * 
         * Secondly, the statements are also validated. 
         * includesJump and includesReturn are also set at this point.
         * 
         * These exist both for validatation  and code generatiomn reasons:
         *  ->  If this CompoundStatement is the body of a function, includesReturn 
         *      must be true.
         *  ->  If this CompoundStatement is part of an if-else statement, includesJump 
         *      indicates no more code should be generated to finish of the block.
         */
        
        
        for (Declaration decl : declarations) decl.validate(symbolTable);
        
        for (Statement stmt : statements)
        { 
            stmt.validate(symbolTable); 
            
            if (stmt instanceof JumpStatement)
            {
                includesJump = true;
                if (stmt instanceof ReturnStatement)
                {
                    includesReturn = true;
                }
            }
        
        }
    }
}
