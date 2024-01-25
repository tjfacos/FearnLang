package parser;

import java.util.ArrayList;
import java.util.Stack;

import parser.gen.*;
import semantics.table.*;
import util.*;

import ast.*;
import ast.expression.*;
import ast.expression.AssignExpression.AssignmentOperator;
import ast.expression.Expression.ExprType;
import ast.function.Function;
import ast.function.Parameter;
import ast.type.ArraySpecifier;
import ast.type.PrimitiveSpecifier.PrimitiveDataType;
import codegen.ImportCompiler;
import ast.type.PrimitiveSpecifier;
import ast.type.StructInstanceSpecifier;
import ast.type.TypeSpecifier;
import ast.statement.*;
import ast.statement.JumpStatement.JumpType;

public class ASTConstructor extends FearnGrammarBaseVisitor<ASTNode> {
    
    /* SYMBOL ANALYSIS */
    
    Stack<String> symAnalysisStack = new Stack<String>();


    /* PRIMARY EXPRESSIONS */
    @Override
    public Expression visitId_expr(FearnGrammarParser.Id_exprContext ctx)
    {
        String id = ctx.getText();

        if (!symAnalysisStack.contains(id))
        {
            Reporter.ReportErrorAndExit(String.format(
                " Line %s : Variable Identifer Unknown in Scope: %s .",
                ctx.getStart().getLine(),
                id
            ), null);
        }

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
                Reporter.ReportErrorAndExit("Parse Error: Unable to Parse literal value", null);
        }
        
