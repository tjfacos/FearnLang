package ast.expression;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.MethodVisitor;

import ast.type.PrimitiveSpecifier.PrimitiveDataType;
import ast.type.PrimitiveSpecifier;
import ast.type.TypeSpecifier;
import codegen.CodeGenerator;
import semantics.table.SymbolTable;

public class PrimaryExpression<T> extends Expression {
    public T value;
    public ExprType type;

    public PrimaryExpression(T val, ExprType t)
    {
        this.value = val;
        this.type = t;

    }

    @Override
    public String toString()
    {
        if (type == ExprType.StrLiteral) return "\"" + value.toString() + "\"";
        else return value.toString();
    }

    public void GenerateBytecode(MethodVisitor mv)
    {
        if (type == ExprType.VariableReference)
        {

            // Find index of variable (in THIS scope)
            // ALOAD <index>
            if (CodeGenerator.LocalSymbolTable.Contains(this.value.toString()))
            {
                mv.visitVarInsn(ALOAD, CodeGenerator.LocalSymbolTable.GetIndex(this.value.toString()));
            }

            // Otherwise, variable is global, GETSTATIC
            else
            {
                mv.visitFieldInsn(
                    GETSTATIC, 
                    CodeGenerator.GlobalSymbolTable.GetOwner(this.value.toString(), false), 
                    this.value.toString(), 
                    CodeGenerator.GlobalSymbolTable.GetVarDescriptor(this.value.toString())
                );
            }
            
            
        // Handle Literals
        } else {
            
            switch (this.type) {
                case IntLiteral:
                    mv.visitIntInsn(SIPUSH, (int)value);
                    mv.visitMethodInsn(
                        INVOKESTATIC, 
                        "java/lang/Integer", 
                        "valueOf", 
                        "(I)Ljava/lang/Integer;",
                        false
                    );
                    return;

                case FloatLiteral:
                    mv.visitLdcInsn((Double)value);
                    mv.visitMethodInsn(
                        INVOKESTATIC, 
                        "java/lang/Double", 
                        "valueOf", 
                        "(D)Ljava/lang/Double;",
                        false
                    );
                    return;

                case BoolLiteral:
                    if ((Boolean)this.value)    { mv.visitInsn(ICONST_1); }
                    else                        { mv.visitInsn(ICONST_0); }


                    mv.visitMethodInsn(
                        INVOKESTATIC, 
                        "java/lang/Boolean", 
                        "valueOf", 
                        "(Z)Ljava/lang/Boolean;",
                        false
                    );
                    return;

                case StrLiteral:
                    mv.visitLdcInsn(value);
                    return;
                    
                default:
                    break;
            }

        }
    }

    @Override
    public TypeSpecifier validate(SymbolTable symTable) {
        
        if ( type == ExprType.VariableReference && symTable.Contains(value.toString()))
        {
            expression_type = symTable.GetTypeSpecifier(value.toString(), false);
        }
        else if ( type == ExprType.VariableReference ) {
            expression_type = CodeGenerator.GlobalSymbolTable.GetTypeSpecifier(value.toString(), false);
        } else {
            switch (this.type) {
                case IntLiteral    : expression_type = new PrimitiveSpecifier(PrimitiveDataType.INT    ); break;
                case FloatLiteral  : expression_type = new PrimitiveSpecifier(PrimitiveDataType.FLOAT  ); break;
                case StrLiteral    : expression_type = new PrimitiveSpecifier(PrimitiveDataType.STR    ); break;
                case BoolLiteral   : expression_type = new PrimitiveSpecifier(PrimitiveDataType.BOOL   ); break;
                default: break;
            }
        }

        return expression_type;
    }

}
