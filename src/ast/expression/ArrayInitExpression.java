package ast.expression;

import java.util.ArrayList;

import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;

import ast.type.ArrayBodySpecifier;
import ast.type.ArraySpecifier;
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
        return type.toString() + "(dims: " + dimenisons.toString() + ")" + init_body.toString();
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
                
            }

            if (dimenisons.size() > 1)
            {
                String desc = SymbolTable.GenBasicDescriptor(new ArraySpecifier(type, dimenisons.size()));
                mv.visitMultiANewArrayInsn(desc, dimenisons.size());
                return;
            } else {
                String desc = SymbolTable.GenBasicDescriptor(type).substring(1, dimenisons.size() - 1 );
                mv.visitTypeInsn(ANEWARRAY, desc);
            }


        }
    }

    @Override
    public TypeSpecifier validateType(SymbolTable symTable) {        
        
        // Check the Elements are of the same type
        ArrayBodySpecifier bodySpecifier = (ArrayBodySpecifier)init_body.validateType(symTable);
        
        if (!bodySpecifier.element_type.equals(type))
        {
            Reporter.ReportErrorAndExit("Type of Elements in Array Body don't match the element type of the Array.");
        }
        
        
        // Return ArraySpecifier
        expression_type = new ArraySpecifier(type, dimenisons.size());
        return expression_type;
    }
    

}
