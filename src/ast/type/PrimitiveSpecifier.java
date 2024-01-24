package ast.type;

public class PrimitiveSpecifier extends TypeSpecifier {
    
    public enum PrimitiveDataType {
        INT,
        FLOAT,
        BOOL,
        STR
    }

    public PrimitiveDataType element_type;

    public PrimitiveSpecifier(PrimitiveDataType eleT)
    {
        type = Category.Primitive;
        element_type = eleT;
    }

    @Override
    public String toString()
    {
        return element_type.name();
    }

}
