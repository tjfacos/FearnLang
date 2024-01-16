package semantics.table;

public abstract class Row {
    public String identifier;
    public String descriptor;

    public Row(String id)
    {
        identifier = id;
    }
}
