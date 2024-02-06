package ast.expression;

import java.util.ArrayList;

import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;

import ast.type.ArrayBodySpecifier;
import ast.type.ArraySpecifier;
import ast.type.PrimitiveSpecifier.PrimitiveDataType;
import ast.type.PrimitiveSpecifier;
import ast.type.TypeSpecifier;
import semantics.table.SymbolTable;
import util.Reporter;

/* ArrayInitExpression.java
 * 
 * Represents an Array Initialisation in the AST (e.g. new int[5]). 
 * 
 * Fields:
 *  ->  type : TypeSpecifier for element types 
 *  ->  dimensions: The expressions for the array dimensions (can be null if body provided)
 *  ->  init_body: The body used to initialise the array (can be null if dimensions provided)
 */

public class ArrayInitExpression extends Expression {
    
    public TypeSpecifier type;
    public ArrayList<Expression> dimensions;
    public ArrayBody init_body;

    public ArrayInitExpression(TypeSpecifier t, ArrayList<Expression> dims, ArrayBody ele)
    {
        type = t;
        dimensions = dims;
        init_body = ele;
    }

    @Override
    public String toString()
    {
        String s = type.toString();
        if (dimensions.get(0) == null)
        {
            s += "[]".repeat(dimensions.size());
        } else {
            for (Expression dim : dimensions)
            {
                s += '[' + dim.toString() + ']';
            }
        }

        if (init_body != null) s += init_body.toString();

        return s;
    
    }

    /* To generate bytecode for an Array Initialisation, the method...
     *  1)  If a body has been provided, just generate that
     *  2)  Otherwise, Generate the bytecode for each dimension, casting the values to 
     *      primitive 'I'
     *  3)  If the array is multi-dimensional, generate an array descriptor using 
     *      SymbolTable, and use visitMultiANewArray, with the number of dimensions
     *  4)  Otherwise, Use a new array, with the descriptor for an element
     */
    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        
        // Just Generate the Body, if provided
        if (init_body != null)
        {
            init_body.GenerateBytecode(mv);
            return;
        }
        
        // Otherwise
        else
        {

            for (Expression dim : dimensions)
            {
                dim.GenerateBytecode(mv);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
            }

            if (dimensions.size() > 1)
            {
                String desc = SymbolTable.GenBasicDescriptor(new ArraySpecifier(type, dimensions.size()));
                mv.visitMultiANewArrayInsn(desc, dimensions.size());
                return;
            } else {
                String desc = SymbolTable.GenBasicDescriptor(type);
                desc = desc.substring(1, desc.length() - 1 );
                mv.visitTypeInsn(ANEWARRAY, desc);
            }
        }
    }

    /* To validate an Array Initialisation, the method...
     *  1)  If a body has been provided...
     *      a)  Validate the body (this ensures the types of elements in the body 
     *          is consistent)
     *      b)  Check the type of elements in the body matches that of the array
     *          ->  This is done by repeatedly accessing the element type of the body 
     *              specifier, repeating for each dimension (e.g. for a 2D array, this 
     *              runs twice), until the type of the actual elements is reached
     *          ->  Raise error otherwise
     *      c)  Check the dimensions are the same (a 3D body is used for a 3D array)
     *      d)  Set expression_type to an ArraySpecifier
     * 2) Otherwise...
     *      a)  For each dimension, validate that they're of type int (raise error 
     *          otherwise)
     *      b)  Set expression_type to an ArraySpecifier
     * 3) Return expression_type
     */
    @Override
    public TypeSpecifier validate(SymbolTable symTable) {        
        
        if (init_body != null)
        {
            // Check the Elements are of the same type
            ArrayBodySpecifier bodySpecifier = (ArrayBodySpecifier)init_body.validate(symTable);
            
            TypeSpecifier typeOfElements = bodySpecifier;
            for (int i = 0; i < bodySpecifier.dimensions.size(); i++)
            {
                typeOfElements = ((ArrayBodySpecifier)typeOfElements).element_type;
            }
    
            if (!typeOfElements.equals(type))
            {
                Reporter.ReportErrorAndExit("Type of Elements in Array Body don't match the element type of the Array.", this);
            }

            if (bodySpecifier.dimensions.size() != dimensions.size())
            {
                Reporter.ReportErrorAndExit("Dimensions of Array Body don't match dimensions of Array Initialisation.", this);
            }

            expression_type = new ArraySpecifier(type, bodySpecifier.dimensions.size());
        } else {
            
            for (Expression e: dimensions)
            {
                TypeSpecifier dim_type = e.validate(symTable);
                if (!dim_type.equals(new PrimitiveSpecifier(PrimitiveDataType.INT)))
                {
                    Reporter.ReportErrorAndExit("Dimensions of arrays must be of type int.", this);
                }
            }
              
            expression_type = new ArraySpecifier(type, dimensions.size());
        }
                
        // Return ArraySpecifier
        return expression_type;
    }
    

}
