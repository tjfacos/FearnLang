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

public class ArrayInitExpression extends Expression {
    
    public TypeSpecifier type;
    public ArrayList<Expression> dimenisons;
    public ArrayBody init_body;

    public ArrayInitExpression(TypeSpecifier t, ArrayList<Expression> dims, ArrayBody ele)
    {
        type = t;
        dimenisons = dims;
        init_body = ele;
    }

    @Override
    public String toString()
    {
        String s = type.toString();
        if (dimenisons.get(0) == null)
        {
            s += "[]".repeat(dimenisons.size());
        } else {
            for (Expression dim : dimenisons)
            {
                s += '[' + dim.toString() + ']';
            }
        }

        if (init_body != null) s += init_body.toString();

        return s;
    
    }

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

            for (Expression dim : dimenisons)
            {
                dim.GenerateBytecode(mv);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
            }

            if (dimenisons.size() > 1)
            {
                String desc = SymbolTable.GenBasicDescriptor(new ArraySpecifier(type, dimenisons.size()));
                mv.visitMultiANewArrayInsn(desc, dimenisons.size());
                return;
            } else {
                String desc = SymbolTable.GenBasicDescriptor(type);
                desc = desc.substring(1, desc.length() - 1 );
                mv.visitTypeInsn(ANEWARRAY, desc);
            }


        }
    }

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
                Reporter.ReportErrorAndExit("Type of Elements in Array Body don't match the element type of the Array.");
            }

            if (bodySpecifier.dimensions.size() != dimenisons.size())
            {
                Reporter.ReportErrorAndExit("Dimensions of Array Body don't match dimensions of Array Initialisation.");
            }

            expression_type = new ArraySpecifier(type, bodySpecifier.dimensions.size());
        } else {
            
            for (Expression e: dimenisons)
            {
                TypeSpecifier dim_type = e.validate(symTable);
                if (!dim_type.equals(new PrimitiveSpecifier(PrimitiveDataType.INT)))
                {
                    Reporter.ReportErrorAndExit(toString() + ": Dimensions of arrays must be of type int.");
                }
            }
            
            
            expression_type = new ArraySpecifier(type, dimenisons.size());
        }
                
        // Return ArraySpecifier
        return expression_type;
    }
    

}
