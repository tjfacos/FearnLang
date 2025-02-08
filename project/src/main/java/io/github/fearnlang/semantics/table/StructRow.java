package io.github.fearnlang.semantics.table;

/* 
* StructRow.java
* 
* Represents a struct, within the global symbol table
* Includes:
    ->  localSymbolTable - a table of the struct's attributes
*/

public class StructRow extends Row {
    
    public SymbolTable localSymbolTable = new SymbolTable();
    
    public StructRow(String id, SymbolTable symtab)
    {
        super(id);
        localSymbolTable = symtab;

        // Generate method descriptor for struct class's constructor
        descriptor = SymbolTable.GenStructDescriptor(symtab);
    }
}
