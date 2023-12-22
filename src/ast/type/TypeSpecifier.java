package ast.type;

import ast.ASTNode;

public class TypeSpecifier extends ASTNode {
    public enum Category
    {
        Primitive,
        Array,
        StructInstance
    }

    public Category type;
}
