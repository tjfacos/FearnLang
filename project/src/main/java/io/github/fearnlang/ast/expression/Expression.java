package io.github.fearnlang.ast.expression;

import org.objectweb.asm.MethodVisitor;

import io.github.fearnlang.ast.ASTNode;
import io.github.fearnlang.ast.type.*;
import io.github.fearnlang.semantics.table.SymbolTable;

/* Expression.java
 * 
 * The abstract class that represents all expressions that exist in Fearn. 
 * 
 * Fields:
 *  ->  ExprType: Enum of expression types, used to tell certain expression objects apart.
 *  ->  toString(): Abstract method, that returns a string representation of the node, in 
 *      the syntax of Fearn
 *  ->  validate(): Method that validates an expression, and its sub-expressions, as being 
 *      syntactically in and of themselves (not considering the context in which they are 
 *      used)
 *          ->  Unlike Statement.validate(), this returns a TypeSpecifier, representing 
 *              the data type of the data the expression valuates to (e.g. 1 + 2 evaluates 
 *              to an int)
 *          ->  This is vital to ensure the types of sub-expressions are valid 
 *              (e.g. "Hello" + 1 is an invalid expression, because one operand is a str, 
 *              and the other an int)
 *  ->  GenerateBytecode(): Method that generates the bytecode that will leave the evaluated
 *      value of the expression on the top of the operand stack.
 *      ->  This is always left as the Object version of the basic primitive type, to restrict
 *      the number of unique instructions the compiler needs to use (JVM instruction are often 
 *      typed, but objects only use one set)
 *  ->  expression_type : The TypeSpecifier of the data type an expression evaluates to. It 
 *      is set during validation, so nodes don't need to be repeatedly re-validated - and 
 *      the type can be accessed during code generation.
 */

public abstract class Expression extends ASTNode {

    /**
     * Represents the type of an expression
     */
    public static enum ExprType {
        // MISC
        FuncCall,
        StructAttribute,
        TypeCast,
        StructInit,
        ArrayInit,
        Index,

        // PRIMARY
        VariableReference,
        IntLiteral,
        FloatLiteral,
        BoolLiteral,
        StrLiteral,

        // UNARY
        Negate,
        LogicalNot,
        Increment,

        // BINARY
        Eq,
        NotEq,

        Less,
        Greater,
        LessEq,
        GreaterEq,
        LogicalAnd,
        LogicalOr,
        Exponent,
        Mult,
        Div,
        Mod,
        Add,
        Sub
    }

    @Override
    public abstract String toString();

    /**
     * Generates the bytecode for the expression, by having
     * the method visitor visit the relevant instructions.
     * 
     * @param mv The method visitor for this part of the program
     */
    public abstract void GenerateBytecode(MethodVisitor mv);

    /**
     * Validates the expression, enforcing the correct types throughout.
     * 
     * If there's any issues with the expression (e.g. a sub-expression is of the
     * wrong type, there are too many sub expressions, etc.) then the program will
     * exit, reporting an error to the user.
     * 
     * @param symTable The local SymbolTable to validate against
     * @return The TypeSpecifier of the value of this expression, once evaluated
     */
    public abstract TypeSpecifier validate(SymbolTable symTable);

    /**
     * The TypeSpecifier of the value of this expression, once evaluated
     */
    public TypeSpecifier expression_type;
}
