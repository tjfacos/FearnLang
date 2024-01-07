package ast.type;

import java.util.ArrayList;

public class ArrayBodySpecifier extends TypeSpecifier {
    public TypeSpecifier element_type;
    public ArrayList<Integer> dimensions;

    public ArrayBodySpecifier(TypeSpecifier eleT, ArrayList<Integer> dims)
    {
        element_type = eleT;
        dimensions = dims;
    }

    @Override
    public String toString()
    {
        return String.format("ArayBody(%s[%s])", element_type.toString(), dimensions.toString());
    }
}
