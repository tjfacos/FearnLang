// ANTLR4 Dependencies
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

// Generated ANTLR Dependencies
import parser.*;
import parser.gen.*;
import semantics.table.SymbolTable;

// Java IO Dependencies
import java.io.FileInputStream;
import java.nio.file.Path;
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
    static String sourceFileArgument;


    public static void main(String[] args)
    {
        
        CodeGenerator.generatorStack.push(cg);

        if ( args.length == 0) {
            Reporter.ReportErrorAndExit("NO SOURCE FILE", null);
        }
    
        sourceFileArgument = args[0];

        cg.SetBuildPath(sourceFileArgument);
        cg.SetProgramName(sourceFileArgument);

        Compile(sourceFileArgument);
                
        Path parent = Paths.get(sourceFileArgument).getParent();

        parent = parent == null ? Paths.get("build") : parent.resolve("build");
        
        Reporter.ReportSuccess(
            String.format(
                "Compilation Successful! \n\t -> Run `cd %s ; FearnRun %s [args...]` to run Program", 
                parent.toString(),
                Paths.get(sourceFileArgument).getFileName().toString().replace(".fearn", "")
            ), 
            true
        );
    }
            
    static void Compile(String path)
    {

        CharStream input = null;
        
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

        cg.Generate(root, symTable);

        CodeGenerator.generatorStack.pop();
    }
  
};

// java jdk.internal.org.objectweb.asm.util.ASMifier test.class