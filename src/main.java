// ANTLR4 Dependencies
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

// Generated ANTLR Dependencies
import parser.*;
import parser.gen.*;
import semantics.table.SymbolTable;

// Java IO Dependencies
import java.io.FileInputStream;
import java.nio.file.*;

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
            Reporter.ReportErrorAndExit("NO SOURCE FILE");
        }

        CharStream input = null;

        try {
            input = CharStreams.fromStream(new FileInputStream(args[0]));
        } catch (Exception e) {
            Reporter.ReportErrorAndExit("FILE " + args[0] + " NOT FOUND");
        }

        if (args[0].endsWith("FearnRuntime.fearn") )
        {
            Reporter.ReportErrorAndExit("FILENAME FearnRuntime.fearn IS FORBIDDEN.");
        }
        
        // Get Build Path
        if (args[1].equals("--dir"))
        {
            String program_name = Paths.get(args[0]).getFileName().toString();

            try {
                Path NewSourcePath = Paths.get(args[2], program_name);
                args[0] = NewSourcePath.toString();
            } catch (IndexOutOfBoundsException e) {
                Reporter.ReportErrorAndExit("--dir command must be followed by output path.");
            }
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



        cg.Generate(root, symTable, args[0]);
    }
  
};

// java jdk.internal.org.objectweb.asm.util.ASMifier test.class