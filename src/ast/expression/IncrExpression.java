package ast.expression;

import org.objectweb.asm.MethodVisitor;

import ast.expression.AssignExpression.AssignmentOperator;
import ast.statement.ExpressionStatement;
import ast.type.PrimitiveSpecifier.PrimitiveDataType;
import ast.type.PrimitiveSpecifier;
import ast.type.TypeSpecifier;
import semantics.table.SymbolTable;
import util.Reporter;

public class IncrExpression extends Expression {

    public Expression expression;
    public Boolean isPrefix;
    public Boolean isDecrement;

    ExpressionStatement incStatement;

    public IncrExpression(Expression expr, Boolean isDec, Boolean isPre) {
        expression = expr;
        isPrefix = isPre;
        isDecrement = isDec;
    }

    @Override public String toString()
    {
    
        String symbol = isDecrement ? "--" : "++";
        if (isPrefix)
        {
            return symbol + expression.toString();
        } 

        return expression.toString() + symbol;
    
    }

    public void GenerateBytecode(MethodVisitor mv)
    {
        if (isPrefix)
        {
            incStatement.GenerateBytecode(mv);
            expression.GenerateBytecode(mv);
            return;
        }
        
        expression.GenerateBytecode(mv);
        incStatement.GenerateBytecode(mv);
        return;

    }
    
    @SuppressWarnings("rawtypes")
    public TypeSpecifier validate(SymbolTable symTable)
    {

        if (expression instanceof PrimaryExpression && ( (PrimaryExpression)expression ).type == ExprType.VariableReference) {}
        else if (expression.getClass() == IndexExpression.class) {}
        else if (expression.getClass() == StructAttrExpression.class) {}
        else {
            Reporter.ReportErrorAndExit("Cannot assign value to " + expression.getClass().getName(), this);
        }


        TypeSpecifier expr_type = expression.validate(symTable);
        
        if ((!expr_type.equals(new PrimitiveSpecifier(PrimitiveDataType.INT))))
        {
            Reporter.ReportErrorAndExit("Can only increment/decrement ints.", this);
        }

        incStatement = new ExpressionStatement(
            new AssignExpression(
                expression, 
                new PrimaryExpression<Integer>(1, ExprType.IntLiteral), 
                isDecrement ? AssignmentOperator.SubEquals : AssignmentOperator.AddEquals
            ), true
        );
                
        incStatement.validate(symTable);
        
        expression_type = expr_type;
        return expression_type;
    }
            
}