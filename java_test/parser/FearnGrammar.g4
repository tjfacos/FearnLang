grammar FearnGrammar;

program : 
        IDENTIFIER
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
IDENTIFIER  :   L(L|D)*         ;




/* Define Keywords */

/*
// Iteration Keywords
WHILE   :   'while'             ;
BREAK   :   'break'             ;
CONTINUE:   'continue'          ;

// Type Keywords
STRING  :   'str'               ;
INT     :   'int'               ;
FLOAT   :   'float'             ;
BOOL    :   'bool'              ;
VOID    :   'void'              ;

// Selection Keywords
IF      :   'if'                ;
ELSE    :   'else'              ;

// Function Keywords
FN      :   'fn'                ;
RETURN  :   'return'            ;

*/

/* Define Operators */

/*
// Comparison
LESS        :   '<'             ;
GREATER     :   '>'             ;
LOE         :   '<='            ;
GOE         :   '>='            ;
EQUAL       :   '=='            ;
NOT_EQUAL   :   '!='            ;

// Boolean
AND         :   '&&'            ;
OR          :   '||'            ;
NOT         :   '!'             ;

// Arithmetic
ADD             :   '+'         ;
SUB             :   '-'         ;
MULT            :   '*'         ;
DIV             :   '/'         ;
MOD             :   '%'         ;
ADD_ASSIGN      :   '+='        ;
SUB_ASSIGN      :   '-='        ;
MUL_ASSIGN      :   '*='        ;
DIV_ASSIGN      :   '/='        ;
MOD_ASSIGN      :   '%='        ;
EXP             :   '^'         ;

*/
