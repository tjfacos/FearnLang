package ast.expression;

import java.util.ArrayList;

import org.objectweb.asm.MethodVisitor;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GenerateBytecode'");
    }


}
