package semantics.table;

import java.util.ArrayList;
import ast.function.*;
import ast.type.TypeSpecifier;

public class FunctionRow extends Row {
    ArrayList<Parameter> parameters;
    TypeSpecifier return_type;
    SymbolTable localSymbolTable = new SymbolTable();
    String descriptor;

    public FunctionRow(String identifier, ArrayList<Parameter> params, TypeSpecifier rt, SymbolTable local)
    {
        super(identifier);
        parameters = params;
        return_type = rt;

        descriptor = SymbolTable.GenFuncDescriptor(params, rt);
        localSymbolTable = local;
    }

}
