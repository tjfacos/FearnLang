// Generated from FearnGrammar.g4 by ANTLR 4.13.2
package io.github.fearnlang.parser.gen;
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
	 * Enter a parse tree produced by {@link FearnGrammarParser#type_specifier_primitive}.
	 * @param ctx the parse tree
	 */
	void enterType_specifier_primitive(FearnGrammarParser.Type_specifier_primitiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#type_specifier_primitive}.
	 * @param ctx the parse tree
	 */
	void exitType_specifier_primitive(FearnGrammarParser.Type_specifier_primitiveContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#type_specifier_struct}.
	 * @param ctx the parse tree
	 */
	void enterType_specifier_struct(FearnGrammarParser.Type_specifier_structContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#type_specifier_struct}.
	 * @param ctx the parse tree
	 */
	void exitType_specifier_struct(FearnGrammarParser.Type_specifier_structContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#type_specifier_arr}.
	 * @param ctx the parse tree
	 */
	void enterType_specifier_arr(FearnGrammarParser.Type_specifier_arrContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#type_specifier_arr}.
	 * @param ctx the parse tree
	 */
	void exitType_specifier_arr(FearnGrammarParser.Type_specifier_arrContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#module_import}.
	 * @param ctx the parse tree
	 */
	void enterModule_import(FearnGrammarParser.Module_importContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#module_import}.
	 * @param ctx the parse tree
	 */
	void exitModule_import(FearnGrammarParser.Module_importContext ctx);
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
	 * Enter a parse tree produced by {@link FearnGrammarParser#struct_def}.
	 * @param ctx the parse tree
	 */
	void enterStruct_def(FearnGrammarParser.Struct_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#struct_def}.
	 * @param ctx the parse tree
	 */
	void exitStruct_def(FearnGrammarParser.Struct_defContext ctx);
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
	 * Enter a parse tree produced by the {@code simple_expr_stmt}
	 * labeled alternative in {@link FearnGrammarParser#expression_statement}.
	 * @param ctx the parse tree
	 */
	void enterSimple_expr_stmt(FearnGrammarParser.Simple_expr_stmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simple_expr_stmt}
	 * labeled alternative in {@link FearnGrammarParser#expression_statement}.
	 * @param ctx the parse tree
	 */
	void exitSimple_expr_stmt(FearnGrammarParser.Simple_expr_stmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assign_expr_stmt}
	 * labeled alternative in {@link FearnGrammarParser#expression_statement}.
	 * @param ctx the parse tree
	 */
	void enterAssign_expr_stmt(FearnGrammarParser.Assign_expr_stmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assign_expr_stmt}
	 * labeled alternative in {@link FearnGrammarParser#expression_statement}.
	 * @param ctx the parse tree
	 */
	void exitAssign_expr_stmt(FearnGrammarParser.Assign_expr_stmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code single_if}
	 * labeled alternative in {@link FearnGrammarParser#selection_statement}.
	 * @param ctx the parse tree
	 */
	void enterSingle_if(FearnGrammarParser.Single_ifContext ctx);
	/**
	 * Exit a parse tree produced by the {@code single_if}
	 * labeled alternative in {@link FearnGrammarParser#selection_statement}.
	 * @param ctx the parse tree
	 */
	void exitSingle_if(FearnGrammarParser.Single_ifContext ctx);
	/**
	 * Enter a parse tree produced by the {@code if_else}
	 * labeled alternative in {@link FearnGrammarParser#selection_statement}.
	 * @param ctx the parse tree
	 */
	void enterIf_else(FearnGrammarParser.If_elseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code if_else}
	 * labeled alternative in {@link FearnGrammarParser#selection_statement}.
	 * @param ctx the parse tree
	 */
	void exitIf_else(FearnGrammarParser.If_elseContext ctx);
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
	 * Enter a parse tree produced by {@link FearnGrammarParser#init_expression}.
	 * @param ctx the parse tree
	 */
	void enterInit_expression(FearnGrammarParser.Init_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#init_expression}.
	 * @param ctx the parse tree
	 */
	void exitInit_expression(FearnGrammarParser.Init_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#continue_condition}.
	 * @param ctx the parse tree
	 */
	void enterContinue_condition(FearnGrammarParser.Continue_conditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#continue_condition}.
	 * @param ctx the parse tree
	 */
	void exitContinue_condition(FearnGrammarParser.Continue_conditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#iteration_expression}.
	 * @param ctx the parse tree
	 */
	void enterIteration_expression(FearnGrammarParser.Iteration_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#iteration_expression}.
	 * @param ctx the parse tree
	 */
	void exitIteration_expression(FearnGrammarParser.Iteration_expressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code cont_stmt}
	 * labeled alternative in {@link FearnGrammarParser#jump_statement}.
	 * @param ctx the parse tree
	 */
	void enterCont_stmt(FearnGrammarParser.Cont_stmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code cont_stmt}
	 * labeled alternative in {@link FearnGrammarParser#jump_statement}.
	 * @param ctx the parse tree
	 */
	void exitCont_stmt(FearnGrammarParser.Cont_stmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code break_stmt}
	 * labeled alternative in {@link FearnGrammarParser#jump_statement}.
	 * @param ctx the parse tree
	 */
	void enterBreak_stmt(FearnGrammarParser.Break_stmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code break_stmt}
	 * labeled alternative in {@link FearnGrammarParser#jump_statement}.
	 * @param ctx the parse tree
	 */
	void exitBreak_stmt(FearnGrammarParser.Break_stmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code return_stmt}
	 * labeled alternative in {@link FearnGrammarParser#jump_statement}.
	 * @param ctx the parse tree
	 */
	void enterReturn_stmt(FearnGrammarParser.Return_stmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code return_stmt}
	 * labeled alternative in {@link FearnGrammarParser#jump_statement}.
	 * @param ctx the parse tree
	 */
	void exitReturn_stmt(FearnGrammarParser.Return_stmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code pre_inc_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPre_inc_expr(FearnGrammarParser.Pre_inc_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code pre_inc_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPre_inc_expr(FearnGrammarParser.Pre_inc_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code add_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAdd_expr(FearnGrammarParser.Add_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code add_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAdd_expr(FearnGrammarParser.Add_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code index_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIndex_expr(FearnGrammarParser.Index_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code index_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIndex_expr(FearnGrammarParser.Index_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code less_eq_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLess_eq_expr(FearnGrammarParser.Less_eq_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code less_eq_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLess_eq_expr(FearnGrammarParser.Less_eq_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code u_not_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterU_not_expr(FearnGrammarParser.U_not_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code u_not_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitU_not_expr(FearnGrammarParser.U_not_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dot_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDot_expr(FearnGrammarParser.Dot_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dot_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDot_expr(FearnGrammarParser.Dot_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code post_inc_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPost_inc_expr(FearnGrammarParser.Post_inc_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code post_inc_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPost_inc_expr(FearnGrammarParser.Post_inc_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arr_init_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArr_init_expr(FearnGrammarParser.Arr_init_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arr_init_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArr_init_expr(FearnGrammarParser.Arr_init_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code struct_init_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterStruct_init_expr(FearnGrammarParser.Struct_init_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code struct_init_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitStruct_init_expr(FearnGrammarParser.Struct_init_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code fn_call_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFn_call_expr(FearnGrammarParser.Fn_call_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code fn_call_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFn_call_expr(FearnGrammarParser.Fn_call_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code less_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLess_expr(FearnGrammarParser.Less_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code less_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLess_expr(FearnGrammarParser.Less_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code greater_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterGreater_expr(FearnGrammarParser.Greater_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code greater_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitGreater_expr(FearnGrammarParser.Greater_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code and_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAnd_expr(FearnGrammarParser.And_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code and_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAnd_expr(FearnGrammarParser.And_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code lit_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLit_expr(FearnGrammarParser.Lit_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code lit_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLit_expr(FearnGrammarParser.Lit_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code or_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOr_expr(FearnGrammarParser.Or_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code or_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOr_expr(FearnGrammarParser.Or_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exp_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExp_expr(FearnGrammarParser.Exp_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exp_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExp_expr(FearnGrammarParser.Exp_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code greater_eq_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterGreater_eq_expr(FearnGrammarParser.Greater_eq_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code greater_eq_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitGreater_eq_expr(FearnGrammarParser.Greater_eq_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code id_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterId_expr(FearnGrammarParser.Id_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code id_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitId_expr(FearnGrammarParser.Id_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code u_plus_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterU_plus_expr(FearnGrammarParser.U_plus_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code u_plus_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitU_plus_expr(FearnGrammarParser.U_plus_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code cast_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCast_expr(FearnGrammarParser.Cast_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code cast_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCast_expr(FearnGrammarParser.Cast_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code u_minus_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterU_minus_expr(FearnGrammarParser.U_minus_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code u_minus_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitU_minus_expr(FearnGrammarParser.U_minus_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code brac_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBrac_expr(FearnGrammarParser.Brac_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code brac_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBrac_expr(FearnGrammarParser.Brac_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mult_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMult_expr(FearnGrammarParser.Mult_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mult_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMult_expr(FearnGrammarParser.Mult_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code eq_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterEq_expr(FearnGrammarParser.Eq_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code eq_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitEq_expr(FearnGrammarParser.Eq_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code not_eq_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNot_eq_expr(FearnGrammarParser.Not_eq_exprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code not_eq_expr}
	 * labeled alternative in {@link FearnGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNot_eq_expr(FearnGrammarParser.Not_eq_exprContext ctx);
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
	 * Enter a parse tree produced by {@link FearnGrammarParser#array_init}.
	 * @param ctx the parse tree
	 */
	void enterArray_init(FearnGrammarParser.Array_initContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#array_init}.
	 * @param ctx the parse tree
	 */
	void exitArray_init(FearnGrammarParser.Array_initContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#array_body}.
	 * @param ctx the parse tree
	 */
	void enterArray_body(FearnGrammarParser.Array_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#array_body}.
	 * @param ctx the parse tree
	 */
	void exitArray_body(FearnGrammarParser.Array_bodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#struct_init}.
	 * @param ctx the parse tree
	 */
	void enterStruct_init(FearnGrammarParser.Struct_initContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#struct_init}.
	 * @param ctx the parse tree
	 */
	void exitStruct_init(FearnGrammarParser.Struct_initContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#assign_expression}.
	 * @param ctx the parse tree
	 */
	void enterAssign_expression(FearnGrammarParser.Assign_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#assign_expression}.
	 * @param ctx the parse tree
	 */
	void exitAssign_expression(FearnGrammarParser.Assign_expressionContext ctx);
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