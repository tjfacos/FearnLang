// ANTLR4 Dependencies
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

// Generated ANTLR Dependencies
import parser.*;
import parser.gen.*;

// Java IO Dependencies
import java.io.FileInputStream;

// Local
import util.*;
import ast.ASTNode;

class FearnC
{


    public static FearnGrammarLexer lexer;
    public static CommonTokenStream tokens;
    public static FearnGrammarParser parser;
    
    public static void main(String []args)
    {
        if ( args.length == 0) {
            ErrorReporter.ReportErrorAndExit("NO SOURCE FILE", 1);
        }

        CharStream input = null;

        try {
            input = CharStreams.fromStream(new FileInputStream(args[0]));
        } catch (Exception e) {
            ErrorReporter.ReportErrorAndExit("FILE " + args[0] + " NOT FOUND", 2);
        }

        lexer = new FearnGrammarLexer(input);
        tokens = new CommonTokenStream(lexer);
        parser = new FearnGrammarParser(tokens);

        // ParseTree parseTree = parser.program();
        ParseTree parseTree = parser.statement();

        ASTConstructor astConstructor = new ASTConstructor();
        ASTNode AST = astConstructor.visit(parseTree);

        System.out.println(AST.toString());
    }
};