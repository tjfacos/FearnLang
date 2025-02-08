package io.github.tjfacos.fearnlang.parser;

import java.util.ArrayList;
import java.util.Stack;

import io.github.tjfacos.fearnlang.parser.gen.*;
import io.github.tjfacos.fearnlang.semantics.table.*;
import io.github.tjfacos.fearnlang.util.*;

import io.github.tjfacos.fearnlang.ast.*;
import io.github.tjfacos.fearnlang.ast.expression.*;
import io.github.tjfacos.fearnlang.ast.expression.AssignExpression.AssignmentOperator;
import io.github.tjfacos.fearnlang.ast.expression.Expression.ExprType;
import io.github.tjfacos.fearnlang.ast.function.Function;
import io.github.tjfacos.fearnlang.ast.function.Parameter;
import io.github.tjfacos.fearnlang.ast.type.ArraySpecifier;
import io.github.tjfacos.fearnlang.ast.type.PrimitiveSpecifier.PrimitiveDataType;
import io.github.tjfacos.fearnlang.codegen.ImportCompiler;
import io.github.tjfacos.fearnlang.ast.type.PrimitiveSpecifier;
import io.github.tjfacos.fearnlang.ast.type.StructInstanceSpecifier;
import io.github.tjfacos.fearnlang.ast.type.TypeSpecifier;
import io.github.tjfacos.fearnlang.ast.statement.*;
import io.github.tjfacos.fearnlang.ast.statement.JumpStatement.JumpType;

/* ASTConstructor.java
 * 
 * This class is derived from the FearnGrammarBaseVisitor class,
 * generated by ANTLR from the grammar specified in FearnGrammar.g4
 * 
 * The base class 'walks' (DFT) the parse tree recursively, at returns a 
 * new AST Node at each parse tree node, after having walked its entire 
 * subtree.
 * 
 * The point of doing this, as opposed to just operating directly on the
 * ANTLR Parse Tree is:
 *  A)  To eliminate redundant semantic information (e.g. punctuation, where 
 *      brackets are used, etc.)
 *  B)  To identify the structures used where it may be ambiguous (e.g. a dot expression
 *      could be used to access a struct's attribute, or could be a function call using 
 *      universal function notation / UFN)
 *  C)  To perform symbolic analysis, ensuring that every variable used has been declared.
 *          ->  This doesn't perform all symbol analysis tasks (e.g. ensuring a 
 *              function/struct is defined, but this is handled during the general 
 *              validation traversal)
 *  D)  To construct a symbol table, used to perform a full validation on the program and to
 *      generate its implementation bytecode
 * 
 * Each method defined below specifies actions taken when the traversal meets a particular 
 * production. The visit() function is used to traverse a child node of the current node.
 * 
 * ANTLR Walkers use contexts (ctx) to give information about the form of the node. These can
 * be used to 
 *  A)  Access a child node by name (e.g. ctx.expression() gives the child ctx of the child 
 *      expression node of the ctx)
 *  B)  Access children by index
 *  C)  Retrieve the number of children, the text representing the node (which matches the grammar 
 *      production) in the source file, or get the line number (by getting the line number of the 
 *      first token in the production rule match (the subtree of the production) )
 * 
 */

public class ASTConstructor extends FearnGrammarBaseVisitor<ASTNode> {

    /*
     * SYMBOL ANALYSIS
     * 
     * This is done using a stack
     * -> New identifiers for variables are added to the stack
     * -> They are popped of when they exit scope
     * -> Any variable references using names not in the stack
     * raises an error
     */

    Stack<String> symbolAnalysisStack = new Stack<String>();

    /*
     * PRIMARY EXPRESSIONS
     * 
     * Fundamental, atomic expression
     * -> visitId_expr : visits references to variables
     * -> Raises an error if the variable referenced isn't in the stack
     * (hence, out of scope when it is used)
     * -> visitLiteral : visit literal values
     * 
     * Both return PrimaryExpression objects, which are generic objects
     * when hold the data value they represent, or the string ID of the
     * referenced variable.
     * 
     * The ExprType enum (part of the Expression class) is used to specify the
     * type of expression. Enums are used throughout the compiler for this
     * purpose (differentiating the purpose/function of AST Nodes).
     * 
     */

