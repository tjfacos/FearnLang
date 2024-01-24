package ast.expression;

import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;

import ast.type.PrimitiveSpecifier.PrimitiveDataType;
import ast.type.PrimitiveSpecifier;
import ast.type.TypeSpecifier;
import semantics.table.SymbolTable;
import util.Reporter;

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
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/String", "valueOf", "(Ljava/lang/Object;)Ljava/lang/String;", false);
                return;
            case BOOL:
                mv.visitMethodInsn(INVOKESTATIC, "FearnRuntime", "Obj2B", "(Ljava/lang/Object;)Ljava/lang/Boolean;", false);
                return;
        }

    }

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
                    Reporter.ReportErrorAndExit("Cannot perform cast from " + op_type.toString() + "to int.", this);
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
                    Reporter.ReportErrorAndExit("Cannot perform cast from " + op_type.toString() + "to float.", this);
                } 
                break;
            
            case PrimitiveDataType.STR: // You can cast anything, inculding arrays and structs, to strings
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
                    Reporter.ReportErrorAndExit("Cannot perform cast from " + op_type.toString() + "to bool.", this);
                } 
                break;

            default: // This should never run, but is here for completeness
                Reporter.ReportErrorAndExit("Cannot perform cast from " + op_type.toString() + "to" + target.name(), this);
                break;
        }

        return expression_type;
    }

}
