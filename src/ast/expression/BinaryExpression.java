package ast.expression;

import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

import ast.type.PrimitiveDataType;
import ast.type.PrimitiveSpecifier;
import ast.type.TypeSpecifier;
import semantics.table.SymbolTable;
import util.Reporter;

public class BinaryExpression extends Expression {
    
    public Expression Op1;
    public Expression Op2;
    public ExprType Operator;
    
    public BinaryExpression(Expression op1, Expression op2, ExprType op)
    {
        Op1 = op1;
        Op2 = op2;
        Operator = op;
    }

    @Override
    public String toString()
    {
        return '(' + Op1.toString() + ' ' + Operator.name() + ' ' + Op2.toString() + ')';
    }

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        String t = "";
        
        if (Operator == ExprType.Eq || Operator == ExprType.NotEq)
        {
            // For these operations, the values on the stacks cannot be primitive
            Op1.GenerateBytecode(mv);
            Op2.GenerateBytecode(mv);
        }

        else if (Op1.expression_type.equals(new PrimitiveSpecifier(PrimitiveDataType.INT)))
        {
            // Generate operands, and cast to int
            t = "int";
            Op1.GenerateBytecode(mv);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
            Op2.GenerateBytecode(mv);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
        }

        else if (Op1.expression_type.equals(new PrimitiveSpecifier(PrimitiveDataType.FLOAT)))
        {
            t = "double";
            // Generate operands, and cast to double
            Op1.GenerateBytecode(mv);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D", false);
            Op2.GenerateBytecode(mv);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D", false);
            
        }
        
        else if (Op1.expression_type.equals(new PrimitiveSpecifier(PrimitiveDataType.STR)))
        {
            t = "str";
            Op1.GenerateBytecode(mv);
            Op2.GenerateBytecode(mv);
        }

