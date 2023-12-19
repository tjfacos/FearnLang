package ast;

public class Declaration {
    
    private String identifer;
    private TypeSpecifier type;
    private Expression init_expr;
    
    public void setIdentifier(String id)
    {
        this.identifer = id;
    }

    public void setTypeSpecifier(TypeSpecifier type)
    {
        this.type = type;
    }

    public void setInitExpression(Expression expr)
    {
        this.init_expr = expr;
    }

    public String getIdentifier()
    {
        return this.identifer;
    }

    public TypeSpecifier getTypeSpecifier()
    {
        return this.type;
    }

    public Expression getInitExpression()
    {
        return this.init_expr;
    }

}
