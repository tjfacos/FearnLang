package ast.expression;

import org.objectweb.asm.MethodVisitor;

import ast.ASTNode;
import ast.type.*;
import semantics.table.SymbolTable;

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

    public static enum ExprType 
    {
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

    @Override public abstract String toString();
    
    public abstract void GenerateBytecode(MethodVisitor mv);
    public abstract TypeSpecifier validate(SymbolTable symTable);
    
    public TypeSpecifier expression_type;
}
