package ast.statement;

import ast.Declaration;
import ast.expression.*;

public class IterationStatement extends Statement {

    public Declaration var_declaration;
    public Expression continue_expression;
    public AssignExpression iteration_expression;
    public CompoundStatement body;

    public IterationStatement(Declaration decl, Expression c_expr, AssignExpression i_expr, CompoundStatement bod)
    {
        var_declaration = decl;
        continue_expression = c_expr;
        iteration_expression = i_expr;
        body = bod;
    }

    @Override public String toString()
    {
        String d, c, i, b;
        if (var_declaration == null) { d = "null"; } else { d = var_declaration.toString(); }
        if (continue_expression == null) { c = "null"; } else { c = continue_expression.toString(); }
        if (iteration_expression == null) { i = "null"; } else { i = iteration_expression.toString(); }
        b = body.toString();
        
        return String.format("FOR ( %s ; %s ; %s ) %s\n\t",
            d, c, i, b
        );

    }
    
}
