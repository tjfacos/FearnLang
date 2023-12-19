grammar FearnGrammar;

type_name
    : 'int'         # int_type_keyword
    | 'float'       # float_type_keyword
    | 'bool'        # bool_type_keyword
    | 'str'         # str_type_keyword
    ; 

type_specifier
    : type_name                             # type_specifier_primitive
    | type_specifier '[' ']'                # type_specifier_arr
    | '$' IDENTIFIER                        # type_specifier_struct
    ;

program 
    : (function | declaration )+
    ;

function 
    : 'fn' IDENTIFIER '(' (parameters_list)? ')' '=>' ( type_specifier | 'void' )  '{' ( statement | declaration )* '}' 
    ;

declaration
    : 'let' IDENTIFIER ':' type_specifier ( '=' expression)? ';'    # var_decl
    | 'struct' IDENTIFIER '{' declaration* '}'                      # struct_decl
    ;

parameters_list
    : ( parameter ',' )* parameter
    ;

parameter
    : IDENTIFIER ':' type_specifier
    ;

// Statement Definitions
statement 
    : compound_statement        # comp_stmt
    | expression_statement      # expr_stmt
    | selection_statement       # sel_stmt
    | iteration_statement       # iter_stmt
    | jump_statement            # jmp_stmt
    ;

compound_statement
    : '{' (statement)* '}'
    ;

expression_statement
    : expression ';'            # simple_expr_stmt
    | assign_expression ';'     # assign_expr_stmt
    | ';'                       # colon_expr_stmt
    ;

selection_statement
    : 'if' '(' expression ')' compound_statement                                # single_if
    | 'if' '(' expression ')' compound_statement 'else' compound_statement      # if_else
    | 'if' '(' expression ')' compound_statement 'else' selection_statement     # if_else_chain
    ;

iteration_statement
    : 'while' '(' expression ')' compound_statement
    ;

jump_statement
    : 'continue' ';'                # cont_stmt
    | 'break' ';'                   # break_stmt
    | 'return' ';'                  # return_void_stmt
    | 'return' expression ';'       # return_expr_stmt
    ;


// Expression Rules

literal
    : STR_LIT   
    | BOOL_LIT
    | INT_LIT
    | FLOAT_LIT
    ;

array_init
    : 'new' type_name '[' expression ']' ( '{' (expression ',' )* expression '}' )?
    ;

struct_init
    : 'new' IDENTIFIER '(' argument_list? ')'
    ;

expression
    : IDENTIFIER                                # id_expr
    | literal                                   # lit_expr
    | array_init                                # arr_init_expr
    | struct_init                               # struct_init_expr
    | '(' expression ')'                        # brac_expr
    | expression '[' expression ']'             # index_expr
    | IDENTIFIER '(' ')'                        # fn_call_expr
    | IDENTIFIER '(' argument_list? ')'         # fn_call_args_expr
    | '+' expression                            # u_plus_expr
    | '-' expression                            # u_minus_expr
    | '!' expression                            # u_not_expr
    | '(' type_name ')' expression              # cast_expt
    | expression '^' expression                 # exp_expr
    | expression '*' expression                 # mult_expr
    | expression '/' expression                 # div_expr
    | expression '%' expression                 # mod_expr
    | expression '+' expression                 # add_expr
    | expression '-' expression                 # sub_expr
    | expression '<' expression                 # less_expr
    | expression '>' expression                 # greater_expr
    | expression '<=' expression                # less_eq_expr
    | expression '>=' expression                # greater_eq_expr
    | expression '==' expression                # eq_expr
    | expression '!=' expression                # not_eq_expr
    | expression '&&' expression                # and_expr
    | expression '||' expression                # or_expr
    ;

assign_expression
    : IDENTIFIER assignment_operator expression                     # assign_var_expr
    | IDENTIFIER '[' expression ']' assignment_operator expression  # assign_index_expr
    ;

/* List of arguments */
argument_list
    : ( expression ',')* expression
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
IDENTIFIER  :   L(L|D)*                 ;

/* Ignore (certain) whitespace      */
WS          :   ( ' ' | '\t' | '\n' | '\r' )+ -> skip      ;
