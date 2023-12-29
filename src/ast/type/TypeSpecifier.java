package ast.type;

import ast.ASTNode;

public abstract class TypeSpecifier extends ASTNode {
    public enum Category
    {
        Primitive,
        Array,
        StructInstance
    }

    public Category type;
    public String type_descriptor;

}
