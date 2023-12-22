package ast.type;

public class ArraySpecifier extends TypeSpecifier {
    
    public PrimitiveDataType element_type;

    public ArraySpecifier(PrimitiveDataType eleT)
    {
        type = Category.Array;
        element_type = eleT;
    }

    @Override
    public String toString()
    {
        return "T[Array]" + element_type.name() + "[]";
    }

}
