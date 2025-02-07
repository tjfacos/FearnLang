package io.github.tjfacos.fearnlang.ast.type;

/* StructInstanceSpecifier.java
 * 
 * Represents the data type for the instance of a struct. It contains the name
 * of the struct.
 * 
 */

public class StructInstanceSpecifier extends TypeSpecifier {
    
    public String name;

    public StructInstanceSpecifier(String structName)
    {
        type = Category.StructInstance;
        name = structName;
    }

    @Override
    public String toString()
    {
        return String.format("$%s", name);
    }
}
