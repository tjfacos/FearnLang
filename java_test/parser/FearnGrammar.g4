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




// Expressions

/* Fundamental expressions */
primary_expression
    : IDENTIFIER
    | STR_LIT
    | BOOL_LIT
    | INT_LIT
    | FLOAT_LIT
    | '(' expression ')'
    | '[' expression ']' 
    ;


/* Expressions with some sort of suffix (e.g. function calls, indexing) */
postfix_expression
    : primary_expression
    | postfix_expression '[' expression ']'
    | postfix_expression '(' ')'
    | postfix_expression '(' argument_list? ')'
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


/* Expressions with unary operators */
unary_expression
    : postfix_expression
    | '+' cast_expression
    | '-' cast_expression
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
    | relational_expression '>=' additive_expression
    | relational_expression '<=' additive_expression
    ;


equality_expression
    : relational_expression
    | equality_expression '==' relational_expression
    | equality_expression '!=' relational_expression
    ;


and_expression
    : equality_expression
    | and_expression '&&' equality_expression
    ;


or_expression
    : and_expression
    | or_expression '||' and_expression
    ;


assignment_expression
    : or_expression
    | unary_expression assignment_operator assignment_expression
    ;


expression
    : assignment_expression
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
