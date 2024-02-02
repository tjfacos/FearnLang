// Generated from FearnGrammar.g4 by ANTLR 4.13.1
package parser.gen;
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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, T__50=51, T__51=52, 
		T__52=53, INT_LIT=54, FLOAT_LIT=55, STR_LIT=56, BOOL_LIT=57, IDENTIFIER=58, 
		WS=59, BLOCKCOMMENT=60, LINECOMMENT=61;
	public static final int
		RULE_type_name = 0, RULE_type_specifier = 1, RULE_type_specifier_primitive = 2, 
		RULE_type_specifier_struct = 3, RULE_type_specifier_arr = 4, RULE_module_import = 5, 
		RULE_program = 6, RULE_function = 7, RULE_parameter = 8, RULE_struct_def = 9, 
		RULE_declaration = 10, RULE_statement = 11, RULE_compound_statement = 12, 
		RULE_expression_statement = 13, RULE_selection_statement = 14, RULE_iteration_statement = 15, 
		RULE_init_expression = 16, RULE_continue_condition = 17, RULE_iteration_expression = 18, 
		RULE_jump_statement = 19, RULE_expression = 20, RULE_literal = 21, RULE_array_init = 22, 
		RULE_array_body = 23, RULE_struct_init = 24, RULE_assign_expression = 25, 
		RULE_assignment_operator = 26;
	private static String[] makeRuleNames() {
		return new String[] {
			"type_name", "type_specifier", "type_specifier_primitive", "type_specifier_struct", 
			"type_specifier_arr", "module_import", "program", "function", "parameter", 
			"struct_def", "declaration", "statement", "compound_statement", "expression_statement", 
			"selection_statement", "iteration_statement", "init_expression", "continue_condition", 
			"iteration_expression", "jump_statement", "expression", "literal", "array_init", 
			"array_body", "struct_init", "assign_expression", "assignment_operator"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'int'", "'float'", "'bool'", "'str'", "'$'", "'[]'", "'import'", 
			"'from'", "'fn'", "'('", "','", "')'", "'=>'", "'void'", "':'", "'struct'", 
			"'{'", "'}'", "'let'", "'='", "';'", "'if'", "'else'", "'for'", "'continue'", 
			"'break'", "'return'", "'.'", "'['", "']'", "'+'", "'-'", "'!'", "'++'", 
			"'--'", "'^'", "'*'", "'/'", "'%'", "'<'", "'>'", "'<='", "'>='", "'=='", 
			"'!='", "'&&'", "'||'", "'new'", "'+='", "'-='", "'*='", "'/='", "'%='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, "INT_LIT", "FLOAT_LIT", "STR_LIT", 
			"BOOL_LIT", "IDENTIFIER", "WS", "BLOCKCOMMENT", "LINECOMMENT"
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
	public static class Type_nameContext extends ParserRuleContext {
		public Type_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_name; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitType_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Type_nameContext type_name() throws RecognitionException {
		Type_nameContext _localctx = new Type_nameContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_type_name);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 30L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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

	@SuppressWarnings("CheckReturnValue")
	public static class Type_specifierContext extends ParserRuleContext {
		public Type_specifier_primitiveContext type_specifier_primitive() {
			return getRuleContext(Type_specifier_primitiveContext.class,0);
		}
		public Type_specifier_structContext type_specifier_struct() {
			return getRuleContext(Type_specifier_structContext.class,0);
		}
		public Type_specifier_arrContext type_specifier_arr() {
			return getRuleContext(Type_specifier_arrContext.class,0);
		}
		public Type_specifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_specifier; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitType_specifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Type_specifierContext type_specifier() throws RecognitionException {
		Type_specifierContext _localctx = new Type_specifierContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_type_specifier);
		try {
			setState(59);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(56);
				type_specifier_primitive();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(57);
				type_specifier_struct();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(58);
				type_specifier_arr();
				}
				break;
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

	@SuppressWarnings("CheckReturnValue")
	public static class Type_specifier_primitiveContext extends ParserRuleContext {
		public Type_nameContext type_name() {
			return getRuleContext(Type_nameContext.class,0);
		}
		public Type_specifier_primitiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_specifier_primitive; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitType_specifier_primitive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Type_specifier_primitiveContext type_specifier_primitive() throws RecognitionException {
		Type_specifier_primitiveContext _localctx = new Type_specifier_primitiveContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_type_specifier_primitive);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			type_name();
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

	@SuppressWarnings("CheckReturnValue")
	public static class Type_specifier_structContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(FearnGrammarParser.IDENTIFIER, 0); }
		public Type_specifier_structContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_specifier_struct; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitType_specifier_struct(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Type_specifier_structContext type_specifier_struct() throws RecognitionException {
		Type_specifier_structContext _localctx = new Type_specifier_structContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_type_specifier_struct);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63);
			match(T__4);
			setState(64);
			match(IDENTIFIER);
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

	@SuppressWarnings("CheckReturnValue")
	public static class Type_specifier_arrContext extends ParserRuleContext {
		public Type_specifier_primitiveContext type_specifier_primitive() {
			return getRuleContext(Type_specifier_primitiveContext.class,0);
		}
		public Type_specifier_structContext type_specifier_struct() {
			return getRuleContext(Type_specifier_structContext.class,0);
		}
		public Type_specifier_arrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_specifier_arr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitType_specifier_arr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Type_specifier_arrContext type_specifier_arr() throws RecognitionException {
		Type_specifier_arrContext _localctx = new Type_specifier_arrContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_type_specifier_arr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
			case T__1:
			case T__2:
			case T__3:
				{
				setState(66);
				type_specifier_primitive();
				}
				break;
			case T__4:
				{
				setState(67);
				type_specifier_struct();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(71); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(70);
				match(T__5);
				}
				}
				setState(73); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__5 );
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

	@SuppressWarnings("CheckReturnValue")
	public static class Module_importContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(FearnGrammarParser.IDENTIFIER, 0); }
		public TerminalNode STR_LIT() { return getToken(FearnGrammarParser.STR_LIT, 0); }
		public Module_importContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_module_import; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitModule_import(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Module_importContext module_import() throws RecognitionException {
		Module_importContext _localctx = new Module_importContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_module_import);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			match(T__6);
			setState(79);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				{
				setState(76);
				match(IDENTIFIER);
				}
				break;
			case T__7:
				{
				setState(77);
				match(T__7);
				setState(78);
				match(STR_LIT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
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

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(FearnGrammarParser.EOF, 0); }
		public List<Module_importContext> module_import() {
			return getRuleContexts(Module_importContext.class);
		}
		public Module_importContext module_import(int i) {
			return getRuleContext(Module_importContext.class,i);
		}
		public List<FunctionContext> function() {
			return getRuleContexts(FunctionContext.class);
		}
		public FunctionContext function(int i) {
			return getRuleContext(FunctionContext.class,i);
		}
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public List<Struct_defContext> struct_def() {
			return getRuleContexts(Struct_defContext.class);
		}
		public Struct_defContext struct_def(int i) {
			return getRuleContext(Struct_defContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__6) {
				{
				{
				setState(81);
				module_import();
				}
				}
				setState(86);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(90); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(90);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__8:
					{
					setState(87);
					function();
					}
					break;
				case T__18:
					{
					setState(88);
					declaration();
					}
					break;
				case T__15:
					{
					setState(89);
					struct_def();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(92); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 590336L) != 0) );
			setState(94);
			match(EOF);
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

	@SuppressWarnings("CheckReturnValue")
	public static class FunctionContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(FearnGrammarParser.IDENTIFIER, 0); }
		public Compound_statementContext compound_statement() {
			return getRuleContext(Compound_statementContext.class,0);
		}
		public Type_specifierContext type_specifier() {
			return getRuleContext(Type_specifierContext.class,0);
		}
		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}
		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}
		public FunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_function);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			match(T__8);
			setState(97);
			match(IDENTIFIER);
			setState(98);
			match(T__9);
			setState(108);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(104);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(99);
						parameter();
						setState(100);
						match(T__10);
						}
						} 
					}
					setState(106);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
				}
				setState(107);
				parameter();
				}
			}

			setState(110);
			match(T__11);
			setState(111);
			match(T__12);
			setState(114);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
			case T__1:
			case T__2:
			case T__3:
			case T__4:
				{
				setState(112);
				type_specifier();
				}
				break;
			case T__13:
				{
				setState(113);
				match(T__13);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(116);
			compound_statement();
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

	@SuppressWarnings("CheckReturnValue")
	public static class ParameterContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(FearnGrammarParser.IDENTIFIER, 0); }
		public Type_specifierContext type_specifier() {
			return getRuleContext(Type_specifierContext.class,0);
		}
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_parameter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
			match(IDENTIFIER);
			setState(119);
			match(T__14);
			setState(120);
			type_specifier();
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

	@SuppressWarnings("CheckReturnValue")
	public static class Struct_defContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(FearnGrammarParser.IDENTIFIER, 0); }
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public Struct_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_struct_def; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitStruct_def(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Struct_defContext struct_def() throws RecognitionException {
		Struct_defContext _localctx = new Struct_defContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_struct_def);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			match(T__15);
			setState(123);
			match(IDENTIFIER);
			setState(124);
			match(T__16);
			setState(128);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__18) {
				{
				{
				setState(125);
				declaration();
				}
				}
				setState(130);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(131);
			match(T__17);
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

	@SuppressWarnings("CheckReturnValue")
	public static class DeclarationContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(FearnGrammarParser.IDENTIFIER, 0); }
		public Type_specifierContext type_specifier() {
			return getRuleContext(Type_specifierContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			match(T__18);
			setState(134);
			match(IDENTIFIER);
			setState(135);
			match(T__14);
			setState(136);
			type_specifier();
			setState(139);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__19) {
				{
				setState(137);
				match(T__19);
				setState(138);
				expression(0);
				}
			}

			setState(141);
			match(T__20);
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

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public Compound_statementContext compound_statement() {
			return getRuleContext(Compound_statementContext.class,0);
		}
		public Expression_statementContext expression_statement() {
			return getRuleContext(Expression_statementContext.class,0);
		}
		public Selection_statementContext selection_statement() {
			return getRuleContext(Selection_statementContext.class,0);
		}
		public Iteration_statementContext iteration_statement() {
			return getRuleContext(Iteration_statementContext.class,0);
		}
		public Jump_statementContext jump_statement() {
			return getRuleContext(Jump_statementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_statement);
		try {
			setState(148);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__16:
				enterOuterAlt(_localctx, 1);
				{
				setState(143);
				compound_statement();
				}
				break;
			case T__9:
			case T__30:
			case T__31:
			case T__32:
			case T__33:
			case T__34:
			case T__47:
			case INT_LIT:
			case FLOAT_LIT:
			case STR_LIT:
			case BOOL_LIT:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				setState(144);
				expression_statement();
				}
				break;
			case T__21:
				enterOuterAlt(_localctx, 3);
				{
				setState(145);
				selection_statement();
				}
				break;
			case T__23:
				enterOuterAlt(_localctx, 4);
				{
				setState(146);
				iteration_statement();
				}
				break;
			case T__24:
			case T__25:
			case T__26:
				enterOuterAlt(_localctx, 5);
				{
				setState(147);
				jump_statement();
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

	@SuppressWarnings("CheckReturnValue")
	public static class Compound_statementContext extends ParserRuleContext {
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public Compound_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compound_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitCompound_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Compound_statementContext compound_statement() throws RecognitionException {
		Compound_statementContext _localctx = new Compound_statementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_compound_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(150);
			match(T__16);
			setState(155);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 558727895599154176L) != 0)) {
				{
				setState(153);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__18:
					{
					setState(151);
					declaration();
					}
					break;
				case T__9:
				case T__16:
				case T__21:
				case T__23:
				case T__24:
				case T__25:
				case T__26:
				case T__30:
				case T__31:
				case T__32:
				case T__33:
				case T__34:
				case T__47:
				case INT_LIT:
				case FLOAT_LIT:
				case STR_LIT:
				case BOOL_LIT:
				case IDENTIFIER:
					{
					setState(152);
					statement();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(157);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(158);
			match(T__17);
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

	@SuppressWarnings("CheckReturnValue")
	public static class Expression_statementContext extends ParserRuleContext {
		public Expression_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression_statement; }
	 
		public Expression_statementContext() { }
		public void copyFrom(Expression_statementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Simple_expr_stmtContext extends Expression_statementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Simple_expr_stmtContext(Expression_statementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitSimple_expr_stmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Assign_expr_stmtContext extends Expression_statementContext {
		public Assign_expressionContext assign_expression() {
			return getRuleContext(Assign_expressionContext.class,0);
		}
		public Assign_expr_stmtContext(Expression_statementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitAssign_expr_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expression_statementContext expression_statement() throws RecognitionException {
		Expression_statementContext _localctx = new Expression_statementContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_expression_statement);
		try {
			setState(166);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				_localctx = new Simple_expr_stmtContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(160);
				expression(0);
				setState(161);
				match(T__20);
				}
				break;
			case 2:
				_localctx = new Assign_expr_stmtContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(163);
				assign_expression();
				setState(164);
				match(T__20);
				}
				break;
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

	@SuppressWarnings("CheckReturnValue")
	public static class Selection_statementContext extends ParserRuleContext {
		public Selection_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selection_statement; }
	 
		public Selection_statementContext() { }
		public void copyFrom(Selection_statementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class If_else_chainContext extends Selection_statementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Compound_statementContext compound_statement() {
			return getRuleContext(Compound_statementContext.class,0);
		}
		public Selection_statementContext selection_statement() {
			return getRuleContext(Selection_statementContext.class,0);
		}
		public If_else_chainContext(Selection_statementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitIf_else_chain(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class If_elseContext extends Selection_statementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<Compound_statementContext> compound_statement() {
			return getRuleContexts(Compound_statementContext.class);
		}
		public Compound_statementContext compound_statement(int i) {
			return getRuleContext(Compound_statementContext.class,i);
		}
		public If_elseContext(Selection_statementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitIf_else(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Single_ifContext extends Selection_statementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Compound_statementContext compound_statement() {
			return getRuleContext(Compound_statementContext.class,0);
		}
		public Single_ifContext(Selection_statementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitSingle_if(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Selection_statementContext selection_statement() throws RecognitionException {
		Selection_statementContext _localctx = new Selection_statementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_selection_statement);
		try {
			setState(190);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				_localctx = new Single_ifContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(168);
				match(T__21);
				setState(169);
				match(T__9);
				setState(170);
				expression(0);
				setState(171);
				match(T__11);
				setState(172);
				compound_statement();
				}
				break;
			case 2:
				_localctx = new If_elseContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(174);
				match(T__21);
				setState(175);
				match(T__9);
				setState(176);
				expression(0);
				setState(177);
				match(T__11);
				setState(178);
				compound_statement();
				setState(179);
				match(T__22);
				setState(180);
				compound_statement();
				}
				break;
			case 3:
				_localctx = new If_else_chainContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(182);
				match(T__21);
				setState(183);
				match(T__9);
				setState(184);
				expression(0);
				setState(185);
				match(T__11);
				setState(186);
				compound_statement();
				setState(187);
				match(T__22);
				setState(188);
				selection_statement();
				}
				break;
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

	@SuppressWarnings("CheckReturnValue")
	public static class Iteration_statementContext extends ParserRuleContext {
		public Init_expressionContext init_expression() {
			return getRuleContext(Init_expressionContext.class,0);
		}
		public Compound_statementContext compound_statement() {
			return getRuleContext(Compound_statementContext.class,0);
		}
		public Continue_conditionContext continue_condition() {
			return getRuleContext(Continue_conditionContext.class,0);
		}
		public Iteration_expressionContext iteration_expression() {
			return getRuleContext(Iteration_expressionContext.class,0);
		}
		public Iteration_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iteration_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitIteration_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Iteration_statementContext iteration_statement() throws RecognitionException {
		Iteration_statementContext _localctx = new Iteration_statementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_iteration_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(192);
			match(T__23);
			setState(193);
			match(T__9);
			setState(194);
			init_expression();
			setState(196);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 558727895342646272L) != 0)) {
				{
				setState(195);
				continue_condition();
				}
			}

			setState(198);
			match(T__20);
			setState(200);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 558727895342646272L) != 0)) {
				{
				setState(199);
				iteration_expression();
				}
			}

			setState(202);
			match(T__11);
			setState(203);
			compound_statement();
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

	@SuppressWarnings("CheckReturnValue")
	public static class Init_expressionContext extends ParserRuleContext {
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Assign_expressionContext assign_expression() {
			return getRuleContext(Assign_expressionContext.class,0);
		}
		public Init_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_init_expression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitInit_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Init_expressionContext init_expression() throws RecognitionException {
		Init_expressionContext _localctx = new Init_expressionContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_init_expression);
		try {
			setState(213);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(205);
				declaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(206);
				expression(0);
				setState(207);
				match(T__20);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(209);
				assign_expression();
				setState(210);
				match(T__20);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(212);
				match(T__20);
				}
				break;
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

	@SuppressWarnings("CheckReturnValue")
	public static class Continue_conditionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Continue_conditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_continue_condition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitContinue_condition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Continue_conditionContext continue_condition() throws RecognitionException {
		Continue_conditionContext _localctx = new Continue_conditionContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_continue_condition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(215);
			expression(0);
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

	@SuppressWarnings("CheckReturnValue")
	public static class Iteration_expressionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Assign_expressionContext assign_expression() {
			return getRuleContext(Assign_expressionContext.class,0);
		}
		public Iteration_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iteration_expression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitIteration_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Iteration_expressionContext iteration_expression() throws RecognitionException {
		Iteration_expressionContext _localctx = new Iteration_expressionContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_iteration_expression);
		try {
			setState(219);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(217);
				expression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(218);
				assign_expression();
				}
				break;
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

	@SuppressWarnings("CheckReturnValue")
	public static class Jump_statementContext extends ParserRuleContext {
		public Jump_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jump_statement; }
	 
		public Jump_statementContext() { }
		public void copyFrom(Jump_statementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Break_stmtContext extends Jump_statementContext {
		public Break_stmtContext(Jump_statementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitBreak_stmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Cont_stmtContext extends Jump_statementContext {
		public Cont_stmtContext(Jump_statementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitCont_stmt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Return_stmtContext extends Jump_statementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Return_stmtContext(Jump_statementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitReturn_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Jump_statementContext jump_statement() throws RecognitionException {
		Jump_statementContext _localctx = new Jump_statementContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_jump_statement);
		int _la;
		try {
			setState(230);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__24:
				_localctx = new Cont_stmtContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(221);
				match(T__24);
				setState(222);
				match(T__20);
				}
				break;
			case T__25:
				_localctx = new Break_stmtContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(223);
				match(T__25);
				setState(224);
				match(T__20);
				}
				break;
			case T__26:
				_localctx = new Return_stmtContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(225);
				match(T__26);
				setState(227);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 558727895342646272L) != 0)) {
					{
					setState(226);
					expression(0);
					}
				}

				setState(229);
				match(T__20);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Pre_inc_exprContext extends ExpressionContext {
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Pre_inc_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitPre_inc_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Add_exprContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Add_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitAdd_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Index_exprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Index_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitIndex_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Less_eq_exprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Less_eq_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitLess_eq_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class U_not_exprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public U_not_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitU_not_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Dot_exprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Dot_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitDot_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Post_inc_exprContext extends ExpressionContext {
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Post_inc_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitPost_inc_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Arr_init_exprContext extends ExpressionContext {
		public Array_initContext array_init() {
			return getRuleContext(Array_initContext.class,0);
		}
		public Arr_init_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitArr_init_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Struct_init_exprContext extends ExpressionContext {
		public Struct_initContext struct_init() {
			return getRuleContext(Struct_initContext.class,0);
		}
		public Struct_init_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitStruct_init_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Fn_call_exprContext extends ExpressionContext {
		public TerminalNode IDENTIFIER() { return getToken(FearnGrammarParser.IDENTIFIER, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Fn_call_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitFn_call_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Less_exprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Less_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitLess_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Greater_exprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Greater_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitGreater_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class And_exprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public And_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitAnd_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Lit_exprContext extends ExpressionContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public Lit_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitLit_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Or_exprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Or_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitOr_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Exp_exprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Exp_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitExp_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Greater_eq_exprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Greater_eq_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitGreater_eq_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Id_exprContext extends ExpressionContext {
		public TerminalNode IDENTIFIER() { return getToken(FearnGrammarParser.IDENTIFIER, 0); }
		public Id_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitId_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class U_plus_exprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public U_plus_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitU_plus_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Cast_exprContext extends ExpressionContext {
		public Type_nameContext type_name() {
			return getRuleContext(Type_nameContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Cast_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitCast_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class U_minus_exprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public U_minus_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitU_minus_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Brac_exprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Brac_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitBrac_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Mult_exprContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Mult_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitMult_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Eq_exprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Eq_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitEq_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Not_eq_exprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Not_eq_exprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitNot_eq_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 40;
		enterRecursionRule(_localctx, 40, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(270);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				{
				_localctx = new Id_exprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(233);
				match(IDENTIFIER);
				}
				break;
			case 2:
				{
				_localctx = new Lit_exprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(234);
				literal();
				}
				break;
			case 3:
				{
				_localctx = new Arr_init_exprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(235);
				array_init();
				}
				break;
			case 4:
				{
				_localctx = new Struct_init_exprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(236);
				struct_init();
				}
				break;
			case 5:
				{
				_localctx = new Fn_call_exprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(237);
				match(IDENTIFIER);
				setState(238);
				match(T__9);
				setState(248);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 558727895342646272L) != 0)) {
					{
					setState(244);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
					while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
						if ( _alt==1 ) {
							{
							{
							setState(239);
							expression(0);
							setState(240);
							match(T__10);
							}
							} 
						}
						setState(246);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
					}
					setState(247);
					expression(0);
					}
				}

				setState(250);
				match(T__11);
				}
				break;
			case 6:
				{
				_localctx = new Brac_exprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(251);
				match(T__9);
				setState(252);
				expression(0);
				setState(253);
				match(T__11);
				}
				break;
			case 7:
				{
				_localctx = new U_plus_exprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(255);
				match(T__30);
				setState(256);
				expression(18);
				}
				break;
			case 8:
				{
				_localctx = new U_minus_exprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(257);
				match(T__31);
				setState(258);
				expression(17);
				}
				break;
			case 9:
				{
				_localctx = new U_not_exprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(259);
				match(T__32);
				setState(260);
				expression(16);
				}
				break;
			case 10:
				{
				_localctx = new Pre_inc_exprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(261);
				((Pre_inc_exprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__33 || _la==T__34) ) {
					((Pre_inc_exprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(262);
				expression(15);
				}
				break;
			case 11:
				{
				_localctx = new U_not_exprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(263);
				match(T__32);
				setState(264);
				expression(13);
				}
				break;
			case 12:
				{
				_localctx = new Cast_exprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(265);
				match(T__9);
				setState(266);
				type_name();
				setState(267);
				match(T__11);
				setState(268);
				expression(12);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(317);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(315);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
					case 1:
						{
						_localctx = new Dot_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(272);
						if (!(precpred(_ctx, 22))) throw new FailedPredicateException(this, "precpred(_ctx, 22)");
						setState(273);
						match(T__27);
						setState(274);
						expression(23);
						}
						break;
					case 2:
						{
						_localctx = new Exp_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(275);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(276);
						match(T__35);
						setState(277);
						expression(12);
						}
						break;
					case 3:
						{
						_localctx = new Mult_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(278);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(279);
						((Mult_exprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 962072674304L) != 0)) ) {
							((Mult_exprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(280);
						expression(11);
						}
						break;
					case 4:
						{
						_localctx = new Add_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(281);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(282);
						((Add_exprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__30 || _la==T__31) ) {
							((Add_exprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(283);
						expression(10);
						}
						break;
					case 5:
						{
						_localctx = new Less_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(284);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(285);
						match(T__39);
						setState(286);
						expression(9);
						}
						break;
					case 6:
						{
						_localctx = new Greater_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(287);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(288);
						match(T__40);
						setState(289);
						expression(8);
						}
						break;
					case 7:
						{
						_localctx = new Less_eq_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(290);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(291);
						match(T__41);
						setState(292);
						expression(7);
						}
						break;
					case 8:
						{
						_localctx = new Greater_eq_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(293);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(294);
						match(T__42);
						setState(295);
						expression(6);
						}
						break;
					case 9:
						{
						_localctx = new Eq_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(296);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(297);
						match(T__43);
						setState(298);
						expression(5);
						}
						break;
					case 10:
						{
						_localctx = new Not_eq_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(299);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(300);
						match(T__44);
						setState(301);
						expression(4);
						}
						break;
					case 11:
						{
						_localctx = new And_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(302);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(303);
						match(T__45);
						setState(304);
						expression(3);
						}
						break;
					case 12:
						{
						_localctx = new Or_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(305);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(306);
						match(T__46);
						setState(307);
						expression(2);
						}
						break;
					case 13:
						{
						_localctx = new Index_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(308);
						if (!(precpred(_ctx, 19))) throw new FailedPredicateException(this, "precpred(_ctx, 19)");
						setState(309);
						match(T__28);
						setState(310);
						expression(0);
						setState(311);
						match(T__29);
						}
						break;
					case 14:
						{
						_localctx = new Post_inc_exprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(313);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(314);
						((Post_inc_exprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__33 || _la==T__34) ) {
							((Post_inc_exprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						}
						break;
					}
					} 
				}
				setState(319);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode STR_LIT() { return getToken(FearnGrammarParser.STR_LIT, 0); }
		public TerminalNode BOOL_LIT() { return getToken(FearnGrammarParser.BOOL_LIT, 0); }
		public TerminalNode INT_LIT() { return getToken(FearnGrammarParser.INT_LIT, 0); }
		public TerminalNode FLOAT_LIT() { return getToken(FearnGrammarParser.FLOAT_LIT, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(320);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 270215977642229760L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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

	@SuppressWarnings("CheckReturnValue")
	public static class Array_initContext extends ParserRuleContext {
		public Type_specifier_primitiveContext type_specifier_primitive() {
			return getRuleContext(Type_specifier_primitiveContext.class,0);
		}
		public Type_specifier_structContext type_specifier_struct() {
			return getRuleContext(Type_specifier_structContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Array_bodyContext array_body() {
			return getRuleContext(Array_bodyContext.class,0);
		}
		public Array_initContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array_init; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitArray_init(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Array_initContext array_init() throws RecognitionException {
		Array_initContext _localctx = new Array_initContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_array_init);
		int _la;
		try {
			int _alt;
			setState(347);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(322);
				match(T__47);
				setState(325);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__0:
				case T__1:
				case T__2:
				case T__3:
					{
					setState(323);
					type_specifier_primitive();
					}
					break;
				case T__4:
					{
					setState(324);
					type_specifier_struct();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(331); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(327);
						match(T__28);
						setState(328);
						expression(0);
						setState(329);
						match(T__29);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(333); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(335);
				match(T__47);
				setState(338);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__0:
				case T__1:
				case T__2:
				case T__3:
					{
					setState(336);
					type_specifier_primitive();
					}
					break;
				case T__4:
					{
					setState(337);
					type_specifier_struct();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(341); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(340);
					match(T__5);
					}
					}
					setState(343); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__5 );
				setState(345);
				array_body();
				}
				break;
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

	@SuppressWarnings("CheckReturnValue")
	public static class Array_bodyContext extends ParserRuleContext {
		public List<Array_bodyContext> array_body() {
			return getRuleContexts(Array_bodyContext.class);
		}
		public Array_bodyContext array_body(int i) {
			return getRuleContext(Array_bodyContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Array_bodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array_body; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitArray_body(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Array_bodyContext array_body() throws RecognitionException {
		Array_bodyContext _localctx = new Array_bodyContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_array_body);
		try {
			int _alt;
			setState(375);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(349);
				match(T__16);
				setState(355);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(350);
						array_body();
						setState(351);
						match(T__10);
						}
						} 
					}
					setState(357);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,33,_ctx);
				}
				setState(358);
				array_body();
				setState(359);
				match(T__17);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(361);
				match(T__16);
				setState(367);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(362);
						expression(0);
						setState(363);
						match(T__10);
						}
						} 
					}
					setState(369);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
				}
				setState(370);
				expression(0);
				setState(371);
				match(T__17);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(373);
				match(T__16);
				setState(374);
				match(T__17);
				}
				break;
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

	@SuppressWarnings("CheckReturnValue")
	public static class Struct_initContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(FearnGrammarParser.IDENTIFIER, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Struct_initContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_struct_init; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitStruct_init(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Struct_initContext struct_init() throws RecognitionException {
		Struct_initContext _localctx = new Struct_initContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_struct_init);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(377);
			match(T__47);
			setState(378);
			match(IDENTIFIER);
			setState(379);
			match(T__9);
			setState(389);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 558727895342646272L) != 0)) {
				{
				setState(385);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(380);
						expression(0);
						setState(381);
						match(T__10);
						}
						} 
					}
					setState(387);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
				}
				setState(388);
				expression(0);
				}
			}

			setState(391);
			match(T__11);
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

	@SuppressWarnings("CheckReturnValue")
	public static class Assign_expressionContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Assignment_operatorContext assignment_operator() {
			return getRuleContext(Assignment_operatorContext.class,0);
		}
		public Assign_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign_expression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitAssign_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Assign_expressionContext assign_expression() throws RecognitionException {
		Assign_expressionContext _localctx = new Assign_expressionContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_assign_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(393);
			expression(0);
			setState(394);
			assignment_operator();
			setState(395);
			expression(0);
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

	@SuppressWarnings("CheckReturnValue")
	public static class Assignment_operatorContext extends ParserRuleContext {
		public Assignment_operatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment_operator; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FearnGrammarVisitor ) return ((FearnGrammarVisitor<? extends T>)visitor).visitAssignment_operator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Assignment_operatorContext assignment_operator() throws RecognitionException {
		Assignment_operatorContext _localctx = new Assignment_operatorContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_assignment_operator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(397);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 17451448557109248L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 20:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 22);
		case 1:
			return precpred(_ctx, 11);
		case 2:
			return precpred(_ctx, 10);
		case 3:
			return precpred(_ctx, 9);
		case 4:
			return precpred(_ctx, 8);
		case 5:
			return precpred(_ctx, 7);
		case 6:
			return precpred(_ctx, 6);
		case 7:
			return precpred(_ctx, 5);
		case 8:
			return precpred(_ctx, 4);
		case 9:
			return precpred(_ctx, 3);
		case 10:
			return precpred(_ctx, 2);
		case 11:
			return precpred(_ctx, 1);
		case 12:
			return precpred(_ctx, 19);
		case 13:
			return precpred(_ctx, 14);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001=\u0190\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001<\b\u0001\u0001\u0002"+
		"\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004"+
		"\u0003\u0004E\b\u0004\u0001\u0004\u0004\u0004H\b\u0004\u000b\u0004\f\u0004"+
		"I\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005P\b\u0005"+
		"\u0001\u0006\u0005\u0006S\b\u0006\n\u0006\f\u0006V\t\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0004\u0006[\b\u0006\u000b\u0006\f\u0006\\\u0001"+
		"\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0005\u0007g\b\u0007\n\u0007\f\u0007j\t\u0007\u0001"+
		"\u0007\u0003\u0007m\b\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0003\u0007s\b\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0005\t\u007f\b\t\n\t\f\t\u0082"+
		"\t\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003"+
		"\n\u008c\b\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0003\u000b\u0095\b\u000b\u0001\f\u0001\f\u0001\f\u0005"+
		"\f\u009a\b\f\n\f\f\f\u009d\t\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r"+
		"\u0001\r\u0001\r\u0001\r\u0003\r\u00a7\b\r\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0003\u000e\u00bf\b\u000e\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0003\u000f\u00c5\b\u000f\u0001\u000f\u0001\u000f\u0003"+
		"\u000f\u00c9\b\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0003\u0010\u00d6\b\u0010\u0001\u0011\u0001\u0011\u0001\u0012\u0001"+
		"\u0012\u0003\u0012\u00dc\b\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0003\u0013\u00e4\b\u0013\u0001\u0013\u0003"+
		"\u0013\u00e7\b\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0005"+
		"\u0014\u00f3\b\u0014\n\u0014\f\u0014\u00f6\t\u0014\u0001\u0014\u0003\u0014"+
		"\u00f9\b\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0003\u0014\u010f\b\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0005\u0014\u013c\b\u0014\n\u0014\f\u0014\u013f\t\u0014\u0001\u0015\u0001"+
		"\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0003\u0016\u0146\b\u0016\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0004\u0016\u014c\b\u0016\u000b"+
		"\u0016\f\u0016\u014d\u0001\u0016\u0001\u0016\u0001\u0016\u0003\u0016\u0153"+
		"\b\u0016\u0001\u0016\u0004\u0016\u0156\b\u0016\u000b\u0016\f\u0016\u0157"+
		"\u0001\u0016\u0001\u0016\u0003\u0016\u015c\b\u0016\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0005\u0017\u0162\b\u0017\n\u0017\f\u0017\u0165"+
		"\t\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001"+
		"\u0017\u0001\u0017\u0005\u0017\u016e\b\u0017\n\u0017\f\u0017\u0171\t\u0017"+
		"\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0003\u0017"+
		"\u0178\b\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0005\u0018\u0180\b\u0018\n\u0018\f\u0018\u0183\t\u0018\u0001"+
		"\u0018\u0003\u0018\u0186\b\u0018\u0001\u0018\u0001\u0018\u0001\u0019\u0001"+
		"\u0019\u0001\u0019\u0001\u0019\u0001\u001a\u0001\u001a\u0001\u001a\u0000"+
		"\u0001(\u001b\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016"+
		"\u0018\u001a\u001c\u001e \"$&(*,.024\u0000\u0006\u0001\u0000\u0001\u0004"+
		"\u0001\u0000\"#\u0001\u0000%\'\u0001\u0000\u001f \u0001\u000069\u0002"+
		"\u0000\u0014\u001415\u01ba\u00006\u0001\u0000\u0000\u0000\u0002;\u0001"+
		"\u0000\u0000\u0000\u0004=\u0001\u0000\u0000\u0000\u0006?\u0001\u0000\u0000"+
		"\u0000\bD\u0001\u0000\u0000\u0000\nK\u0001\u0000\u0000\u0000\fT\u0001"+
		"\u0000\u0000\u0000\u000e`\u0001\u0000\u0000\u0000\u0010v\u0001\u0000\u0000"+
		"\u0000\u0012z\u0001\u0000\u0000\u0000\u0014\u0085\u0001\u0000\u0000\u0000"+
		"\u0016\u0094\u0001\u0000\u0000\u0000\u0018\u0096\u0001\u0000\u0000\u0000"+
		"\u001a\u00a6\u0001\u0000\u0000\u0000\u001c\u00be\u0001\u0000\u0000\u0000"+
		"\u001e\u00c0\u0001\u0000\u0000\u0000 \u00d5\u0001\u0000\u0000\u0000\""+
		"\u00d7\u0001\u0000\u0000\u0000$\u00db\u0001\u0000\u0000\u0000&\u00e6\u0001"+
		"\u0000\u0000\u0000(\u010e\u0001\u0000\u0000\u0000*\u0140\u0001\u0000\u0000"+
		"\u0000,\u015b\u0001\u0000\u0000\u0000.\u0177\u0001\u0000\u0000\u00000"+
		"\u0179\u0001\u0000\u0000\u00002\u0189\u0001\u0000\u0000\u00004\u018d\u0001"+
		"\u0000\u0000\u000067\u0007\u0000\u0000\u00007\u0001\u0001\u0000\u0000"+
		"\u00008<\u0003\u0004\u0002\u00009<\u0003\u0006\u0003\u0000:<\u0003\b\u0004"+
		"\u0000;8\u0001\u0000\u0000\u0000;9\u0001\u0000\u0000\u0000;:\u0001\u0000"+
		"\u0000\u0000<\u0003\u0001\u0000\u0000\u0000=>\u0003\u0000\u0000\u0000"+
		">\u0005\u0001\u0000\u0000\u0000?@\u0005\u0005\u0000\u0000@A\u0005:\u0000"+
		"\u0000A\u0007\u0001\u0000\u0000\u0000BE\u0003\u0004\u0002\u0000CE\u0003"+
		"\u0006\u0003\u0000DB\u0001\u0000\u0000\u0000DC\u0001\u0000\u0000\u0000"+
		"EG\u0001\u0000\u0000\u0000FH\u0005\u0006\u0000\u0000GF\u0001\u0000\u0000"+
		"\u0000HI\u0001\u0000\u0000\u0000IG\u0001\u0000\u0000\u0000IJ\u0001\u0000"+
		"\u0000\u0000J\t\u0001\u0000\u0000\u0000KO\u0005\u0007\u0000\u0000LP\u0005"+
		":\u0000\u0000MN\u0005\b\u0000\u0000NP\u00058\u0000\u0000OL\u0001\u0000"+
		"\u0000\u0000OM\u0001\u0000\u0000\u0000P\u000b\u0001\u0000\u0000\u0000"+
		"QS\u0003\n\u0005\u0000RQ\u0001\u0000\u0000\u0000SV\u0001\u0000\u0000\u0000"+
		"TR\u0001\u0000\u0000\u0000TU\u0001\u0000\u0000\u0000UZ\u0001\u0000\u0000"+
		"\u0000VT\u0001\u0000\u0000\u0000W[\u0003\u000e\u0007\u0000X[\u0003\u0014"+
		"\n\u0000Y[\u0003\u0012\t\u0000ZW\u0001\u0000\u0000\u0000ZX\u0001\u0000"+
		"\u0000\u0000ZY\u0001\u0000\u0000\u0000[\\\u0001\u0000\u0000\u0000\\Z\u0001"+
		"\u0000\u0000\u0000\\]\u0001\u0000\u0000\u0000]^\u0001\u0000\u0000\u0000"+
		"^_\u0005\u0000\u0000\u0001_\r\u0001\u0000\u0000\u0000`a\u0005\t\u0000"+
		"\u0000ab\u0005:\u0000\u0000bl\u0005\n\u0000\u0000cd\u0003\u0010\b\u0000"+
		"de\u0005\u000b\u0000\u0000eg\u0001\u0000\u0000\u0000fc\u0001\u0000\u0000"+
		"\u0000gj\u0001\u0000\u0000\u0000hf\u0001\u0000\u0000\u0000hi\u0001\u0000"+
		"\u0000\u0000ik\u0001\u0000\u0000\u0000jh\u0001\u0000\u0000\u0000km\u0003"+
		"\u0010\b\u0000lh\u0001\u0000\u0000\u0000lm\u0001\u0000\u0000\u0000mn\u0001"+
		"\u0000\u0000\u0000no\u0005\f\u0000\u0000or\u0005\r\u0000\u0000ps\u0003"+
		"\u0002\u0001\u0000qs\u0005\u000e\u0000\u0000rp\u0001\u0000\u0000\u0000"+
		"rq\u0001\u0000\u0000\u0000st\u0001\u0000\u0000\u0000tu\u0003\u0018\f\u0000"+
		"u\u000f\u0001\u0000\u0000\u0000vw\u0005:\u0000\u0000wx\u0005\u000f\u0000"+
		"\u0000xy\u0003\u0002\u0001\u0000y\u0011\u0001\u0000\u0000\u0000z{\u0005"+
		"\u0010\u0000\u0000{|\u0005:\u0000\u0000|\u0080\u0005\u0011\u0000\u0000"+
		"}\u007f\u0003\u0014\n\u0000~}\u0001\u0000\u0000\u0000\u007f\u0082\u0001"+
		"\u0000\u0000\u0000\u0080~\u0001\u0000\u0000\u0000\u0080\u0081\u0001\u0000"+
		"\u0000\u0000\u0081\u0083\u0001\u0000\u0000\u0000\u0082\u0080\u0001\u0000"+
		"\u0000\u0000\u0083\u0084\u0005\u0012\u0000\u0000\u0084\u0013\u0001\u0000"+
		"\u0000\u0000\u0085\u0086\u0005\u0013\u0000\u0000\u0086\u0087\u0005:\u0000"+
		"\u0000\u0087\u0088\u0005\u000f\u0000\u0000\u0088\u008b\u0003\u0002\u0001"+
		"\u0000\u0089\u008a\u0005\u0014\u0000\u0000\u008a\u008c\u0003(\u0014\u0000"+
		"\u008b\u0089\u0001\u0000\u0000\u0000\u008b\u008c\u0001\u0000\u0000\u0000"+
		"\u008c\u008d\u0001\u0000\u0000\u0000\u008d\u008e\u0005\u0015\u0000\u0000"+
		"\u008e\u0015\u0001\u0000\u0000\u0000\u008f\u0095\u0003\u0018\f\u0000\u0090"+
		"\u0095\u0003\u001a\r\u0000\u0091\u0095\u0003\u001c\u000e\u0000\u0092\u0095"+
		"\u0003\u001e\u000f\u0000\u0093\u0095\u0003&\u0013\u0000\u0094\u008f\u0001"+
		"\u0000\u0000\u0000\u0094\u0090\u0001\u0000\u0000\u0000\u0094\u0091\u0001"+
		"\u0000\u0000\u0000\u0094\u0092\u0001\u0000\u0000\u0000\u0094\u0093\u0001"+
		"\u0000\u0000\u0000\u0095\u0017\u0001\u0000\u0000\u0000\u0096\u009b\u0005"+
		"\u0011\u0000\u0000\u0097\u009a\u0003\u0014\n\u0000\u0098\u009a\u0003\u0016"+
		"\u000b\u0000\u0099\u0097\u0001\u0000\u0000\u0000\u0099\u0098\u0001\u0000"+
		"\u0000\u0000\u009a\u009d\u0001\u0000\u0000\u0000\u009b\u0099\u0001\u0000"+
		"\u0000\u0000\u009b\u009c\u0001\u0000\u0000\u0000\u009c\u009e\u0001\u0000"+
		"\u0000\u0000\u009d\u009b\u0001\u0000\u0000\u0000\u009e\u009f\u0005\u0012"+
		"\u0000\u0000\u009f\u0019\u0001\u0000\u0000\u0000\u00a0\u00a1\u0003(\u0014"+
		"\u0000\u00a1\u00a2\u0005\u0015\u0000\u0000\u00a2\u00a7\u0001\u0000\u0000"+
		"\u0000\u00a3\u00a4\u00032\u0019\u0000\u00a4\u00a5\u0005\u0015\u0000\u0000"+
		"\u00a5\u00a7\u0001\u0000\u0000\u0000\u00a6\u00a0\u0001\u0000\u0000\u0000"+
		"\u00a6\u00a3\u0001\u0000\u0000\u0000\u00a7\u001b\u0001\u0000\u0000\u0000"+
		"\u00a8\u00a9\u0005\u0016\u0000\u0000\u00a9\u00aa\u0005\n\u0000\u0000\u00aa"+
		"\u00ab\u0003(\u0014\u0000\u00ab\u00ac\u0005\f\u0000\u0000\u00ac\u00ad"+
		"\u0003\u0018\f\u0000\u00ad\u00bf\u0001\u0000\u0000\u0000\u00ae\u00af\u0005"+
		"\u0016\u0000\u0000\u00af\u00b0\u0005\n\u0000\u0000\u00b0\u00b1\u0003("+
		"\u0014\u0000\u00b1\u00b2\u0005\f\u0000\u0000\u00b2\u00b3\u0003\u0018\f"+
		"\u0000\u00b3\u00b4\u0005\u0017\u0000\u0000\u00b4\u00b5\u0003\u0018\f\u0000"+
		"\u00b5\u00bf\u0001\u0000\u0000\u0000\u00b6\u00b7\u0005\u0016\u0000\u0000"+
		"\u00b7\u00b8\u0005\n\u0000\u0000\u00b8\u00b9\u0003(\u0014\u0000\u00b9"+
		"\u00ba\u0005\f\u0000\u0000\u00ba\u00bb\u0003\u0018\f\u0000\u00bb\u00bc"+
		"\u0005\u0017\u0000\u0000\u00bc\u00bd\u0003\u001c\u000e\u0000\u00bd\u00bf"+
		"\u0001\u0000\u0000\u0000\u00be\u00a8\u0001\u0000\u0000\u0000\u00be\u00ae"+
		"\u0001\u0000\u0000\u0000\u00be\u00b6\u0001\u0000\u0000\u0000\u00bf\u001d"+
		"\u0001\u0000\u0000\u0000\u00c0\u00c1\u0005\u0018\u0000\u0000\u00c1\u00c2"+
		"\u0005\n\u0000\u0000\u00c2\u00c4\u0003 \u0010\u0000\u00c3\u00c5\u0003"+
		"\"\u0011\u0000\u00c4\u00c3\u0001\u0000\u0000\u0000\u00c4\u00c5\u0001\u0000"+
		"\u0000\u0000\u00c5\u00c6\u0001\u0000\u0000\u0000\u00c6\u00c8\u0005\u0015"+
		"\u0000\u0000\u00c7\u00c9\u0003$\u0012\u0000\u00c8\u00c7\u0001\u0000\u0000"+
		"\u0000\u00c8\u00c9\u0001\u0000\u0000\u0000\u00c9\u00ca\u0001\u0000\u0000"+
		"\u0000\u00ca\u00cb\u0005\f\u0000\u0000\u00cb\u00cc\u0003\u0018\f\u0000"+
		"\u00cc\u001f\u0001\u0000\u0000\u0000\u00cd\u00d6\u0003\u0014\n\u0000\u00ce"+
		"\u00cf\u0003(\u0014\u0000\u00cf\u00d0\u0005\u0015\u0000\u0000\u00d0\u00d6"+
		"\u0001\u0000\u0000\u0000\u00d1\u00d2\u00032\u0019\u0000\u00d2\u00d3\u0005"+
		"\u0015\u0000\u0000\u00d3\u00d6\u0001\u0000\u0000\u0000\u00d4\u00d6\u0005"+
		"\u0015\u0000\u0000\u00d5\u00cd\u0001\u0000\u0000\u0000\u00d5\u00ce\u0001"+
		"\u0000\u0000\u0000\u00d5\u00d1\u0001\u0000\u0000\u0000\u00d5\u00d4\u0001"+
		"\u0000\u0000\u0000\u00d6!\u0001\u0000\u0000\u0000\u00d7\u00d8\u0003(\u0014"+
		"\u0000\u00d8#\u0001\u0000\u0000\u0000\u00d9\u00dc\u0003(\u0014\u0000\u00da"+
		"\u00dc\u00032\u0019\u0000\u00db\u00d9\u0001\u0000\u0000\u0000\u00db\u00da"+
		"\u0001\u0000\u0000\u0000\u00dc%\u0001\u0000\u0000\u0000\u00dd\u00de\u0005"+
		"\u0019\u0000\u0000\u00de\u00e7\u0005\u0015\u0000\u0000\u00df\u00e0\u0005"+
		"\u001a\u0000\u0000\u00e0\u00e7\u0005\u0015\u0000\u0000\u00e1\u00e3\u0005"+
		"\u001b\u0000\u0000\u00e2\u00e4\u0003(\u0014\u0000\u00e3\u00e2\u0001\u0000"+
		"\u0000\u0000\u00e3\u00e4\u0001\u0000\u0000\u0000\u00e4\u00e5\u0001\u0000"+
		"\u0000\u0000\u00e5\u00e7\u0005\u0015\u0000\u0000\u00e6\u00dd\u0001\u0000"+
		"\u0000\u0000\u00e6\u00df\u0001\u0000\u0000\u0000\u00e6\u00e1\u0001\u0000"+
		"\u0000\u0000\u00e7\'\u0001\u0000\u0000\u0000\u00e8\u00e9\u0006\u0014\uffff"+
		"\uffff\u0000\u00e9\u010f\u0005:\u0000\u0000\u00ea\u010f\u0003*\u0015\u0000"+
		"\u00eb\u010f\u0003,\u0016\u0000\u00ec\u010f\u00030\u0018\u0000\u00ed\u00ee"+
		"\u0005:\u0000\u0000\u00ee\u00f8\u0005\n\u0000\u0000\u00ef\u00f0\u0003"+
		"(\u0014\u0000\u00f0\u00f1\u0005\u000b\u0000\u0000\u00f1\u00f3\u0001\u0000"+
		"\u0000\u0000\u00f2\u00ef\u0001\u0000\u0000\u0000\u00f3\u00f6\u0001\u0000"+
		"\u0000\u0000\u00f4\u00f2\u0001\u0000\u0000\u0000\u00f4\u00f5\u0001\u0000"+
		"\u0000\u0000\u00f5\u00f7\u0001\u0000\u0000\u0000\u00f6\u00f4\u0001\u0000"+
		"\u0000\u0000\u00f7\u00f9\u0003(\u0014\u0000\u00f8\u00f4\u0001\u0000\u0000"+
		"\u0000\u00f8\u00f9\u0001\u0000\u0000\u0000\u00f9\u00fa\u0001\u0000\u0000"+
		"\u0000\u00fa\u010f\u0005\f\u0000\u0000\u00fb\u00fc\u0005\n\u0000\u0000"+
		"\u00fc\u00fd\u0003(\u0014\u0000\u00fd\u00fe\u0005\f\u0000\u0000\u00fe"+
		"\u010f\u0001\u0000\u0000\u0000\u00ff\u0100\u0005\u001f\u0000\u0000\u0100"+
		"\u010f\u0003(\u0014\u0012\u0101\u0102\u0005 \u0000\u0000\u0102\u010f\u0003"+
		"(\u0014\u0011\u0103\u0104\u0005!\u0000\u0000\u0104\u010f\u0003(\u0014"+
		"\u0010\u0105\u0106\u0007\u0001\u0000\u0000\u0106\u010f\u0003(\u0014\u000f"+
		"\u0107\u0108\u0005!\u0000\u0000\u0108\u010f\u0003(\u0014\r\u0109\u010a"+
		"\u0005\n\u0000\u0000\u010a\u010b\u0003\u0000\u0000\u0000\u010b\u010c\u0005"+
		"\f\u0000\u0000\u010c\u010d\u0003(\u0014\f\u010d\u010f\u0001\u0000\u0000"+
		"\u0000\u010e\u00e8\u0001\u0000\u0000\u0000\u010e\u00ea\u0001\u0000\u0000"+
		"\u0000\u010e\u00eb\u0001\u0000\u0000\u0000\u010e\u00ec\u0001\u0000\u0000"+
		"\u0000\u010e\u00ed\u0001\u0000\u0000\u0000\u010e\u00fb\u0001\u0000\u0000"+
		"\u0000\u010e\u00ff\u0001\u0000\u0000\u0000\u010e\u0101\u0001\u0000\u0000"+
		"\u0000\u010e\u0103\u0001\u0000\u0000\u0000\u010e\u0105\u0001\u0000\u0000"+
		"\u0000\u010e\u0107\u0001\u0000\u0000\u0000\u010e\u0109\u0001\u0000\u0000"+
		"\u0000\u010f\u013d\u0001\u0000\u0000\u0000\u0110\u0111\n\u0016\u0000\u0000"+
		"\u0111\u0112\u0005\u001c\u0000\u0000\u0112\u013c\u0003(\u0014\u0017\u0113"+
		"\u0114\n\u000b\u0000\u0000\u0114\u0115\u0005$\u0000\u0000\u0115\u013c"+
		"\u0003(\u0014\f\u0116\u0117\n\n\u0000\u0000\u0117\u0118\u0007\u0002\u0000"+
		"\u0000\u0118\u013c\u0003(\u0014\u000b\u0119\u011a\n\t\u0000\u0000\u011a"+
		"\u011b\u0007\u0003\u0000\u0000\u011b\u013c\u0003(\u0014\n\u011c\u011d"+
		"\n\b\u0000\u0000\u011d\u011e\u0005(\u0000\u0000\u011e\u013c\u0003(\u0014"+
		"\t\u011f\u0120\n\u0007\u0000\u0000\u0120\u0121\u0005)\u0000\u0000\u0121"+
		"\u013c\u0003(\u0014\b\u0122\u0123\n\u0006\u0000\u0000\u0123\u0124\u0005"+
		"*\u0000\u0000\u0124\u013c\u0003(\u0014\u0007\u0125\u0126\n\u0005\u0000"+
		"\u0000\u0126\u0127\u0005+\u0000\u0000\u0127\u013c\u0003(\u0014\u0006\u0128"+
		"\u0129\n\u0004\u0000\u0000\u0129\u012a\u0005,\u0000\u0000\u012a\u013c"+
		"\u0003(\u0014\u0005\u012b\u012c\n\u0003\u0000\u0000\u012c\u012d\u0005"+
		"-\u0000\u0000\u012d\u013c\u0003(\u0014\u0004\u012e\u012f\n\u0002\u0000"+
		"\u0000\u012f\u0130\u0005.\u0000\u0000\u0130\u013c\u0003(\u0014\u0003\u0131"+
		"\u0132\n\u0001\u0000\u0000\u0132\u0133\u0005/\u0000\u0000\u0133\u013c"+
		"\u0003(\u0014\u0002\u0134\u0135\n\u0013\u0000\u0000\u0135\u0136\u0005"+
		"\u001d\u0000\u0000\u0136\u0137\u0003(\u0014\u0000\u0137\u0138\u0005\u001e"+
		"\u0000\u0000\u0138\u013c\u0001\u0000\u0000\u0000\u0139\u013a\n\u000e\u0000"+
		"\u0000\u013a\u013c\u0007\u0001\u0000\u0000\u013b\u0110\u0001\u0000\u0000"+
		"\u0000\u013b\u0113\u0001\u0000\u0000\u0000\u013b\u0116\u0001\u0000\u0000"+
		"\u0000\u013b\u0119\u0001\u0000\u0000\u0000\u013b\u011c\u0001\u0000\u0000"+
		"\u0000\u013b\u011f\u0001\u0000\u0000\u0000\u013b\u0122\u0001\u0000\u0000"+
		"\u0000\u013b\u0125\u0001\u0000\u0000\u0000\u013b\u0128\u0001\u0000\u0000"+
		"\u0000\u013b\u012b\u0001\u0000\u0000\u0000\u013b\u012e\u0001\u0000\u0000"+
		"\u0000\u013b\u0131\u0001\u0000\u0000\u0000\u013b\u0134\u0001\u0000\u0000"+
		"\u0000\u013b\u0139\u0001\u0000\u0000\u0000\u013c\u013f\u0001\u0000\u0000"+
		"\u0000\u013d\u013b\u0001\u0000\u0000\u0000\u013d\u013e\u0001\u0000\u0000"+
		"\u0000\u013e)\u0001\u0000\u0000\u0000\u013f\u013d\u0001\u0000\u0000\u0000"+
		"\u0140\u0141\u0007\u0004\u0000\u0000\u0141+\u0001\u0000\u0000\u0000\u0142"+
		"\u0145\u00050\u0000\u0000\u0143\u0146\u0003\u0004\u0002\u0000\u0144\u0146"+
		"\u0003\u0006\u0003\u0000\u0145\u0143\u0001\u0000\u0000\u0000\u0145\u0144"+
		"\u0001\u0000\u0000\u0000\u0146\u014b\u0001\u0000\u0000\u0000\u0147\u0148"+
		"\u0005\u001d\u0000\u0000\u0148\u0149\u0003(\u0014\u0000\u0149\u014a\u0005"+
		"\u001e\u0000\u0000\u014a\u014c\u0001\u0000\u0000\u0000\u014b\u0147\u0001"+
		"\u0000\u0000\u0000\u014c\u014d\u0001\u0000\u0000\u0000\u014d\u014b\u0001"+
		"\u0000\u0000\u0000\u014d\u014e\u0001\u0000\u0000\u0000\u014e\u015c\u0001"+
		"\u0000\u0000\u0000\u014f\u0152\u00050\u0000\u0000\u0150\u0153\u0003\u0004"+
		"\u0002\u0000\u0151\u0153\u0003\u0006\u0003\u0000\u0152\u0150\u0001\u0000"+
		"\u0000\u0000\u0152\u0151\u0001\u0000\u0000\u0000\u0153\u0155\u0001\u0000"+
		"\u0000\u0000\u0154\u0156\u0005\u0006\u0000\u0000\u0155\u0154\u0001\u0000"+
		"\u0000\u0000\u0156\u0157\u0001\u0000\u0000\u0000\u0157\u0155\u0001\u0000"+
		"\u0000\u0000\u0157\u0158\u0001\u0000\u0000\u0000\u0158\u0159\u0001\u0000"+
		"\u0000\u0000\u0159\u015a\u0003.\u0017\u0000\u015a\u015c\u0001\u0000\u0000"+
		"\u0000\u015b\u0142\u0001\u0000\u0000\u0000\u015b\u014f\u0001\u0000\u0000"+
		"\u0000\u015c-\u0001\u0000\u0000\u0000\u015d\u0163\u0005\u0011\u0000\u0000"+
		"\u015e\u015f\u0003.\u0017\u0000\u015f\u0160\u0005\u000b\u0000\u0000\u0160"+
		"\u0162\u0001\u0000\u0000\u0000\u0161\u015e\u0001\u0000\u0000\u0000\u0162"+
		"\u0165\u0001\u0000\u0000\u0000\u0163\u0161\u0001\u0000\u0000\u0000\u0163"+
		"\u0164\u0001\u0000\u0000\u0000\u0164\u0166\u0001\u0000\u0000\u0000\u0165"+
		"\u0163\u0001\u0000\u0000\u0000\u0166\u0167\u0003.\u0017\u0000\u0167\u0168"+
		"\u0005\u0012\u0000\u0000\u0168\u0178\u0001\u0000\u0000\u0000\u0169\u016f"+
		"\u0005\u0011\u0000\u0000\u016a\u016b\u0003(\u0014\u0000\u016b\u016c\u0005"+
		"\u000b\u0000\u0000\u016c\u016e\u0001\u0000\u0000\u0000\u016d\u016a\u0001"+
		"\u0000\u0000\u0000\u016e\u0171\u0001\u0000\u0000\u0000\u016f\u016d\u0001"+
		"\u0000\u0000\u0000\u016f\u0170\u0001\u0000\u0000\u0000\u0170\u0172\u0001"+
		"\u0000\u0000\u0000\u0171\u016f\u0001\u0000\u0000\u0000\u0172\u0173\u0003"+
		"(\u0014\u0000\u0173\u0174\u0005\u0012\u0000\u0000\u0174\u0178\u0001\u0000"+
		"\u0000\u0000\u0175\u0176\u0005\u0011\u0000\u0000\u0176\u0178\u0005\u0012"+
		"\u0000\u0000\u0177\u015d\u0001\u0000\u0000\u0000\u0177\u0169\u0001\u0000"+
		"\u0000\u0000\u0177\u0175\u0001\u0000\u0000\u0000\u0178/\u0001\u0000\u0000"+
		"\u0000\u0179\u017a\u00050\u0000\u0000\u017a\u017b\u0005:\u0000\u0000\u017b"+
		"\u0185\u0005\n\u0000\u0000\u017c\u017d\u0003(\u0014\u0000\u017d\u017e"+
		"\u0005\u000b\u0000\u0000\u017e\u0180\u0001\u0000\u0000\u0000\u017f\u017c"+
		"\u0001\u0000\u0000\u0000\u0180\u0183\u0001\u0000\u0000\u0000\u0181\u017f"+
		"\u0001\u0000\u0000\u0000\u0181\u0182\u0001\u0000\u0000\u0000\u0182\u0184"+
		"\u0001\u0000\u0000\u0000\u0183\u0181\u0001\u0000\u0000\u0000\u0184\u0186"+
		"\u0003(\u0014\u0000\u0185\u0181\u0001\u0000\u0000\u0000\u0185\u0186\u0001"+
		"\u0000\u0000\u0000\u0186\u0187\u0001\u0000\u0000\u0000\u0187\u0188\u0005"+
		"\f\u0000\u0000\u01881\u0001\u0000\u0000\u0000\u0189\u018a\u0003(\u0014"+
		"\u0000\u018a\u018b\u00034\u001a\u0000\u018b\u018c\u0003(\u0014\u0000\u018c"+
		"3\u0001\u0000\u0000\u0000\u018d\u018e\u0007\u0005\u0000\u0000\u018e5\u0001"+
		"\u0000\u0000\u0000&;DIOTZ\\hlr\u0080\u008b\u0094\u0099\u009b\u00a6\u00be"+
		"\u00c4\u00c8\u00d5\u00db\u00e3\u00e6\u00f4\u00f8\u010e\u013b\u013d\u0145"+
		"\u014d\u0152\u0157\u015b\u0163\u016f\u0177\u0181\u0185";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}