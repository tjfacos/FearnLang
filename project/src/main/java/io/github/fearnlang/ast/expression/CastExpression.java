package io.github.fearnlang.ast.expression;

import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;

import io.github.fearnlang.ast.type.PrimitiveSpecifier.PrimitiveDataType;
import io.github.fearnlang.ast.type.PrimitiveSpecifier;
import io.github.fearnlang.ast.type.TypeSpecifier;
import io.github.fearnlang.semantics.table.SymbolTable;
import io.github.fearnlang.util.Reporter;

/* CastExpression.java
 * 
 * Represents a Type Cast in the AST. 
 * 
 * Fields:
 *  ->  target: The PrimitiveDataType being cast to 
 *  ->  Operand: The expression to be cast
 */

public class CastExpression extends Expression {
    
    public PrimitiveDataType target;
    public Expression Operand;
    
    public CastExpression(Expression operand, PrimitiveDataType targetType)
    {
        target = targetType;
        Operand = operand;
    }

    @Override
    public String toString()
    {
        return "(" + target.name().toLowerCase() + ")" + Operand.toString();
    }

    /* To generate bytecode, generate the operand, then...
     *  ->  If targeting int, call the appropriate method based on operands 
     *      expression_type, casting to Integer after
     *  ->  If targeting float, follow a similar procedure
     *  ->  If targeting str, call the Obj2Str method of the FearnRuntime
     *      (remember, generating the Operand's bytecode leaves an object 
     *      of its value at runtime on top of the JVM's operand stack)
     *  ->  If targeting bool, call the Obj2B method of the FearnRuntime
     */
    @Override
    public void GenerateBytecode(MethodVisitor mv) {

        Operand.GenerateBytecode(mv);

        switch (target) {
            case INT:
                if (Operand.expression_type.equals(new PrimitiveSpecifier(PrimitiveDataType.FLOAT)))
                {
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Double", "intValue", "()I", false);
                }

                else if (Operand.expression_type.equals(new PrimitiveSpecifier(PrimitiveDataType.STR)))
                {
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(Ljava/lang/String;)Ljava/lang/Integer;", false);
                    return;
                }

                else if (Operand.expression_type.equals(new PrimitiveSpecifier(PrimitiveDataType.BOOL)))
                {
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z", false);
                    mv.visitInsn(ICONST_0);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "compare", "(ZZ)I", false);
                } else { return; }
                
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
                return;

            case FLOAT:
                if (Operand.expression_type.equals(new PrimitiveSpecifier(PrimitiveDataType.INT)))
                {
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Double", "intValue", "()I", false);
                    mv.visitInsn(I2D);
                }

                else if (Operand.expression_type.equals(new PrimitiveSpecifier(PrimitiveDataType.STR)))
                {
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(Ljava/lang/String;)Ljava/lang/Double;", false);
                    return;
                } else { return; }
                
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
                return;

            case STR:
                mv.visitMethodInsn(INVOKESTATIC, "io/github/tjfacos/fearnlang/runtime/FearnRuntime", "Obj2Str", "(Ljava/lang/Object;)Ljava/lang/String;", false);
                return;
            case BOOL:
                mv.visitMethodInsn(INVOKESTATIC, "io/github/tjfacos/fearnlang/runtime/FearnRuntime", "Obj2B", "(Ljava/lang/Object;)Ljava/lang/Boolean;", false);
                return;
        }

    }

    /* To validate, validate the operand, and check that, for the target type, the 
     * operand.expression_type is one where that operation is valid.
     */
    @Override
    public TypeSpecifier validate(SymbolTable symTable) {
        // For Each target type, ensure the operand can be cast
        TypeSpecifier op_type = Operand.validate(symTable);

        switch (target) {
            case PrimitiveDataType.INT: // You can cast strings, floats, and bools (Boolean.compare) to integers
                if (
                        op_type.equals(new PrimitiveSpecifier(PrimitiveDataType.INT     ))
                    ||  op_type.equals(new PrimitiveSpecifier(PrimitiveDataType.FLOAT   ))
                    ||  op_type.equals(new PrimitiveSpecifier(PrimitiveDataType.STR     ))
                    ||  op_type.equals(new PrimitiveSpecifier(PrimitiveDataType.BOOL    ))
                ) {
                    expression_type = new PrimitiveSpecifier(PrimitiveDataType.INT);
                } else {
                    Reporter.ReportErrorAndExit("Cannot perform cast from " + op_type.toString() + " to int.", this);
                } 
                break;
            
            case PrimitiveDataType.FLOAT: // You can cast strings and ints to floats
                if (
                        op_type.equals(new PrimitiveSpecifier(PrimitiveDataType.INT     ))
                    ||  op_type.equals(new PrimitiveSpecifier(PrimitiveDataType.FLOAT   ))
                    ||  op_type.equals(new PrimitiveSpecifier(PrimitiveDataType.STR     ))
                ) {
                    expression_type = new PrimitiveSpecifier(PrimitiveDataType.FLOAT);
                } else {
                    Reporter.ReportErrorAndExit("Cannot perform cast from " + op_type.toString() + " to float.", this);
                } 
                break;
            
            case PrimitiveDataType.STR: // You can cast anything, including arrays and structs, to strings
                expression_type = new PrimitiveSpecifier(PrimitiveDataType.STR); 
                break;
            
            case PrimitiveDataType.BOOL: // You can cast strings, ints, and floats to bools
                if (
                        op_type.equals(new PrimitiveSpecifier(PrimitiveDataType.INT     ))
                    ||  op_type.equals(new PrimitiveSpecifier(PrimitiveDataType.STR     ))
                    ||  op_type.equals(new PrimitiveSpecifier(PrimitiveDataType.FLOAT   ))
                    ||  op_type.equals(new PrimitiveSpecifier(PrimitiveDataType.BOOL    ))
                ) {
                    expression_type = new PrimitiveSpecifier(PrimitiveDataType.BOOL);
                } else {
                    Reporter.ReportErrorAndExit("Cannot perform cast from " + op_type.toString() + " to bool.", this);
                } 
                break;

            default: break;
        }

        return expression_type;
    }

}
