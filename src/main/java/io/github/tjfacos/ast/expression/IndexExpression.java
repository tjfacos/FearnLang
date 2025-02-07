package io.github.tjfacos.ast.expression;

import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;

import io.github.tjfacos.ast.type.ArraySpecifier;
import io.github.tjfacos.ast.type.PrimitiveSpecifier.PrimitiveDataType;
import io.github.tjfacos.ast.type.PrimitiveSpecifier;
import io.github.tjfacos.ast.type.TypeSpecifier;
import io.github.tjfacos.semantics.table.SymbolTable;
import io.github.tjfacos.util.Reporter;

/* IndexExpression.java
 * 
 * Represents an Index Expression in the AST (e.g list[0] ). 
 * 
 * Fields:
 *  ->  sequence: An expression that is indexable (an array or string) 
 *  ->  index: An integer expression, hat represents the index to access
 */

public class IndexExpression extends Expression {
    
    public Expression sequence;
    public Expression index;
    
    public IndexExpression(Expression id, Expression i)
    {
        sequence = id;
        index = i;
    }

    @Override
    public String toString()
    {
        return sequence.toString() + '[' + index.toString() + ']';
    }

    
    /* To generate bytecode, first generate the sequence and index bytecode, to prepare 
     * the stack. 
     * 
     * If the sequence is an array, use AALOAD instruction. Otherwise (sequence is a 
     * string), use charAt method, then cast result to string
     * 
     */
    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        
        sequence.GenerateBytecode(mv);
        index.GenerateBytecode(mv);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
        
        if (sequence.expression_type instanceof ArraySpecifier)
        {
            mv.visitInsn(AALOAD);
        } else {
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "charAt", "(I)C", false);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/String", "valueOf", "(C)Ljava/lang/String;", false);
        }
    }
    
    /* To validate, validate the sequence and index.
     * 
     * Raise an error is the sequence is not an array or string, or if the index is not 
     * an int.
     * 
     * Set expression_type to string if the sequence is a string. For arrays, take 1 
     * dimension from the specifier of a multi-dimensional array. If the array is only
     * 1D, set the expression_type to the TypeSpecifier of an element.
     * 
     * Return expression_type.
     * 
     */
    @Override
    public TypeSpecifier validate(SymbolTable symTable) {
        
        TypeSpecifier seq_type      =  sequence.validate(symTable);
        TypeSpecifier index_type    =  index.validate(symTable);

        if (seq_type.getClass() != ArraySpecifier.class && !seq_type.equals(new PrimitiveSpecifier(PrimitiveDataType.STR)))
        {
            Reporter.ReportErrorAndExit("Can only take index of Arrays and Strings.", this);
        }

        if (!index_type.equals(new PrimitiveSpecifier(PrimitiveDataType.INT)))
        {
            Reporter.ReportErrorAndExit("Index can only be an int.", this);
        }

        
        // Use seq_type to set expression type
        if (seq_type.equals(new PrimitiveSpecifier(PrimitiveDataType.STR)))
        {
            expression_type = seq_type;
            return expression_type;
        }


        ArraySpecifier seq_arr_spec = (ArraySpecifier)seq_type;
        if (seq_arr_spec.dimensionCount == 1) {
            expression_type = seq_arr_spec.element_type;
        } else {
            expression_type = new ArraySpecifier(seq_arr_spec.element_type, seq_arr_spec.dimensionCount - 1);
        }

        return expression_type;

    }
}
