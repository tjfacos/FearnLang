%{
	/* Definitions */
	#include "Parser.tab.h"

	#include <stdlib.h>
	#include <stdio.h>
	#include <string.h>

	#define fileno _fileno

	int lineNumber = 1;
	int columnNumber = 1;

%}

%option noyywrap

digit		[0-9]
letter		[a-zA-Z_]
whitespace	[ \t\v\n\f]+


%x c_comment

/* Rules */

%%

{whitespace} 				{ count(); 								}
"/*"						{ count(); BEGIN(c_comment); 			}
<c_comment>\n				{ count(); 								}
<c_comment>.*"*/"       	{ count(); BEGIN(INITIAL); 				}
<c_comment>.*	        	{ count(); 								}
"//".* 						{ count(); 								}

"while"						{ count(); return(WHILE); 				}
"for"						{ count(); return(FOR); 				}
"break"						{ count(); return(BREAK); 				}
"continue"					{ count(); return(CONTINUE);			}

"char"						{ count(); return(CHAR); 				}
"int"						{ count(); return(INT); 				}
"float"						{ count(); return(FLOAT); 				}
"bool"						{ count(); return(BOOL); 				}
"void"						{ count(); return(VOID); 				}

"if"						{ count(); return(IF);	 				}
"else"						{ count(); return(ELSE); 				}

"return"					{ count(); return(RETURN); 				}

{letter}({letter}|{digit})* { count(); return(IDENTIFIER);			}
(true|false) 				{ count(); return(BOOL_LITERAL);		}
{letter}({letter}|{digit})* { count(); return(IDENTIFIER);			}
{letter}({letter}|{digit})* { count(); return(IDENTIFIER);			}
{letter}({letter}|{digit})* { count(); return(IDENTIFIER);			}


"<="						{ count(); return(LESS_OR_EQUAL);		}
">="						{ count(); return(GREATER_OR_EQUAL);	}
"=="						{ count(); return(EQUIVALENT);			}
"!="						{ count(); return(NOT_EQUIVALENT);		}

"&&"						{ count(); return(AND);					}
"||"						{ count(); return(OR);					}

"++"						{ count(); return(INCREMENT);			}
"--"						{ count(); return(DECREMENT);			}

"+="						{ count(); return(ADD_ASSIGN);			}
"-="						{ count(); return(SUB_ASSIGN);			}
"*="						{ count(); return(MULT_ASSIGN);			}
"/="						{ count(); return(DIV_ASSIGN);			}
"%="						{ count(); return(MOD_ASSIGN);			}

";"							{ count(); return(';');					}
"{"							{ count(); return('{');					}
"}"							{ count(); return('}');					}
","							{ count(); return(',');					}
":"							{ count(); return(':');					}
"="							{ count(); return('=');					}
"("							{ count(); return('(');					}
")"							{ count(); return(')');					}
"&"							{ count(); return('&'); 				}
"!"							{ count(); return('!'); 				}
"-"							{ count(); return('-'); 				}
"+"							{ count(); return('+'); 				}
"*"							{ count(); return('*'); 				}
"/"							{ count(); return('/'); 				}
"%"							{ count(); return('%'); 				}
"<"							{ count(); return('<'); 				}
">"							{ count(); return('>'); 				}
"^"							{ count(); return('^'); 				}

. {}

%%


void count()
{
	int i;

	for (i = 0; yytext[i] != '\0'; i++)
		if (yytext[i] == '\n')
			column = 1;
			lineNumber++;
		else if (yytext[i] == '\t')
			column += 8 - (column % 8);
		else
			column++;

	ECHO;
}