// ANTLR4 Dependencies
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;


// Java IO Dependencies
import java.io.FileInputStream;

class FearnC
{

    static public void ReportErrorAndExit(String err, int code)
    {
        System.out.println("FearnC: " + (char)27 + "[31m" + "ERROR:" + err + (char)27 + "[0m");
        System.exit(code);
    }

    
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

        FearnGrammarLexer lexer = new FearnGrammarLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        FearnGrammarParser parser = new FearnGrammarParser(tokens);

        // ParseTree parseTree = parser.program();
        ParseTree parseTree = parser.expression();

        // System.out.println(parseTree.toStringTree(parser));

        ASTConstructor astConstructor = new ASTConstructor();
        astConstructor.visit(parseTree);
    }
};