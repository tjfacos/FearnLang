package ast.expression;

import org.objectweb.asm.MethodVisitor;

import ast.type.TypeSpecifier;
import semantics.table.SymbolTable;

public class IndexExpression extends Expression {
    
    public Expression array;
    public Expression index;
    
    public IndexExpression(Expression id, Expression i)
    {
        array = id;
        index = i;
    }

    @Override
    public String toString()
    {
        return "{" + array.toString() + "}" + '[' + index.toString() + ']';
    }

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        // TODO GenByte IndexExpression

        // Gen Array, Gen Index, cast Index to I, AALOAD
        throw new UnsupportedOperationException("Unimplemented method 'GenerateBytecode'");
    }
    
    @Override
    public TypeSpecifier validateType(SymbolTable symTable) {
        // TODO validateExpression IndexExpression

        // Not much can be done here at compile time, so just return type of an element
        throw new UnsupportedOperationException("Unimplemented method 'validateType'");
    }
}
