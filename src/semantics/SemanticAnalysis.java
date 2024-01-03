package semantics;

import ast.Program;
import semantics.table.SymbolTable;

public class SemanticAnalysis {
    public static void Analyse(Program root, SymbolTable symbolTable)
    {
        root.verifyType(symbolTable);
    }
}
