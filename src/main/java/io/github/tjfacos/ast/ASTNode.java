package io.github.tjfacos.ast;

import static org.assertj.core.api.Assertions.*;

/* ASTNode.java
 * 
 * This class is the super class for every AST class, for nodes which 
 * appear in the Abstract Syntax Tree.
 * 
 * These are the classes responsible for representing a program, 
 * validating it as as following Fearn's semantic rules, and generating
 * bytecode for their logic when they're used. 
 * 
 * This is an abstract class, and includes
 *  ->  equals(): A method to compare ASTNode classes, often used to compare
 *      TypeSpecifier object to validate data types. It uses AssertJ's 
 *      recursive assertion function, which compares an object by comparing
 *      their attributes, rather than addresses in memory. If the assertion
 *      that two ASTNodes are equal fails, the exception is caught and false
 *      is returned. Otherwise, method returns true.
 *  ->  toString(): An abstract method to return a string representation of a 
 *      node. This is used when reporting errors, to show the offending source 
 *      code.
 *      ->  It was also heavily used during debugging to ensure programs 
 *          where parse correctly, and the AST was being properly constructed.
 */

public abstract class ASTNode {

    @Override
    public boolean equals(Object o) {
        
        try {
            assertThat(this).usingRecursiveComparison().isEqualTo(o);
        } catch (AssertionError e) {
            return false;
        }

        return true;

    }

    public abstract String toString();
}
