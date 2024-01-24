package ast.type;

public class StructInstanceSpecifier extends TypeSpecifier {
    
    public String name;

    public StructInstanceSpecifier(String structName)
    {
        type = Category.StructInstance;
        name = structName;
    }

    @Override
    public String toString()
    {
        return String.format("$", name);
    }
}
