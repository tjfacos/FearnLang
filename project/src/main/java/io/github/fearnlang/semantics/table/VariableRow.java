package io.github.fearnlang.semantics.table;

import io.github.fearnlang.ast.type.TypeSpecifier;

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
        super(id);
        dataType = type;

        // Generate type descriptor, representing variable's type 
        // to the JVM 
        descriptor = SymbolTable.GenBasicDescriptor(dataType);
    }
    
}
