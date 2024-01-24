// ANTLR4 Dependencies
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

// Generated ANTLR Dependencies
import parser.*;
import parser.gen.*;
import semantics.table.SymbolTable;

// Java IO Dependencies
import java.io.FileInputStream;

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
            Reporter.ReportErrorAndExit("NO SOURCE FILE", null);
        }

        CharStream input = null;


        String sourceFileArgument = args[0];


        try {
            input = CharStreams.fromStream(new FileInputStream(sourceFileArgument));
        } catch (Exception e) {
            Reporter.ReportErrorAndExit("FILE " + sourceFileArgument + " NOT FOUND", null);
        }

        if (sourceFileArgument.endsWith("FearnRuntime.fearn") )
        {
            Reporter.ReportErrorAndExit("FILENAME FearnRuntime.fearn IS FORBIDDEN.", null);
        }

               
        lexer = new FearnGrammarLexer(input);
        tokens = new CommonTokenStream(lexer);
        parser = new FearnGrammarParser(tokens);

        parser.removeErrorListeners();
        parser.addErrorListener(new FearnErrorListener());

        ParseTree parseTree = parser.program();

        ASTConstructor astConstructor = new ASTConstructor();
        ASTNode AST = astConstructor.visit(parseTree);

        Program root = (Program)AST;
        SymbolTable symTable = astConstructor.symTabStack.pop();

        CodeGenerator.GlobalSymbolTable = symTable;

        // Perform Type Analysis
        root.validate(symTable);



        cg.Generate(root, symTable, sourceFileArgument);
    }
  
};

// java jdk.internal.org.objectweb.asm.util.ASMifier test.class