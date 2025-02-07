package io.github.tjfacos.ast.type;

/* PrimitiveSpecifier.java
 * 
 * TypeSpecifier to describe primitive data types. It uses a PrimitiveDataType
 * enum, which describes the data type as an INT, FLOAT, STR, or BOOL.
 * 
 */

public class PrimitiveSpecifier extends TypeSpecifier {
    
    public enum PrimitiveDataType {
        INT,
        FLOAT,
        BOOL,
        STR
    }

    public PrimitiveDataType element_type;

    public PrimitiveSpecifier(PrimitiveDataType eleT)
    {
        type = Category.Primitive;
        element_type = eleT;
    }

    @Override
    public String toString()
    {
        return element_type.name().toLowerCase();
    }

}
