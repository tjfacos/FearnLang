package ast.expression;

import ast.ASTNode;

public abstract class Expression extends ASTNode {

    public static enum ExprType 
    {
        FuncCall,
        StructAttribute,
        Negate,
        TypeCast,
        StructInit,
        ArrayInit,


        VariableReference,
        IntLiteral,
        FloatLiteral,
        BoolLiteral,
        StrLiteral,
        Index,


    
        Less,
        Greater,
        LessEq,
        GreaterEq,
        Eq,
        NotEq,
        LogicalAnd,
        LogicalOr,
        LogicalNot,
    
    
        Exponent,
        Mult,
        Div,
        Mod,
        Add,
        Sub
        

    }

    @Override public abstract String toString();

}
