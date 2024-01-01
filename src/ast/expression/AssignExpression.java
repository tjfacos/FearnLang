package ast.expression;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.MethodVisitor;

import ast.type.TypeSpecifier;
import codegen.CodeGenerator;
import semantics.table.SymbolTable;
import util.Reporter;

public class AssignExpression extends Expression {
    public static enum AssignmentOperator {
        Equals,
        AddEquals,
        SubEquals,
        MultEquals,
        DivEquals,
        ModEquals
    }

    public Expression target;
    public Expression expression;
    public AssignmentOperator operator;


    public AssignExpression(Expression t, Expression e, AssignmentOperator op)
    {
        target = t;
        expression = e;
        operator = op;
    }

    @Override 
    public String toString()
    {
        return target.toString() + " " + operator.name() + " ( " + expression.toString() + " ) ";
    }

    @SuppressWarnings("unchecked")
    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        expression.GenerateBytecode(mv);

        /* The Target Expression can be one of the folowing
         * -> A variable reference              (ASTORE at <INDEX> / PUTSTATIC if Global                )
         * -> An Index Expression               (Iterative Loading of the array, then AASTORE           )
         * -> A Struct Attribute Expression     (PUTFIELD, as Structs can exist within other Structs    )
         * 
         */

        if (target.getClass() == PrimaryExpression.class) // Variable Reference
        {
            PrimaryExpression<String> t = (PrimaryExpression<String>)target;
            String identifier = t.value.toString();
            
            if (CodeGenerator.GlobalSymbolTable.Contains(identifier)) { // Target is a Global Variable => PUTFIELD
                mv.visitFieldInsn(
                    PUTFIELD, 
                    CodeGenerator.mainProgramName, 
                    identifier,
                    CodeGenerator.GlobalSymbolTable.GetGlobalVarDescriptor(identifier)
                );
            } else { // Local Variable => ASTORE
                mv.visitVarInsn(ASTORE, CodeGenerator.LocalSymbolTable.GetIndex(identifier));
            }

        } else if (target.getClass() == IndexExpression.class) { // Index Expression
            // TODO : Implement Assign to Index Expression
        } else { // Struct Attribute Expression
            // TODO : Implement Assign to Struct Attribute Expression
        }
    }

    @Override
    public TypeSpecifier validateType(SymbolTable symTable) {
        // Check the TypeSpecifiers of the target and expression are equal
        TypeSpecifier targetType =  target.validateType(symTable);
        TypeSpecifier exprType =    expression.validateType(symTable);

        if (!targetType.equals(exprType))
        {
            Reporter.ReportErrorAndExit("Cannot assign " + exprType.toString() + " to " + targetType.toString());
        }

        expression_type = null; // Assign Expression perform a job, they don't evaluate to anything
        return expression_type;
    }

}
