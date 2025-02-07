package io.github.tjfacos.ast.function;

import io.github.tjfacos.ast.ASTNode;
import io.github.tjfacos.ast.type.TypeSpecifier;

/* Parameter.java
 * 
 * Represents a function parameter in the AST
 * 
 * Fields:
 *  ->  identifier: string name of parameter, which can be used as a variable 
 *      within the function body
 *  ->  type: TypeSpecifier of the data value of the parameter (just like any 
 *      normal variable). Used to validate the type of arguments when function 
 *      is called
 */

public class Parameter extends ASTNode {
    
    public String identifier;
    public TypeSpecifier type;
    
    public Parameter(String id, TypeSpecifier t)
    {
       identifier = id;
       type = t;
    }

    @Override public String toString()
    {
        return String.format("%s : %s", type.toString(), identifier);
    }
    
}
