%{

	#include <stdio.h>
	#include <stdbool.h> 

	FILE *yyin;

	int yyerror();
	extern int yylex();

%}

%union {
	int intval;
	double floatval;
	int boolval;
	char* strval;
}


/* Token Definitions (only compound symbols need tokens, e.g. +=, ++, etc. ) */
%token IDENTIFIER STRING_LITERAL INT_LITERAL FLOAT_LITERAL BOOL_LITERAL
%token INCREMENT "++" DECREMENT "--"
%token LESS_OR_EQUAL "<=" GREATER_OR_EQUAL ">=" EQUIVALENT "==" NOT_EQUIVALENT "!="
%token AND "&&" OR "||" 
%token MULT_ASSIGN "*=" DIV_ASSIGN "/=" MOD_ASSIGN "%=" ADD_ASSIGN "+=" SUB_ASSIGN "-="

%token STRING "string" INT "int" FLOAT "float" BOOL "bool" VOID "void"

%token IF "if" ELSE "else" 
%token FOR "for" CONTINUE "continue" BREAK "break" 
%token RETURN "return" LET "let"

/* Precedences */
%left "<" "<=" ">" ">="

%left "+" "-"
%left "*" "/" "%"

%start program

/* Rules - Reference: https://www.lysator.liu.se/c/ANSI-C-grammar-y.html */
%% 

/* Names of the basic data types */
type_name
    : VOID
	| INT
	| STRING
	| FLOAT
	| BOOL
	; 

/* Specifier for return types, declarations etc. 
Can either be a basic type, or list of elements of a basic type */
type_specifier
    : type_name
    | type_name '[' expression ']' 
	| type_name '[' ']' 
    ;

/* Whole Program is comprised of functions and global declarations */
program
	: 
	| function program
    | declaration program
	;

/* A Function is a return_type, identifier, parameters, and a compound statement. */
function
    : type_specifier IDENTIFIER '(' ')' compound_statement
    | type_specifier IDENTIFIER '(' parameters_list ')' compound_statement
    ;

/* A list of parameters */
parameters_list
    : parameter
    | parameters_list ',' parameter
    ;

/* The written parameter to a function, in the form <type, identifier> */
parameter
    : type_specifier IDENTIFIER
    ;

/* The declaration of a new variable */
declaration
    : LET type_specifier IDENTIFIER ';'
    | LET type_specifier IDENTIFIER '=' expression ';'
    ;

/* Statements used to build up programs */
statement
    : compound_statement
	| expression_statement
	| selection_statement
	| iteration_statement
	| jump_statement
    ;

/* If and If-Else statements */
selection_statement
	: IF '(' expression ')' compound_statement
	| IF '(' expression ')' compound_statement ELSE compound_statement
	| IF '(' expression ')' compound_statement ELSE selection_statement
	;

/* Expression Statements evaluate to something */
expression_statement
	: ';'
	| expression ';'
	;

/* For Loops */
iteration_statement
	: FOR '(' declaration expression_statement ')' compound_statement
	| FOR '(' declaration expression_statement expression ')' compound_statement
	| FOR '(' ';' ';' ')' compound_statement
	;

/* Flow - control statements */
jump_statement
	: CONTINUE ';'
	| BREAK ';'
	| RETURN ';'
	| RETURN expression ';'
	;

/* Code Blocks (variable declarations must come before statements) */
compound_statement
	: '{' '}'
	| '{' statement_list '}'
	| '{' declaration_list '}'
	| '{' declaration_list statement_list '}'
	;

/* List of statements (used inside code blocks) */
statement_list
    : statement
    | statement_list statement
    ;

/* List of variable declarations */
declaration_list
    : declaration
    | declaration_list declaration
    ;


/* Fundamental expressions */
primary_expression
    : IDENTIFIER
    | STRING_LITERAL
    | BOOL_LITERAL
    | INT_LITERAL
    | FLOAT_LITERAL
    | '(' expression ')'
	| '[' expression ']' // For lists
    ;

/* Expressions with some sort of suffix (e.g. function calls, indexing) */
postfix_expression
    : primary_expression
	| postfix_expression '[' expression ']'
	| postfix_expression '(' ')'
	| postfix_expression '(' argument_expression_list ')'
	| postfix_expression INCREMENT
	| postfix_expression DECREMENT
	;

/* List of arguements */
argument_expression_list
    : expression
    | argument_expression_list ',' expression
    ;

/* Operators on a single operand */
unary_operator
    : '+'
	| '-'
	;
/* Assignment Operators */
assignment_operator
	: '='
	| MULT_ASSIGN
	| DIV_ASSIGN
	| MOD_ASSIGN
	| ADD_ASSIGN
	| SUB_ASSIGN
	;

/* Expressions with unary operators */
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

exponential_expression
	: cast_expression
	| exponential_expression '^' cast_expression
	;

multiplicative_expression
	: exponential_expression
	| multiplicative_expression '*' exponential_expression
	| multiplicative_expression '/' exponential_expression
	| multiplicative_expression '%' exponential_expression
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

expression
	: assignment_expression
    | expression ',' assignment_expression
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