    /**
     * Visits an ID expression (variable reference). Raises an error if the variable
     * is out of scope.
     * 
     * @param ctx The parse tree
     * @return A Primary Expression represention the reference.
     */
    @Override
    public Expression visitId_expr(FearnGrammarParser.Id_exprContext ctx) {
        String id = ctx.getText();

        if (!symbolAnalysisStack.contains(id)) {
            Reporter.ReportErrorAndExit(String.format(
                    " Line %s : Variable identifier Unknown in Scope: %s .",
                    ctx.getStart().getLine(),
                    id), null);
        }

        return new PrimaryExpression<String>(id, ExprType.VariableReference);
    }

    /**
     * Visits a primitive literal.
     * 
     * @param ctx The parse tree
     * @return An expression representing the literal
     */
    @Override
    public Expression visitLiteral(FearnGrammarParser.LiteralContext ctx) {
        switch (ctx.getStart().getType()) {
            case FearnGrammarLexer.INT_LIT:
                return new PrimaryExpression<Integer>(Integer.valueOf(ctx.getText()), ExprType.IntLiteral);
            case FearnGrammarLexer.BOOL_LIT:
                return new PrimaryExpression<Boolean>(Boolean.valueOf(ctx.getText()), ExprType.BoolLiteral);

            case FearnGrammarLexer.FLOAT_LIT:
                // FearnLang Floats are represented by Java doubles
                return new PrimaryExpression<Double>(Double.valueOf(ctx.getText()), ExprType.FloatLiteral);

            case FearnGrammarLexer.STR_LIT:
                return new PrimaryExpression<String>(ctx.getText().substring(1, ctx.getText().length() - 1),
                        ExprType.StrLiteral);

            default:
                Reporter.ReportErrorAndExit("Parse Error: Unable to Parse literal value", null);
        }

        return null;

    }

    /**
     * Visits a Bracket expression.
     * 
     * Bracket expressions, ( expression ), are basically used to clarify
     * precedence. Since this is natural as part of a tree, bracket 
     * expressions simply return whatever's in the brackets.
     * 
     * @param ctx The parse tree
     * @return The expression resulting from visiting the bracketed expression
     */
    @Override
    public Expression visitBrac_expr(FearnGrammarParser.Brac_exprContext ctx) {
        return (Expression) visit(ctx.expression());
    }

    /*
     * BINARY OPERATIONS
     * 
     * These all have two operands, and all function (roughly) in the same way
     * 1) Visit both operands
     * 2) Perform any additional processing (e.g. setting the ExprType)
     * 3) Return an Binary Expression object
     * 
     * ExprType is again used to specify the operation
     * 
     */

    /**
     * Visits a multiplicative Expression ( a (*|/|%) b ).
     * 
     * @param ctx The parse tree
     * @return A binary expression between the two operands, using the specified operation.
     */
    @Override
    public BinaryExpression visitMult_expr(FearnGrammarParser.Mult_exprContext ctx) {
        Expression op1 = (Expression) visit(ctx.expression(0));
        Expression op2 = (Expression) visit(ctx.expression(1));

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

        assert (type != null);

        return new BinaryExpression(op1, op2, type);
    }

    /**
     * Visits an Additive Expression (a (+|-) b )
     * 
     * @param ctx The parse tree
     * @return A binary expression between the two operands, using the specified operation.
     */
    @Override
    public BinaryExpression visitAdd_expr(FearnGrammarParser.Add_exprContext ctx) {
        Expression op1 = (Expression) visit(ctx.expression(0));
        Expression op2 = (Expression) visit(ctx.expression(1));

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

        assert (type != null);

        return new BinaryExpression(op1, op2, type);
    }

    /**
     * Visits an Exponential Expression (a ^ b)
     * 
     * @param ctx The parse tree
     * @return A binary expression between the two operands, using the specified operation.
     */
    @Override
    public BinaryExpression visitExp_expr(FearnGrammarParser.Exp_exprContext ctx) {
        Expression op1 = (Expression) visit(ctx.expression(0));
        Expression op2 = (Expression) visit(ctx.expression(1));

        return new BinaryExpression(op1, op2, ExprType.Exponent);
    }

    /**
     * Visits a Boolean less-than operation (a < b)
     * 
     * @param ctx The parse tree
     * @return A binary expression between the two operands, using the specified operation.
     */
    @Override
    public BinaryExpression visitLess_expr(FearnGrammarParser.Less_exprContext ctx) {
        Expression op1 = (Expression) visit(ctx.expression(0));
        Expression op2 = (Expression) visit(ctx.expression(1));

        return new BinaryExpression(op1, op2, ExprType.Less);
    }

