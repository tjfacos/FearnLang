package codegen;

import java.io.FileInputStream;
import java.nio.file.Paths;

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

    static CodeGenerator cg = new CodeGenerator();

    public static String originProgramPath;

    public static SymbolTable Compile(String path) {
        
        String currentOrigin = originProgramPath;

        path = Paths.get(originProgramPath).getParent().resolve(path.replaceAll("(\')", "")).toString();

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

        cg.Generate(root, symTable, path);

        for (Row row : symTable.GetAllRows())
        {
            row.owner = Paths.get(path).getFileName().toString().replace(".fearn", "");
        }

        originProgramPath = currentOrigin;

        return CodeGenerator.GlobalSymbolTable;        
    }

    public static SymbolTable GetStdLib(String id) {
        
        SymbolTable table = new SymbolTable();

        switch (id) {
            default:
                Reporter.ReportErrorAndExit("Standard library " + id + " does not exist.", null);
                break;
        }

        return table;
    }
}
