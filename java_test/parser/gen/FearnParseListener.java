// Generated from FearnParse.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FearnParseParser}.
 */
public interface FearnParseListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FearnParseParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(FearnParseParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnParseParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(FearnParseParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnParseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(FearnParseParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnParseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(FearnParseParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnParseParser#let}.
	 * @param ctx the parse tree
	 */
	void enterLet(FearnParseParser.LetContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnParseParser#let}.
	 * @param ctx the parse tree
	 */
	void exitLet(FearnParseParser.LetContext ctx);
	/**
	 * Enter a parse tree produced by {@link FearnParseParser#show}.
	 * @param ctx the parse tree
	 */
	void enterShow(FearnParseParser.ShowContext ctx);
	/**
	 * Exit a parse tree produced by {@link FearnParseParser#show}.
	 * @param ctx the parse tree
	 */
	void exitShow(FearnParseParser.ShowContext ctx);
}