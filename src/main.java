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


/* 
 * Main.java
 * 
 * This is the compiler's main class, from which compilation begins.
 * 
*/

class FearnC
{
    public static FearnGrammarLexer lexer;
    public static CommonTokenStream tokens;
    public static FearnGrammarParser parser;
    
    
    static CodeGenerator cg = new CodeGenerator();
    static String sourceFileArgument;

    /* The main function is the first to be called.
     * 
     * The function...
     * 1)   Adds the initial CodeGenerator (used to compile the script the 
     *      user first passes) to the generatorStack
     * 2)   Raising an error if a source file has not been provided, storing its 
     *      path otherwise
     * 3)   Sets the global build path (a static property of the 
     *      CodeGenerator class - as all files must bew generated in the 
     *      same place), and the program name of the initial script
     * 4)   Calls the Compile method
     * 5)   Assuming no errors have been raised during Compilation, prints a
     *      success message, including a command-line instruction to run the 
     *      compiled preogram
     */

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
       
    /* Compile
     * 
     * The method responsible for conducting the compilation of the 
     * file the user has passed.
     * 
     * 1)   Loads the source file, rasing an error if not found
     * 2)   Calls the ANTLR-Generated lexer/parser, to convert the program to
     *      a walkable parse tree
     * 3)   Calls the AST Constructor to walk the parse tree, generating the 
     *      Abstract Syntax Tree
     * 4)   Retrieves the AST's root, and the program's Global Symbol Table. 
     *      The Symbol Table is stored in the CodeGenerator class, to be globally 
     *      accessible
     * 5)   The AST is validated, to ensure it follows the rule of the language
     *      (e.g. type rules, definitions of all functions/structs are present, etc.)
     * 6)   Given it is valid, calls the CodeGenerator to generate the program binary,
     *      writing it into the build directory.
     * 7)   Pops the initial CodeGenerator off the stack.
     * 
     */


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

        root.validate(symTable);

        cg.Generate(root, symTable);

        CodeGenerator.generatorStack.pop();
    }
  
};