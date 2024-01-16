package ast.statement;

import org.objectweb.asm.MethodVisitor;

import codegen.CodeGenerator;

import static org.objectweb.asm.Opcodes.*;

import semantics.table.SymbolTable;
import util.Reporter;

public class JumpStatement extends Statement {
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
        /* This is the reason CodeGenerator.LabelStack exists!
         * 
         * If JumpType is Break, GOTO the last label on the stack.
         * This should be peeked, to prevent it being removed.
         * 
         * If JumpType is Continue, GOTO the second-to-last label.
         * This requires me to treat the stack like an array, getting
         * the label from index size() - 2
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
        /* This class slightly breaks my own rules, as no 
         * validation is done before Code Generation. 
         * This is because Code Generation maintains a stack 
         * of labels, and the only time this statement is 
         * invalid is if it's not contained within a stack.
         * Thus, I leave it until CodeGeneration, as if the 
         * Label cannot be found, that detects the semantic error.
         */

        return;

    }

}
