package ast.type;

public class PrimitiveSpecifier extends TypeSpecifier {
    
    public PrimitiveDataType element_type;

    public PrimitiveSpecifier(PrimitiveDataType eleT)
    {
        type = Category.Primitive;
        element_type = eleT;
    }

    @Override
    public String toString()
    {
        return String.format("T[Primitive: %s]", element_type.name());
    }

}
