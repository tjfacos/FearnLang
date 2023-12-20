// Local Dependencies
import ast.*;
import ast.expression.*;
import ast.expression.Expression.ExprType;

public class ASTConstructor extends FearnGrammarBaseVisitor<ASTNode> {
    Program root = new Program();

    @Override
    public ASTNode visitStruct_def(FearnGrammarParser.Struct_defContext ctx)
    {
        return new Struct();
    }
    
    // Rules for Primary Expressions
    @Override
    public ASTNode visitId_expr(FearnGrammarParser.Id_exprContext ctx)
    {
        String id = ctx.getText();
        return new PrimaryExpression<String>(id, ExprType.Primary.VariableReference);
    }

    // @Override
    // public ASTNode visitIndex_expr(FearnGrammarParser.Index_exprContext ctx)
    // {

    // }


    @Override
    public ASTNode visitLiteral(FearnGrammarParser.LiteralContext ctx)
    {
        switch (ctx.getStart().getType())
        {
            case FearnGrammarLexer.INT_LIT:
                return new PrimaryExpression<Integer>( Integer.valueOf(ctx.getText()), ExprType.Primary.IntLiteral);
                

            case FearnGrammarLexer.BOOL_LIT:
                return new PrimaryExpression<Boolean>( Boolean.valueOf(ctx.getText()), ExprType.Primary.BoolLiteral);
            
            case FearnGrammarLexer.FLOAT_LIT:
                return new PrimaryExpression<Float>(Float.valueOf(ctx.getText()), ExprType.Primary.FloatLiteral);
            
            case FearnGrammarLexer.STR_LIT:
                return new PrimaryExpression<String>(ctx.getText().substring( 1, ctx.getText().length() - 1), ExprType.Primary.StrLiteral);

            default:
                FearnC.ReportErrorAndExit("Parse Error: Unable to Parse literal value", 3);
        }

        return new PrimaryExpression<Integer>(5, ExprType.Primary.IntLiteral);

    }


}
