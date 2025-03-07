package io.github.fearnlang.ast.expression;

import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

import io.github.fearnlang.ast.type.PrimitiveSpecifier.PrimitiveDataType;
import io.github.fearnlang.ast.type.PrimitiveSpecifier;
import io.github.fearnlang.ast.type.TypeSpecifier;
import io.github.fearnlang.semantics.table.SymbolTable;
import io.github.fearnlang.util.Reporter;

/* BinaryExpression.java
 * 
 * Represents a Binary Expression in the AST. 
 * 
 * Fields:
 *  ->  Op1: Left operand 
 *  ->  Op2: Right operand
 *  ->  Operation: The operation to be performed
 */

public class BinaryExpression extends Expression {
    
    public Expression Op1;
    public Expression Op2;
    public ExprType Operation;
    
    public BinaryExpression(Expression op1, Expression op2, ExprType op)
    {
        Op1 = op1;
        Op2 = op2;
        Operation = op;
    }

    @Override
    public String toString()
    {

        String opString = switch (Operation) {
            case Add        -> "+" ;
            case Sub        -> "-" ;
            case Mult       -> "*" ;
            case Div        -> "/" ;
            case Mod        -> "%" ;
            case Exponent   -> "^" ;
            case Less       -> "<" ;
            case Greater    -> ">" ;
            case LessEq     -> "<=";
            case GreaterEq  -> ">=";
            case LogicalAnd -> "&&";
            case LogicalOr  -> "||";
            case Eq         -> "==";
            case NotEq      -> "!=";
            default         -> null;
        };

        return String.format("%s %s %s", Op1.toString(), opString, Op2.toString());
    }

    /* To generate bytecode, in general...
     *  1)  Generate both Operands (casting them if necessary)
     *  2)  For each type of operation, call the corresponding instruction (or FearnRuntime 
     *      method)
     *  3)  Cast the result back to an object if necessary
     */

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        String t = "";
        
        if (Operation == ExprType.Eq || Operation == ExprType.NotEq)
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


        
        
        
        switch (Operation) {
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
                    mv.visitMethodInsn(INVOKESTATIC, "io/github/fearnlang/runtime/FearnRuntime", "concat", "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", false);
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
                    mv.visitMethodInsn(INVOKESTATIC, "io/github/fearnlang/runtime/FearnRuntime", "exp", "(II)Ljava/lang/Integer;", false);
                    return;
                } else { // Floats
                    mv.visitMethodInsn(INVOKESTATIC, "io/github/fearnlang/runtime/FearnRuntime", "exp", "(DD)Ljava/lang/Double;", false);
                    return;
                }            
            case Less:
                if (t == "int") {
                    mv.visitMethodInsn(INVOKESTATIC, "io/github/fearnlang/runtime/FearnRuntime", "less", "(II)Ljava/lang/Boolean;", false);
                    return;
                } else { // Floats
                    mv.visitMethodInsn(INVOKESTATIC, "io/github/fearnlang/runtime/FearnRuntime", "less", "(DD)Ljava/lang/Boolean;", false);
                    return;
                }            
            case LessEq:
                if (t == "int") {
                    mv.visitMethodInsn(INVOKESTATIC, "io/github/fearnlang/runtime/FearnRuntime", "less_eq", "(II)Ljava/lang/Boolean;", false);
                    return;
                } else { // Floats
                    mv.visitMethodInsn(INVOKESTATIC, "io/github/fearnlang/runtime/FearnRuntime", "less_eq", "(DD)Ljava/lang/Boolean;", false);
                    return;
                }            
            case Greater:
                if (t == "int") {
                    mv.visitMethodInsn(INVOKESTATIC, "io/github/fearnlang/runtime/FearnRuntime", "greater", "(II)Ljava/lang/Boolean;", false);
                    return;
                } else { // Floats
                    mv.visitMethodInsn(INVOKESTATIC, "io/github/fearnlang/runtime/FearnRuntime", "greater", "(DD)Ljava/lang/Boolean;", false);
                    return;
                }            
            case GreaterEq:
                if (t == "int") {
                    mv.visitMethodInsn(INVOKESTATIC, "io/github/fearnlang/runtime/FearnRuntime", "greater_eq", "(II)Ljava/lang/Boolean;", false);
                    return;
                } else { // Floats
                    mv.visitMethodInsn(INVOKESTATIC, "io/github/fearnlang/runtime/FearnRuntime", "greater_eq", "(DD)Ljava/lang/Boolean;", false);
                    return;
                }
                
        
            case Mod:
                mv.visitInsn(IREM);
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
                return;
            case LogicalAnd:
                mv.visitMethodInsn(INVOKESTATIC, "io/github/fearnlang/runtime/FearnRuntime", "and", "(Ljava/lang/Boolean;Ljava/lang/Boolean;)Ljava/lang/Boolean;", false);
                return;
            case LogicalOr:
                mv.visitMethodInsn(INVOKESTATIC, "io/github/fearnlang/runtime/FearnRuntime", "or", "(Ljava/lang/Boolean;Ljava/lang/Boolean;)Ljava/lang/Boolean;", false);
                return;
            case Eq:
                mv.visitMethodInsn(INVOKESTATIC, "io/github/fearnlang/runtime/FearnRuntime", "equals", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Boolean;", false);
                return;
            case NotEq:
                mv.visitMethodInsn(INVOKESTATIC, "io/github/fearnlang/runtime/FearnRuntime", "equals", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Boolean;", false);
                mv.visitMethodInsn(INVOKESTATIC, "io/github/fearnlang/runtime/FearnRuntime", "not", "(Ljava/lang/Boolean;)Ljava/lang/Boolean;", false);
                return;
            default:
                Reporter.ReportErrorAndExit("Error in Generating Binary Expression.", null);
                break;
        }
    }

