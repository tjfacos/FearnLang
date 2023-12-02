// Generated from FearnGrammar.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FearnGrammarParser}.
 */
public interface FearnGrammarListener extends ParseTreeListener {
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
	 * Enter a parse tree produced by {@link FearnGrammarParser#let}.
	 * @param ctx the parse tree
	 */
	void enterLet(FearnGrammarParser.LetContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#let}.
	 * @param ctx the parse tree
	 */
	void exitLet(FearnGrammarParser.LetContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnGrammarParser#show}.
	 * @param ctx the parse tree
	 */
	void enterShow(FearnGrammarParser.ShowContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnGrammarParser#show}.
	 * @param ctx the parse tree
	 */
	void exitShow(FearnGrammarParser.ShowContext ctx);
}