    /**
     * Visits a Boolean greater-than operation (a > b)
     * 
     * @param ctx The parse tree
     * @return A binary expression between the two operands, using the specified operation.
     */
    @Override
    public BinaryExpression visitGreater_expr(FearnGrammarParser.Greater_exprContext ctx) {
        Expression op1 = (Expression) visit(ctx.expression(0));
        Expression op2 = (Expression) visit(ctx.expression(1));

        return new BinaryExpression(op1, op2, ExprType.Greater);
    }

    /**
     * Visits a Boolean less-equal operation (a <= b)
     * 
     * @param ctx The parse tree
     * @return A binary expression between the two operands, using the specified operation.
     */
    @Override
    public BinaryExpression visitLess_eq_expr(FearnGrammarParser.Less_eq_exprContext ctx) {
        Expression op1 = (Expression) visit(ctx.expression(0));
        Expression op2 = (Expression) visit(ctx.expression(1));

        return new BinaryExpression(op1, op2, ExprType.LessEq);
    }

    /**
     * Visits a Boolean greater-equal operation (a >= b)
     * 
     * @param ctx The parse tree
     * @return A binary expression between the two operands, using the specified operation.
     */
    @Override
    public BinaryExpression visitGreater_eq_expr(FearnGrammarParser.Greater_eq_exprContext ctx) {
        Expression op1 = (Expression) visit(ctx.expression(0));
        Expression op2 = (Expression) visit(ctx.expression(1));

        return new BinaryExpression(op1, op2, ExprType.GreaterEq);
    }

    /**
     * Visits a Boolean equality operation (a == b)
     * 
     * @param ctx The parse tree
     * @return A binary expression between the two operands, using the specified operation.
     */
    @Override
    public BinaryExpression visitEq_expr(FearnGrammarParser.Eq_exprContext ctx) {
        Expression op1 = (Expression) visit(ctx.expression(0));
        Expression op2 = (Expression) visit(ctx.expression(1));

        return new BinaryExpression(op1, op2, ExprType.Eq);
    }

    /**
     * Visits a Boolean non-equality operation (a != b)
     * 
     * @param ctx The parse tree
     * @return A binary expression between the two operands, using the specified operation.
     */
    @Override
    public BinaryExpression visitNot_eq_expr(FearnGrammarParser.Not_eq_exprContext ctx) {
        Expression op1 = (Expression) visit(ctx.expression(0));
        Expression op2 = (Expression) visit(ctx.expression(1));

        return new BinaryExpression(op1, op2, ExprType.NotEq);
    }

    /**
     * Visits a Boolean AND operation (a && b)
     * 
     * @param ctx The parse tree
     * @return A binary expression between the two operands, using the specified operation.
     */
    @Override
    public BinaryExpression visitAnd_expr(FearnGrammarParser.And_exprContext ctx) {
        Expression op1 = (Expression) visit(ctx.expression(0));
        Expression op2 = (Expression) visit(ctx.expression(1));

        return new BinaryExpression(op1, op2, ExprType.LogicalAnd);
    }

    /**
     * Visits a Boolean OR operation (a || b)
     * 
     * @param ctx The parse tree
     * @return A binary expression between the two operands, using the specified operation.
     */
    @Override
    public BinaryExpression visitOr_expr(FearnGrammarParser.Or_exprContext ctx) {
        Expression op1 = (Expression) visit(ctx.expression(0));
        Expression op2 = (Expression) visit(ctx.expression(1));

        return new BinaryExpression(op1, op2, ExprType.LogicalOr);
    }

    /* UNARY OPERATIONS (1 operand) */
    
    /**
     * Visits a unary negation operation (-a)
     * 
     * @param ctx The parse tree
     * @return A unary expression, characterised by the operand and the operation 
     */
    @Override
    public UnaryExpression visitU_minus_expr(FearnGrammarParser.U_minus_exprContext ctx) {
        Expression op = (Expression) visit(ctx.expression());

        return new UnaryExpression(op, ExprType.Negate);
    }

    /**
     * Visits a unary boolean NOT operation (!a)
     * 
     * @param ctx The parse tree
     * @return A unary expression, characterised by the operand and the operation 
     */
    @Override
    public UnaryExpression visitU_not_expr(FearnGrammarParser.U_not_exprContext ctx) {
        Expression op = (Expression) visit(ctx.expression());

        return new UnaryExpression(op, ExprType.LogicalNot);
    }

