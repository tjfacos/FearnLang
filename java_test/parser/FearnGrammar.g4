grammar FearnGrammar;

type_name
    : 'int'
    | 'float'
    | 'bool'
    | 'str'
    ; 

type_specifier
    : type_name
    | type_specifier '[' ']'
    | type_specifier '[' INT_LIT ']' 
    ;

program 
    : function+
    ;

function 
    : 'fn' IDENTIFIER '(' (parameters_list)? ')' '=>' ( type_specifier | 'void' )  '{' declaration_block? ( statement )* '}' 
    ;

declaration_block
    : 'let' '{' declaration_list '}' 
    ;

declaration_list
    : declaration ';'?
    | declaration ';' declaration_list
    ;

declaration
    : IDENTIFIER ':' type_specifier ( '=' expression)?
    ;

parameters_list
    : parameter
    | parameter ',' parameters_list
    ;

parameter
    : IDENTIFIER ':' type_specifier
    ;

// Statement Definitions
statement 
    : compound_statement
    | expression_statement
    | selection_statement
    | iteration_statement
    | jump_statement
    ;

compound_statement
    : '{' (statement)* '}'
    ;

expression_statement
    : expression ';'
    | ';'
    ;

selection_statement
    : 'if' '(' expression ')' compound_statement
    | 'if' '(' expression ')' compound_statement 'else' compound_statement
    | 'if' '(' expression ')' compound_statement 'else' selection_statement
    ;

iteration_statement
    : 'while' '(' expression ')' compound_statement
    ;

jump_statement
    : 'continue' ';'
    | 'break' ';'
    | 'return' ';'
    | 'return' expression ';'
    ;


// Expression Rules

literal
    : STR_LIT
    | BOOL_LIT
    | INT_LIT
    | FLOAT_LIT
    ;

/* List of arguments */
argument_list
    : expression
    | argument_list ',' expression
    ;

/* Assignment Operators */
assignment_operator
    : '='
    | '+='
    | '-='
    | '*='
    | '/='
    | '%='
    ;





/* Fundamental expressions */
primary_expression
    : IDENTIFIER
    | literal
    | '(' expression ')'
    | '[' expression ']' 
    ;


postfix_expression
    : primary_expression
    | postfix_expression '[' expression ']'
    | postfix_expression '(' ')'
    | postfix_expression '(' argument_list? ')'
    ;


unary_expression
    : postfix_expression
    | '+' unary_expression
    | '-' unary_expression
    | '!' unary_expression
    | '(' type_name ')' unary_expression
    ;

arithmetic_expression
    : unary_expression
    | arithmetic_expression '^' arithmetic_expression
    | arithmetic_expression '*' arithmetic_expression
    | arithmetic_expression '/' arithmetic_expression
    | arithmetic_expression '%' arithmetic_expression
    | arithmetic_expression '+' arithmetic_expression
    | arithmetic_expression '-' arithmetic_expression
    ;

bool_expression
    : arithmetic_expression
    | bool_expression '<' bool_expression
    | bool_expression '>' bool_expression
    | bool_expression '>=' bool_expression
    | bool_expression '<=' bool_expression
    | bool_expression '==' bool_expression
    | bool_expression '!=' bool_expression
    | bool_expression '&&' bool_expression
    | bool_expression '||' bool_expression
    ;



expression
    : bool_expression
    | unary_expression assignment_operator expression
    ;




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
IDENTIFIER  :   L(L|D)*                 ;

/* Ignore (certain) whitespace      */
WS          :   ( ' ' | '\t' | '\n' | '\r' )+ -> skip      ;
