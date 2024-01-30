package semantics.table;

import java.util.ArrayList;
import ast.function.*;
import ast.type.TypeSpecifier;

/* 
 * FunctionRow.java
 * 
 * Represents a function, within the global symbol table
 * Includes:
    ->  parameters - An list of Parameter objects, 
        representing the function paramters
    ->  return_type - the typeSpecifier of the 
        value the function returns (null is the 
        function is void)
    ->  localSymbolTable - a table of the local 
        variables that only exist within the 
        scope of the function
*/

public class FunctionRow extends Row {
    ArrayList<Parameter> parameters;
    TypeSpecifier return_type;
    SymbolTable localSymbolTable = new SymbolTable();
    
    public FunctionRow(String identifier, ArrayList<Parameter> params, TypeSpecifier rt, SymbolTable local)
    {
        super(identifier);
        parameters = params;
        return_type = rt;

        // Generate method descriptor
        descriptor = SymbolTable.GenFuncDescriptor(params, rt);
        localSymbolTable = local;
    }

}
