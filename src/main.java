// ANTLR4 Dependencies
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import ast.ASTNode;

// Java IO Dependencies
import java.io.FileInputStream;

class FearnC
{

    static public void ReportErrorAndExit(String err, int code)
    {
        System.out.println("FearnC: " + (char)27 + "[31m" + "ERROR:" + err + (char)27 + "[0m");
        System.exit(code);
    }


    public static FearnGrammarLexer lexer;
    public static CommonTokenStream tokens;
    public static FearnGrammarParser parser;
    
    public static void main(String []args)
    {
        if ( args.length == 0) {
            ReportErrorAndExit("NO SOURCE FILE", 1);
        }

        CharStream input = null;

        try {
            input = CharStreams.fromStream(new FileInputStream(args[0]));
        } catch (Exception e) {
            ReportErrorAndExit("FILE " + args[0] + " NOT FOUND", 2);
        }

        lexer = new FearnGrammarLexer(input);
        tokens = new CommonTokenStream(lexer);
        parser = new FearnGrammarParser(tokens);

        // ParseTree parseTree = parser.program();
        ParseTree parseTree = parser.expression();

        ASTConstructor astConstructor = new ASTConstructor();
        ASTNode AST = astConstructor.visit(parseTree);

        System.out.println(AST.toString());
    }
};