    /**
     * Visits a unary postfix increment/decrement operation (a-- | a++)
     * 
     * @param ctx The parse tree
     * @return A unary expression, characterised by the operand and the operation 
     */
    @Override
    public IncrExpression visitPost_inc_expr(FearnGrammarParser.Post_inc_exprContext ctx) {
        // Uses the text representation of the operator to discern if the operation
        // in an increment or a decrement
        Boolean isDecrement = false;
        if (ctx.op.getText().equals("--")) {
            isDecrement = true;
        }

        // Last argument indicates if the expression is prefix (important for code
        // generation
        // to perform operations in the right order)
        return new IncrExpression((Expression) visit(ctx.expression()), isDecrement, false);
    }

    /**
     * Visits a unary prefix increment/decrement operation (--a | ++a)
     * 
     * @param ctx The parse tree
     * @return A unary expression, characterised by the operand and the operation 
     */
    @Override
    public IncrExpression visitPre_inc_expr(FearnGrammarParser.Pre_inc_exprContext ctx) {
        Boolean isDecrement = false;
        if (ctx.op.getText().equals("--")) {
            isDecrement = true;
        }

        return new IncrExpression((Expression) visit(ctx.expression()), isDecrement, true);
    }

    // Cast Expressions (Casting data to a primitive data type)
    // Utilises the PrimitiveDataType enum (see PrimitiveExpression)
    /**
     * Visits a cast expression (casting data to a primitive type) (e.g. (int)"12" )
     * 
     * @param ctx The parse tree
     * @return A unary expression, characterised by the operand and the operation 
     */
    @Override
    public CastExpression visitCast_expr(FearnGrammarParser.Cast_exprContext ctx) {
        PrimitiveDataType targetType = null;

        switch (ctx.type_name().getText()) {
            case "int":
                targetType = PrimitiveDataType.INT;
                break;
            case "float":
                targetType = PrimitiveDataType.FLOAT;
                break;
            case "str":
                targetType = PrimitiveDataType.STR;
                break;
            case "bool":
                targetType = PrimitiveDataType.BOOL;
                break;
            default:
                break;
        }

        return new CastExpression((Expression) visit(ctx.expression()), targetType);
    }

    // Index Expression ( e.g. myArray[0] )
    @Override
    public IndexExpression visitIndex_expr(FearnGrammarParser.Index_exprContext ctx) {
        return new IndexExpression(
                (Expression) visit(ctx.expression(0)),
                (Expression) visit(ctx.expression(1)));
    }

    /* MISCELLANEOUS EXPRESSIONS */

    // Function Calls
    // -> Returns a FnCallExpression object, with the functions string identifier,
    // and expression arguments
    @Override
    public FnCallExpression visitFn_call_expr(FearnGrammarParser.Fn_call_exprContext ctx) {
        String function_name = ctx.IDENTIFIER().getText();
        ArrayList<Expression> args = new ArrayList<Expression>();

        // Iterate through the child expressions (the arguments), visit them, and
        // add the returned Expression objects to args
        for (int i = 0; i < ctx.expression().size(); i++) {
            args.add((Expression) visit(ctx.expression(i)));
        }

        return new FnCallExpression(function_name, args);
    }

    // Dot Expressions (e.g. x.y ) /
    @SuppressWarnings("rawtypes")
    @Override
    public Expression visitDot_expr(FearnGrammarParser.Dot_exprContext ctx) {

        // Get the expression before and after the dot
        Expression predot = (Expression) visit(ctx.expression(0));
        Expression postdot = (Expression) visit(ctx.expression(1));

        /*
         * EITHER:
         * A) postdot is a function call
         * B) postdot is an identifier (a variable reference)
         * C) Input in erroneous
         */

        if (postdot instanceof FnCallExpression) {

            // This matches a function call using universal function notation
            // e.g. a.equals(b) == equals(a, b)

            FnCallExpression UFN_call = (FnCallExpression) postdot;

            // Add the predot expression to the arguments
            UFN_call.arguments.add(0, predot);

            // Sets the is_UFN flag, which is used to print the function call
            // correctly, in case of error
            UFN_call.isUFN = true;

            return UFN_call;
        }

        else if (postdot instanceof PrimaryExpression
                && ((PrimaryExpression) postdot).type == ExprType.VariableReference) {
            // This matches an expression to access an attribute of a struct instance
            return new StructAttrExpression(predot, ctx.expression(1).getText());
        } else {
            // If neither above cases matched, the expression is illegal, and an error is
            // raised
            Reporter.ReportErrorAndExit(String.format(
                    "Line %s : %s.%s is not an attribute expression or a function call.",
                    ctx.getStart().getLine(),
                    predot.toString(),
                    postdot.toString()), null);
            return null;
        }
    }

