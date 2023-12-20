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
        ArrayInit;

        public enum Primary
        {
            VariableReference,
            IntLiteral,
            FloatLiteral,
            BoolLiteral,
            StrLiteral,
            Index,
        }

        public enum Bool
        {
            Less,
            Greater,
            LessEq,
            GreaterEq,
            Eq,
            NotEq,
            LogicalAnd,
            LogicalOr,
            LogicalNot
        }
        
        public enum Arithmetic
        {
            Exponent,
            Mult,
            Div,
            Mod,
            Add,
            Sub
        }

    }
}
