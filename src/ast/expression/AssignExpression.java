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
        String opString = null;

        switch (operator) {
            case Equals     : opString = "="    ; break;
            case AddEquals  : opString = "+="   ; break;
            case SubEquals  : opString = "-="   ; break;
            case MultEquals : opString = "*="   ; break;
            case DivEquals  : opString = "/="   ; break;
            case ModEquals  : opString = "%="   ; break;
        }

        return target.toString() + " " + opString + " " + expression.toString();
    }


    /* The Target Expression can be one of the folowing
    * -> A variable reference              (ASTORE at <INDEX> / PUTSTATIC if Global                )
    * -> An Index Expression               (Iterative Loading of the array, then AASTORE           )
    * -> A Struct Attribute Expression     (PUTFIELD, as Structs can't exist within other Structs  )
    */

    @SuppressWarnings("unchecked")
    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        
        
        if (target.getClass() == PrimaryExpression.class) // Variable Reference
        {
            expression.GenerateBytecode(mv);
            PrimaryExpression<String> t = (PrimaryExpression<String>)target;
            String identifier = t.value.toString();
            
            if (CodeGenerator.LocalSymbolTable.Contains(identifier)) { // Local Variable => ASTORE
                mv.visitVarInsn(ASTORE, CodeGenerator.LocalSymbolTable.GetIndex(identifier));
            } else { // Target is a Global Variable => PUTSTATIC
                mv.visitFieldInsn(
                    PUTSTATIC, 
                    CodeGenerator.generatorStack.peek().programName, 
                    identifier,
                    CodeGenerator.GlobalSymbolTable.GetVarDescriptor(identifier)
                );
            }

        } else if (target.getClass() == IndexExpression.class) { // Index Expression
            
            IndexExpression targ = (IndexExpression)target;
            
            /*
             * To get an assign to an index expression, first I need to load the array by 
             * generating the target. Then, I need to generate the index. Finally, 
             * I need to generate the expression I wish to store and call AASTORE.
             */

            targ.sequence.GenerateBytecode(mv);
            targ.index.GenerateBytecode(mv);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
            expression.GenerateBytecode(mv);
            mv.visitInsn(AASTORE);
    
        } else { // Struct Attribute Expression

            StructAttrExpression targ = (StructAttrExpression)target;
            

            /*
             * To assign to a struct attribute, I need to load the object, 
             * then generate the expression. Then, I can use PUTFIELD to set
             * the attribute.
             */

            targ.instance.GenerateBytecode(mv);
            expression.GenerateBytecode(mv);

            mv.visitFieldInsn(
                PUTFIELD, 
                "$" + targ.struct_name, 
                targ.attribute, 
                targ.attr_descriptor
            );
        }
    }


    @SuppressWarnings("rawtypes")
    public TypeSpecifier validate(SymbolTable symTable) {
        
        if (target instanceof PrimaryExpression && ( (PrimaryExpression)target ).type == ExprType.VariableReference) {}
        else if (target.getClass() == IndexExpression.class) {}
        else if (target.getClass() == StructAttrExpression.class) {}
        else {
            Reporter.ReportErrorAndExit("Cannot assign value to " + target.getClass().getName(), this);
        }

        HandleOperators();

        // Check the TypeSpecifiers of the target and expression are equal
        TypeSpecifier targetType =  target.validate(symTable);
        TypeSpecifier exprType =    expression.validate(symTable);

        if (!targetType.equals(exprType))
        {
            Reporter.ReportErrorAndExit("Cannot assign " + exprType.toString() + " to " + targetType.toString(), this);
        }

        expression_type = null; // Assign Expression perform a job, they don't evaluate to anything
        return expression_type;

    }

    
    private void HandleOperators()
    {
        
        class OpEqualsExpr extends BinaryExpression {
            
            public OpEqualsExpr(Expression op1, Expression op2, ExprType op) { super(op1, op2, op ); }

            @Override
            public String toString() {

                String opString = null;

                switch (Operation) {
                    case ExprType.Add   : opString = "+="; break;
                    case ExprType.Sub   : opString = "-="; break;
                    case ExprType.Mult  : opString = "*="; break;
                    case ExprType.Div   : opString = "/="; break;
                    case ExprType.Mod   : opString = "%="; break;
                    default: break;
                }

                return String.format("%s %s %s", Op1.toString(), opString, Op2.toString());

            }

        }

        switch (operator) {
            case Equals:
                return;
            case AddEquals:
                expression = new OpEqualsExpr(target, expression, ExprType.Add);
                return;
            case SubEquals:
                expression = new OpEqualsExpr(target, expression, ExprType.Sub);
                return;
            case MultEquals:
                expression = new OpEqualsExpr(target, expression, ExprType.Mult);
                return;
            case DivEquals:
                expression = new OpEqualsExpr(target, expression, ExprType.Div);
                return;
            case ModEquals:
                expression = new OpEqualsExpr(target, expression, ExprType.Mod);
                return;
        }
    }

}
