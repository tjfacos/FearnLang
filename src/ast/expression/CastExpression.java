package ast.expression;

import org.objectweb.asm.MethodVisitor;

import ast.type.PrimitiveDataType;
import ast.type.TypeSpecifier;
import semantics.table.SymbolTable;

public class CastExpression extends Expression {
    
    public PrimitiveDataType target;
    public Expression Op;
    
    public CastExpression(Expression operand, PrimitiveDataType targetType)
    {
        target = targetType;
        Op = operand;
    }

    @Override
    public String toString()
    {
        return "( " + Op.toString() + " -> " + target.name() + " )";
    }

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        // TODO GenBytecode CastExpression

        // Consider creating a runtime method for this
        throw new UnsupportedOperationException("Unimplemented method 'GenerateBytecode'");
    }

    @Override
    public TypeSpecifier validateType(SymbolTable symTable) {
        // TODO Auto-generated method stub

        // For Each target type, ensure the operand can be cast
        throw new UnsupportedOperationException("Unimplemented method 'validateType'");
    }

}
