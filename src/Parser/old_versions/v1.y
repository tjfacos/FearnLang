%{

	#include <stdio.h>

	FILE *yyin;

	int yyerror();
	extern int yylex();

%}

%union {
	int intval;
	float floatval;
	bool boolval;
	char* strval;
};

/* Token Definitions (only compound symbols need tokens, e.g. +=, ++, etc. ) */
%token IDENTIFIER STRING_LITERAL INT_LITERAL FLOAT_LITERAL BOOL_LITERAL
%token INCREMENT "++" DECREMENT "--"
%token LESS_OR_EQUAL "<=" GREATER_OR_EQUAL ">=" EQUIVALENT "==" NOT_EQUIVALENT "!="
%token AND "&&" OR "||" 
%token MULT_ASSIGN "*=" DIV_ASSIGN "/=" MOD_ASSIGN "%=" ADD_ASSIGN "+=" SUB_ASSIGN "-="

%token CHAR "char" INT "int" FLOAT "float" BOOL "bool" VOID "void"

%token IF "if" ELSE "else" 
%token WHILE "while" FOR "for" CONTINUE "continue" BREAK "break" 
/* %token NEW "new" */
%token RETURN "return"

/* Precedences */
%left "+" "-"
%left "*" "/" "%"

%start program

/* Rules - Reference: https://www.lysator.liu.se/c/ANSI-C-grammar-y.html */
%% 

program
	: 
	| function program
	;

primary_expression
	: IDENTIFIER
	| INT_LITERAL
	| FLOAT_LITERAL
	| BOOL_LITERAL
	| STRING_LITERAL
	| '(' expression ')'
	;

postfix_expression
	: primary_expression
	| postfix_expression '[' expression ']'
	| postfix_expression '(' ')'
	| postfix_expression '(' argument_expression_list ')'
	| postfix_expression INCREMENT
	| postfix_expression DECREMENT
	;

argument_expression_list
	: assignment_expression
	| argument_expression_list ',' assignment_expression
	;

unary_operator
	: '+'
	| '-'
	| '&'
	| '*'
	| '!'
	;

unary_expression
	: postfix_expression
	| INCREMENT unary_expression
	| DECREMENT unary_expression
	| unary_operator cast_expression
	;


cast_expression
	: unary_expression
	| '(' type_name ')' unary_expression
	;

type_name
	: VOID
	| INT
	| CHAR
	| FLOAT
	| BOOL
	;

exponential_expression
	: cast_expression
	| exponential_expression '^' cast_expression
	;

multiplicative_expression
	: exponential_expression
	| multiplicative_expression '*' cast_expression
	| multiplicative_expression '/' cast_expression
	| multiplicative_expression '%' cast_expression
	;

additive_expression
	: multiplicative_expression
	| additive_expression '+' multiplicative_expression
	| additive_expression '-' multiplicative_expression
	;

relational_expression
	: additive_expression
	| relational_expression '<' additive_expression
	| relational_expression '>' additive_expression
	| relational_expression GREATER_OR_EQUAL additive_expression
	| relational_expression LESS_OR_EQUAL additive_expression
	;

equality_expression
	: relational_expression
	| equality_expression EQUIVALENT relational_expression
	| equality_expression NOT_EQUIVALENT relational_expression
	;

and_expression
	: equality_expression
	| and_expression AND equality_expression
	;

or_expression
	: and_expression
	| or_expression OR and_expression
	;

assignment_expression
	: or_expression
	| unary_expression assignment_operator assignment_expression
	;

assignment_operator
	: '='
	| MULT_ASSIGN
	| DIV_ASSIGN
	| MOD_ASSIGN
	| ADD_ASSIGN
	| SUB_ASSIGN
	;

expression
	: assignment_expression
	| expression ',' assignment_expression
	;

constant_expression
	: or_expression
	;


declaration
	: type_name init_declarator ';'
	;

init_declarator
	: declarator
	| declarator '=' initialiser
	;

declarator
	: pointer direct_declarator
	| direct_declarator
	;

pointer
	: '*'
	| '*' pointer
	;

direct_declarator
	: IDENTIFIER
	| direct_declarator '[' constant_expression ']'
	| direct_declarator '[' ']'
	| direct_declarator '(' parameter_list ')'
	| direct_declarator '(' identifier_list ')'
	| direct_declarator '(' ')'
	;

parameter_list
	: parameter_declaration
	| parameter_declaration ',' parameter_list
	;

parameter_declaration
	: type_name declarator
	;

identifier_list
	: IDENTIFIER
	| identifier_list ',' IDENTIFIER
	;

initialiser
	: assignment_expression
	| '{' initialiser_list '}'
	;

initialiser_list
	: initialiser
	| initialiser ',' initialiser_list
	;

statement
	: compound_statement
	| expression_statement
	| selection_statement
	| iteration_statement
	| jump_statement
	;

compound_statement
	: '{' '}'
	| '{' statement_list '}'
	| '{' declaration_list '}'
	| '{' declaration_list statement_list '}'
	;

declaration_list
	: declaration
	| declaration_list declaration
	;

statement_list
	: statement
	| statement_list statement
	;

expression_statement
	: ';'
	| expression ';'
	;


selection_statement
	: IF '(' expression ')' statement
	| IF '(' expression ')' statement ELSE statement
	;

iteration_statement
	: WHILE '(' expression ')' statement
	| FOR '(' expression_statement expression_statement ')' statement
	| FOR '(' expression_statement expression_statement expression ')' statement
	;

jump_statement
	: CONTINUE ';'
	| BREAK ';'
	| RETURN ';'
	| RETURN expression ';'
	;

function
	: type_name declarator declaration_list compound_statement
	| type_name declarator compound_statement
	| declarator declaration_list compound_statement
	| declarator compound_statement
	;





/* Driver Program */
%%

int parser_main(int argc, char* argv[])
{
	
	if (argc == 0)
	{
		printf("ERROR: No file");
		exit(1);
	}

	FILE *file;
	fopen_s(&file, argv[0], "r");
	if (file) {
		yyin = file;
	} else {
		perror("Failed to open file.");
		exit(-1);
	}

	yyparse();

	if (file != NULL)
	{
		fclose(file);
	}

	return 0;
}

yyerror(char* s)
{
	fprintf(stderr, "ERROR: %s\n", s);
	exit(-1);
	return 0;
}