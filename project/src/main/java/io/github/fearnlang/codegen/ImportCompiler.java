package io.github.fearnlang.codegen;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import io.github.fearnlang.ast.ASTNode;
import io.github.fearnlang.ast.Program;
import io.github.fearnlang.ast.function.Parameter;
import io.github.fearnlang.ast.type.PrimitiveSpecifier;
import io.github.fearnlang.ast.type.PrimitiveSpecifier.PrimitiveDataType;
import io.github.fearnlang.parser.ASTConstructor;
import io.github.fearnlang.parser.gen.*;
import io.github.fearnlang.semantics.table.*;
import io.github.fearnlang.util.FearnErrorListener;
import io.github.fearnlang.util.Reporter;

/* ImportCompiler.java
 * 
 * This contains methods to handle the importing of other Fearn Programs,
 * and standard library modules.
 */

public class ImportCompiler {

    /**
     * Compiles a Fearn program from a file path, while also returning the symbol
     * table.
     * 
     * @param path
     * @return SymbolTable The symbol table of the imported program
     */
    public SymbolTable Compile(String path) {

        path = CodeGenerator.getBuildPath().getParent().resolve(path.replaceAll("(\'|\")", "")).toString();

        CharStream input = null;

        try {
            input = CharStreams.fromStream(new FileInputStream(path));
        } catch (Exception e) {
            Reporter.ReportErrorAndExit("IMPORTED FILE " + path + " NOT FOUND", null);
        }

        if (path.endsWith("FearnRuntime.fearn")) {
            Reporter.ReportErrorAndExit("FILENAME FearnRuntime.fearn IS FORBIDDEN.", null);
        }

      
        String programName = Paths.get(path).getFileName().toString().replace(".fearn", "");

        CodeGenerator.ProgramNameStack.push(programName);

        FearnGrammarLexer lexer = new FearnGrammarLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        FearnGrammarParser parser = new FearnGrammarParser(tokens);

        parser.removeErrorListeners();
        parser.addErrorListener(new FearnErrorListener());

        ParseTree parseTree = parser.program();

        ASTConstructor astConstructor = new ASTConstructor();
        ASTNode AST = astConstructor.visit(parseTree);

        Program root = (Program) AST;
        SymbolTable symTable = astConstructor.symbolTableStack.pop();

        CodeGenerator.GlobalSymbolTable = symTable;

        // Perform Type Analysis
        root.validate(symTable);

        CodeGenerator.Generate(root, symTable);

        CodeGenerator.ProgramNameStack.pop();

        return CodeGenerator.GlobalSymbolTable;
    }

    /**
     * Get the standard library for a given identifier
     * 
     * 
     * @param id The identifier of the standard library to import
     * @return SymbolTable The symbol table of the standard library
     */
    public SymbolTable GetStdLib(String id) {

        SymbolTable table = new SymbolTable();

        ArrayList<Parameter> params;

        // Switch makes standard library easy to expand
        switch (id) {
            case "io":
                // Set Program Name
                // This sets the row's owner, ensuring the bytecode for calling these
                // function calls refer the the correct package io.github.fearnlang.and class
                CodeGenerator.ProgramNameStack.push("io/github/fearnlang/FearnStdLib/io");

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

                break;

            case "maths":

                CodeGenerator.ProgramNameStack.push("io/github/fearnlang/FearnStdLib/maths");

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

                break;

            case "random":
            CodeGenerator.ProgramNameStack.push("io/github/fearnlang/FearnStdLib/RandomNumbers");

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

                break;

            default:
                Reporter.ReportErrorAndExit("Standard library " + id + " does not exist.", null);
                break;
        }

        // Pop Generator, to return to primary program
        CodeGenerator.ProgramNameStack.pop();

        // Return Symbol Table to primary compilation process
        return table;
    }
}
