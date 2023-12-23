package ast.statement;

public class JumpStatement extends Statement {
    public enum JumpType 
    {
        Continue,
        Break,
        Return
    }

    public JumpType type;

    public JumpStatement (JumpType t)
    {
        type = t;
    }

    @Override public String toString()
    {
        return "\n\t" + type.name() + ";";
    }
}
