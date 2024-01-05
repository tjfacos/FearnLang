package ast.expression;

import org.objectweb.asm.MethodVisitor;

import ast.type.ArraySpecifier;
import ast.type.PrimitiveDataType;
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
        return "{" + sequence.toString() + "}" + '[' + index.toString() + ']';
    }

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        // TODO GenByte IndexExpression

        // Gen Array, Gen Index, cast Index to I, AALOAD
        throw new UnsupportedOperationException("Unimplemented method 'GenerateBytecode'");
    }
    
    @Override
    public TypeSpecifier validateType(SymbolTable symTable) {
        
        TypeSpecifier seq_type      =  sequence.validateType(symTable);
        TypeSpecifier index_type    =  index.validateType(symTable);

        if (seq_type.getClass() != ArraySpecifier.class && !seq_type.equals(new PrimitiveSpecifier(PrimitiveDataType.STR)))
        {
            Reporter.ReportErrorAndExit(toString() + ": Can only take index of Arrays and Strings.");
        }

        if (index_type.equals(new PrimitiveSpecifier(PrimitiveDataType.INT)))
        {
            Reporter.ReportErrorAndExit(toString() + ": Index can only be an int.");
        }

        // Use seq_type to set expression type

        // TODO Finish THIS

        return expression_type;

    }
}
