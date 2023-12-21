package parser;

import parser.gen.*;

// Local Dependencies
import util.*;

import java.util.ArrayList;

import ast.*;
import ast.expression.*;
import ast.expression.AssignExpression.AssignmentOperator;
import ast.expression.Expression.ExprType;
import ast.type.PrimitiveDataType;

public class ASTConstructor extends FearnGrammarBaseVisitor<ASTNode> {
    Program root = new Program();

    
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
    
    /* UNARY OPERATIONS (1 operand) */
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

    @Override
    public ASTNode visitCast_expr(FearnGrammarParser.Cast_exprContext ctx) 
    { 
        PrimitiveDataType targetType = null;

        switch (ctx.getChild(1).getText()) {
            case "int"  : targetType = PrimitiveDataType.INT;   break;
            case "float": targetType = PrimitiveDataType.FLOAT; break;
            case "str"  : targetType = PrimitiveDataType.STR;   break;
            case "bool" : targetType = PrimitiveDataType.BOOL;  break;
            default: break;
        }

        return new CastExpression((Expression)visit(ctx.getChild(3)), targetType);
    }

    @Override
    public ASTNode visitIndex_expr(FearnGrammarParser.Index_exprContext ctx) 
    { 
        return new IndexExpression(
            (Expression)visit(ctx.getChild(0)), 
            (Expression)visit(ctx.getChild(2))
        );
    }
    
    




    /* Misc. Expressions */
    
    // Function Call
    @Override
    public ASTNode visitFn_call_expr(FearnGrammarParser.Fn_call_exprContext ctx)
    {
        String function_name = ctx.getChild(0).getText();
        ArrayList<Expression> args = new ArrayList<Expression>();

        for (int i = 2; i < ctx.getChildCount(); i += 2)
        {
            args.add((Expression)visit(ctx.getChild(i)));
        }

        return new FnCallExpression(function_name, args);
    }

    // Struct Attribute Expression (e.g. x.y )
    @Override
    public ASTNode visitStruct_attr_expr(FearnGrammarParser.Struct_attr_exprContext ctx)
    { 
        Expression struct_name = (Expression)visit(ctx.getChild(0));
        Expression attribute = (Expression)visit(ctx.getChild(2));
        
        return new StructAttrExpression(struct_name, attribute);
    }

    // Array Initialisation (e.g. (int[] x =) new int[5] {1, 2, 3, 7, 123 })
    @Override
    public ASTNode visitArray_init(FearnGrammarParser.Array_initContext ctx)
    { 
        PrimitiveDataType type = null;

        switch (ctx.getChild(1).getText()) {
            case "int"  : type = PrimitiveDataType.INT;   break;
            case "float": type = PrimitiveDataType.FLOAT; break;
            case "str"  : type = PrimitiveDataType.STR;   break;
            case "bool" : type = PrimitiveDataType.BOOL;  break;
            default: break;
        }
        
        Expression arr_length = (Expression)visit(ctx.getChild(3));
        
        ArrayList<Expression> elements = new ArrayList<Expression>();

        for (int i = 6; i < ctx.getChildCount(); i += 2)
        {
            elements.add((Expression)visit(ctx.getChild(i)));
        }

        return new ArrayInitExpression(type, arr_length, elements);
    }

    // Struct Initialisation ( e.g. ($Person y =) new Person('Kennith', 23) )
    @Override
    public ASTNode visitStruct_init(FearnGrammarParser.Struct_initContext ctx)
    { 
        String struct_id = ctx.getChild(1).getText();
        
        ArrayList<Expression> args = new ArrayList<Expression>();

        for (int i = 3; i < ctx.getChildCount(); i += 2)
        {
            args.add((Expression)visit(ctx.getChild(i)));
        }

        return new StructInitExpression(struct_id, args);
    }

    /* Assignment Expressions (e.g. x = y; x += 3; y %= 2) */
    @Override
    public ASTNode visitAssign_expression(FearnGrammarParser.Assign_expressionContext ctx)
    { 
        Expression target = (Expression)visit(ctx.getChild(0));
        Expression expression = (Expression)visit(ctx.getChild(2));
        
        AssignmentOperator op = null;

        switch (ctx.getChild(1).getText()) {
            case "="    : op = AssignmentOperator.Equals    ;   break;
            case "+="   : op = AssignmentOperator.AddEquals ;   break;
            case "-="   : op = AssignmentOperator.SubEquals ;   break;
            case "*="   : op = AssignmentOperator.MultEquals;   break;
            case "/="   : op = AssignmentOperator.DivEquals ;   break;
            case "%="   : op = AssignmentOperator.ModEquals ;   break;
            default: break;
        }


        return new AssignExpression(target, expression, op);
        
    }

































    @Override
    public ASTNode visitStruct_def(FearnGrammarParser.Struct_defContext ctx)
    {
        return new Struct();
    }



}
