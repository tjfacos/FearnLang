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
        return "T[Array](" + element_type.toString() + ")";
    }

}
