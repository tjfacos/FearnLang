// Generated from FearnGrammar.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class FearnGrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		INT=1, FLOAT=2, STR=3, BOOL=4;
	public static final int
		RULE_program = 0;
	private static String[] makeRuleNames() {
		return new String[] {
			"program"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "INT", "FLOAT", "STR", "BOOL"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "FearnGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public FearnGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public List<TerminalNode> INT() { return getTokens(FearnGrammarParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(FearnGrammarParser.INT, i);
		}
		public List<TerminalNode> FLOAT() { return getTokens(FearnGrammarParser.FLOAT); }
		public TerminalNode FLOAT(int i) {
			return getToken(FearnGrammarParser.FLOAT, i);
		}
		public List<TerminalNode> STR() { return getTokens(FearnGrammarParser.STR); }
		public TerminalNode STR(int i) {
			return getToken(FearnGrammarParser.STR, i);
		}
		public List<TerminalNode> BOOL() { return getTokens(FearnGrammarParser.BOOL); }
		public TerminalNode BOOL(int i) {
			return getToken(FearnGrammarParser.BOOL, i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FearnGrammarListener ) ((FearnGrammarListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FearnGrammarListener ) ((FearnGrammarListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			setState(22);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(3); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(2);
					match(INT);
					}
					}
					setState(5); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==INT );
				}
				break;
			case FLOAT:
				enterOuterAlt(_localctx, 2);
				{
				setState(8); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(7);
					match(FLOAT);
					}
					}
					setState(10); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==FLOAT );
				}
				break;
			case STR:
				enterOuterAlt(_localctx, 3);
				{
				setState(13); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(12);
					match(STR);
					}
					}
					setState(15); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==STR );
				}
				break;
			case BOOL:
				enterOuterAlt(_localctx, 4);
				{
				setState(18); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(17);
					match(BOOL);
					}
					}
					setState(20); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==BOOL );
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u0004\u0019\u0002\u0000\u0007\u0000\u0001\u0000\u0004\u0000"+
		"\u0004\b\u0000\u000b\u0000\f\u0000\u0005\u0001\u0000\u0004\u0000\t\b\u0000"+
		"\u000b\u0000\f\u0000\n\u0001\u0000\u0004\u0000\u000e\b\u0000\u000b\u0000"+
		"\f\u0000\u000f\u0001\u0000\u0004\u0000\u0013\b\u0000\u000b\u0000\f\u0000"+
		"\u0014\u0003\u0000\u0017\b\u0000\u0001\u0000\u0000\u0000\u0001\u0000\u0000"+
		"\u0000\u001e\u0000\u0016\u0001\u0000\u0000\u0000\u0002\u0004\u0005\u0001"+
		"\u0000\u0000\u0003\u0002\u0001\u0000\u0000\u0000\u0004\u0005\u0001\u0000"+
		"\u0000\u0000\u0005\u0003\u0001\u0000\u0000\u0000\u0005\u0006\u0001\u0000"+
		"\u0000\u0000\u0006\u0017\u0001\u0000\u0000\u0000\u0007\t\u0005\u0002\u0000"+
		"\u0000\b\u0007\u0001\u0000\u0000\u0000\t\n\u0001\u0000\u0000\u0000\n\b"+
		"\u0001\u0000\u0000\u0000\n\u000b\u0001\u0000\u0000\u0000\u000b\u0017\u0001"+
		"\u0000\u0000\u0000\f\u000e\u0005\u0003\u0000\u0000\r\f\u0001\u0000\u0000"+
		"\u0000\u000e\u000f\u0001\u0000\u0000\u0000\u000f\r\u0001\u0000\u0000\u0000"+
		"\u000f\u0010\u0001\u0000\u0000\u0000\u0010\u0017\u0001\u0000\u0000\u0000"+
		"\u0011\u0013\u0005\u0004\u0000\u0000\u0012\u0011\u0001\u0000\u0000\u0000"+
		"\u0013\u0014\u0001\u0000\u0000\u0000\u0014\u0012\u0001\u0000\u0000\u0000"+
		"\u0014\u0015\u0001\u0000\u0000\u0000\u0015\u0017\u0001\u0000\u0000\u0000"+
		"\u0016\u0003\u0001\u0000\u0000\u0000\u0016\b\u0001\u0000\u0000\u0000\u0016"+
		"\r\u0001\u0000\u0000\u0000\u0016\u0012\u0001\u0000\u0000\u0000\u0017\u0001"+
		"\u0001\u0000\u0000\u0000\u0005\u0005\n\u000f\u0014\u0016";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}