        else if (Op1.expression_type.equals(new PrimitiveSpecifier(PrimitiveDataType.BOOL)))
        {
            t = "bool";
            Op1.GenerateBytecode(mv);
            Op2.GenerateBytecode(mv);
        }


        
        
        
        switch (Operator) {
            case Add:
                if (t == "int") {
                    // Add
                    mv.visitInsn(IADD);
                    // Cast result to Integer
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
                    return;
                } else if (t == "double") {
                    // Add
                    mv.visitInsn(DADD);
                    // Cast result to Double
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
                    return;
                } else { // String Concatenation
                    mv.visitMethodInsn(INVOKESTATIC, "FearnRuntime", "concat", "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", false);
                    return;
                }
            
            case Sub:
                if (t == "int") {
                    // Add
                    mv.visitInsn(ISUB);
                    // Cast result to Integer
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
                    return;
                } else { // Floats
                    mv.visitInsn(DSUB);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
                    return;
                }
            
            case Mult:
                if (t == "int") {
                    mv.visitInsn(IMUL);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
                    return;
                } else { // Floats
                    mv.visitInsn(DMUL);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
                    return;
                }
            case Div:
                if (t == "int") {
                    mv.visitInsn(IDIV);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
                    return;
                } else { // Floats
                    mv.visitInsn(DDIV);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
                    return;
                }
                
            case Exponent:
                if (t == "int") {
                    mv.visitMethodInsn(INVOKESTATIC, "FearnRuntime", "exp", "(II)Ljava/lang/Integer;", false);
                    return;
                } else { // Floats
                    mv.visitMethodInsn(INVOKESTATIC, "FearnRuntime", "exp", "(DD)Ljava/lang/Double;", false);
                    return;
                }            
            case Less:
                if (t == "int") {
                    mv.visitMethodInsn(INVOKESTATIC, "FearnRuntime", "less", "(II)Ljava/lang/Boolean;", false);
                    return;
                } else { // Floats
                    mv.visitMethodInsn(INVOKESTATIC, "FearnRuntime", "less", "(DD)Ljava/lang/Boolean;", false);
                    return;
                }            
            case LessEq:
                if (t == "int") {
                    mv.visitMethodInsn(INVOKESTATIC, "FearnRuntime", "less_eq", "(II)Ljava/lang/Boolean;", false);
                    return;
                } else { // Floats
                    mv.visitMethodInsn(INVOKESTATIC, "FearnRuntime", "less_eq", "(DD)Ljava/lang/Boolean;", false);
                    return;
                }            
            case Greater:
                if (t == "int") {
                    mv.visitMethodInsn(INVOKESTATIC, "FearnRuntime", "greater", "(II)Ljava/lang/Boolean;", false);
                    return;
                } else { // Floats
                    mv.visitMethodInsn(INVOKESTATIC, "FearnRuntime", "greater", "(DD)Ljava/lang/Boolean;", false);
                    return;
                }            
            case GreaterEq:
                if (t == "int") {
                    mv.visitMethodInsn(INVOKESTATIC, "FearnRuntime", "greater_eq", "(II)Ljava/lang/Boolean;", false);
                    return;
                } else { // Floats
                    mv.visitMethodInsn(INVOKESTATIC, "FearnRuntime", "greater_eq", "(DD)Ljava/lang/Boolean;", false);
                    return;
                }
                
        
            case Mod:
                mv.visitInsn(IREM);
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
                return;
            case LogicalAnd:
                mv.visitMethodInsn(INVOKESTATIC, "FearnRuntime", "and", "(Ljava/lang/Boolean;Ljava/lang/Boolean;)Ljava/lang/Boolean;", false);
                return;
            case LogicalOr:
                mv.visitMethodInsn(INVOKESTATIC, "FearnRuntime", "or", "(Ljava/lang/Boolean;Ljava/lang/Boolean;)Ljava/lang/Boolean;", false);
                return;
            case Eq:
                mv.visitMethodInsn(INVOKESTATIC, "FearnRuntime", "equals", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Boolean;", false);
                return;
            case NotEq:
                mv.visitMethodInsn(INVOKESTATIC, "FearnRuntime", "equals", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Boolean;", false);
                mv.visitMethodInsn(INVOKESTATIC, "FearnRuntime", "not", "(Ljava/lang/Boolean;)Ljava/lang/Boolean;", false);
                return;
            default:
                Reporter.ReportErrorAndExit("Error in Generating Binary Expression.");
                break;
        }
    }

    @Override
    public TypeSpecifier validateType(SymbolTable symTable) {
        
        TypeSpecifier op1_type = Op1.validateType(symTable);
        TypeSpecifier op2_type = Op2.validateType(symTable);
        
        switch (Operator) {

            // All cases where the operands must
            // both be numeric.
            case Mult:
            case Div:
            case Sub:
            case Less:
            case LessEq:
            case Greater:
            case GreaterEq:
            case Exponent:
                if (
                    op1_type.equals(op2_type) && 
                    (
                        op1_type.equals(new PrimitiveSpecifier(PrimitiveDataType.INT)) ||
                        op1_type.equals(new PrimitiveSpecifier(PrimitiveDataType.FLOAT))
                    )
                ) {
                    expression_type = op1_type; // This may cause errors, I dunno...
                } else {
                    Reporter.ReportErrorAndExit(toString() + ": Operators must be either (a) both ints, or (b) both floats.");
                }
                break;   
                
            // Can work on numbers or strings
            case Add:
                if (
                    op1_type.equals(op2_type) && 
                    (
                        op1_type.equals(new PrimitiveSpecifier(PrimitiveDataType.INT)) ||
                        op1_type.equals(new PrimitiveSpecifier(PrimitiveDataType.FLOAT)) ||
                        op1_type.equals(new PrimitiveSpecifier(PrimitiveDataType.STR))
                    )
                ) {
                    expression_type = op1_type; // This may cause errors, I dunno...
                } else {
                    Reporter.ReportErrorAndExit(toString() + ": Operators must be either (a) both ints, (b) both floats, or (c) both strings.");
                }
                break;   
            
            // Modulo only works on integers 
            case Mod:
                if ( op1_type.equals(op2_type) && op1_type.equals(new PrimitiveSpecifier(PrimitiveDataType.INT)) ) {
                    expression_type = op1_type; // This may cause errors, I dunno...
                } else {
                    Reporter.ReportErrorAndExit(toString() + ": Operators must both be ints.");
                }
                break;
        
            // Both operands must be boolean
            case LogicalAnd:
            case LogicalOr:
                if ( op1_type.equals(op2_type) && op1_type.equals(new PrimitiveSpecifier(PrimitiveDataType.BOOL)) ) {
                    expression_type = op1_type; // This may cause errors, I dunno...
                } else {
                    Reporter.ReportErrorAndExit(toString() + ": Operators must both be boolean values.");
                }
                break;

            // Both must be of the same type
            case Eq:
            case NotEq:
                if ( op1_type.equals(op2_type)) {
                    expression_type = op1_type; // This may cause errors, I dunno...
                } else {
                    Reporter.ReportErrorAndExit(toString() + ": Operators must both be of the same type.");
                }
            
            default:
                Reporter.ReportErrorAndExit(toString() + ": An Error has occured.");
                break;
        
        }

        return expression_type;
    }
}
