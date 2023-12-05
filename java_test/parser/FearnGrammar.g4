grammar FearnGrammar;

program 
    : ( function ('\n')* (EOF)? )+;

function : 'fn' IDENTIFIER '(' ')' '=>' 'void' '\n' statement_list ;

statement_list : ( '    ' statement '\n')+ ;
statement : STR_LIT ;


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