    // Array Initialisation (e.g. new int[] {1, 2, 3, 7, 123 } / new str[5])
    @Override
    public ArrayInitExpression visitArray_init(FearnGrammarParser.Array_initContext ctx) {
        // Get type of the elements (either primitive or struct instances)
        TypeSpecifier type = (TypeSpecifier) visit(ctx.getChild(1));

        // Get numerical dimensions (if specified)
        ArrayList<Expression> dims = new ArrayList<Expression>();

        for (int i = 0; i < ctx.expression().size(); i++) {
            dims.add((Expression) visit(ctx.expression(i)));
        }

        // If an array body ( e.g. {1, 2, 3} ) is provided, visit the body
        ArrayBody init = null;

        if (ctx.array_body() != null) {
            init = (ArrayBody) visit(ctx.array_body());

            // If an array body is provided, dimensions are not present
            // Thus, dims is populated with the correct number of null values
            // `ctx.getChildCount() - 3` is needed, to remove from the count
            // the `new` keyword, type specifier, and arraybody - leaving the number
            // of '[]' used (the N of the N-dimensional array)

            for (int i = 0; i < ctx.getChildCount() - 3; i++)
                dims.add(null);
        }

        return new ArrayInitExpression(type, dims, init);
    }

    // ArrayBody (e.g. { 1 , 2 })
    @Override
    public Expression visitArray_body(FearnGrammarParser.Array_bodyContext ctx) {
        ArrayList<Expression> elements = new ArrayList<Expression>();

        // Visit each element in the array, and add it to the elements
        for (int i = 1; i < ctx.getChildCount() - 1; i += 2) {

            elements.add((Expression) visit(ctx.getChild(i)));
        }

        return new ArrayBody(elements);
    }

    // Struct Initialisation ( e.g. new Person('Kenneth', 23) )
    // Get struct ID, visit arguments (expressions), and return the
    // StructInitExpression
    @Override
    public StructInitExpression visitStruct_init(FearnGrammarParser.Struct_initContext ctx) {
        String struct_id = ctx.IDENTIFIER().getText();

        ArrayList<Expression> args = new ArrayList<Expression>();

        for (int i = 0; i < ctx.expression().size(); i++) {
            args.add((Expression) visit(ctx.expression(i)));
        }

        return new StructInitExpression(struct_id, args);
    }

    /* Assignment Expressions (e.g. x = y; x += 3; y %= 2) */
    @Override
    public AssignExpression visitAssign_expression(FearnGrammarParser.Assign_expressionContext ctx) {
        // Visit target (what you're assigning a value to), and expression (the new
        // value)
        Expression target = (Expression) visit(ctx.expression(0));
        Expression expression = (Expression) visit(ctx.expression(1));

        // Set operator (an enum value - see AssignmentExpression)
        AssignmentOperator op = null;

        switch (ctx.assignment_operator().getText()) {
            case "=":
                op = AssignmentOperator.Equals;
                break;
            case "+=":
                op = AssignmentOperator.AddEquals;
                break;
            case "-=":
                op = AssignmentOperator.SubEquals;
                break;
            case "*=":
                op = AssignmentOperator.MultEquals;
                break;
            case "/=":
                op = AssignmentOperator.DivEquals;
                break;
            case "%=":
                op = AssignmentOperator.ModEquals;
                break;
            default:
                break;
        }

        return new AssignExpression(target, expression, op);

    }

    /*
     * TYPE SPECIFIERS
     * 
     * These, syntactically, are used to indicate type.
     * 
     * There are three sorts of type specifiers:
     * -> Primitive: One of the 4 simple data types: int, float, str, bool
     * -> Struct: A struct instance, shown as `$IDENTIFIER`, where IDENTIFIER
     * indicates the struct used
     * -> Array: An N-dimensional array, that stores primitives or struct instances
     * 
     */

    public PrimitiveSpecifier visitType_specifier_primitive(FearnGrammarParser.Type_specifier_primitiveContext ctx) {
        PrimitiveDataType type = null;

        switch (ctx.getText()) {
            case "int":
                type = PrimitiveDataType.INT;
                break;
            case "float":
                type = PrimitiveDataType.FLOAT;
                break;
            case "str":
                type = PrimitiveDataType.STR;
                break;
            case "bool":
                type = PrimitiveDataType.BOOL;
                break;
        }

        return new PrimitiveSpecifier(type);

    }

