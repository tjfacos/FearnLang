package codegen;

import java.io.FileInputStream;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import ast.ASTNode;
import ast.Program;
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
        SymbolTable symTable = astConstructor.symTabStack.pop();

        CodeGenerator.GlobalSymbolTable = symTable;

        // Perform Type Analysis
        root.validate(symTable);


        cg.Generate(root, symTable);

        CodeGenerator.generatorStack.pop();

        return CodeGenerator.GlobalSymbolTable;        
    }

    public SymbolTable GetStdLib(String id) {
        
        SymbolTable table = new SymbolTable();

        switch (id) {
            default:
                Reporter.ReportErrorAndExit("Standard library " + id + " does not exist.", null);
                break;
        }

        return table;
    }
}
