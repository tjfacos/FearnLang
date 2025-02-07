package io.github.tjfacos.fearnlang.ast.type;

import io.github.tjfacos.fearnlang.ast.ASTNode;

/* TypeSpecifier.java
 * 
 * The abstract superclass of all TypeSpecifier classes.
 * 
 * TypeSpecifiers represent data types, associated with variables, function
 * return types, etc. These can be compared to ensure a structure in the program
 * is valid.
 * 
 * It has a `type`, which is a Category from the below enum.
 *  ->  Primitive Types are indivisible, such as ints, floats, strings, and 
 *      Booleans (bools)
 *  ->  Array types are for arrays of primitive values or struct instances
 *  ->  StructInstance represents the data is an instance of a user-defined struct
 * 
 */

public abstract class TypeSpecifier extends ASTNode {
    
    public enum Category
    {
        Primitive,
        Array,
        StructInstance
    }

    public Category type;

}
