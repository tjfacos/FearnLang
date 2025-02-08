package io.github.fearnlang.ast.expression;

import org.objectweb.asm.MethodVisitor;

import io.github.fearnlang.ast.expression.AssignExpression.AssignmentOperator;
import io.github.fearnlang.ast.type.PrimitiveSpecifier.PrimitiveDataType;
import io.github.fearnlang.ast.type.PrimitiveSpecifier;
import io.github.fearnlang.ast.type.TypeSpecifier;
import io.github.fearnlang.semantics.table.SymbolTable;
import io.github.fearnlang.util.Reporter;

/* IncrExpression.java
 * 
 * Represents an Increment/Decrement Expression in the AST. 
 * 
 * Fields:
 *  ->  expression: An assignable integer expression, to increment/decrement 
 *  ->  isPrefix: Boolean flag, true if the expression is a prefix increment/decrement
 *  ->  isDecrement: Boolean flag, true if expression is a Decrement
 * 
 *  ->  increment: private AssignExpression, used to generate the bytecode to 
 *      increment/decrement the target expression
 */

public class IncrExpression extends Expression {

    public Expression expression;
    public Boolean isPrefix;
    public Boolean isDecrement;

    private AssignExpression increment;

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

    /* To generate bytecode, the bytecode to load the expression and perform the increment 
     * both need to be generated. The order in which this is done depends on whether the 
     * expression is prefix.
     */
    public void GenerateBytecode(MethodVisitor mv)
    {

        if (isPrefix)
        {
            // Increment first, then load value to stack
            increment.GenerateBytecode(mv);
            expression.GenerateBytecode(mv);
            return;
        }
        
        // Load value to stack, then increment
        expression.GenerateBytecode(mv);
        increment.GenerateBytecode(mv);
        return;

    }
    
    /* To validate, ensure the expression is assignable. This means it is either
     *  ->  A variable reference (a sort of Primary expression)
     *  ->  An index expression (e.g. x[0])
     *  ->  A reference to an attribute of a struct instance
     * 
     * Then, the expression is validated, and an error is raised if it's not of type int.
     * 
     * If the expression is valid, increment is set to a new AssignExpression, where the 
     * operator is determined using the isDecrement property. If so, the expression uses -=,
     * otherwise +=.  This expression is validated (no errors should be raised at this point, 
     * but the nodes below need to configure themselves before generation). expression_type is
     * set to int, and this is returned.
     * 
     */
    @SuppressWarnings("rawtypes")
    public TypeSpecifier validate(SymbolTable symTable)
    {

        if (expression instanceof PrimaryExpression 
            && ( (PrimaryExpression)expression ).type == ExprType.VariableReference) {}
        else if (expression instanceof IndexExpression) {}
        else if (expression instanceof StructAttrExpression) {}
        else Reporter.ReportErrorAndExit(
            "Cannot increment " + expression.toString() + ". Only int variables can be incremented/decremented.", 
            this
        );
        

        TypeSpecifier expr_type = expression.validate(symTable);
        
        if ((!expr_type.equals(new PrimitiveSpecifier(PrimitiveDataType.INT))))
        {
            Reporter.ReportErrorAndExit("Can only increment/decrement ints.", this);
        }

        increment = new AssignExpression(
            expression, 
            new PrimaryExpression<Integer>(1, ExprType.IntLiteral), 
            isDecrement ? AssignmentOperator.SubEquals : AssignmentOperator.AddEquals
        );
                
        increment.validate(symTable);
        
        expression_type = expr_type;
        return expression_type;
    }
}