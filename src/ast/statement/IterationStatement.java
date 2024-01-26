package ast.statement;

import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.Label;

import ast.ASTNode;
import ast.Declaration;
import ast.expression.*;
import ast.type.PrimitiveSpecifier.PrimitiveDataType;
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
        
        return String.format("for ( %s ; %s ; %s )",
            d, c, i
        );

    }

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
         * should never really change.
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

                if (init_expression.getClass() != AssignExpression.class) mv.visitInsn(POP);
            
            } else {
                ((Declaration)init_expression).GenerateBytecode(mv);
            }
        }

        // Visit start of loop
        mv.visitLabel(startLoopLabel);

        // Verify Frame State
        
        // WARNING May break stuff
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
        /* The init expression can be either an expression, or a declaration
         * The continue expression must be a boolean value
         * The iteration expression and body must also be visited
         * It must be notes that any one of these, except the body, can be null
         */

        // Increment Loop Depth, so jump statements like
        // break and continue nested within the body register as 
        // valid.
        
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

        // If the continue condition (loop continues until condition evaluates false),
        // raise an error
        if (continue_expression == null)
        {
            Reporter.ReportErrorAndExit("Iteration condition missing.", this);
        }

        // Raise Error is the continue expression doesn evaluate to a boolean value
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
