package io.github.fearnlang.ast.statement;

import java.util.ArrayList;

import org.objectweb.asm.MethodVisitor;

import io.github.fearnlang.ast.ASTNode;
import io.github.fearnlang.ast.Declaration;
import io.github.fearnlang.semantics.table.SymbolTable;

/* CompoundStatement.java
 * 
 * Represents a compound statement, a collection of statements within curly brackets.
 * 
 * Contains nested_nodes, Statements and Declarations that exist within the scope of the 
 * block. It also has boolean values includesJump and includesReturn.
 */


public class CompoundStatement extends Statement {

    public ArrayList<ASTNode> nested_nodes;
    public Boolean includesJump = false;
    public Boolean includesReturn = false;

    public CompoundStatement(ArrayList<ASTNode> nodes)
    {
        nested_nodes = nodes;
    }

    @Override public String toString()
    {
        return "{" + nested_nodes.toString() + "}";
    }

    public void GenerateBytecode(MethodVisitor mv) 
    {
        /* Iterates through nested nodes, generating their bytecode
         * by calling the common GenerateBytecode method
         * 
         * A small optimisation is performed here: if a JumpStatement is encountered,
         * the remaining code beyond is unreachable, and so is not generated.
         * 
         */
        for (ASTNode node : nested_nodes)
        {
            if (node instanceof Declaration) ((Declaration)node).GenerateBytecode(mv);
            else ((Statement)node).GenerateBytecode(mv);

            if (node instanceof JumpStatement) return;
        }    
    }
    
    public void validate(SymbolTable symbolTable) {
        /* Iterate nested_nodes, and validates them
         * 
         * includesJump and includesReturn are also set at this point.
         * 
         * These exist both for validation  and code generation reasons:
         *  ->  If this CompoundStatement is the body of a function, includesReturn 
         *      must be true.
         *  ->  If this CompoundStatement is part of an if-else statement, includesJump 
         *      indicates no more code should be generated to finish of the block.
         */
        for (ASTNode node : nested_nodes)
        {
            if (node instanceof Declaration) ((Declaration)node).validate(symbolTable);
            else ((Statement)node).validate(symbolTable);

            if (node instanceof JumpStatement) includesJump = true;
            if (node instanceof ReturnStatement) includesReturn = true;
        }
    }
}
