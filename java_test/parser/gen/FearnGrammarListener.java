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
}