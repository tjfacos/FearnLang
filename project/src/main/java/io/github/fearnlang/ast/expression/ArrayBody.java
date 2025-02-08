package io.github.fearnlang.ast.expression;

import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;

import java.util.ArrayList;


import io.github.fearnlang.ast.type.ArrayBodySpecifier;
import io.github.fearnlang.ast.type.TypeSpecifier;
import io.github.fearnlang.semantics.table.SymbolTable;
import io.github.fearnlang.util.Reporter;

/* ArrayBody.java
 * 
 * Represents an ArrayBody in the AST (e.g. {1, 2, 3}). 
 * 
 * Fields:
 *  ->  elements : The body's elements, modelled as a list of expressions. 
 * 
 */


public class ArrayBody extends Expression {
    
    public ArrayList<Expression> elements;
    
    public ArrayBody(ArrayList<Expression> ele)
    {
        elements = ele;
    }

    @Override
    public String toString()
    {
        return elements.toString().replace("[", "{").replace("]", "}");
    }

    /* To generate bytecode for an ArrayBody, the method...
     *  1)  Gets a descriptor of the body's elements from the SymbolTable
     *  2)  If the body is 1-D, the "L" and ";" ate the front and end of the descriptor 
     *      are removed, to make it a class name
     *  3)  Push the array length to the stack, and use ANEWARRAY to create an empty 
     *      array, of that size, on the top of the stack
     *  4)  For each element in the body ...
     *      4.1)    Duplicate the array (as the AASTORE instruction will pop the array 
     *              from the stack, and we still have more element to assign)
     *      4.2)    Push the index to store the element (starting at 0 and incrementing 
     *              with each element) to the stack
     *      4.3)    Generate the expression to store at that index (GenerateBytecode() 
     *              on expressions leaves its value on top of the stack)
     *      4.4)    Use AASTORE to take the expression value, index, and array, and store
     *              the element - at the index - in the array
     */
    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        
        String desc = SymbolTable.GenBasicDescriptor(elements.get(0).expression_type);

        if (elements.get(0).getClass() != ArrayBody.class) // Array is 1-D
        {
            desc = desc.substring(1, desc.length() - 1);
            // This would transform "Ljava\lang\Integer;" to "java\lang\Integer" (a class name)
        }

        
        mv.visitIntInsn(SIPUSH, elements.size());
        mv.visitTypeInsn(ANEWARRAY, desc);
        
        int i = 0;
        for (Expression e : elements)
        {
            mv.visitInsn(DUP);
            mv.visitIntInsn(SIPUSH, i++);
            e.GenerateBytecode(mv);
            mv.visitInsn(AASTORE);
        }
        
    }

    /* To validate an ArrayBody, the method...
     *  1)  Gets the TypeSpecifier of the first element
     *  2)  Check the rest of the elements have the same TypeSpecifier
     *      ->  Raise Error otherwise
     *  3)  If the body contains other array bodies (N-D array), add the dimensions of the child 
     *      bodies to the end of this body's dimensions
     *  4)  Set expression_type to an ArrayBodySpecifier, and return it
     */
    @Override
    public TypeSpecifier validate(SymbolTable symTable) {

        TypeSpecifier element_type = elements.get(0).validate(symTable);
        
        
        for (Expression e : elements.subList(1, elements.size() ))
        {
            TypeSpecifier e_type = e.validate(symTable);

            if (!element_type.equals(e_type)) Reporter.ReportErrorAndExit(
                "ArrayBody has inconsistent element type.", 
                this
            );
        }

        ArrayList<Integer> dimensions = new ArrayList<Integer>();
        dimensions.add(elements.size());
        
        if (element_type.getClass() == ArrayBodySpecifier.class)
        {
            dimensions.addAll(((ArrayBodySpecifier)element_type).dimensions);
        }

        expression_type = new ArrayBodySpecifier(element_type, dimensions);

        return expression_type;
        
    }


}
