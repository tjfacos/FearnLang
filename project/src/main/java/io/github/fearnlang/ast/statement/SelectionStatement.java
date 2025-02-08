package io.github.fearnlang.ast.statement;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import io.github.fearnlang.ast.expression.Expression;
import io.github.fearnlang.ast.type.PrimitiveSpecifier.PrimitiveDataType;
import io.github.fearnlang.ast.type.PrimitiveSpecifier;
import io.github.fearnlang.semantics.table.SymbolTable;
import io.github.fearnlang.util.Reporter;

/* SelectionStatement.java
 * 
 * Represents an if(-else) statement. 
 * 
 * The class contains an expression condition, a boolean expression which
 * determines if the if_branch executes. The if_branch is a compound statement - 
 * executing only if the expression is true. else_branch is a statement, either a 
 * compound statement or another selection statement (for if-else chains), which 
 * only executes if the condition evaluates to false.
 * 
 */

public class SelectionStatement extends Statement {
    
    public Expression condition;
    public CompoundStatement if_branch;
    public Statement else_branch;

    public SelectionStatement(Expression cond, CompoundStatement if_body, Statement else_body)
    {
        condition = cond;
        if_branch = if_body;
        else_branch = else_body;
    }
    
    @Override public String toString()
    {
        if (else_branch == null)
        {
            return String.format(
                "if (%s) {...}",
                condition.toString()
            );
            
        }

        else if (else_branch instanceof SelectionStatement)
        {
            return String.format(
                "if (%s) {...} else %s",
                condition.toString(),
                else_branch.toString()
            );
        }
        else {
            return String.format(
                "if (%s) {...} else {...}",
                condition.toString()
            );
            
        }
        
    }

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        
        /* This requires labels and GOTO statements. 
         * First, I generate the condition, leaving a Boolean
         * object at the top of the stack. Then, I cast to
         * the privative boolean type. If that equals 0, then
         * GOTO the label after the if_branch (either else or end, 
         * depending on whether an else branch exists). Otherwise, fall
         * through to the if_branch body.
         */
        

        // Create array with type descriptors for all variables within stack

        // This is vital for verifying the state of the stack frame after any 
        // unconditional jump statement
        
        Object[] locals = GetLocalDecriptors();
        int numLocals = locals.length;

        
        // Create labels
        Label else_label = new Label();
        Label end_label = new Label();

        // Get Condition
        condition.GenerateBytecode(mv);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z", false);
        
        // If equal to 0 (false), skip if_branch (jump to else - if defined - or end)
        if (else_branch != null) mv.visitJumpInsn(IFEQ, else_label);
        else mv.visitJumpInsn(IFEQ, end_label);

        // Generate if branch (program falls through to this if expression evaluates to true)
        if_branch.GenerateBytecode(mv);

        // Only GOTO end if no jump statement exists within the if_branch body
        if (!if_branch.includesJump) mv.visitJumpInsn(GOTO, end_label);
        
        // If an else branch exists, Generate its bytecode here
        if (else_branch != null)
        {
            mv.visitLabel(else_label);
            mv.visitFrame(F_FULL, numLocals, locals, 0, new Object[] {});
            else_branch.GenerateBytecode(mv);
        }

        mv.visitLabel(end_label);
        mv.visitFrame(F_FULL, numLocals, locals, 0, new Object[] {});
        
    }

    /* To validate, confirm the condition evaluates to a boolean value. 
     * Validate if and else branches also.
     */
    public void validate(SymbolTable symbolTable) {
        if(!condition.validate(symbolTable).equals(new PrimitiveSpecifier(PrimitiveDataType.BOOL)))    
            Reporter.ReportErrorAndExit(
                condition.toString() + ": Condition must be boolean.", 
                null
            );
    
        if_branch.validate(symbolTable);
        if (else_branch != null) else_branch.validate(symbolTable);
        
    }

}