        return null;
        
    }
    
    
    
    @Override
    public Expression visitBrac_expr(FearnGrammarParser.Brac_exprContext ctx) { return (Expression)visit(ctx.expression()); }
    
    
    /* BINARY OPERATIONS ( 2 operands ) */
    
    // Arithmetic
    
    @Override
    public BinaryExpression visitMult_expr(FearnGrammarParser.Mult_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.expression(0));
        Expression op2 = (Expression)visit(ctx.expression(1));
        
        ExprType type = null;

        switch (ctx.op.getText()) {
            case "*":
                type = ExprType.Mult;
                break;
            case "/":
                type = ExprType.Div;
                break;
            case "%":
                type = ExprType.Mod;
                break;
            default:
                break;
        }

        assert(type != null);

        return new BinaryExpression(op1, op2, type);
    }
    
    @Override
    public BinaryExpression visitAdd_expr(FearnGrammarParser.Add_exprContext ctx) 
    {
        Expression op1 = (Expression)visit(ctx.expression(0));
        Expression op2 = (Expression)visit(ctx.expression(1));
        
        ExprType type = null;

        switch (ctx.op.getText()) {
            case "+":
                type = ExprType.Add;
                break;
            case "-":
                type = ExprType.Sub;
                break;
            default:
                break;
        }

        assert(type != null);

        return new BinaryExpression(op1, op2, type);
    }
    
    @Override
    public BinaryExpression visitExp_expr(FearnGrammarParser.Exp_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.expression(0));
        Expression op2 = (Expression)visit(ctx.expression(1));
        
        return new BinaryExpression(op1, op2, ExprType.Exponent);
    }
    
    
    
    // Boolean Logical
    @Override
    public BinaryExpression visitLess_expr(FearnGrammarParser.Less_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.expression(0));
        Expression op2 = (Expression)visit(ctx.expression(1));
        
        return new BinaryExpression(op1, op2, ExprType.Less);
    }
    
    @Override
    public BinaryExpression visitGreater_expr(FearnGrammarParser.Greater_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.expression(0));
        Expression op2 = (Expression)visit(ctx.expression(1));
        
        return new BinaryExpression(op1, op2, ExprType.Greater);
    }
    
    @Override
    public BinaryExpression visitLess_eq_expr(FearnGrammarParser.Less_eq_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.expression(0));
        Expression op2 = (Expression)visit(ctx.expression(1));
        
        return new BinaryExpression(op1, op2, ExprType.LessEq);
    }
    
    @Override
    public BinaryExpression visitGreater_eq_expr(FearnGrammarParser.Greater_eq_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.expression(0));
        Expression op2 = (Expression)visit(ctx.expression(1));
        
        return new BinaryExpression(op1, op2, ExprType.GreaterEq);
    }
    
    @Override
    public BinaryExpression visitEq_expr(FearnGrammarParser.Eq_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.expression(0));
        Expression op2 = (Expression)visit(ctx.expression(1));
        
        return new BinaryExpression(op1, op2, ExprType.Eq);
    }
    
    @Override
    public BinaryExpression visitNot_eq_expr(FearnGrammarParser.Not_eq_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.expression(0));
        Expression op2 = (Expression)visit(ctx.expression(1));
        
        return new BinaryExpression(op1, op2, ExprType.NotEq);
    }
    
    @Override
    public BinaryExpression visitAnd_expr(FearnGrammarParser.And_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.expression(0));
        Expression op2 = (Expression)visit(ctx.expression(1));
        
        return new BinaryExpression(op1, op2, ExprType.LogicalAnd);
    }
    
    @Override
    public BinaryExpression visitOr_expr(FearnGrammarParser.Or_exprContext ctx) 
    { 
        Expression op1 = (Expression)visit(ctx.expression(0));
        Expression op2 = (Expression)visit(ctx.expression(1));
        
        return new BinaryExpression(op1, op2, ExprType.LogicalOr);
    }
    
    /* UNARY OPERATIONS (1 operand) */
    @Override
    public UnaryExpression visitU_minus_expr(FearnGrammarParser.U_minus_exprContext ctx) 
    { 
        Expression op = (Expression)visit(ctx.expression());
        
        return new UnaryExpression(op, ExprType.Negate);
    }
    
    @Override
    public UnaryExpression visitU_not_expr(FearnGrammarParser.U_not_exprContext ctx) 
    { 
        Expression op = (Expression)visit(ctx.expression());
        
        return new UnaryExpression(op, ExprType.LogicalNot);
    }

    @Override
    public IncrExpression visitPost_inc_expr(FearnGrammarParser.Post_inc_exprContext ctx)
    {
        Boolean isDecrement = false;
        if(ctx.op.getText().equals("--"))
        {
            isDecrement = true;
        }

        return new IncrExpression((Expression)visit(ctx.expression()), isDecrement, false);
    }

    @Override
    public IncrExpression visitPre_inc_expr(FearnGrammarParser.Pre_inc_exprContext ctx)
    {
        Boolean isDecrement = false;
        if(ctx.op.getText().equals("--"))
        {
            isDecrement = true;
        }

        return new IncrExpression((Expression)visit(ctx.expression()), isDecrement, true);
    }




    @Override
    public CastExpression visitCast_expr(FearnGrammarParser.Cast_exprContext ctx) 
    { 
        PrimitiveDataType targetType = null;

        switch (ctx.type_name().getText()) {
            case "int"  : targetType = PrimitiveDataType.INT;   break;
            case "float": targetType = PrimitiveDataType.FLOAT; break;
            case "str"  : targetType = PrimitiveDataType.STR;   break;
            case "bool" : targetType = PrimitiveDataType.BOOL;  break;
            default: break;
        }

        return new CastExpression((Expression)visit(ctx.expression()), targetType);
    }

    @Override
    public IndexExpression visitIndex_expr(FearnGrammarParser.Index_exprContext ctx) 
    { 
        return new IndexExpression(
            (Expression)visit(ctx.expression(0)), 
            (Expression)visit(ctx.expression(1))
        );
    }
    
    




    /* Misc. Expressions */
    
    // Function Call
    @Override
    public FnCallExpression visitFn_call_expr(FearnGrammarParser.Fn_call_exprContext ctx)
    {
        String function_name = ctx.IDENTIFIER().getText();
        ArrayList<Expression> args = new ArrayList<Expression>();

        for (int i = 0; i < ctx.expression().size(); i++)
        {
            args.add((Expression)visit(ctx.expression(i)));
        }

        return new FnCallExpression(function_name, args);
    }

    // Dot Expressions (e.g. x.y ) / 
    @SuppressWarnings("rawtypes")
    @Override
    public Expression visitDot_expr(FearnGrammarParser.Dot_exprContext ctx)
    { 

        Expression predot = (Expression)visit(ctx.expression(0));
        Expression postdot = (Expression)visit(ctx.expression(1));

        /* EITHER:
         * A) postdot is a function call
         * B) postdot is an identifier (a variable reference)
         * C) Input in erroneous
         */


        if (postdot.getClass() == FnCallExpression.class)
        {
            // This should match a function call using universal function notation
            // e.g. a.equals(b) == equals(a, b)

            FnCallExpression Ufunction_call = (FnCallExpression)postdot;
    
            Ufunction_call.arguments.add(0, predot);
            return Ufunction_call;
        }
        else if (postdot.getClass() == PrimaryExpression.class && ((PrimaryExpression)postdot).type == ExprType.VariableReference)
        {
            return new StructAttrExpression(predot, ctx.expression(1).getText());
        }
        else {
            Reporter.ReportErrorAndExit(String.format(
                "%s.%s is not an attribute expression or a function call.", 
                predot.toString(), 
                postdot.toString()
            ), null);
            return null;
        }
    }

    // Array Initialisation (e.g. new int[] {1, 2, 3, 7, 123 } / new str[5])
    @Override
    public ArrayInitExpression visitArray_init(FearnGrammarParser.Array_initContext ctx)
    { 
        TypeSpecifier type = (TypeSpecifier)visit(ctx.getChild(1));
        
        ArrayList<Expression> dims = new ArrayList<Expression>();
        
        for (int i = 0; i < ctx.expression().size(); i++)
        {
            dims.add((Expression)visit(ctx.expression(i)));
        }

        ArrayBody init = null;
        
        if (ctx.array_body() != null)
        {
            init = (ArrayBody)visit(ctx.array_body());
        
            for (int i = 0; i < ctx.getChildCount() - 3; i++)
            {
                dims.add(null);
            }
        }
        

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
        String struct_id = ctx.IDENTIFIER().getText();
        
        ArrayList<Expression> args = new ArrayList<Expression>();

        for (int i = 0; i < ctx.expression().size(); i++)
        {
            args.add((Expression)visit(ctx.expression(i)));
        }

        return new StructInitExpression(struct_id, args);
    }

    /* Assignment Expressions (e.g. x = y; x += 3; y %= 2) */
    @Override
    public AssignExpression visitAssign_expression(FearnGrammarParser.Assign_expressionContext ctx)
    { 
        Expression target = (Expression)visit(ctx.expression(0));
        Expression expression = (Expression)visit(ctx.expression(1));
        
        AssignmentOperator op = null;

        switch (ctx.assignment_operator().getText()) {
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
        return new StructInstanceSpecifier(ctx.IDENTIFIER().getText());
    }




    /* DECLARATION */
    public Declaration visitDeclaration(FearnGrammarParser.DeclarationContext ctx)
    {
        String identifer = ctx.IDENTIFIER().getText();
        symAnalysisStack.push(identifer);
        TypeSpecifier type_spec = (TypeSpecifier)visit(ctx.type_specifier());

        Expression init_expression = null;
        
        if (ctx.getChildCount() > 5)
        {
            init_expression = (Expression)visit(ctx.expression());
        }

        symTabStack.peek().addRow(
            new VariableRow(
                identifer, 
                type_spec
            )
        );

        return new Declaration(identifer, type_spec, init_expression);

    }



    /* STATEMENTS */

    // Compound Statement
    @Override
    public CompoundStatement visitCompound_statement(FearnGrammarParser.Compound_statementContext ctx)
    {
        
        
        int numOfSyms = symAnalysisStack.size();

        ArrayList<Declaration> local_decls = new ArrayList<Declaration>();
        ArrayList<Statement> local_stmts = new ArrayList<Statement>();
        
        for (int i = 0; i < ctx.declaration().size(); i++)
        {
            local_decls.add((Declaration)visit(ctx.declaration(i)));
        }

        for (int i = 0; i < ctx.statement().size(); i++)
        {
            local_stmts.add((Statement)visit(ctx.statement(i)));
        }   
        
        // Remove Symbols added within the compound statement
        for (int i = 0; i < symAnalysisStack.size() - numOfSyms; i++)
        {
            symAnalysisStack.pop();
        }
            
        return new CompoundStatement(local_decls, local_stmts);
            
    }
        
    
    
    // Expression Statements
    @Override
    public ExpressionStatement visitSimple_expr_stmt(FearnGrammarParser.Simple_expr_stmtContext ctx)
    {
        return new ExpressionStatement((Expression)visit(ctx.expression()), false);
    }
    
    @Override
    public ExpressionStatement visitAssign_expr_stmt(FearnGrammarParser.Assign_expr_stmtContext ctx)
    {
        return new ExpressionStatement((Expression)visit(ctx.assign_expression()), true);
    }



    // Selection Statements
    @Override
    public SelectionStatement visitSingle_if(FearnGrammarParser.Single_ifContext ctx)
    {

        return new SelectionStatement(
            (Expression)visit(ctx.expression()),
            (CompoundStatement)visit(ctx.compound_statement()),
            null
        );
    }
    
    @Override
    public SelectionStatement visitIf_else(FearnGrammarParser.If_elseContext ctx)
    {

        return new SelectionStatement(
            (Expression)visit(ctx.expression()),
            (CompoundStatement)visit(ctx.compound_statement(0)),
            (Statement)visit(ctx.compound_statement(1))
        );
    }
    
    @Override
    public SelectionStatement visitIf_else_chain(FearnGrammarParser.If_else_chainContext ctx)
    {

        return new SelectionStatement(
            (Expression)visit(ctx.expression()),
            (CompoundStatement)visit(ctx.compound_statement()),
            (Statement)visit(ctx.selection_statement())
        );
    }

    // Iteration Statement
    @Override
    public IterationStatement visitIteration_statement(FearnGrammarParser.Iteration_statementContext ctx)
    {
        ASTNode init = null;
        Expression cont_expr = null;
        Expression iter_expr = null;
        CompoundStatement body = null;

        if (!ctx.init_expression().getText().equals(";")) 
        {
            init = visit(ctx.init_expression().getChild(0)); // Either an Expression or a Declaration
        }

        if (ctx.continue_condition() != null)   { cont_expr =   (Expression)visit(ctx.continue_condition().getChild(0));            }
        if (ctx.iteration_expression() != null) { iter_expr =   (Expression)visit(ctx.iteration_expression().getChild(0));    }

        body = (CompoundStatement)visit(ctx.compound_statement());

        return new IterationStatement(init, cont_expr, iter_expr, body);
    
    }
	    
    
	
        
    
	

    // Jump Statements
    @Override
    public JumpStatement visitCont_stmt(FearnGrammarParser.Cont_stmtContext ctx)
    {
        return new JumpStatement(JumpType.Continue);
    }
	
	@Override
    public JumpStatement visitBreak_stmt(FearnGrammarParser.Break_stmtContext ctx)
    {
        return new JumpStatement(JumpType.Break);
    }
	
	@Override
    public ReturnStatement visitReturn_stmt(FearnGrammarParser.Return_stmtContext ctx)
    {
        Expression expr = null;
        if (ctx.expression() != null) expr = (Expression)visit(ctx.expression());

        return new ReturnStatement(JumpType.Return, expr);
    }
	




    /* FUNCTIONS AND STRUCTURES */

    // Function Definition
    @Override
    public Function visitFunction(FearnGrammarParser.FunctionContext ctx)
    {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();
        TypeSpecifier return_type = null;
        Boolean is_void = false;

        if ( ctx.type_specifier() == null ) {
            is_void = true;
        } else {
            return_type = (TypeSpecifier)visit(ctx.type_specifier());
        }

        for (int i = 0; i < ctx.parameter().size(); i++)
        {
            // Visit Parameter
            Parameter param = (Parameter)visit(ctx.parameter(i));
            
            // Add parameter to symbol table
            symTabStack.peek().addRow(
                new VariableRow(
                    param.identifier, 
                    param.type
                )
            );

            // Add to parameters
            parameters.add(param);

        }

        CompoundStatement body = (CompoundStatement)visit(ctx.compound_statement());

        for (int i = 0; i < parameters.size(); i++)
        {
            symAnalysisStack.pop();
        }

        return new Function(ctx.IDENTIFIER().getText(), parameters, return_type, is_void, body);
    }

    // Parameter
	@Override
    public Parameter visitParameter(FearnGrammarParser.ParameterContext ctx)
    {
        symAnalysisStack.push(ctx.IDENTIFIER().getText());
        return new Parameter(ctx.IDENTIFIER().getText(), (TypeSpecifier)visit(ctx.type_specifier()));
    }

    // Struct Definition
    @Override
    public Struct visitStruct_def(FearnGrammarParser.Struct_defContext ctx)
    {
        ArrayList<Declaration> decl = new ArrayList<Declaration>();
        for (int i = 0; i < ctx.declaration().size(); i++)
        {
            decl.add((Declaration)visit(ctx.declaration(i)));
        }

        return new Struct(ctx.IDENTIFIER().getText(), decl);
    }


    
    @Override
    public ASTNode visitImp(FearnGrammarParser.ImpContext ctx)
    {

        ImportCompiler comp = new ImportCompiler();

        if (ctx.IDENTIFIER() == null)
        {
            symTabStack.peek().addRowsFromTable(
                comp.Compile(ctx.STR_LIT().toString())
            );
        } else {
            symTabStack.peek().addRowsFromTable(
                comp.GetStdLib(ctx.IDENTIFIER().toString())
            );
        }
        
        return null;
    
    }
    
    public Stack<SymbolTable> symTabStack = new Stack<SymbolTable>();
    
    /* Program (root of AST) */
    @Override
    public Program visitProgram(FearnGrammarParser.ProgramContext ctx)
    {

        ArrayList<Declaration> global_declarations = new ArrayList<Declaration>(); 
        ArrayList<Struct> structs = new ArrayList<Struct>(); 
        ArrayList<Function> functions = new ArrayList<Function>();
        
        
        symTabStack.add(new SymbolTable());
        
        
        for (int i = 0; i < ctx.imp().size(); i++) visit(ctx.imp(i));

        for (int i = 0; i < ctx.declaration().size(); i++)
        {
            Declaration decl = visitDeclaration(ctx.declaration(i));
            global_declarations.add(decl);
        }
        
        for (int i = 0; i < ctx.declaration().size(); i++)
        {
            Declaration decl = visitDeclaration(ctx.declaration(i));
            global_declarations.add(decl);
        }


        for (int i = 0; i < ctx.struct_def().size(); i++)
        {
            symTabStack.add(new SymbolTable());

            Struct struct = visitStruct_def(ctx.struct_def(i));
            structs.add(struct);

            SymbolTable local_syms = symTabStack.pop();

            symTabStack.peek().addRow(
                new StructRow(struct.identifier, local_syms)
            );
        }


        for (int i = 0; i < ctx.function().size(); i++)
        {
            symTabStack.add(new SymbolTable());
            
            Function func = visitFunction(ctx.function(i));
            functions.add(func);

            SymbolTable local_syms = symTabStack.pop();
            
            symTabStack.peek().addRow(
                new FunctionRow(func.identifier, func.parameters, func.return_type, local_syms)
            );
        }

        
        return new Program(global_declarations, functions, structs);
    }

}