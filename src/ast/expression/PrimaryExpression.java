package ast.expression;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.MethodVisitor;

import codegen.CodeGenerator;

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
        return value.toString();
    }

    public void GenerateBytecode(MethodVisitor mv)
    {
        
        // TODO : Verify this is correct

        if (type == ExprType.VariableReference)
        {
            // Identify if the Variable is a global variable
            // If yes, GETSTATIC
            if (CodeGenerator.GlobalSymbolTable.Contains(this.value.toString()))
            {
                mv.visitFieldInsn(
                    GETSTATIC, 
                    CodeGenerator.mainProgramName, 
                    this.value.toString(), 
                    CodeGenerator.GlobalSymbolTable.GetGlobalVarDescriptor(this.value.toString())
                );
            }


            // Otherwise, find index of variable (in THIS scope)
            // ALOAD <index>
            // NB: variables will be given indexes when they are declared
            else 
            {
                mv.visitVarInsn(ALOAD, CodeGenerator.GlobalSymbolTable.GetIndex(this.value.toString()));
            }
            
            
        // Handle Literals
        } else {
            
            switch (this.type) {
                case ExprType.IntLiteral:
                    mv.visitIntInsn(BIPUSH, (int)value);
                    mv.visitMethodInsn(
                        INVOKESTATIC, 
                        "java/lang/Integer", 
                        "valueOf", 
                        "(I)Ljava/lang/Integer;",
                        false
                    );
                    return;

                case ExprType.FloatLiteral:
                    mv.visitLdcInsn((Double)value);
                    mv.visitMethodInsn(
                        INVOKESTATIC, 
                        "java/lang/Double", 
                        "valueOf", 
                        "(D)Ljava/lang/Double;",
                        false
                    );
                    return;

                case ExprType.BoolLiteral:
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

                case ExprType.StrLiteral:
                    mv.visitLdcInsn(value);
                    return;
                    
                default:
                    break;
            }

        }
    }

}
