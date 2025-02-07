package io.github.tjfacos.ast.expression;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.MethodVisitor;

import io.github.tjfacos.ast.type.PrimitiveSpecifier.PrimitiveDataType;
import io.github.tjfacos.ast.type.PrimitiveSpecifier;
import io.github.tjfacos.ast.type.TypeSpecifier;
import io.github.tjfacos.codegen.CodeGenerator;
import io.github.tjfacos.semantics.table.SymbolTable;

/* PrimaryExpression.java
 * 
 * This represents literals and variable references in the AST.
 * 
 * This is a generic class, which allows the object to store the value of a literal
 * using the correct data type.
 * 
 * Fields:
 *  ->  value: The value of the expression. This has a generic type to store literals 
 *      as the right type (e.g. a bool literal 'true' will be stored in a 
 *      PrimaryExpression<Boolean> object)
 *  ->  type: Indicates the type of primary expression (e.g. VariableReference, 
 *      FloatLiteral, etc.)
 */


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

    /* To generate bytecode, the procedure depends on the type of expression
     *  ->  For variable references, ALOAD is used to load local variables, 
     *      and GETSTATIC is used to retrieve global variables
     *      ->  The LocalSymbolTable is checked first, as if a local and global variable 
     *          have the same identifier, the local value takes priority.
     *  ->  For integers, SIPUSH is used to push the value to the stack, which is cast to 
     *      an Integer object
     *  ->  For float, LDC loads the value to the stack, and then it's cast to a Double 
     *      Object
     *  ->  For boolean, a 1 or a 0 is loaded to the stack, and then cast to Boolean
     *  ->  For string, LDC is used to load the value
     */
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

    /* This validate method doesn't do much, as a primary expression, isolated from context,
     * cannot be invalid. The method simply returns the relevant TypeSpecifier. For variable
     * references, this is retrieved from the Symbol Table.
     */
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
