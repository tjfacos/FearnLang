package io.github.tjfacos.ast.expression;

import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

import io.github.tjfacos.ast.type.PrimitiveSpecifier.PrimitiveDataType;
import io.github.tjfacos.ast.type.PrimitiveSpecifier;
import io.github.tjfacos.ast.type.TypeSpecifier;
import io.github.tjfacos.semantics.table.SymbolTable;
import io.github.tjfacos.util.Reporter;

/* UnaryExpression.java
 * 
 * This represents a unary expression (e.g. -a, !a)
 * 
 * Fields:
 *  ->  operand: The expression to operate on
 *  ->  operator: Indicates the operation to be done
 */

public class UnaryExpression extends Expression {

    public Expression operand;
    public ExprType operator;
    
    public UnaryExpression(Expression op, ExprType type)
    {

        operand = op;
        operator = type;

    }

    @Override
    public String toString()
    {
        String opString = null;

        switch (operator) {
            case Negate:
                opString = "-";
                break;
            case LogicalNot:
                opString = "!";
            default:
                break;
        }

        return opString + operand.toString();
    }

    /* To generate bytecode, the instructions used depend on the operation, and type of 
     * operand. First, the operand must be generated (value now at top of stack)
     * For negation...
     *  ->  IF the operand is an int, Convert the Integer object to a primitive I, use the 
     *      INEG instruction to negate the value, and cast the result back to Integer
     *  ->  IF the operand is an float, Convert the Double object to a primitive D, use the 
     *      DNEG instruction to negate the value, and cast the result back to Double
     * For the not operation, call the FearnRuntime.not() method.
     */
    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        
        // Generate Instructions to place operand expression on top of stack
        operand.GenerateBytecode(mv);
        
        if (operator == ExprType.Negate) {

            // Cast to I or D (as applicable), and Negate
            if (((PrimitiveSpecifier)operand.expression_type).element_type == PrimitiveDataType.INT)
            {
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
                mv.visitInsn(INEG);
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
            } else {
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D", false);
                mv.visitInsn(DNEG);
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
            }

        } else { // operator == ExprType.LogicalNot
            mv.visitMethodInsn(INVOKESTATIC, "FearnRuntime", "not", "(Ljava/lang/Boolean;)Ljava/lang/Boolean;", false);
        }
    }

    /* To validate
     *  ->  For negation, the operand must be an int or float, otherwise an error is raised
     *  ->  For logical not, the operand must evaluate to a boolean value
     */
    @Override
    public TypeSpecifier validate(SymbolTable symTable) {

        TypeSpecifier operandType = operand.validate(symTable);

        if (operator == ExprType.Negate) {
            if (operandType instanceof PrimitiveSpecifier)
            {
                switch (((PrimitiveSpecifier)operand.expression_type).element_type) {
                    case INT: expression_type = new PrimitiveSpecifier(PrimitiveDataType.INT  ); break;
                    case FLOAT: expression_type = new PrimitiveSpecifier(PrimitiveDataType.FLOAT); break;
                    default: Reporter.ReportErrorAndExit(
                        "Type Error: " + operand.toString() + " must be an INT or FLOAT value.", 
                        this
                    );
                }
            } else Reporter.ReportErrorAndExit(
                "Type Error: " + operand.toString() + " must be an INT or FLOAT value.", 
                this
            );

        } else { // Logical Not
            if (
                operandType instanceof PrimitiveSpecifier 
                && ((PrimitiveSpecifier)operand.expression_type).element_type == PrimitiveDataType.BOOL 
            ) expression_type = new PrimitiveSpecifier(PrimitiveDataType.BOOL);
            else Reporter.ReportErrorAndExit("Type Error: " + operand.toString() + " must be a Boolean value.", this);
        }
        return expression_type;
    }
}