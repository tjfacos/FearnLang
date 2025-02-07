package io.github.tjfacos.fearnlang.ast.type;

/* ArraySpecifier.java
 * 
 * Used to specify an array data type (e.g. int[]). This includes the 
 * TypeSpecifier of the elements, and the number of dimensions.
 * 
 */

public class ArraySpecifier extends TypeSpecifier {    

    public TypeSpecifier element_type;
    public Integer dimensionCount;

    public ArraySpecifier(TypeSpecifier eleT, Integer dims)
    {
        type = Category.Array;
        element_type = eleT;
        dimensionCount = dims;
    }

    @Override
    public String toString()
    {
        return element_type.toString() + "[]".repeat(dimensionCount);
    }

}
