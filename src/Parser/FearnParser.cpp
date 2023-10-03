#include "FearnParser.h"

extern "C" int parser_main(int argc, char* argv[]);

int Parser::Parse(int argc, char* argv[])
{
	// Incrementing to avoid program name
	return parser_main(--argc, ++argv);
}
