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
     *      compiled program
     */

    public static void main(String[] args)
    {
        // Add initial code generator to stack
        CodeGenerator.generatorStack.push(cg);

        // Raise error if no source file has been passed
        if ( args.length == 0) {
            Reporter.ReportErrorAndExit("NO SOURCE FILE", null);
        }
    
        // Set Build Path and Program name for generator
        
        // Build Path describes where to put all generated class file, and 
        // is the same for all generators

        sourceFileArgument = args[0];
        cg.SetBuildPath(sourceFileArgument);
        cg.SetProgramName(sourceFileArgument);

        // Compile source file
        Compile(sourceFileArgument);
        
        // Pop generator from stack, as it's no longer in use
        CodeGenerator.generatorStack.pop();

        // Print green Success Message
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

        // Read source file
        CharStream input = null;
        
        try {
            input = CharStreams.fromStream(new FileInputStream(sourceFileArgument));
        } catch (Exception e) {
            Reporter.ReportErrorAndExit("FILE " + sourceFileArgument + " NOT FOUND", null);
        }

        // FearnRuntime.fearn is prohibited, as the resulting class would conflict with the runtime
        if (sourceFileArgument.endsWith("FearnRuntime.fearn") )
        {
            Reporter.ReportErrorAndExit("FILENAME FearnRuntime.fearn IS FORBIDDEN.", null);
        }

        // Create lexer and parser objects
        lexer = new FearnGrammarLexer(input);
        tokens = new CommonTokenStream(lexer);
        parser = new FearnGrammarParser(tokens);

        // Add custom error listener (to stylise error messages)
        parser.removeErrorListeners();
        parser.addErrorListener(new FearnErrorListener());

        // Parse program from the root 'program' production
        ParseTree parseTree = parser.program();

        // Construct AST
        ASTConstructor astConstructor = new ASTConstructor();
        ASTNode AST = astConstructor.visit(parseTree);

        // Retrieve AST root, and Symbol Table
        Program root = (Program)AST;
        SymbolTable symTable = astConstructor.symbolTableStack.pop();

        CodeGenerator.GlobalSymbolTable = symTable;

        // Perform semantic analysis
        root.validate(symTable);

        // Generate program and struct class files
        cg.Generate(root, symTable);
    }
  
};