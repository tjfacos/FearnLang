package io.github.fearnlang.ast.statement;

import java.util.ArrayList;

import org.objectweb.asm.MethodVisitor;

import io.github.fearnlang.ast.ASTNode;
import io.github.fearnlang.ast.type.TypeSpecifier;
import io.github.fearnlang.codegen.CodeGenerator;
import io.github.fearnlang.semantics.table.SymbolTable;

/* Statement.java
 * 
 * Abstract class representing a statement in Fearn.
 * 
 * Fields:
 *  ->  toString(): Abstract method, that returns a string representation of the node, in 
 *      the syntax of Fearn
 *  ->  GenerateBytecode(): Method that generates the bytecode that will perform the 
 *      functionality of the statement.
 *  ->  validate(): Method that checks that a Statement is syntactically valid, raising 
 *      an error if not
 *  ->  GetLocalDescriptors(): Protected method that iterates through the TypeSpecifiers 
 *      for all local variables, converting them to descriptors and returning them as 
 *      an Object array.
 *      ->  This is used for when the visitField method is used, after jumps in the bytecode,
 *          to specify the state of the stack frame, at runtime.
 */

public abstract class Statement extends ASTNode {

    @Override
    public abstract String toString();

    /**
     * Generates the bytecode for the statement, by having
     * the method visitor visit the relevant instructions.
     * 
     * @param mv The method visitor for this part of the program
     */
    public abstract void GenerateBytecode(MethodVisitor mv);

    /**
     * Validates the expression, enforcing the correct types throughout.
     * 
     * If there's any issues with the expression (e.g. an expression is of the
     * wrong type, the statement isn't used in the right context etc.) then the
     * program will exit, reporting an error to the user.
     * 
     * @param symTable The local SymbolTable to validate against
     */
    public abstract void validate(SymbolTable symbolTable);

    protected Object[] GetLocalDecriptors() {
        ArrayList<String> descriptors = new ArrayList<String>();

        for (TypeSpecifier spec : CodeGenerator.LocalSymbolTable.GetAllVarTypeSpecifiers()) {
            String desc = SymbolTable.GenBasicDescriptor(spec);
            // The descriptors for objects (e.g. Ljava/lang/Integer;) need to be
            // converted into just the class name (java/lang/Integer)
            if (!desc.startsWith("["))
                desc = desc.substring(1, desc.length() - 1);
            descriptors.add(desc);
        }

        Object[] locals = descriptors.toArray();

        return locals;
    }
}