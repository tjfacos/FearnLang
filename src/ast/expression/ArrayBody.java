package ast.expression;

import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;

import java.util.ArrayList;


import ast.type.ArrayBodySpecifier;
import ast.type.TypeSpecifier;
import semantics.table.SymbolTable;
import util.Reporter;

public class ArrayBody extends Expression {
    
    public ArrayList<Expression> elements;
    
    public ArrayBody(ArrayList<Expression> ele)
    {
        elements = ele;
    }

    @Override
    public String toString()
    {
        return "{" + elements.toString() + "}";
    }

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        
        String desc = SymbolTable.GenBasicDescriptor(elements.get(0).expression_type);

        if (elements.get(0).getClass() != ArrayBody.class) // Array is 1-D
        {
            desc = desc.substring(1, desc.length() - 1);
        }

        
        mv.visitIntInsn(SIPUSH, elements.size());
        mv.visitTypeInsn(ANEWARRAY, desc);
        mv.visitInsn(DUP);
        
        int i = 0;
        for (Expression e : elements)
        {
            mv.visitIntInsn(SIPUSH, i++);
            e.GenerateBytecode(mv);
            mv.visitInsn(AASTORE);
            
            if (i < elements.size())
            {
                mv.visitInsn(DUP);
            }
        }
        
    }

    @Override
    public TypeSpecifier validate(SymbolTable symTable) {
        
        // Check Dimensions and Element Types
        // Recursion needed for N-D Arrays

        TypeSpecifier element_type = elements.get(0).validate(symTable);
        
        
        for (Expression e : elements.subList(1, elements.size() ))
        {
            TypeSpecifier e_type = e.validate(symTable);

            if (element_type.equals(e_type)) {}
            else { Reporter.ReportErrorAndExit("Type Error :- ArrayBody " + this.toString() + " has inconsistent element type."); }
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
