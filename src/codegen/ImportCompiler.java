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


public class ImportCompiler {

    
    public String originProgramPath;
    
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

    public SymbolTable GetStdLib(String id) {
        
        SymbolTable table = new SymbolTable();

        ArrayList<Parameter> params;

        // Switch makes stdlib easy to expand
        switch (id) {
            case "io":

            // Add Print Function

            params = new ArrayList<>();
            params.add(new Parameter(
                "", new PrimitiveSpecifier(PrimitiveDataType.STR)
            ));

            table.addRow(
                new FunctionRow(
                    "print", 
                    params, 
                    null, 
                    null
                )
            );

            table.GetAllRows().getLast().owner = "FearnStdLib/io";
            
            // Add Input Function

            params = new ArrayList<>();
            params.add(new Parameter(
                "", new PrimitiveSpecifier(PrimitiveDataType.STR)
            ));

            table.addRow(
                new FunctionRow(
                    "input", 
                    params, 
                    new PrimitiveSpecifier(PrimitiveDataType.STR), 
                    null
                )
            );

            table.GetAllRows().getLast().owner = "FearnStdLib/io";

            return table;

            default:
                Reporter.ReportErrorAndExit("Standard library " + id + " does not exist.", null);
                break;
        }

        return table;
    }
}
