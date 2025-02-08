package io.github.fearnlang.ast.type;

import java.util.ArrayList;

/* ArrayBodySpecifier.java
 * 
 * This describes the type of an arraybody, and is used to ensure
 * multi-dimensional arrays have consistent dimensions.
 * 
 * It contains a TypeSpecifier for the elements, and a list of dimensions.
 * (e.g. for a 2-D array body with 2 arrays, each containing 3 elements, 
 * the body would have a specifier [2, 3])
 */


public class ArrayBodySpecifier extends TypeSpecifier {
    
    public TypeSpecifier element_type;
    public ArrayList<Integer> dimensions;

    public ArrayBodySpecifier(TypeSpecifier eleT, ArrayList<Integer> dims)
    {
        element_type = eleT;
        dimensions = dims;
    }

    @Override
    public String toString()
    {
        return element_type.toString() + dimensions.toString();
    }
}
