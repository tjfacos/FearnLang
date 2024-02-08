package codegen;

import java.io.FileInputStream;
import java.util.ArrayList;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import ast.ASTNode;
import ast.Program;
import ast.function.Parameter;
import ast.type.PrimitiveSpecifier;
import ast.type.PrimitiveSpecifier.PrimitiveDataType;
import parser.ASTConstructor;
import parser.gen.*;
import semantics.table.*;
import util.FearnErrorListener;
import util.Reporter;

/* FearnMethodVisitor.java
 * 
 * This contains methods to handle the importing of other Fearn Programs,
 * and standard library modules.
 */

public class ImportCompiler {

    // This performs an identical process as Compile in main.java, with the 
    // difference of also returning the symbol table.
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
        
        CodeGenerator.generatorStack.push(cg);
               
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

        CodeGenerator.generatorStack.pop();

        return CodeGenerator.GlobalSymbolTable;        
    }

    // This method handles the importing of standard library modules
    // Each module has a case in the below statement, and these cases
    // build and return a SymbolTable for the functions that module contains
    public SymbolTable GetStdLib(String id) {
        
        CodeGenerator.generatorStack.push(new CodeGenerator());

        SymbolTable table = new SymbolTable();

        ArrayList<Parameter> params;

        // Switch makes standard library easy to expand
        switch (id) {
            case "io":
                // Set Program Name
                // This sets the row's owner, ensuring the bytecode for calling these 
                // function calls refer the the correct package and class
                CodeGenerator.generatorStack.peek().programName = "FearnStdLib/io";

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
                
            CodeGenerator.generatorStack.peek().programName =  "FearnStdLib/maths";

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
                CodeGenerator.generatorStack.peek().programName = "FearnStdLib/RandomNumbers";

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
        CodeGenerator.generatorStack.pop();

        // Return Symbol Table to primary compilation process
        return table;
    }
}