    public ArraySpecifier visitType_specifier_arr(FearnGrammarParser.Type_specifier_arrContext ctx) {
        TypeSpecifier type = (TypeSpecifier) visit(ctx.getChild(0));
        Integer dims = ctx.getChildCount() - 1;
        return new ArraySpecifier(type, dims);
    }

    public StructInstanceSpecifier visitType_specifier_struct(FearnGrammarParser.Type_specifier_structContext ctx) {
        return new StructInstanceSpecifier(ctx.IDENTIFIER().getText());
    }

    /*
     * DECLARATIONS
     * 
     * New variables are declared at the start of the compound statement in
     * which they're in scope. This function...
     * 1) Gets the identifier for the variable, its type specifier, and the
     * initialisation expression, if present.
     * 2) Adds the identifier to the stack
     * 3) Adds a row for the variable to the local/global symbol table (this
     * will throw an error if another variable of the same name exists in
     * scope), by adding the row to the SymbolTable at the top of the
     * symbolTableStack
     * 4) Returns a Declaration object
     * 
     */

    public Declaration visitDeclaration(FearnGrammarParser.DeclarationContext ctx) {
        String identifier = ctx.IDENTIFIER().getText();
        symbolAnalysisStack.push(identifier);
        TypeSpecifier type_spec = (TypeSpecifier) visit(ctx.type_specifier());

        Expression init_expression = null;

        if (ctx.getChildCount() > 5) {
            init_expression = (Expression) visit(ctx.expression());
        }

        symbolTableStack.peek().addRow(
                new VariableRow(
                        identifier,
                        type_spec));

        return new Declaration(identifier, type_spec, init_expression);

    }

    /* STATEMENTS */

    /*
     * COMPOUND STATEMENTS
     * 
     * A collection of statements, surrounded by curly braces.
     * 
     * This function:
     * 1) Records the number of symbols in the stack initially
     * 2) Visits all nested declarations and statements
     * 4) Removes any new symbols added to the stack during the traversal
     * of its subtree (by popping the stack for the difference between
     * the new size of the stack, and the initial number). This is done
     * because said variables declared within the statement are out of
     * scope beyond it.
     */
    @Override
    public CompoundStatement visitCompound_statement(FearnGrammarParser.Compound_statementContext ctx) {

        int numOfSyms = symbolAnalysisStack.size();

        ArrayList<ASTNode> local_nodes = new ArrayList<ASTNode>();

        for (int i = 1; i < ctx.getChildCount() - 1; i++) {
            local_nodes.add(visit(ctx.getChild(i)));
        }

        // Remove Symbols added within the compound statement
        for (int i = 0; i < symbolAnalysisStack.size() - numOfSyms; i++) {
            symbolAnalysisStack.pop();
        }

        return new CompoundStatement(local_nodes);

    }

    /*
     * EXPRESSION STATEMENTS
     * 
     * Simply a simple expression or assignment expression. The expressions are
     * visited, and
     * the resulting Expression object is used to return an ExpressionStatement.
     * 
     * The boolean flag at the end indicates the ExpressStatement is an assignment
     * expression
     * if true.
     */
    @Override
    public ExpressionStatement visitSimple_expr_stmt(FearnGrammarParser.Simple_expr_stmtContext ctx) {
        return new ExpressionStatement((Expression) visit(ctx.expression()), false);
    }

    @Override
    public ExpressionStatement visitAssign_expr_stmt(FearnGrammarParser.Assign_expr_stmtContext ctx) {
        return new ExpressionStatement((Expression) visit(ctx.assign_expression()), true);
    }

    /*
     * SELECTION STATEMENTS
     * 
     * Statements used for branching (if and if-else)
     * 
     * Each of the below returns a SelectionStatement, which takes three arguments:
     * the condition expression, the if branch body (a compound statement), and
     * the else branch statement (either a compound statement, or another selection
     * statement - to build a if-else chain)
     * 
     */

    @Override
    public SelectionStatement visitSingle_if(FearnGrammarParser.Single_ifContext ctx) {

        return new SelectionStatement(
                (Expression) visit(ctx.expression()),
                (CompoundStatement) visit(ctx.compound_statement()),
                null);
    }

    @Override
    public SelectionStatement visitIf_else(FearnGrammarParser.If_elseContext ctx) {

        return new SelectionStatement(
                (Expression) visit(ctx.expression()),
                (CompoundStatement) visit(ctx.compound_statement(0)),
                (Statement) visit(ctx.getChild(ctx.getChildCount() - 1)));
    }

