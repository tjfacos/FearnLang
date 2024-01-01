package semantics.type;

import ast.Program;
import semantics.table.SymbolTable;

public class TypeAnalysis {
    public static void Analyse(Program root, SymbolTable symbolTable)
    {
        root.verifyType(symbolTable);
    }
}