    /* To validate...
     *  1)  Get types for both operands
     *  2)  Check they are valid types for the operation, and the same
     *      ->  Raise errors otherwise
     *  3)  Set expression_type to an appropriate TypeSpecifier, and 
     *      return it
     */
    @Override
    public TypeSpecifier validate(SymbolTable symTable) {
        
        TypeSpecifier op1_type = Op1.validate(symTable);
        TypeSpecifier op2_type = Op2.validate(symTable);
        
        switch (Operation) {

            // All cases where the operands must
            // both be numeric.
            case Mult:
            case Div:
            case Sub:
            case Exponent:
            case Less:
            case LessEq:
            case Greater:
            case GreaterEq:
                if (
                    op1_type.equals(op2_type) && 
                    (
                        op1_type.equals(new PrimitiveSpecifier(PrimitiveDataType.INT)) ||
                        op1_type.equals(new PrimitiveSpecifier(PrimitiveDataType.FLOAT))
                    )
                ) {
                    switch (Operation) {
                        case Mult:
                        case Div:
                        case Sub:
                        case Exponent:
                            expression_type = op1_type;
                            break;
                        default:
                            expression_type = new PrimitiveSpecifier(PrimitiveDataType.BOOL);
                            break;
                    }
                } else {
                    Reporter.ReportErrorAndExit("Operands must be either (a) both ints, or (b) both floats.", this);
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
                    expression_type = op1_type;
                } else {
                    Reporter.ReportErrorAndExit("Operands must be either (a) both ints, (b) both floats, or (c) both strings.", this);
                }
                break;   
            
            // Modulo only works on integers 
            case Mod:
                if ( op1_type.equals(op2_type) && op1_type.equals(new PrimitiveSpecifier(PrimitiveDataType.INT)) ) {
                    expression_type = op1_type;
                } else {
                    Reporter.ReportErrorAndExit("Operands must both be ints.", this);
                }
                break;
        
            // Both operands must be boolean
            case LogicalAnd:
            case LogicalOr:
                if ( op1_type.equals(op2_type) && op1_type.equals(new PrimitiveSpecifier(PrimitiveDataType.BOOL)) ) {
                    expression_type = op1_type;
                } else {
                    Reporter.ReportErrorAndExit("Operands must both be boolean values.", this);
                }
                break;

            // Both must be of the same type
            case Eq:
            case NotEq:
                if ( op1_type.equals(op2_type)) {
                    expression_type = new PrimitiveSpecifier(PrimitiveDataType.BOOL);
                } else {
                    Reporter.ReportErrorAndExit("Operands must both be of the same type.", this);
                }
                break;
            
            default:
                Reporter.ReportErrorAndExit("An Error has occurred.", this);
                break;
        
        }

        return expression_type;
    }
}