    /*
     * ITERATION STATEMENTS
     * 
     * Statements used for loops (for loops, to be precise - Fearn doesn't
     * support while loop, but the syntax for for loops is permissive enough
     * for it to be used as a while loop)
     * 
     * This function:
     * 1) Visits the initialisation expression/declaration (run before the loop),
     * if present
     * 2) Visits the continue expression (a boolean expression that must evaluate
     * to true for the loop body to run)
     * 3) Visits the iteration expression, run at the end of each iteration, if
     * present
     * 4) Visits the compound statement, that is the body of the loop
     * 5) Returns an IterationStatement
     * 
     */
    @Override
    public IterationStatement visitIteration_statement(FearnGrammarParser.Iteration_statementContext ctx) {
        ASTNode init = null;
        Expression cont_expr = null;
        Expression iter_expr = null;
        CompoundStatement body = null;

        if (!ctx.init_expression().getText().equals(";"))
            init = visit(ctx.init_expression().getChild(0)); // Either an Expression or a Declaration
        if (!ctx.continue_condition().getText().equals(";"))
            cont_expr = (Expression) visit(ctx.continue_condition().getChild(0));

        if (ctx.iteration_expression().getChildCount() > 0)
            iter_expr = (Expression) visit(ctx.iteration_expression().getChild(0));

        body = (CompoundStatement) visit(ctx.compound_statement());

        return new IterationStatement(init, cont_expr, iter_expr, body);

    }

    /*
     * JUMP STATEMENTS
     * 
     * Statements used to jump around a program (return, break, and continue)
     * 
     * Each one returns a JumpStatement, with return statements returning the
     * subclass ReturnStatement, which allows an expression for the statement to
     * return to be passed.
     * 
     * Jump Statements use the enum JumpType to differentiate themselves.
     * 
     */

    @Override
    public JumpStatement visitCont_stmt(FearnGrammarParser.Cont_stmtContext ctx) {
        return new JumpStatement(JumpType.Continue);
    }

    @Override
    public JumpStatement visitBreak_stmt(FearnGrammarParser.Break_stmtContext ctx) {
        return new JumpStatement(JumpType.Break);
    }

    @Override
    public ReturnStatement visitReturn_stmt(FearnGrammarParser.Return_stmtContext ctx) {
        return new ReturnStatement(
                JumpType.Return,
                (ctx.expression() == null) ? null : (Expression) visit(ctx.expression()));
    }

    /* HIGH-LEVEL STRUCTURES */

    /*
     * FUNCTION DEFINITIONS
     * 
     * In the form `fn IDENTIFIER(parameter*) => return_type_specifier`
     * 
     * This function:
     * 1) Determines if the function returns void, by checking for a type_specifier
     * 2) Visits the return type specifier if present
     * 3) Visits the parameters, adding them to a list. These are added to the
     * symbol table before any local variables within the function, as the JVM
     * adds arguments in the first indexes of the local variable list. Thus, since
     * this table is used to get variable indexes during code generation, arguments
     * have to be first.
     * 4) Visits the body
     * 5) Pops the arguments form the symbol stack (they are added when the
     * parameters
     * are visited)
     * 6) Returns a Function object, retrieving the string identifier from the
     * IDENTIFIER
     * token
     */
    @Override
    public Function visitFunction(FearnGrammarParser.FunctionContext ctx) {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();
        TypeSpecifier return_type = null;
        Boolean is_void = false;

        if (ctx.type_specifier() == null) {
            is_void = true;
        } else {
            return_type = (TypeSpecifier) visit(ctx.type_specifier());
        }

        for (int i = 0; i < ctx.parameter().size(); i++) {
            // Visit Parameter
            Parameter param = (Parameter) visit(ctx.parameter(i));

            // Add parameter to symbol table
            symbolTableStack.peek().addRow(
                    new VariableRow(
                            param.identifier,
                            param.type));

            // Add to parameters
            parameters.add(param);

        }

        CompoundStatement body = (CompoundStatement) visit(ctx.compound_statement());

        for (int i = 0; i < parameters.size(); i++)
            symbolAnalysisStack.pop();

        return new Function(ctx.IDENTIFIER().getText(), parameters, return_type, is_void, body);
    }

    /*
     * 
     * Parameter, in the form `IDENTIFIER : type_specifier`.
     * 
     * The function visits the type_specifier, gets the string identifier, and adds
     * the parameter
     * to the symbol stack, to ensure references to the parameters are detected as
     * valid during
     * traversal of the function body.
     * 
     */
    @Override
    public Parameter visitParameter(FearnGrammarParser.ParameterContext ctx) {
        symbolAnalysisStack.push(ctx.IDENTIFIER().getText());
        return new Parameter(ctx.IDENTIFIER().getText(), (TypeSpecifier) visit(ctx.type_specifier()));
    }

