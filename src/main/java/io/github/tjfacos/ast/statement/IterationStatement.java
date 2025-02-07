package io.github.tjfacos.ast.statement;

import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.Label;

import io.github.tjfacos.ast.ASTNode;
import io.github.tjfacos.ast.Declaration;
import io.github.tjfacos.ast.expression.*;
import io.github.tjfacos.ast.type.PrimitiveSpecifier.PrimitiveDataType;
import io.github.tjfacos.ast.type.PrimitiveSpecifier;
import io.github.tjfacos.codegen.CodeGenerator;
import io.github.tjfacos.semantics.table.SymbolTable;
import io.github.tjfacos.util.Reporter;

/* IterationStatement.java
 * 
 * Represents a for loop in the AST. 
 * 
 * The class contains an init_expression (an Expression or Declaration), a 
 * continue_expression (A boolean expression, determining whether the loop 
 * should run), and an iteration_expression (running at the end of each loop).
 * 
 * It also contains the loop body, which runs every iteration.
 * 
 */

public class IterationStatement extends Statement {

    public ASTNode init_expression;
    public Expression continue_expression;
    public Expression iteration_expression;
    public CompoundStatement body;

    public IterationStatement(
        ASTNode init, Expression c_expr, Expression i_expr, CompoundStatement bod
    ) {
        init_expression = init;
        continue_expression = c_expr;
        iteration_expression = i_expr;
        body = bod;
    }

    @Override public String toString()
    {
        String d, c, i;
        if (init_expression == null) d = ""; else d = init_expression.toString();
        if (continue_expression == null) c = ""; else c = continue_expression.toString();
        if (iteration_expression == null) i = ""; else i = iteration_expression.toString();

        return String.format("for ( %s ; %s ; %s )", d, c, i);

    }

    public void GenerateBytecode(MethodVisitor mv) {
        
        /* This requires three labels, one at the top, one at the end of the body (before 
         * the iteration statement), and another at the bottom. The second label is used 
         * as a target for continue statements.
         * 
         * These labels are added to a stack, in the order (START, ITERATE, END), so they
         * can be referenced by jump statements `continue` and `return`.
         * 
         * First, generate the init_expression, and visit the start label. 
         * 
         * Then, generate the continue expression, and IFEQ (if value at top of stack 
         * equals 0 [false]) skip to the end label (don't run the body). Then, 
         * Generate the body. Visit the second label, and generate iteration statement. 
         * Finally, GOTO start.
         * 
         * Visit the end label.
         * 
         * After the start label, mv.visitFrame(Opcodes.F_FULL);
         *  ->  This common procedure is a protected method of the Statement class.
         *  ->  After the other labels, mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null) 
         *      is needed (frame remains the same)
         * 
         * Remember, all variables are initialised null at the start of any function, so 
         * the number of locals should never really change.
         */

        Label startLoopLabel = new Label();
        Label endBodyLabel = new Label();
        Label endLoopLabel = new Label();

        CodeGenerator.LabelStack.add(startLoopLabel);
        CodeGenerator.LabelStack.add(endBodyLabel);
        CodeGenerator.LabelStack.add(endLoopLabel);

        Object[] locals = GetLocalDecriptors();
        int numLocals = locals.length;

        if (init_expression != null) {
            if (init_expression instanceof Expression)
            {
                ((Expression)init_expression).GenerateBytecode(mv);
                if (((Expression)init_expression).expression_type != null) mv.visitInsn(POP);
            } else ((Declaration)init_expression).GenerateBytecode(mv);
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
        
        // Visit end of body
        mv.visitLabel(endBodyLabel);

        // Verify Frame State
        mv.visitFrame(
            F_SAME, 
            0, 
            null, 
            0, 
            null
        );

        // Generate iteration_expression (which runs at the end of every loop)
        if (iteration_expression != null) {
            iteration_expression.GenerateBytecode(mv);
            if (iteration_expression.expression_type != null) mv.visitInsn(POP);
        }

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
        CodeGenerator.LabelStack.pop();

    }
    
    public void validate(SymbolTable symbolTable) {
        /*  ->  The init expression can be either an expression, or a declaration
         *  ->  The continue expression must be a boolean value
         *  ->  The iteration expression and body must also be visited
         * 
         * It must be noted that any one of these, except the continue expression 
         * and body, can be null
         */

        // Increment Loop Depth, so jump statements like
        // break and continue nested within the body register as 
        // valid when they are validated.
        CodeGenerator.loopDepth++;

        // If an initialisation expression is present, validate it
        if (init_expression != null)
        {
            if (init_expression instanceof Expression)
            {
                ((Expression)init_expression).validate(symbolTable);
            } else {
                ((Declaration)init_expression).validate(symbolTable);
            }
        }

        // If the continue condition (loop continues until condition evaluates false)
        // is null, raise an error
        if (continue_expression == null) Reporter.ReportErrorAndExit(
            "Iteration condition missing.", this
        );

        // Raise Error is the continue expression doesn't evaluate to a boolean value
        if (!continue_expression.validate(symbolTable).equals(new PrimitiveSpecifier(PrimitiveDataType.BOOL)))
        {
            Reporter.ReportErrorAndExit("Iteration condition must be a boolean value.", this);
        }

        // If an iteration expression is present (runs at the end of every loop, e.g. i++), validate it
        if (iteration_expression != null) iteration_expression.validate(symbolTable);

        // Validate the body of the loop
        body.validate(symbolTable);

        // Decrement loop depth back to original value
        // (outside a loop, jump statements like break and continue
        // are invalid).
        CodeGenerator.loopDepth--;
    }
}
