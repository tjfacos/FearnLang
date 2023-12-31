package ast.expression;

import org.objectweb.asm.MethodVisitor;

import ast.ASTNode;
import ast.type.*;
import semantics.table.SymbolTable;

public abstract class Expression extends ASTNode {

    public static enum ExprType 
    {
        // MISC
        FuncCall,
        StructAttribute,
        TypeCast,
        StructInit,
        ArrayInit,
        Index,
        
        
        // PRIMARY
        VariableReference,
        IntLiteral,
        FloatLiteral,
        BoolLiteral,
        StrLiteral,
        
        // UNARY
        Negate,
        LogicalNot,
    
        // BINARY
        Eq,
        NotEq,
        
        Less,
        Greater,
        LessEq,
        GreaterEq,
        LogicalAnd,
        LogicalOr,
        Exponent,
        Mult,
        Div,
        Mod,
        Add,
        Sub
    }

    @Override public abstract String toString();
    public abstract void GenerateBytecode(MethodVisitor mv);

    public abstract TypeSpecifier validate(SymbolTable symTable);
    public TypeSpecifier expression_type;

}
