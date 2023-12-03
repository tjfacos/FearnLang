grammar FearnGrammar;

program : 
        INT+
    |   FLOAT+
    |   STR+
    |   BOOL+
    ;




/* Lexer Rules */
fragment D : [0-9] ;

INT     :   D+                  ;
FLOAT   :   D+'.'D+             ;
STR     :   '"'(.)*?'"'         ;
BOOL    :   'true' | 'false'    ;