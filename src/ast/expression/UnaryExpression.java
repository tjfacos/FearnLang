package ast.expression;

import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

import ast.type.PrimitiveDataType;
import ast.type.PrimitiveSpecifier;
import ast.type.TypeSpecifier;
import semantics.table.SymbolTable;
import util.Reporter;

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
        return '(' + operator.name() + ' ' + operand.toString() + ')';
    }

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

    @Override
    public TypeSpecifier validate(SymbolTable symTable) {
        if (operator == ExprType.Negate) {
            if (operand.validate(symTable).getClass() == PrimitiveSpecifier.class)
            {
                switch (((PrimitiveSpecifier)operand.expression_type).element_type) {
                    case INT:     expression_type = new PrimitiveSpecifier(PrimitiveDataType.INT  ); break;
                    case FLOAT:   expression_type = new PrimitiveSpecifier(PrimitiveDataType.FLOAT); break;

                    default: Reporter.ReportErrorAndExit("Type Error: " + operand.toString() + " must be an INT or FLOAT value.");break;
                
                }
            }

        } else { // Logical Not
            if (operand.validate(symTable).getClass() == PrimitiveSpecifier.class && ((PrimitiveSpecifier)operand.expression_type).element_type == PrimitiveDataType.BOOL ) { expression_type = new PrimitiveSpecifier(PrimitiveDataType.BOOL); }
            else { Reporter.ReportErrorAndExit("Type Error: " + operand.toString() + " must be a Boolean value."); }
        }

        return expression_type;

    }

}
