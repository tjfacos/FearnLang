// Generated from FearnGrammar.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FearnGrammarParser}.
 */
public interface FearnGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#type_name}.
	 * @param ctx the parse tree
	 */
	void enterType_name(FearnGrammarParser.Type_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#type_name}.
	 * @param ctx the parse tree
	 */
	void exitType_name(FearnGrammarParser.Type_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#type_specifier}.
	 * @param ctx the parse tree
	 */
	void enterType_specifier(FearnGrammarParser.Type_specifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#type_specifier}.
	 * @param ctx the parse tree
	 */
	void exitType_specifier(FearnGrammarParser.Type_specifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(FearnGrammarParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(FearnGrammarParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(FearnGrammarParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(FearnGrammarParser.FunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#declaration_block}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration_block(FearnGrammarParser.Declaration_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#declaration_block}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration_block(FearnGrammarParser.Declaration_blockContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#declaration_list}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration_list(FearnGrammarParser.Declaration_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#declaration_list}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration_list(FearnGrammarParser.Declaration_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(FearnGrammarParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(FearnGrammarParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#parameters_list}.
	 * @param ctx the parse tree
	 */
	void enterParameters_list(FearnGrammarParser.Parameters_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#parameters_list}.
	 * @param ctx the parse tree
	 */
	void exitParameters_list(FearnGrammarParser.Parameters_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(FearnGrammarParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(FearnGrammarParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(FearnGrammarParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(FearnGrammarParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#compound_statement}.
	 * @param ctx the parse tree
	 */
	void enterCompound_statement(FearnGrammarParser.Compound_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#compound_statement}.
	 * @param ctx the parse tree
	 */
	void exitCompound_statement(FearnGrammarParser.Compound_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#expression_statement}.
	 * @param ctx the parse tree
	 */
	void enterExpression_statement(FearnGrammarParser.Expression_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#expression_statement}.
	 * @param ctx the parse tree
	 */
	void exitExpression_statement(FearnGrammarParser.Expression_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#selection_statement}.
	 * @param ctx the parse tree
	 */
	void enterSelection_statement(FearnGrammarParser.Selection_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#selection_statement}.
	 * @param ctx the parse tree
	 */
	void exitSelection_statement(FearnGrammarParser.Selection_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#iteration_statement}.
	 * @param ctx the parse tree
	 */
	void enterIteration_statement(FearnGrammarParser.Iteration_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#iteration_statement}.
	 * @param ctx the parse tree
	 */
	void exitIteration_statement(FearnGrammarParser.Iteration_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#jump_statement}.
	 * @param ctx the parse tree
	 */
	void enterJump_statement(FearnGrammarParser.Jump_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#jump_statement}.
	 * @param ctx the parse tree
	 */
	void exitJump_statement(FearnGrammarParser.Jump_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(FearnGrammarParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(FearnGrammarParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArray(FearnGrammarParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArray(FearnGrammarParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#array_contents}.
	 * @param ctx the parse tree
	 */
	void enterArray_contents(FearnGrammarParser.Array_contentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#array_contents}.
	 * @param ctx the parse tree
	 */
	void exitArray_contents(FearnGrammarParser.Array_contentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#primary_expression}.
	 * @param ctx the parse tree
	 */
	void enterPrimary_expression(FearnGrammarParser.Primary_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#primary_expression}.
	 * @param ctx the parse tree
	 */
	void exitPrimary_expression(FearnGrammarParser.Primary_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#postfix_expression}.
	 * @param ctx the parse tree
	 */
	void enterPostfix_expression(FearnGrammarParser.Postfix_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#postfix_expression}.
	 * @param ctx the parse tree
	 */
	void exitPostfix_expression(FearnGrammarParser.Postfix_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#argument_list}.
	 * @param ctx the parse tree
	 */
	void enterArgument_list(FearnGrammarParser.Argument_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#argument_list}.
	 * @param ctx the parse tree
	 */
	void exitArgument_list(FearnGrammarParser.Argument_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#unary_expression}.
	 * @param ctx the parse tree
	 */
	void enterUnary_expression(FearnGrammarParser.Unary_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#unary_expression}.
	 * @param ctx the parse tree
	 */
	void exitUnary_expression(FearnGrammarParser.Unary_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#arithmetic_expression}.
	 * @param ctx the parse tree
	 */
	void enterArithmetic_expression(FearnGrammarParser.Arithmetic_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#arithmetic_expression}.
	 * @param ctx the parse tree
	 */
	void exitArithmetic_expression(FearnGrammarParser.Arithmetic_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#bool_expression}.
	 * @param ctx the parse tree
	 */
	void enterBool_expression(FearnGrammarParser.Bool_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#bool_expression}.
	 * @param ctx the parse tree
	 */
	void exitBool_expression(FearnGrammarParser.Bool_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(FearnGrammarParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(FearnGrammarParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#assignment_operator}.
	 * @param ctx the parse tree
	 */
	void enterAssignment_operator(FearnGrammarParser.Assignment_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#assignment_operator}.
	 * @param ctx the parse tree
	 */
	void exitAssignment_operator(FearnGrammarParser.Assignment_operatorContext ctx);
}