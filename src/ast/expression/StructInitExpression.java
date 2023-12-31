package ast.expression;

import java.util.ArrayList;

import org.objectweb.asm.MethodVisitor;

import ast.type.TypeSpecifier;
import semantics.table.SymbolTable;

public class StructInitExpression extends Expression {
    
    public String name;
    public ArrayList<Expression> arguements;
    
    public StructInitExpression(String n, ArrayList<Expression> args)
    {
        name = n;
        arguements = args;
    }

    @Override
    public String toString()
    {
        return "$" + name + "( " + arguements.toString() + " )";
    }

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        // TODO GenByte StructInit
        throw new UnsupportedOperationException("Unimplemented method 'GenerateBytecode'");
    }

    @Override 
    public TypeSpecifier validateType(SymbolTable symTable) {
        // TODO validateType StructInit

        // Checks args, then return type of struct
        throw new UnsupportedOperationException("Unimplemented method 'validateType'");
    }

}