    /*
     * STRUCT DEFINITIONS
     * 
     * In the form `struct IDENTIFIER {declaration*}`
     * 
     * This function:
     * 1) Visits all the declarations within the struct (its attributes)
     * 2) Gets the identifier string
     * 3) Returns a Struct object
     */
    @Override
    public Struct visitStruct_def(FearnGrammarParser.Struct_defContext ctx) {
        ArrayList<Declaration> decl = new ArrayList<Declaration>();
        for (int i = 0; i < ctx.declaration().size(); i++) {
            decl.add((Declaration) visit(ctx.declaration(i)));
        }

        return new Struct(ctx.IDENTIFIER().getText(), decl);
    }

    /*
     * IMPORTS
     * 
     * An Import Compiler is used to compile other fearn programs,
     * imported by the current program, and returns their symbol table,
     * the rows of which are added to the current global symbol table.
     * 
     * If an identifier is used, the import is from a standard library
     * module (e.g. io). The Import Compiler will construct a symbol table
     * for the functions contained within, and add them to the global symbol
     * table, so they can be used anywhere within the program.
     * 
     */
    @Override
    public ASTNode visitModule_import(FearnGrammarParser.Module_importContext ctx) {

        ImportCompiler comp = new ImportCompiler();

        if (ctx.IDENTIFIER() == null) {
            symbolTableStack.peek().addRowsFromTable(
                    comp.Compile(ctx.STR_LIT().toString()));
        } else {
            symbolTableStack.peek().addRowsFromTable(
                    comp.GetStdLib(ctx.IDENTIFIER().toString()));
        }

        // All new symbols from the import are added to the
        // symbol stack
        for (Row row : symbolTableStack.peek().GetAllRows()) {
            if (row instanceof VariableRow) {
                symbolAnalysisStack.push(row.identifier);
            } else if (row instanceof StructRow) {
                for (Row var_row : ((StructRow) row).localSymbolTable.GetAllRows())
                    symbolAnalysisStack.push(var_row.identifier);
            }
        }

        return null;

    }

    /*
     * The symbol table stack is used to store symbol tables for
     * functions, structs, and the global scope. When new rows are
     * created, they are added to the table at the top of the stack.
     * These can then be popped off to be used, say within a FunctionRow
     * or StructRow, stored within the global symbol table. This means rows
     * are always added to the symbol table for the part of the program
     * currently being traversed.
     */

    public Stack<SymbolTable> symbolTableStack = new Stack<SymbolTable>();

    /*
     * Program (root of AST)
     * 
     * This visits all imports, global declarations, structs, and functions.
     * 
     * For structs and functions, a new symbol table is added to the stack.
     * This means newly-declared symbols are added to this symbol table. It
     * is then popped off the stack, and stored within a row for the larger
     * structure, which is finally stored in the global symbol table (always
     * at the bottom of the stack).
     * 
     * The function returns a Program object, representing the root of the tree.
     */

    @Override
    public Program visitProgram(FearnGrammarParser.ProgramContext ctx) {

        ArrayList<Declaration> global_declarations = new ArrayList<Declaration>();
        ArrayList<Struct> structs = new ArrayList<Struct>();
        ArrayList<Function> functions = new ArrayList<Function>();

        symbolTableStack.add(new SymbolTable());

        for (int i = 0; i < ctx.module_import().size(); i++)
            visit(ctx.module_import(i));

        for (int i = 0; i < ctx.declaration().size(); i++) {
            Declaration decl = visitDeclaration(ctx.declaration(i));
            global_declarations.add(decl);
        }

        for (int i = 0; i < ctx.struct_def().size(); i++) {
            symbolTableStack.add(new SymbolTable());

            Struct struct = visitStruct_def(ctx.struct_def(i));
            structs.add(struct);

            SymbolTable local_syms = symbolTableStack.pop();

            symbolTableStack.peek().addRow(
                    new StructRow(struct.identifier, local_syms));
        }

        for (int i = 0; i < ctx.function().size(); i++) {
            symbolTableStack.add(new SymbolTable());

            Function func = visitFunction(ctx.function(i));
            functions.add(func);

            SymbolTable local_syms = symbolTableStack.pop();

            symbolTableStack.peek().addRow(
                    new FunctionRow(func.identifier, func.parameters, func.return_type, local_syms));
        }

        return new Program(global_declarations, functions, structs);
    }

}