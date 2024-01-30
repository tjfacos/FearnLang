package semantics.table;

public class StructRow extends Row {
    
    /* 
    * StructRow.java
    * 
    * Represents a struct, within the global symbol table
    * Includes:
        ->  localSymbolTable - a table of the struct's 
            attributes
    */

    SymbolTable localSymbolTable = new SymbolTable();
    
    public StructRow(String id, SymbolTable symtab)
    {
        super(id);
        localSymbolTable = symtab;

        // Generate method descriptor for struct class's constructor
        descriptor = SymbolTable.GenStructDescriptor(symtab);
    }

}
