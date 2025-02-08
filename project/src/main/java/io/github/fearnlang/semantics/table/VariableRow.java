package io.github.fearnlang.semantics.table;

import io.github.fearnlang.ast.type.TypeSpecifier;
import io.github.fearnlang.semantics.table.SymbolTable.SymbolType;

/* 
 * Variable.java
 * 
 * Represents a variable, within either the global symbol table,
 * or the local symbol table of a function/struct
 * Includes:
    ->  dataType - the type specifier, representing the variable's 
        data type
*/

public class VariableRow extends Row {
    
    TypeSpecifier dataType;

    public VariableRow(String id, TypeSpecifier type)
    {
        super(id, SymbolType.Variable);
        dataType = type;

        // Generate type descriptor, representing variable's type 
        // to the JVM 
        descriptor = SymbolTable.GenBasicDescriptor(dataType);
    }
    
}
