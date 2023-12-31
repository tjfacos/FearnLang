package ast.expression;

import org.objectweb.asm.MethodVisitor;

import ast.type.TypeSpecifier;
import semantics.table.SymbolTable;

public class BinaryExpression extends Expression {
    
    public Expression Op1;
    public Expression Op2;
    public ExprType Operator;
    
    public BinaryExpression(Expression op1, Expression op2, ExprType op)
    {
        Op1 = op1;
        Op2 = op2;
        Operator = op;
    }

    @Override
    public String toString()
    {
        return '(' + Op1.toString() + ' ' + Operator.name() + ' ' + Op2.toString() + ')';
    }

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        // TODO GenerateBytecode BinaryExpression

        // Remember to cast to primitive types before doing operations
        throw new UnsupportedOperationException("Unimplemented method 'GenerateBytecode'");
    }

    @Override
    public TypeSpecifier validateType(SymbolTable symTable) {
        // TODO validateType BinaryExpression
        throw new UnsupportedOperationException("Unimplemented method 'validateType'");
    }
}
