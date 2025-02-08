package io.github.fearnlang;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import io.github.fearnlang.parser.*;
import io.github.fearnlang.parser.gen.*;
import io.github.fearnlang.semantics.table.FunctionRow;
import io.github.fearnlang.semantics.table.SymbolTable;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import io.github.fearnlang.util.*;
import io.github.fearnlang.ast.ASTNode;
import io.github.fearnlang.ast.Program;
import io.github.fearnlang.ast.function.Parameter;
import io.github.fearnlang.ast.type.PrimitiveSpecifier;
import io.github.fearnlang.ast.type.PrimitiveSpecifier.PrimitiveDataType;
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

        String programName = Paths.get(path).getFileName().toString().replace(".fearn", "");
        Path buildPath = Paths.get(path).toAbsolutePath().getParent().resolve("build");

        // Add program name to stack (used for error messages and code generation for
        // class names)
        CodeGenerator.ProgramNameStack.push(programName);

        // Add build path to stack (used for code generation)
        CodeGenerator.setBuildPath(buildPath);

        // Raise error if no source file has been passed
        if (args.length == 0) {
            Reporter.ReportErrorAndExit("NO SOURCE FILE", null);
        }

        // Compile source file
        Compile(path, false);

        // Pop generator from stack, as it's no longer in use
        CodeGenerator.ProgramNameStack.pop();

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

        // Return the global symbol table (the symbol table of the program just
        // compiled)
        return CodeGenerator.GlobalSymbolTable;
    }

    /**
     * Get the standard library for a given identifier
     * 
     * 
     * @param id The identifier of the standard library to import
     * @return SymbolTable The symbol table of the standard library
     */
    public static SymbolTable GetStdLib(String id) {

        SymbolTable table = new SymbolTable();

        ArrayList<Parameter> params;

        // Switch makes standard library easy to expand
        switch (id) {
            case "io" -> {
                // Set Program Name
                // This sets the row's owner, ensuring the bytecode for calling these
                // function calls refer the the correct package io.github.fearnlang.and class
                CodeGenerator.ProgramNameStack.push("io/github/fearnlang/stdlib/io");

                // Add Print Function

                // Create new parameter list
                params = new ArrayList<>();

                // Add string parameter
                params.add(new Parameter(
                        "", new PrimitiveSpecifier(PrimitiveDataType.STR)));

                // Add to symbol table, with identifier print, and null return
                // type and local symbol table (irrelevant as this function has
                // no Fearn implementation)
                table.addRow(
                        new FunctionRow(
                                "print",
                                params,
                                null,
                                null));

                // Add Input Function

                // params remains the same (as both print and input take a single,
                // string argument)

                table.addRow(
                        new FunctionRow(
                                "input",
                                params,
                                new PrimitiveSpecifier(PrimitiveDataType.STR),
                                null));

            }

            case "maths" -> {

                CodeGenerator.ProgramNameStack.push("io/github/fearnlang/stdlib/maths");

                // Add PI() -> value of PI
                params = new ArrayList<>();
                table.addRow(
                        new FunctionRow(
                                "PI",
                                params,
                                new PrimitiveSpecifier(PrimitiveDataType.FLOAT),
                                null));

                // Add Eulers() -> value of Euler's number
                table.addRow(
                        new FunctionRow(
                                "Eulers",
                                params,
                                new PrimitiveSpecifier(PrimitiveDataType.FLOAT),
                                null));

                // Add sin, cos, and tan functions
                params = new ArrayList<>();
                params.add(new Parameter("", new PrimitiveSpecifier(PrimitiveDataType.FLOAT)));
                table.addRow(
                        new FunctionRow(
                                "sin",
                                params,
                                new PrimitiveSpecifier(PrimitiveDataType.FLOAT),
                                null));

                table.addRow(
                        new FunctionRow(
                                "cos",
                                params,
                                new PrimitiveSpecifier(PrimitiveDataType.FLOAT),
                                null));

                table.addRow(
                        new FunctionRow(
                                "tan",
                                params,
                                new PrimitiveSpecifier(PrimitiveDataType.FLOAT),
                                null));

            }

            case "random" -> {
                CodeGenerator.ProgramNameStack.push("io/github/fearnlang/stdlib/RandomNumbers");

                // Add random -> Random double between 0 and 1
                params = new ArrayList<>();
                table.addRow(
                        new FunctionRow("random", params,
                                new PrimitiveSpecifier(PrimitiveDataType.FLOAT), null));

                // Add randomFromRange -> Random integer from range
                params = new ArrayList<>();
                params.add(new Parameter("", new PrimitiveSpecifier(PrimitiveDataType.INT)));
                params.add(new Parameter("", new PrimitiveSpecifier(PrimitiveDataType.INT)));
                table.addRow(
                        new FunctionRow("randomInRange", params,
                                new PrimitiveSpecifier(PrimitiveDataType.INT), null));

            }

            default -> Reporter.ReportErrorAndExit("Standard library " + id + " does not exist.", null);

        }

        // Pop Generator, to return to primary program
        CodeGenerator.ProgramNameStack.pop();

        // Return Symbol Table to primary compilation process
        return table;
    }

};