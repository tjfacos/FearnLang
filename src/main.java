// ANTLR4 Dependencies
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

// Generated ANTLR Dependencies
import parser.*;
import parser.gen.*;

import java.io.File;
// Java IO Dependencies
import java.io.FileInputStream;
import java.io.IOException;

// Local
import util.*;
import ast.ASTNode;
import ast.Program;
import codegen.*;

class FearnC
{


    public static FearnGrammarLexer lexer;
    public static CommonTokenStream tokens;
    public static FearnGrammarParser parser;
    
    static CodeGenerator cg = new CodeGenerator();


    public static void main(String[] args)
    {
        if ( args.length == 0) {
            Reporter.ReportErrorAndExit("NO SOURCE FILE", 1);
        }

        CharStream input = null;

        try {
            input = CharStreams.fromStream(new FileInputStream(args[0]));
        } catch (Exception e) {
            Reporter.ReportErrorAndExit("FILE " + args[0] + " NOT FOUND", 2);
        }

        lexer = new FearnGrammarLexer(input);
        tokens = new CommonTokenStream(lexer);
        parser = new FearnGrammarParser(tokens);

        parser.removeErrorListeners();
        parser.addErrorListener(new FearnErrorListener());

        ParseTree parseTree = parser.program();

        ASTConstructor astConstructor = new ASTConstructor();
        ASTNode AST = astConstructor.visit(parseTree);


        try {
            cg.Generate((Program)AST, args[0]);
        } catch (IOException e) {
            Reporter.ReportErrorAndExit("Code Gen Error: " + e, 23);
        }
        
        Reporter.ReportSuccessAndExit("Compilation Successful!");
    }

    
};