// ANTLR4 Dependencies
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
// import org.assertj.core.internal.Paths;

// Generated ANTLR Dependencies
import parser.*;
import parser.gen.*;
import semantics.table.SymbolTable;

// Java IO Dependencies
import java.io.FileInputStream;
import java.nio.file.Paths;

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

        // System.out.println(args.toString());

        if ( args.length == 0) {
            Reporter.ReportErrorAndExit("NO SOURCE FILE");
        }

        CharStream input = null;

        if ( args[0].equals("--help") )
        {
            System.out.println("Usage: FearnC [--help |--dir <output directory>] <source file path>");
            System.exit(0);
        }

        String program_path = args[args.length - 1];

        try {
            input = CharStreams.fromStream(new FileInputStream(program_path));
        } catch (Exception e) {
            Reporter.ReportErrorAndExit("FILE " + program_path + " NOT FOUND");
        }

        if (program_path.endsWith("FearnRuntime.fearn") )
        {
            Reporter.ReportErrorAndExit("FILENAME FearnRuntime.fearn IS FORBIDDEN.");
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

        // Perform Semantic Analysis Traversal
        root.validate(symTable);


        String program_name = Paths.get(program_path).getFileName().toString();
        System.out.println(program_name);


        cg.Generate(root, symTable, args[0]);
    }
  
};

// java jdk.internal.org.objectweb.asm.util.ASMifier test.class