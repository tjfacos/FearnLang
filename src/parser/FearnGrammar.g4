/*

FearnGrammar.g4

Unlike every other file that forms the compiler, this is 
not a java source file. This is an ANTLR grammar file, 
written using EBNF. The difference between EBNF and regular
BNF is that it allows the use of regex-style metacharacters,
allowing me to write more concise production, and less of them.
The allows me to write a far neater grammar.

Each rule has one or more patterns associated with it, 
containing the rules or tokens it is made up of. 

The rule at the bottom of file (in UPPER CASE) are token
rules - used by the lexer to match patterns for tokens 
in the source code, which have a large language that couldn't 
be explicitly specified (such as keywords, e.g. 'new', 'fn', 
'int', etc.) 

Some rules have labels next to them ( indicted with #). These 
instruct ANTLR to generate the custom walker class with separate
visit methods and contexts for each 

*/


grammar FearnGrammar;


/* DATA TYPES AND TYPE SPECIFIERS */

// type_name matches the literal names of the 4 primitive types
type_name
    : 'int'         
    | 'float'       
    | 'bool'        
    | 'str'         
    ; 

// type_specifiers can describe a type which is primitive, an array, 
// or a struct
type_specifier
    : type_specifier_primitive
    | type_specifier_struct
    | type_specifier_arr
    ;

// primitive specifiers are just the type name (e.g. let num : int; )
type_specifier_primitive
    : type_name
    ;

// To use a struct as a data type, it can be done in the form $[MyStruct]
// A $ prefix is used to differentiate a struct instance specifier from 
// other source code references to the struct
type_specifier_struct
    : '$' IDENTIFIER
    ;

// An array specifier will be the type specifier of the elements contained, 
// and the N '[]'s, to indicate an N-dimensional array
type_specifier_arr
    : (type_specifier_primitive | type_specifier_struct ) ('[]')+
    ;

/* HIGH LEVEL STRUCTURES */

// Imports start with the 'import' keyword, followed with either
//  ->  An identifier, indicating the import of a standard library module 
//      (e.g. `io` or  `maths`) 
//  ->  The 'from' keyword, followed by a string literal, of the relative 
//      path, from the main program file, of the file being imported
module_import
    : 'import' (IDENTIFIER | 'from' STR_LIT)
    ;

// Fearn Programs, at their root, are 0 or more imports, followed by one 
// or more instances of a function, global variable, or struct definition
//  ->  EOF is a special token for the end of a file, included to ensure
//      this production as consumed the entire source file, and would raise
//      an error if it had, due to poor syntax, not consumed it all.
program 
    : (module_import)* (function | declaration | struct_def )+ EOF
    ;


// Functions are defined using the 'fn' keyword, a list of parameters, a 
// return type (a type specifier or 'void'), and then a compound statement 
// for the function body. 
//  ->  The '=>' symbol is simply to indicate the data type returned.
function 
    : 'fn' IDENTIFIER '(' (( parameter ',' )* parameter)? ')' '=>' ( type_specifier | 'void' )  compound_statement 
    ;

// Function parameters have an identifier, and a data type specifier
parameter
    : IDENTIFIER ':' type_specifier
    ;

// Structs are defined using the 'struct' keyword, an identifer, as well as a 
// list of 0 or more declarations, used to declare variables. These declared 
// variables become the attributes of the struct, with each instance of a struct 
// having a value for each attribute.
struct_def
    : 'struct' IDENTIFIER '{' declaration* '}'
    ;

// Declarations represent the creation of new variables within a defined scope.
// It uses the 'let' keyword, an identifier for the variable, and a type specifier.
// An expression (to initialiswe the variable) can also be added.
declaration
    : 'let' IDENTIFIER ':' type_specifier ( '=' expression)? ';'
    ;

/* STATEMENTS */

// Statements can be one of the five below types
statement 
    : compound_statement        
    | expression_statement      
    | selection_statement       
    | iteration_statement       
    | jump_statement            
    ;

// A compound statement, syntactically, is a collection of declarations and statements,
// between '{}'.
compound_statement
    : '{' (declaration | statement)* '}'
    ;

// An expression statement is an expression (e.g. a function call), or an assignment 
// (changing the value of a variable)
expression_statement
    : expression ';'            # simple_expr_stmt
    | assign_expression ';'     # assign_expr_stmt
    ;

