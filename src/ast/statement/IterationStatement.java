package ast.statement;

import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.Label;

import ast.ASTNode;
import ast.Declaration;
import ast.expression.*;
import ast.type.PrimitiveDataType;
import ast.type.PrimitiveSpecifier;
import codegen.CodeGenerator;
import semantics.table.SymbolTable;
import util.Reporter;

public class IterationStatement extends Statement {

    public ASTNode init_expression;
    public Expression continue_expression;
    public Expression iteration_expression;
    public CompoundStatement body;

    public IterationStatement(ASTNode init, Expression c_expr, Expression i_expr, CompoundStatement bod)
    {
        init_expression = init;
        continue_expression = c_expr;
        iteration_expression = i_expr;
        body = bod;
    }

    @Override public String toString()
    {
        String d, c, i;
        if (init_expression == null) { d = "null"; } else { d = init_expression.toString(); }
        if (continue_expression == null) { c = "null"; } else { c = continue_expression.toString(); }
        if (iteration_expression == null) { i = "null"; } else { i = iteration_expression.toString(); }
        
        return String.format("\n\tfor ( %s ; %s ; %s )",
            d, c, i
        );

    }

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        
        /* This requires two labels, one at the top and another at the bottom.
         * First, generate the init_expression, and visit the start label. 
         * Then, generate the continue expression, and IFEQ skip to the end label.
         * Then, Generate the body, and the iteration statement. Finally, GOTO start.
         * Visit the end label.
         * 
         * After the start label, mv.visitFrame(Opcodes.F_APPEND,2, new Object[] {"java/lang/Integer", "java/lang/Integer"}, 0, null);
            * This common procedure should be made a method of the abstract Statement class.
            * Be sure to correct selectionStatement after you do this.
         * After the second label, mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null) is needed
         * 
         * Remember, all variables are initialised null at the start of any function, so the number of locals
         * should never really change. Consider this when looking at ASMifier logs.
         * 
         * Have fun...
         */

        Label startLoopLabel = new Label();
        Label endLoopLabel = new Label();

        CodeGenerator.LabelStack.add(startLoopLabel);
        CodeGenerator.LabelStack.add(endLoopLabel);

        Object[] locals = GetLocalDecriptors();
        int numLocals = locals.length;

        if (init_expression != null) {
            if (init_expression.getClass() == Expression.class)
            {
                ((Expression)init_expression).GenerateBytecode(mv);

                if (init_expression.getClass() != AssignExpression.class) mv.visitInsn(POP);
            
            } else {
                ((Declaration)init_expression).GenerateBytecode(mv);
            }
        }

        // Visit start of loop
        mv.visitLabel(startLoopLabel);

        // Verify Frame State
        mv.visitFrame(
            F_FULL, 
            numLocals, 
            locals, 
            0, 
            new Object[] {}
        );

        // Generate continue expression, and cast to primitive boolean (Z)
        continue_expression.GenerateBytecode(mv);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z", false);
        
        // If the condition is false, skip the body, to the end label
        mv.visitJumpInsn(IFEQ, endLoopLabel);

        // Generate the body
        body.GenerateBytecode(mv);

        // Generate iteration_expression (which runs at the end of every loop)
        if (iteration_expression != null) iteration_expression.GenerateBytecode(mv);

        // Return to the start of the loop
        mv.visitJumpInsn(GOTO, startLoopLabel);

        // Visit end of loop
        mv.visitLabel(endLoopLabel);

        mv.visitFrame(
            F_SAME, 
            0, 
            null, 
            0, 
            null
        );


        // Remove the labels from the global LabelStack
        CodeGenerator.LabelStack.pop();
        CodeGenerator.LabelStack.pop();

    }
    
    public void validate(SymbolTable symbolTable) {
        /* The init expression can be either an expression, or a declaration
         * The continue expression must be a boolean value
         * The iteration expression and body must also be visited
         * It must be notes that any one of these, except the body, can be null
         */

        if (init_expression != null)
        {
            if (init_expression.getClass() == Expression.class)
            {
                ((Expression)init_expression).validate(symbolTable);
            } else {
                ((Declaration)init_expression).validate(symbolTable);
            }
        }

        if (continue_expression == null)
        {
            Reporter.ReportErrorAndExit(toString() + ": Iteration condition missing.");
        }

        if (!continue_expression.validate(symbolTable).equals(new PrimitiveSpecifier(PrimitiveDataType.BOOL)))
        {
            Reporter.ReportErrorAndExit(toString() + ": Iteration condition must be a boolean value.");
        }

        if (iteration_expression != null) iteration_expression.validate(symbolTable);

        body.validate(symbolTable);

    }
    
}
