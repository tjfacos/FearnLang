package ast.statement;

import org.objectweb.asm.MethodVisitor;

import codegen.CodeGenerator;

import static org.objectweb.asm.Opcodes.*;

import semantics.table.SymbolTable;
import util.Reporter;

public class JumpStatement extends Statement {
    // Represents the type of jump being performed
    public enum JumpType 
    {
        Continue,
        Break,
        Return
    }

    public JumpType type;

    public JumpStatement (JumpType t)
    {
        type = t;
    }

    @Override public String toString()
    {
        return type.name() + ";";
    }


    public void GenerateBytecode(MethodVisitor mv) {
        /* This is the reason CodeGenerator.LabelStack exists
         * 
         * If JumpType is Break, GOTO the last label on the stack.
         * This should be peeked, to prevent it being removed.
         * 
         * If JumpType is Continue, GOTO the second-to-last label.
         * This requires me to treat the stack like an array (something 
         * that Java's flexible Stack type lets me do), getting the 
         * label from index size() - 2.
         */

        try {
            switch (type) {
                case Break:
                    mv.visitJumpInsn(
                        GOTO, 
                        CodeGenerator.LabelStack.peek()
                    );
                    return;
                case Continue:
                    mv.visitJumpInsn(
                        GOTO, 
                        CodeGenerator.LabelStack.get(
                            CodeGenerator.LabelStack.size() - 2
                        )
                    );
                    return;
                default:
                    break;
            }
        } catch (Exception e) {
            Reporter.ReportErrorAndExit(type.name() + " must be contained with a loop.");
        }


    }

    
    public void validate(SymbolTable symbolTable) {
        // If loop depth (the number of loops the traversal is currently in)
        // is > 0, statement is valid.
        if (CodeGenerator.loopDepth > 0) return;

        Reporter.ReportErrorAndExit("Jump Statement " + type.name() + " is invalid outside a loop.");

    }

}
