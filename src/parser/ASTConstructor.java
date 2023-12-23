package parser;

import parser.gen.*;

// Local Dependencies
import util.*;

import java.util.ArrayList;

import ast.*;
import ast.expression.*;
import ast.expression.AssignExpression.AssignmentOperator;
import ast.expression.Expression.ExprType;
import ast.type.ArraySpecifier;
import ast.type.PrimitiveDataType;
import ast.type.PrimitiveSpecifier;
import ast.type.StructInstanceSpecifier;
import ast.type.TypeSpecifier;
import ast.statement.*;

public class ASTConstructor extends FearnGrammarBaseVisitor<ASTNode> {
    Program root = new Program();

    
    /* PRIMARY EXPRESSIONS */
    @Override
    public Expression visitId_expr(FearnGrammarParser.Id_exprContext ctx)
    {
        String id = ctx.getText();
        return new PrimaryExpression<String>(id, ExprType.VariableReference);
    }
    
    
    
    @Override
    public Expression visitLiteral(FearnGrammarParser.LiteralContext ctx)
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
    public Expression visitBrac_expr(FearnGrammarParser.Brac_exprContext ctx) { return (Expression)visit(ctx.getChild(1)); }
    
    
    /* BINARY OPERATIONS ( 2 operands ) */
    
    // Arithmetic
    
