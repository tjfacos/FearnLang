grammar FearnGrammar;

type_name
    : 'int'
    | 'float'
    | 'bool'
    | 'str'
    ; 

// Must replace INT_LIT with expression
type_specifier
    : type_name
    | type_name '[' ']' 
    ;



program 
    : ( function ('\n')* )+
    ;

function 
    : 'fn' IDENTIFIER '(' (parameters_list)? ')' '=>' ( type_specifier | 'void' )  declaration_block statement_list 
    ;

declaration_block
    : '\n' '    ' 'let' ( '\n' '    ' declarations )+


parameters_list
    : parameter
    | parameter ',' parameters_list
    ;

parameter
    : IDENTIFIER ':' type_specifier
    ;


statement_list 
    : ( '\n' '    ' statement)+ 
    ;

statement 
    : STR_LIT 
    ;


/* Lexer Rules */

/* Define Fragments */
fragment D : [0-9] ;
fragment L : [a-z]
           | [A-Z] ;

/* Define Tokens for Literals */
INT_LIT     :   D+                  ;
FLOAT_LIT   :   D+'.'D+             ;
STR_LIT     :   '"'(.)*?'"'         ;
BOOL_LIT    :   'true' | 'false'    ;

/* Define Token for User Identifiers */
IDENTIFIER  :   L(L|D)*             ;

/* Ignore (certain) whitespace      */
WS          :   ( ' ' | '\r' )+ -> skip      ;