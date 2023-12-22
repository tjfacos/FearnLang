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
        return "T[Primitive]" + element_type.name();
    }

}
