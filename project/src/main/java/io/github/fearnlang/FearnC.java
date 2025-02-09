package io.github.fearnlang;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import io.github.fearnlang.parser.*;
import io.github.fearnlang.parser.gen.*;
import io.github.fearnlang.semantics.table.SymbolTable;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Stack;

import io.github.fearnlang.util.*;
import io.github.fearnlang.ast.ASTNode;
import io.github.fearnlang.ast.Program;
import io.github.fearnlang.codegen.*;

/**
 * The principle class, from which compilation begins. Contains the main method,
 * and Compile method, which is responsible for the compilation of source files.
 */
public class FearnC {

    /**
     * The driver function for the compiler, where execution starts.
     * 
     * 1) Adds the initial CodeGenerator (used to compile the script the
     * user first passes) to the generatorStack
     * 2) Raising an error if a source file has not been provided, storing its
     * path otherwise
     * 3) Sets the global build path (a static property of the
     * CodeGenerator class - as all files must be generated in the
     * same place), and the program name of the initial script
     * 4) Calls the Compile method
     * 5) Assuming no errors have been raised during Compilation, prints a
     * success message, including a command-line instruction to run the
     * compiled program
     * 
     * @param args The command-line arguments, through which the source-code path
     *             is received
     */
    public static void main(String[] args) {

        String path = args[0];

        // Raise error if no source file has been passed
        if (path.length() == 0) {
            Reporter.ReportErrorAndExit("NO SOURCE FILE", null);
        }

        // Get build path
        Path buildPath = Paths.get(path).toAbsolutePath().getParent().resolve("build");
        
        // Get absolute path to source file
        path = Paths.get(path).toAbsolutePath().toString();

        // Add build path to stack (used for code generation)
        CodeGenerator.setBuildPath(buildPath);

        // Compile source file
        Compile(path, false);

        // Print green Success Message
        Path parent = Paths.get(path).getParent();
        parent = parent == null ? Paths.get("build") : parent.resolve("build");
        Reporter.ReportSuccess(
                String.format(
                        "Compilation Successful! \n\t -> Run `cd %s ; FearnRun %s [args...]` to run Program",
                        parent.toString(),
                        Paths.get(path).getFileName().toString().replace(".fearn", "")),
                true);
    }

    private static Stack<String> programPathStack = new Stack<String>();

    /**
     * The method responsible for conducting the compilation of the
     * file the user has passed.
     * 
     * 1) Loads the source file, raising an error if not found
     * 2) Calls the ANTLR-Generated lexer/parser, to convert the program to
     * a walkable parse tree
     * 3) Calls the AST Constructor to walk the parse tree, generating the
     * Abstract Syntax Tree
     * 4) Retrieves the AST's root, and the program's Global Symbol Table.
     * The Symbol Table is stored in the CodeGenerator class, to be globally
     * accessible
     * 5) The AST is validated, to ensure it follows the rule of the language
     * (e.g. type rules, definitions of all functions/structs are present, etc.)
     * 6) Given it is valid, calls the CodeGenerator to generate the program binary,
     * writing it into the build directory.
     * 7) Pops the initial CodeGenerator off the stack.
     * 
     * @param path The path to the source file
     */
    public static SymbolTable Compile(String path, Boolean isImport) {

        
        // If the file is an import, transform the path to an absolute path pointing to
        // the correct imported source file
        if (isImport) {
            path = CodeGenerator.getBuildPath().getParent().resolve(path.replaceAll("(\'|\")", "")).toString();
        }
        
        // Check for circular imports
        if (programPathStack.contains(path)) {
            Reporter.ReportErrorAndExit("Circular Import Detected! : " + path, null);
        } else {
            programPathStack.push(path);
        }

        // Read source file
        CharStream input = null;

        try {
            input = CharStreams.fromStream(new FileInputStream(path));
        } catch (Exception e) {
            if (isImport)
                Reporter.ReportErrorAndExit("IMPORTED FILE " + path + " NOT FOUND", null);
            else
                Reporter.ReportErrorAndExit("FILE " + path + " NOT FOUND", null);
        }

        // FearnRuntime.fearn is prohibited, as the resulting class would conflict with
        // the runtime
        if (path.endsWith("FearnRuntime.fearn")) {
            Reporter.ReportErrorAndExit("FILENAME FearnRuntime.fearn IS FORBIDDEN.", null);
        }

        // Get program name, and add it to the stack
        String programName = Paths.get(path).getFileName().toString().replace(".fearn", "");
        CodeGenerator.ProgramNameStack.push(programName);

        // Create lexer and parser objects
        FearnGrammarLexer lexer = new FearnGrammarLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        FearnGrammarParser parser = new FearnGrammarParser(tokens);

        // Add custom error listener (to stylise error messages)
        parser.removeErrorListeners();
        parser.addErrorListener(new FearnErrorListener());

        // Parse program from the root 'program' production
        ParseTree parseTree = parser.program();

        // Construct AST
        ASTConstructor astConstructor = new ASTConstructor();
        ASTNode AST = astConstructor.visit(parseTree);

        // Retrieve AST root, and Symbol Table
        Program root = (Program) AST;
        SymbolTable symTable = astConstructor.symbolTableStack.pop();

        CodeGenerator.GlobalSymbolTable = symTable;

        // Perform semantic analysis
        root.validate(symTable);

        // Generate program and struct class files
        CodeGenerator.Generate(root, symTable);

        // Pop program name from stack
        CodeGenerator.ProgramNameStack.pop();

        // Pop program path from stack
        programPathStack.pop();

        // Return the global symbol table (the symbol table of the program just
        // compiled)
        return CodeGenerator.GlobalSymbolTable;
    }
};