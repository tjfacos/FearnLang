package ast.statement;

import ast.ASTNode;
import ast.expression.*;

public class IterationStatement extends Statement {

    public ASTNode init_expression;
    public Expression continue_expression;
    public AssignExpression iteration_expression;
    public CompoundStatement body;

    public IterationStatement(ASTNode init, Expression c_expr, AssignExpression i_expr, CompoundStatement bod)
    {
        init_expression = init;
        continue_expression = c_expr;
        iteration_expression = i_expr;
        body = bod;
    }

    @Override public String toString()
    {
        String d, c, i, b;
        if (init_expression == null) { d = "null"; } else { d = init_expression.toString(); }
        if (continue_expression == null) { c = "null"; } else { c = continue_expression.toString(); }
        if (iteration_expression == null) { i = "null"; } else { i = iteration_expression.toString(); }
        b = body.toString();
        
        return String.format("\n\tFOR ( %s ; %s ; %s ) %s",
            d, c, i, b
        );

    }
    
}
