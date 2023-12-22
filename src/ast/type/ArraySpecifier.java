package ast.type;

public class ArraySpecifier extends TypeSpecifier {
    
    public TypeSpecifier element_type;

    public ArraySpecifier(TypeSpecifier eleT)
    {
        type = Category.Array;
        element_type = eleT;
    }

    @Override
    public String toString()
    {
        return "T[Array](" + element_type.toString() + ")";
    }

}
