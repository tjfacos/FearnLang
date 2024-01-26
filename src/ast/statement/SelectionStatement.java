package ast.statement;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import ast.expression.Expression;
import ast.type.PrimitiveSpecifier.PrimitiveDataType;
import ast.type.PrimitiveSpecifier;
import semantics.table.SymbolTable;
import util.Reporter;

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
        
        /* This requires labels and goto statements. 
         * First, I generate the condition, leaving a Boolean
         * object at the top of the stack. Then, I cast to
         * the privitive boolean type. If that equals 0, then
         * goto the label after the if_branch. Otherwise, fall
         * through to the if_branch body.
         */
        

        // Create array with type descirptors for all variables within stack

        // This is vital for verifying the state of the stack fram after any 
        // unconditional jump statement
        
        Object[] locals = GetLocalDecriptors();
        int numLocals = locals.length;

        
        // Create labels
        Label else_label = new Label();
        Label end_label = new Label();

        // Get Condition
        condition.GenerateBytecode(mv);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z", false);
        
        // If equal to 0 (false), skip if_branch
        if (else_branch != null) mv.visitJumpInsn(IFEQ, else_label);
        else mv.visitJumpInsn(IFEQ, end_label);


        if_branch.GenerateBytecode(mv);

        if (!if_branch.includesJump)
        {
            mv.visitJumpInsn(GOTO, end_label);
        }
        
        
        // If an else branch exists, Generate its bytecode here
        if (else_branch != null)
        {
            mv.visitLabel(else_label);
            
            mv.visitFrame(
                F_FULL, 
                numLocals, 
                locals, 
                0, 
                new Object[] {}
            );

            else_branch.GenerateBytecode(mv);
        }


        mv.visitLabel(end_label);

        mv.visitFrame(
            F_FULL, 
            numLocals, 
            locals, 
            0, 
            new Object[] {}
        );
        
    }

    public void validate(SymbolTable symbolTable) {
        if(!condition.validate(symbolTable).equals(new PrimitiveSpecifier(PrimitiveDataType.BOOL)))
        {
            Reporter.ReportErrorAndExit(condition.toString() + ": Condition must be boolean.", null);
        }

        if_branch.validate(symbolTable);
        if (else_branch != null) else_branch.validate(symbolTable);
        
    }

}