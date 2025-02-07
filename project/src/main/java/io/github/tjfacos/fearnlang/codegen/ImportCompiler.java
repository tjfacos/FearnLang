package io.github.tjfacos.fearnlang.codegen;

import java.io.FileInputStream;
import java.util.ArrayList;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import io.github.tjfacos.fearnlang.ast.ASTNode;
import io.github.tjfacos.fearnlang.ast.Program;
import io.github.tjfacos.fearnlang.ast.function.Parameter;
import io.github.tjfacos.fearnlang.ast.type.PrimitiveSpecifier;
import io.github.tjfacos.fearnlang.ast.type.PrimitiveSpecifier.PrimitiveDataType;
import io.github.tjfacos.fearnlang.parser.ASTConstructor;
import io.github.tjfacos.fearnlang.parser.gen.*;
import io.github.tjfacos.fearnlang.semantics.table.*;
import io.github.tjfacos.fearnlang.util.FearnErrorListener;
import io.github.tjfacos.fearnlang.util.Reporter;

/* ImportCompiler.java
 * 
 * This contains methods to handle the importing of other Fearn Programs,
 * and standard library modules.
 */

public class ImportCompiler {

    /**
     * Compiles a Fearn program from a file path, while also returning the symbol table.
     * 
     * @param path
     * @return SymbolTable The symbol table of the imported program
     */
    public SymbolTable Compile(String path) {

        path = CodeGenerator.buildPath.getParent().resolve(path.replaceAll("(\'|\")", "")).toString();

        CharStream input = null;
        
        try {
            input = CharStreams.fromStream(new FileInputStream(path));
        } catch (Exception e) {
            Reporter.ReportErrorAndExit("IMPORTED FILE " + path + " NOT FOUND", null);
        }

        if (path.endsWith("FearnRuntime.fearn") )
        {
            Reporter.ReportErrorAndExit("FILENAME FearnRuntime.fearn IS FORBIDDEN.", null);
        }
        
        CodeGenerator cg = new CodeGenerator();
        
        cg.SetProgramName(path);
        
        CodeGenerator.GeneratorStack.push(cg);
               
        FearnGrammarLexer lexer = new FearnGrammarLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        FearnGrammarParser parser = new FearnGrammarParser(tokens);

        parser.removeErrorListeners();
        parser.addErrorListener(new FearnErrorListener());

        ParseTree parseTree = parser.program();

        ASTConstructor astConstructor = new ASTConstructor();
        ASTNode AST = astConstructor.visit(parseTree);

        Program root = (Program)AST;
        SymbolTable symTable = astConstructor.symbolTableStack.pop();

        CodeGenerator.GlobalSymbolTable = symTable;

        // Perform Type Analysis
        root.validate(symTable);

        cg.Generate(root, symTable);

        CodeGenerator.GeneratorStack.pop();

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
        
        CodeGenerator.GeneratorStack.push(new CodeGenerator());

        SymbolTable table = new SymbolTable();

        ArrayList<Parameter> params;

        // Switch makes standard library easy to expand
        switch (id) {
            case "io":
                // Set Program Name
                // This sets the row's owner, ensuring the bytecode for calling these 
                // function calls refer the the correct package io.github.tjfacos.fearnlang.and class
                CodeGenerator.GeneratorStack.peek().programName = "io/github/tjfacos/fearnlang/FearnStdLib/io";

                // Add Print Function
                
                // Create new parameter list
                params = new ArrayList<>();
                
                // Add string parameter
                params.add(new Parameter(
                    "", new PrimitiveSpecifier(PrimitiveDataType.STR)
                ));

                // Add to symbol table, with identifier print, and null return 
                // type and local symbol table (irrelevant as this function has 
                // no Fearn implementation)
                table.addRow(
                    new FunctionRow(
                        "print", 
                        params, 
                        null, 
                        null
                    )
                );
                
                // Add Input Function

                // params remains the same (as both print and input take a single, 
                // string argument)

                table.addRow(
                    new FunctionRow(
                        "input", 
                        params, 
                        new PrimitiveSpecifier(PrimitiveDataType.STR), 
                        null
                    )
                );
                
                break;

            case "maths":
                
            CodeGenerator.GeneratorStack.peek().programName =  "io/github/tjfacos/fearnlang/FearnStdLib/maths";

                // Add PI() -> value of PI
                params = new ArrayList<>(); 
                table.addRow(
                    new FunctionRow(
                        "PI", 
                        params, 
                        new PrimitiveSpecifier(PrimitiveDataType.FLOAT), 
                        null
                    )
                );
                
                // Add Eulers() -> value of Euler's number
                table.addRow(
                    new FunctionRow(
                        "Eulers", 
                        params, 
                        new PrimitiveSpecifier(PrimitiveDataType.FLOAT), 
                        null
                    )
                );
                
                // Add sin, cos, and tan functions
                params = new ArrayList<>();
                params.add(new Parameter("", new PrimitiveSpecifier(PrimitiveDataType.FLOAT)));
                table.addRow(
                    new FunctionRow(
                        "sin", 
                        params, 
                        new PrimitiveSpecifier(PrimitiveDataType.FLOAT), 
                        null
                    )
                );

                table.addRow(
                    new FunctionRow(
                        "cos", 
                        params, 
                        new PrimitiveSpecifier(PrimitiveDataType.FLOAT), 
                        null
                    )
                );

                table.addRow(
                    new FunctionRow(
                        "tan", 
                        params, 
                        new PrimitiveSpecifier(PrimitiveDataType.FLOAT), 
                        null
                    )
                );

                break;

            case "random":
                CodeGenerator.GeneratorStack.peek().programName = "io/github/tjfacos/fearnlang/FearnStdLib/RandomNumbers";

                // Add random -> Random double between 0 and 1
                params = new ArrayList<>();
                table.addRow(
                    new FunctionRow("random", params, 
                    new PrimitiveSpecifier(PrimitiveDataType.FLOAT), null)
                );

                // Add randomFromRange -> Random integer from range
                params = new ArrayList<>();
                params.add(new Parameter("", new PrimitiveSpecifier(PrimitiveDataType.INT)));
                params.add(new Parameter("", new PrimitiveSpecifier(PrimitiveDataType.INT)));
                table.addRow(
                    new FunctionRow("randomInRange", params, 
                    new PrimitiveSpecifier(PrimitiveDataType.INT), null)
                );

                break;

            default:
                Reporter.ReportErrorAndExit("Standard library " + id + " does not exist.", null);
                break;
        }

        // Pop Generator, to return to primary program
        CodeGenerator.GeneratorStack.pop();

        // Return Symbol Table to primary compilation process
        return table;
    }
}
