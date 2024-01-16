package semantics.table;

import ast.type.TypeSpecifier;

public class VariableRow extends Row {
    
    TypeSpecifier typeSpecifier;

    public VariableRow(String id, TypeSpecifier type)
    {
        super(id);
        typeSpecifier = type;
        descriptor = SymbolTable.GenBasicDescriptor(typeSpecifier);
    }
    
}
