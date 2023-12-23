grammar FearnGrammar;

type_name
    : 'int'         # int_type_keyword
    | 'float'       # float_type_keyword
    | 'bool'        # bool_type_keyword
    | 'str'         # str_type_keyword
    ; 

type_specifier
    : type_name                             # type_specifier_primitive
    | '$' IDENTIFIER                        # type_specifier_struct
    | type_specifier ('[]')+                # type_specifier_arr
    ;

// Root Program
program 
    : (function | declaration | struct_def )+
    ;

// Function Definition
function 
    : 'fn' IDENTIFIER '(' (( parameter ',' )* parameter)? ')' '=>' ( type_specifier | 'void' )  compound_statement 
    ;

parameter
    : IDENTIFIER ':' type_specifier
    ;

// Struct Definition
struct_def
    : 'struct' IDENTIFIER '{' declaration* '}'
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
    : '{' (declaration)* (statement)* '}'
    ;

expression_statement
    : expression ';'            # simple_expr_stmt
    | assign_expression ';'     # assign_expr_stmt
    ;

selection_statement
    : 'if' '(' expression ')' compound_statement                                # single_if
    | 'if' '(' expression ')' compound_statement 'else' compound_statement      # if_else
    | 'if' '(' expression ')' compound_statement 'else' selection_statement     # if_else_chain
    ;

iteration_statement
    : 'for' '(' (declaration | ';')? expression? ';' assign_expression? ')' compound_statement
    ;

jump_statement
    : 'continue' ';'                # cont_stmt
    | 'break' ';'                   # break_stmt
    | 'return' expression? ';'       # return_stmt
    ;






declaration
    : 'let' IDENTIFIER ':' type_specifier ( '=' expression)? ';'
    ;

// Expression Rules

literal
    : STR_LIT   
    | BOOL_LIT
    | INT_LIT
    | FLOAT_LIT
    ;

array_init
    : 'new' type_specifier ('[' expression ']')+ array_body?
    ;

array_body
    : '{' (array_body ',')* array_body '}'
    | '{' (expression ',')* expression '}'
    | '{''}'
    ;

struct_init
    : 'new' IDENTIFIER '(' ( ( expression ',')* expression )? ')'
    ;

expression
    : IDENTIFIER                                            # id_expr
    | literal                                               # lit_expr
    | array_init                                            # arr_init_expr
    | struct_init                                           # struct_init_expr
    | expression '.' expression                             # struct_attr_expr
    | IDENTIFIER '(' ( ( expression ',')* expression )? ')' # fn_call_expr
    | '(' expression ')'                                    # brac_expr
    | expression '[' expression ']'                         # index_expr
    | '+' expression                                        # u_plus_expr
    | '-' expression                                        # u_minus_expr
    | '!' expression                                        # u_not_expr
    | '(' type_name ')' expression                          # cast_expr
    | expression '^' expression                             # exp_expr
    | expression '*' expression                             # mult_expr
    | expression '/' expression                             # div_expr
    | expression '%' expression                             # mod_expr
    | expression '+' expression                             # add_expr
    | expression '-' expression                             # sub_expr
    | expression '<' expression                             # less_expr
    | expression '>' expression                             # greater_expr
    | expression '<=' expression                            # less_eq_expr
    | expression '>=' expression                            # greater_eq_expr
    | expression '==' expression                            # eq_expr
    | expression '!=' expression                            # not_eq_expr
    | expression '&&' expression                            # and_expr
    | expression '||' expression                            # or_expr
    ;

assign_expression
    : expression assignment_operator expression                     
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