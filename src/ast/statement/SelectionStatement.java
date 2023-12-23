package ast.statement;

import ast.expression.Expression;

public class SelectionStatement extends Statement {
    
    public Expression condition;
    public CompoundStatement if_branch;
    public Statement else_branch;


    public SelectionStatement(Expression cond, CompoundStatement if_body, Statement else_body)
    {
        condition = cond;
        if_branch = if_body;
        else_branch = else_body;
    }
    
    @Override public String toString()
    {
        if (else_branch != null)
        {
            return String.format(
                "IF (%s) THEN %s ELSE %s\n\t",
                condition.toString(),
                if_branch.toString(),
                else_branch.toString()
            );
        }
        
        return String.format(
            "IF (%s) THEN %s\n\t",
            condition.toString(),
            if_branch.toString()
        );

    }

}