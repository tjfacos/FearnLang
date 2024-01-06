package semantics.table;

public class StructRow extends Row {
    
    SymbolTable localSymbolTable = new SymbolTable();
    String descriptor;

    public StructRow(String id, SymbolTable symtab)
    {
        super(id);
        localSymbolTable = symtab;

        descriptor = SymbolTable.GenStructDescriptor(symtab);
    }

}
