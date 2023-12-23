package ast.type;

public class ArraySpecifier extends TypeSpecifier {
    
    public TypeSpecifier element_type;
    public Integer dimensions;

    public ArraySpecifier(TypeSpecifier eleT, Integer dims)
    {
        type = Category.Array;
        element_type = eleT;
        dimensions = dims;
    }

    @Override
    public String toString()
    {
        return String.format("T[Array: %s]", element_type.toString());
    }

}
