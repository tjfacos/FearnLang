package ast.expression;

import org.objectweb.asm.MethodVisitor;

import ast.type.TypeSpecifier;
import semantics.table.SymbolTable;

public class StructAttrExpression extends Expression {
    
    public Expression instance;
    public Expression attribute;
    
    public StructAttrExpression(Expression n, Expression attr)
    {
        instance = n;
        attribute = attr;
    }

    @Override
    public String toString()
    {
        return  "$" + instance.toString() + "." + attribute.toString();
    }

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        // TODO GenByte StructAttrExpression

        // Gen instance, then GETFIELD
        throw new UnsupportedOperationException("Unimplemented method 'GenerateBytecode'");
    }

    @Override
    public TypeSpecifier validateType(SymbolTable symTable) {
        // TODO validateType StructAttrExpression

        // Get from StructRow in SymbolTable
        throw new UnsupportedOperationException("Unimplemented method 'validateType'");
    }
}