// A selection statement is a standard 'if-else' statement. It can be either:
//  ->  A single if, where the body is only run if the condition is met
//  ->  An if-else chain, where another body/selection is run if the first doesn't
selection_statement
    : 'if' '(' expression ')' compound_statement                                                        # single_if
    | 'if' '(' expression ')' compound_statement 'else' (compound_statement | selection_statement)      # if_else
    ;

// Fearn doesn't support while loops, but does feature a highly permissive for loop,
// which can perform the same purpose. The following few rules all the user to create 
// a loop, in the form `for (int i = 0; i < 10; i++) {...}`

// The last expression is the only optional one (in the grammar), purely because it doesn't match a semi-colon
iteration_statement
    : 'for' '(' init_expression continue_condition iteration_expression ')' compound_statement
    ;

init_expression
    : declaration
    | expression ';'
    | assign_expression ';'
    | ';'
    ;

continue_condition
    : expression ';'
    | ';'
    ;

iteration_expression
    : expression
    | assign_expression
    |
    ;

jump_statement
    : 'continue' ';'                # cont_stmt
    | 'break' ';'                   # break_stmt
    | 'return' expression? ';'       # return_stmt
    ;

// Expression Rules

expression
    : IDENTIFIER                                                                # id_expr
    | literal                                                                   # lit_expr
    | array_init                                                                # arr_init_expr
    | struct_init                                                               # struct_init_expr
    | expression '.' expression                                                 # dot_expr
    | IDENTIFIER '(' ( ( expression ',')* expression )? ')'                     # fn_call_expr
    | '(' expression ')'                                                        # brac_expr
    | expression '[' expression ']'                                             # index_expr
    | '+' expression                                                            # u_plus_expr
    | '-' expression                                                            # u_minus_expr
    | '!' expression                                                            # u_not_expr
    | op=('++'|'--') expression                                                 # pre_inc_expr
    | expression op=('++'|'--')                                                 # post_inc_expr
    | '!' expression                                                            # u_not_expr
    | '(' type_name ')' expression                                              # cast_expr
    | expression '^' expression                                                 # exp_expr

    | expression op=('*'|'/'|'%') expression                                    # mult_expr
    | expression op=('-'|'+')     expression                                    # add_expr
    
    | expression '<' expression                                                 # less_expr
    | expression '>' expression                                                 # greater_expr
    | expression '<=' expression                                                # less_eq_expr
    | expression '>=' expression                                                # greater_eq_expr
    | expression '==' expression                                                # eq_expr
    | expression '!=' expression                                                # not_eq_expr
    | expression '&&' expression                                                # and_expr
    | expression '||' expression                                                # or_expr
    ;

literal 
    : STR_LIT | BOOL_LIT | INT_LIT | FLOAT_LIT ;

array_init
    : 'new' ( type_specifier_primitive | type_specifier_struct ) ('[' expression ']')+
    | 'new' ( type_specifier_primitive | type_specifier_struct ) ('[]')+ array_body
    ;

array_body
    : '{' (array_body ',')* array_body '}'
    | '{' (expression ',')* expression '}'
    | '{''}'
    ;

struct_init
    : 'new' IDENTIFIER '(' ( ( expression ',')* expression )? ')'
    ;




/* Assignment Expression */
assign_expression
    : expression assignment_operator expression                     
    ;
    
assignment_operator
    : '=' | '+=' | '-=' | '*=' | '/=' | '%=' ;






/* Lexer Rules */

/* Define Fragments */
fragment D : [0-9] ;
fragment L : [a-z]
           | [A-Z] 
           | '_'
           ;

/* Define Tokens for Literals */
INT_LIT     :   D+                          ;
FLOAT_LIT   :   D+'.'D+                     ;
STR_LIT     :   '"'(.)*?'"'| '\''(.)*?'\''  ;
BOOL_LIT    :   'true' | 'false'            ;

/* Define Token for User Identifiers */
IDENTIFIER  :   L(L|D)*                     ;

/* Ignore (certain) whitespace      */
WS          :   ( ' ' | '\t' | '\n' | '\r' )+ -> skip   ;

/* Ignore Comments */
BLOCKCOMMENT: '/*' .*? '*/' -> skip                     ;
LINECOMMENT : '//' ~[\r\n]* -> skip                     ;