    @Override
    public BinaryExpression visitMult_expr(FearnGrammarParser.Mult_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));
        
        return new BinaryExpression(op1, op2, ExprType.Mult);
    }
    
    @Override
    public BinaryExpression visitDiv_expr(FearnGrammarParser.Div_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));
        
        return new BinaryExpression(op1, op2, ExprType.Div);
    }
    
    @Override
    public BinaryExpression visitMod_expr(FearnGrammarParser.Mod_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));
        
        return new BinaryExpression(op1, op2, ExprType.Mod);
    }
    
    @Override
    public BinaryExpression visitAdd_expr(FearnGrammarParser.Add_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));
        
        return new BinaryExpression(op1, op2, ExprType.Add);
    }
    
    @Override
    public BinaryExpression visitSub_expr(FearnGrammarParser.Sub_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));
        
        return new BinaryExpression(op1, op2, ExprType.Sub);
    }
    
    @Override
    public BinaryExpression visitExp_expr(FearnGrammarParser.Exp_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));
        
        return new BinaryExpression(op1, op2, ExprType.Exponent);
    }
    
    
    
    // Boolean Logical
    @Override
    public BinaryExpression visitLess_expr(FearnGrammarParser.Less_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));
        
        return new BinaryExpression(op1, op2, ExprType.Less);
    }
    
    @Override
    public BinaryExpression visitGreater_expr(FearnGrammarParser.Greater_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));
        
        return new BinaryExpression(op1, op2, ExprType.Greater);
    }
    
    @Override
    public BinaryExpression visitLess_eq_expr(FearnGrammarParser.Less_eq_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));
        
        return new BinaryExpression(op1, op2, ExprType.LessEq);
    }
    
    @Override
    public BinaryExpression visitGreater_eq_expr(FearnGrammarParser.Greater_eq_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));
        
        return new BinaryExpression(op1, op2, ExprType.GreaterEq);
    }
    
    @Override
    public BinaryExpression visitEq_expr(FearnGrammarParser.Eq_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));
        
        return new BinaryExpression(op1, op2, ExprType.Eq);
    }
    
    @Override
    public BinaryExpression visitNot_eq_expr(FearnGrammarParser.Not_eq_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));
        
        return new BinaryExpression(op1, op2, ExprType.NotEq);
    }
    
    @Override
    public BinaryExpression visitAnd_expr(FearnGrammarParser.And_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));
        
        return new BinaryExpression(op1, op2, ExprType.LogicalAnd);
    }
    
    @Override
    public BinaryExpression visitOr_expr(FearnGrammarParser.Or_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.getChild(0));
        Expression op2 = (Expression)visit(ctx.getChild(2));
        
        return new BinaryExpression(op1, op2, ExprType.LogicalOr);
    }
    
    /* UNARY OPERATIONS (1 operand) */
    @Override
    public UnaryExpression visitU_minus_expr(FearnGrammarParser.U_minus_exprContext ctx) 
    { 
        Expression op = (Expression)visit(ctx.getChild(1));
        
        return new UnaryExpression(op, ExprType.Negate);
    }
    
    @Override
    public UnaryExpression visitU_not_expr(FearnGrammarParser.U_not_exprContext ctx) 
    { 
        Expression op = (Expression)visit(ctx.getChild(1));
        
        return new UnaryExpression(op, ExprType.LogicalNot);
    }

    @Override
    public CastExpression visitCast_expr(FearnGrammarParser.Cast_exprContext ctx) 
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
    public IndexExpression visitIndex_expr(FearnGrammarParser.Index_exprContext ctx) 
    { 
        return new IndexExpression(
            (Expression)visit(ctx.getChild(0)), 
            (Expression)visit(ctx.getChild(2))
        );
    }
    
    




    /* Misc. Expressions */
    
    // Function Call
    @Override
    public FnCallExpression visitFn_call_expr(FearnGrammarParser.Fn_call_exprContext ctx)
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
    public StructAttrExpression visitStruct_attr_expr(FearnGrammarParser.Struct_attr_exprContext ctx)
    { 
        Expression struct_name = (Expression)visit(ctx.getChild(0));
        Expression attribute = (Expression)visit(ctx.getChild(2));
        
        return new StructAttrExpression(struct_name, attribute);
    }

    // Array Initialisation (e.g. (int[] x =) new int[5] {1, 2, 3, 7, 123 })
    @Override
    public ArrayInitExpression visitArray_init(FearnGrammarParser.Array_initContext ctx)
    { 
        TypeSpecifier type = (TypeSpecifier)visit(ctx.getChild(1));
        
        ArrayList<Expression> dims = new ArrayList<Expression>();
        
        int i = 3;

        for (; ctx.getChild(i-1).getText().equals("["); i+= 3)
        {
            dims.add((Expression)visit(ctx.getChild(i)));
        }

        ArrayBody init = (ArrayBody)visit(ctx.getChild(--i));

        return new ArrayInitExpression(type, dims, init);
    }

    // Curly List (e.g. { 1 , 2 })
    @Override
    public Expression visitArray_body(FearnGrammarParser.Array_bodyContext ctx)
    {
        ArrayList<Expression> elements = new ArrayList<Expression>();

        for (int i = 1; i < ctx.getChildCount() ; i += 2)
        {
            elements.add((Expression)visit(ctx.getChild(i)));
        }

        return new ArrayBody(elements);
    }

    // Struct Initialisation ( e.g. ($Person y =) new Person('Kennith', 23) )
    @Override
    public StructInitExpression visitStruct_init(FearnGrammarParser.Struct_initContext ctx)
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
    public AssignExpression visitAssign_expression(FearnGrammarParser.Assign_expressionContext ctx)
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

    /* TYPE SPECIFIERS */
    public PrimitiveSpecifier visitType_specifier_primitive(FearnGrammarParser.Type_specifier_primitiveContext ctx)
    {
        PrimitiveDataType type = null;

        switch (ctx.getText()) {
            case "int"  : type = PrimitiveDataType.INT;   break;
            case "float": type = PrimitiveDataType.FLOAT; break;
            case "str"  : type = PrimitiveDataType.STR;   break;
            case "bool" : type = PrimitiveDataType.BOOL;  break;
        }

        return new PrimitiveSpecifier(type);
    
    }

	public ArraySpecifier visitType_specifier_arr(FearnGrammarParser.Type_specifier_arrContext ctx)
	{

        TypeSpecifier type = (TypeSpecifier)visit(ctx.getChild(0));
        Integer dims = ctx.getChildCount() - 1;

        return new ArraySpecifier(type, dims);
    }

	public StructInstanceSpecifier visitType_specifier_struct(FearnGrammarParser.Type_specifier_structContext ctx)
	{
        return new StructInstanceSpecifier(ctx.getChild(1).getText());
    }




    /* DECLARATION */
    public Declaration visitDeclaration(FearnGrammarParser.DeclarationContext ctx)
    {
        String identifer = ctx.getChild(1).getText();
        TypeSpecifier type_spec = (TypeSpecifier)visit(ctx.getChild(3));

        Expression init_expression = null;
        
        if (ctx.getChildCount() > 5)
        {
            init_expression = (Expression)visit(ctx.getChild(5));
        }

        return new Declaration(identifer, type_spec, init_expression);

    }



    /* STATEMENTS */

    // Compound Statement
    @Override
    public CompoundStatement visitCompound_statement(FearnGrammarParser.Compound_statementContext ctx)
    {
        
        
        ArrayList<Declaration> local_decls = new ArrayList<Declaration>();
        ArrayList<Statement> local_stmts = new ArrayList<Statement>();
        
        int i = 1;

        for (
            ; 
            !ctx.getChild(i).getText().equals("}") && ctx.getChild(i).getChild(0).getText().equals("let"); 
            i++
        )
        {
            local_decls.add((Declaration)visit(ctx.getChild(i)));
        }
        
        for (; i < ctx.getChildCount() - 1; i++)
        {
            local_stmts.add((Statement)visit(ctx.getChild(i)));
        }
        
            
        return new CompoundStatement(local_decls, local_stmts);
            
    }
        
    
    
    // Expression Statements
    @Override
    public ExpressionStatement visitSimple_expr_stmt(FearnGrammarParser.Simple_expr_stmtContext ctx)
    {
        return new ExpressionStatement((Expression)visit(ctx.getChild(0)), false);
    }
    
    @Override
    public ExpressionStatement visitAssign_expr_stmt(FearnGrammarParser.Assign_expr_stmtContext ctx)
    {
        return new ExpressionStatement((Expression)visit(ctx.getChild(0)), true);
    }



    // Selection Statements
    @Override
    public SelectionStatement visitSingle_if(FearnGrammarParser.Single_ifContext ctx)
    {

        return new SelectionStatement(
            (Expression)visit(ctx.getChild(2)),
            (CompoundStatement)visit(ctx.getChild(4)),
            null
        );
    }
    
    @Override
    public SelectionStatement visitIf_else(FearnGrammarParser.If_elseContext ctx)
    {
        Statement else_branch = null;

        if (ctx.getChildCount() == 7)
        {
            else_branch = (Statement)visit(ctx.getChild(6));
        }

        return new SelectionStatement(
            (Expression)visit(ctx.getChild(2)),
            (CompoundStatement)visit(ctx.getChild(4)),
            else_branch
        );
    }
    
    @Override
    public SelectionStatement visitIf_else_chain(FearnGrammarParser.If_else_chainContext ctx)
    {
        Statement else_branch = null;

        if (ctx.getChildCount() == 7)
        {
            else_branch = (Statement)visit(ctx.getChild(6));
        }

        return new SelectionStatement(
            (Expression)visit(ctx.getChild(2)),
            (CompoundStatement)visit(ctx.getChild(4)),
            else_branch
        );
    }

    // Iteration Statement
    @Override
    public Statement visitIteration_statement(FearnGrammarParser.Iteration_statementContext ctx)
    {
        Declaration decl = null;
        Expression cont_expr = null;
        AssignExpression iter_expr = null;
        CompoundStatement body = null;


        if (ctx.declaration() != null)          { decl =        (Declaration)visit(ctx.declaration());              }
        if (ctx.expression() != null)           { cont_expr =   (Expression)visit(ctx.expression());                }
        if (ctx.assign_expression() != null)    { iter_expr =   (AssignExpression)visit(ctx.assign_expression());   }

        body = (CompoundStatement)visit(ctx.compound_statement());

        return new IterationStatement(decl, cont_expr, iter_expr, body);
    
    }
	    
    
	
        
    
	

    // // Jump Statements
    // @Override
    // public Statement visitCont_stmt(FearnGrammarParser.Cont_stmtContext ctx)
    // {

    // }
	
	// @Override
    // public Statement visitBreak_stmt(FearnGrammarParser.Break_stmtContext ctx)
    // {

    // }
	
	// @Override
    // public Statement visitReturn_stmt(FearnGrammarParser.Return_stmtContext ctx)
    // {

    // }
	




    /* FUNCTIONS AND STRUCTURES */

    // Function Definition

    // Parameter

    // Struct Definition


    /* Program (root of AST) */

























}
