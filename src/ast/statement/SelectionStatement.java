package ast.statement;

import static org.objectweb.asm.Opcodes.*;

import java.util.ArrayList;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import ast.expression.Expression;
import ast.type.PrimitiveDataType;
import ast.type.PrimitiveSpecifier;
import ast.type.TypeSpecifier;
import codegen.CodeGenerator;
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

        if (else_branch.getClass() == SelectionStatement.class)
        {
            
        }

    }
    
    @Override public String toString()
    {
        if (else_branch != null)
        {
            return String.format(
                "\n\tIF (%s) THEN %s ELSE %s",
                condition.toString(),
                if_branch.toString(),
                else_branch.toString()
            );
        }
        
        return String.format(
            "\n\tIF (%s) THEN %s",
            condition.toString(),
            if_branch.toString()
        );

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
        ArrayList<String> descriptors = new ArrayList<String>();
        
        for (TypeSpecifier spec : CodeGenerator.LocalSymbolTable.GetAllVarTypeSpecifiers())
        {
                String desc = SymbolTable.GenBasicDescriptor(spec);
                
                if (!desc.startsWith("["))
                {
                    desc = desc.substring(1, desc.length() - 1);
                }
                
                descriptors.add(desc);
        }

        int numLocals = descriptors.size();
        Object[] locals = descriptors.toArray();
        
        
        // Create labels
        Label else_label = new Label();
        Label end_label = new Label();

        // Get Condition
        condition.GenerateBytecode(mv);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z", false);
        
        // If equal to 0 (false), skip if_branch
        mv.visitJumpInsn(IFEQ, else_label);

        if_branch.GenerateBytecode(mv);
        mv.visitJumpInsn(GOTO, end_label);

        mv.visitLabel(else_label);
        mv.visitFrame(
            F_FULL, 
            numLocals, 
            locals, 
            0, 
            new Object[] {}
        );

        // If an else branch exists, Generate its bytecode here
        
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
            Reporter.ReportErrorAndExit(condition.toString() + ": Condition must be boolean.");
        }

        if_branch.validate(symbolTable);
        else_branch.validate(symbolTable);
        
    }

}