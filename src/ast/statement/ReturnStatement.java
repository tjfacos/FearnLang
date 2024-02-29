package ast.statement;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.MethodVisitor;

import ast.expression.Expression;
import codegen.CodeGenerator;
import semantics.table.SymbolTable;
import util.Reporter;

/* ReturnStatement.java
 * 
 * Represents a return statement 
 * 
 * It contains the expression to return, which may be null.
 * 
 */

public class ReturnStatement extends JumpStatement {

    Expression expression;

    public ReturnStatement(JumpType t, Expression e)
    {
        super(t);
        expression = e;
    }

    @Override public String toString()
    {
        return "return " + (expression == null ? "" : expression.toString()) + ";";
    }

    /* To generate bytecode, use RETURN instruction if there is no expression to return.
     * Otherwise, evaluate the expression (generate its bytecode), and return the value 
     * with ARETURN.
     */
    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        if (expression == null) mv.visitInsn(RETURN);
        else {
            expression.GenerateBytecode(mv);
            mv.visitInsn(ARETURN);
        }
    }

    public void validate(SymbolTable symbolTable) {

        // Get return type from Code Generator. Then, ensure the return is of the correct type

        /* This section is complicated by the fact that both expression 
         * and CurrentReturnType can be null, causing the .equals() method
         * to throw an error.
         */

        if (CodeGenerator.CurrentReturnType == null)
        {
            // Expect expression to be null, error otherwise
            if (expression == null) return;
            else Reporter.ReportErrorAndExit("Incorrect return type, expected void", this);
        } else {
            // Expect type of expression to match current return type
            if (expression == null || !expression.validate(symbolTable).equals(CodeGenerator.CurrentReturnType)) 
            {
                Reporter.ReportErrorAndExit(
                    "Incorrect return type, expected " + CodeGenerator.CurrentReturnType.toString(), 
                    this
                );
            }
            else return;
        }
    }
}
