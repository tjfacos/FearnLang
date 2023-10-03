%{
	/* Definitions */
	#include "Parser.tab.h"

	#include <stdlib.h>
	#include <stdio.h>
	#include <string.h>

	#define fileno _fileno

	int lineNumber = 1;
	int columnNumber = 1;

	void count();

%}

%option noyywrap
%option nounistd 

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

"for"						{ count(); return(FOR); 				}
"break"						{ count(); return(BREAK); 				}
"continue"					{ count(); return(CONTINUE);			}

"string"					{ count(); return(STRING); 				}
"int"						{ count(); return(INT); 				}
"float"						{ count(); return(FLOAT); 				}
"bool"						{ count(); return(BOOL); 				}
"void"						{ count(); return(VOID); 				}

"if"						{ count(); return(IF);	 				}
"else"						{ count(); return(ELSE); 				}

"return"					{ count(); return(RETURN); 				}
"let"						{ count(); return(LET); 				}


{letter}({letter}|{digit})* { count(); return(IDENTIFIER);			}
("true"|"false")			{ count(); return(BOOL_LITERAL);		}
{digit}+ 					{ count(); return(INT_LITERAL);			}
{digit}+\.{digit}+			{ count(); return(FLOAT_LITERAL);		}
\".*\"						{ count(); return(STRING_LITERAL);		}


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
"["							{ count(); return('[');					}
"]"							{ count(); return(']');					}
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


%%


void count()
{
	int i;

	for (i = 0; yytext[i] != '\0'; i++)
		if (yytext[i] == '\n')
		{
			columnNumber = 1;
			lineNumber++;
		}
		else if (yytext[i] == '\t')
		{
			columnNumber += 8 - (columnNumber % 8);
		}
		else
		{
			columnNumber++;
		}

	ECHO;
}