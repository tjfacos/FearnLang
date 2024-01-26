package semantics.table;

public abstract class Row {
    
    public String identifier;
    public String descriptor;

    public String owner = null;

    public Row(String id)
    {
        identifier = id;
    }
}
