package ast.expression;

import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;

import ast.type.ArraySpecifier;
import ast.type.PrimitiveSpecifier.PrimitiveDataType;
import ast.type.PrimitiveSpecifier;
import ast.type.TypeSpecifier;
import semantics.table.SymbolTable;
import util.Reporter;

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

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        /* If the sequence is an array, use AALOAD
         * 
         * Otherwise, use charAt, then cast to string
         * 
         */
        
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
    
    @Override
    public TypeSpecifier validate(SymbolTable symTable) {
        
        TypeSpecifier seq_type      =  sequence.validate(symTable);
        TypeSpecifier index_type    =  index.validate(symTable);

        if (seq_type.getClass() != ArraySpecifier.class && !seq_type.equals(new PrimitiveSpecifier(PrimitiveDataType.STR)))
        {
            Reporter.ReportErrorAndExit(toString() + ": Can only take index of Arrays and Strings.");
        }

        if (!index_type.equals(new PrimitiveSpecifier(PrimitiveDataType.INT)))
        {
            Reporter.ReportErrorAndExit(toString() + ": Index can only be an int.");
        }

        
        // Use seq_type to set expression type
        if (seq_type.equals(new PrimitiveSpecifier(PrimitiveDataType.STR)))
        {
            expression_type = seq_type;
            return expression_type;
        }


        ArraySpecifier seq_arr_spec = (ArraySpecifier)seq_type;
        if (seq_arr_spec.dimensions == 1) {
            expression_type = seq_arr_spec.element_type;
        } else {
            expression_type = new ArraySpecifier(seq_arr_spec.element_type, seq_arr_spec.dimensions - 1);
        }

        return expression_type;

    }
}
