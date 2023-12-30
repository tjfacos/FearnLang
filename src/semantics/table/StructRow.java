package semantics.table;

public class StructRow extends Row {
    
    SymbolTable localSymbolTable = new SymbolTable();

    public StructRow(String id, SymbolTable symtab)
    {
        super(id);
        localSymbolTable = symtab;
    }

}
