package parser;

import parser.gen.*;

// Local Dependencies
import util.*;
import ast.*;
import ast.expression.*;
import ast.expression.Expression.ExprType;

public class ASTConstructor extends FearnGrammarBaseVisitor<ASTNode> {
    Program root = new Program();

    @Override
    public ASTNode visitStruct_def(FearnGrammarParser.Struct_defContext ctx)
    {
        return new Struct();
    }
    
    /* PRIMARY EXPRESSIONS */
    @Override
    public ASTNode visitId_expr(FearnGrammarParser.Id_exprContext ctx)
    {
        String id = ctx.getText();
        return new PrimaryExpression<String>(id, ExprType.VariableReference);
    }



    @Override
    public ASTNode visitLiteral(FearnGrammarParser.LiteralContext ctx)
    {
        switch (ctx.getStart().getType())
        {
            case FearnGrammarLexer.INT_LIT:
                return new PrimaryExpression<Integer>( Integer.valueOf(ctx.getText()), ExprType.IntLiteral);
                

            case FearnGrammarLexer.BOOL_LIT:
                return new PrimaryExpression<Boolean>( Boolean.valueOf(ctx.getText()), ExprType.BoolLiteral);
            
            case FearnGrammarLexer.FLOAT_LIT:

                // FearnLang Floats are represented by Java doubles
                return new PrimaryExpression<Double>(Double.valueOf(ctx.getText()), ExprType.FloatLiteral);
            
            case FearnGrammarLexer.STR_LIT:
                return new PrimaryExpression<String>(ctx.getText().substring( 1, ctx.getText().length() - 1), ExprType.StrLiteral);

            default:
                ErrorReporter.ReportErrorAndExit("Parse Error: Unable to Parse literal value", 3);
        }

        return new PrimaryExpression<Integer>(5, ExprType.IntLiteral);

    }



    @Override
    public ASTNode visitBrac_expr(FearnGrammarParser.Brac_exprContext ctx) { return visit(ctx.getChild(1)); }
    

    /* BINARY OPERATIONS ( 2 operands ) */

    // Arithmetic

    @Override
    public ASTNode visitMult_expr(FearnGrammarParser.Mult_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));

        return new BinaryExpression(op1, op2, ExprType.Mult);
    }

    @Override
    public ASTNode visitDiv_expr(FearnGrammarParser.Div_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));

        return new BinaryExpression(op1, op2, ExprType.Div);
    }

    @Override
    public ASTNode visitMod_expr(FearnGrammarParser.Mod_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));

        return new BinaryExpression(op1, op2, ExprType.Mod);
    }

    @Override
    public ASTNode visitAdd_expr(FearnGrammarParser.Add_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));

        return new BinaryExpression(op1, op2, ExprType.Add);
    }

    @Override
    public ASTNode visitSub_expr(FearnGrammarParser.Sub_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));

        return new BinaryExpression(op1, op2, ExprType.Sub);
    }

    @Override
    public ASTNode visitExp_expr(FearnGrammarParser.Exp_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));

        return new BinaryExpression(op1, op2, ExprType.Exponent);
    }
    


    // Boolean Logical
    @Override
    public ASTNode visitLess_expr(FearnGrammarParser.Less_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));

        return new BinaryExpression(op1, op2, ExprType.Less);
    }

    @Override
    public ASTNode visitGreater_expr(FearnGrammarParser.Greater_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));

        return new BinaryExpression(op1, op2, ExprType.Greater);
    }

    @Override
    public ASTNode visitLess_eq_expr(FearnGrammarParser.Less_eq_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));

        return new BinaryExpression(op1, op2, ExprType.LessEq);
    }

    @Override
    public ASTNode visitGreater_eq_expr(FearnGrammarParser.Greater_eq_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));

        return new BinaryExpression(op1, op2, ExprType.GreaterEq);
    }

    @Override
    public ASTNode visitEq_expr(FearnGrammarParser.Eq_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));

        return new BinaryExpression(op1, op2, ExprType.Eq);
    }

    @Override
    public ASTNode visitNot_eq_expr(FearnGrammarParser.Not_eq_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));

        return new BinaryExpression(op1, op2, ExprType.NotEq);
    }
    
    @Override
    public ASTNode visitAnd_expr(FearnGrammarParser.And_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));

        return new BinaryExpression(op1, op2, ExprType.LogicalAnd);
    }
    
    @Override
    public ASTNode visitOr_expr(FearnGrammarParser.Or_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));

        return new BinaryExpression(op1, op2, ExprType.LogicalOr);
    }
    
    /* UNARY OPERATIONS (1 operation) */
    @Override
    public ASTNode visitU_minus_expr(FearnGrammarParser.U_minus_exprContext ctx) 
    { 
        Expression op = (Expression)visit(ctx.getChild(1));

        return new UnaryExpression(op, ExprType.Negate);
    }

    @Override
    public ASTNode visitU_not_expr(FearnGrammarParser.U_not_exprContext ctx) 
    { 
        Expression op = (Expression)visit(ctx.getChild(1));

        return new UnaryExpression(op, ExprType.LogicalNot);
    }
    
    /* Misc. Expressions */

    // Function Call
    
}
