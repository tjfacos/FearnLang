package ast.expression;

import java.util.ArrayList;

import org.objectweb.asm.MethodVisitor;

import ast.type.TypeSpecifier;
import semantics.table.SymbolTable;

public class FnCallExpression extends Expression {
    
    public String identifier;
    public ArrayList<Expression> arguements;
    
    public FnCallExpression(String fn_name, ArrayList<Expression> args)
    {
        identifier = fn_name;
        arguements = args;
    }

    @Override
    public String toString()
    {
        return "FCALL "+ identifier + "( " + arguements.toString() + " )";
    }

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        // TODO GenByte FnCallExpression
        
        // Gen args, then INVOKESTATIC

        throw new UnsupportedOperationException("Unimplemented method 'GenerateBytecode'");
    }

    @Override
    public TypeSpecifier validateType(SymbolTable symTable) {
        // TODO validateType FnCallExpression

        // Check the function's signature (Parameters from Symbol Table) against to types of each arguement
        throw new UnsupportedOperationException("Unimplemented method 'validateType'");
    }

}
