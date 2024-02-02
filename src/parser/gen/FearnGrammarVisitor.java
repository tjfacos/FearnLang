// Generated from FearnGrammar.g4 by ANTLR 4.13.1
package parser.gen;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link FearnGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface FearnGrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link FearnGrammarParser#type_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_name(FearnGrammarParser.Type_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link FearnGrammarParser#type_specifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_specifier(FearnGrammarParser.Type_specifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link FearnGrammarParser#type_specifier_primitive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_specifier_primitive(FearnGrammarParser.Type_specifier_primitiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link FearnGrammarParser#type_specifier_struct}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_specifier_struct(FearnGrammarParser.Type_specifier_structContext ctx);
	/**
	 * Visit a parse tree produced by {@link FearnGrammarParser#type_specifier_arr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_specifier_arr(FearnGrammarParser.Type_specifier_arrContext ctx);
	/**
	 * Visit a parse tree produced by {@link FearnGrammarParser#module_import}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModule_import(FearnGrammarParser.Module_importContext ctx);
	/**
	 * Visit a parse tree produced by {@link FearnGrammarParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(FearnGrammarParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link FearnGrammarParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(FearnGrammarParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link FearnGrammarParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(FearnGrammarParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link FearnGrammarParser#struct_def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStruct_def(FearnGrammarParser.Struct_defContext ctx);
	/**
	 * Visit a parse tree produced by {@link FearnGrammarParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(FearnGrammarParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link FearnGrammarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(FearnGrammarParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link FearnGrammarParser#compound_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompound_statement(FearnGrammarParser.Compound_statementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code simple_expr_stmt}
	 * labeled alternative in {@link FearnGrammarParser#expression_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimple_expr_stmt(FearnGrammarParser.Simple_expr_stmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assign_expr_stmt}
	 * labeled alternative in {@link FearnGrammarParser#expression_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign_expr_stmt(FearnGrammarParser.Assign_expr_stmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code single_if}
	 * labeled alternative in {@link FearnGrammarParser#selection_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingle_if(FearnGrammarParser.Single_ifContext ctx);
	/**
	 * Visit a parse tree produced by the {@code if_else}
	 * labeled alternative in {@link FearnGrammarParser#selection_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_else(FearnGrammarParser.If_elseContext ctx);
	/**
	 * Visit a parse tree produced by {@link FearnGrammarParser#iteration_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIteration_statement(FearnGrammarParser.Iteration_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link FearnGrammarParser#init_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInit_expression(FearnGrammarParser.Init_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link FearnGrammarParser#continue_condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinue_condition(FearnGrammarParser.Continue_conditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link FearnGrammarParser#iteration_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIteration_expression(FearnGrammarParser.Iteration_expressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code cont_stmt}
	 * labeled alternative in {@link FearnGrammarParser#jump_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCont_stmt(FearnGrammarParser.Cont_stmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code break_stmt}
	 * labeled alternative in {@link FearnGrammarParser#jump_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreak_stmt(FearnGrammarParser.Break_stmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code return_stmt}
	 * labeled alternative in {@link FearnGrammarParser#jump_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_stmt(FearnGrammarParser.Return_stmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pre_inc_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPre_inc_expr(FearnGrammarParser.Pre_inc_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code add_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdd_expr(FearnGrammarParser.Add_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code index_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndex_expr(FearnGrammarParser.Index_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code less_eq_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLess_eq_expr(FearnGrammarParser.Less_eq_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code u_not_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitU_not_expr(FearnGrammarParser.U_not_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code dot_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDot_expr(FearnGrammarParser.Dot_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code post_inc_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPost_inc_expr(FearnGrammarParser.Post_inc_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arr_init_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArr_init_expr(FearnGrammarParser.Arr_init_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code struct_init_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStruct_init_expr(FearnGrammarParser.Struct_init_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fn_call_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFn_call_expr(FearnGrammarParser.Fn_call_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code less_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLess_expr(FearnGrammarParser.Less_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code greater_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGreater_expr(FearnGrammarParser.Greater_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code and_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd_expr(FearnGrammarParser.And_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lit_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLit_expr(FearnGrammarParser.Lit_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code or_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOr_expr(FearnGrammarParser.Or_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exp_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp_expr(FearnGrammarParser.Exp_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code greater_eq_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGreater_eq_expr(FearnGrammarParser.Greater_eq_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code id_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId_expr(FearnGrammarParser.Id_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code u_plus_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitU_plus_expr(FearnGrammarParser.U_plus_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code cast_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCast_expr(FearnGrammarParser.Cast_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code u_minus_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitU_minus_expr(FearnGrammarParser.U_minus_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code brac_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBrac_expr(FearnGrammarParser.Brac_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mult_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMult_expr(FearnGrammarParser.Mult_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code eq_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEq_expr(FearnGrammarParser.Eq_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code not_eq_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNot_eq_expr(FearnGrammarParser.Not_eq_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link FearnGrammarParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(FearnGrammarParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link FearnGrammarParser#array_init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray_init(FearnGrammarParser.Array_initContext ctx);
	/**
	 * Visit a parse tree produced by {@link FearnGrammarParser#array_body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray_body(FearnGrammarParser.Array_bodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link FearnGrammarParser#struct_init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStruct_init(FearnGrammarParser.Struct_initContext ctx);
	/**
	 * Visit a parse tree produced by {@link FearnGrammarParser#assign_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign_expression(FearnGrammarParser.Assign_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link FearnGrammarParser#assignment_operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment_operator(FearnGrammarParser.Assignment_operatorContext ctx);
}