package codegen;

import org.objectweb.asm.MethodVisitor;

import ast.expression.*;
import ast.expression.Expression.ExprType;
import util.Reporter;

@SuppressWarnings("unchecked")

public class ExprGenerator {
    
    static void GenerateExpression(Expression expr, MethodVisitor mv) {
        if      (expr.getClass() == ArrayBody.class)            { ExprGenerator.ArrayBody((ArrayBody)expr, mv);                                         }
        else if (expr.getClass() == ArrayInitExpression.class)  { ExprGenerator.ArrayInitExpression((ArrayInitExpression)expr, mv);                     }
        else if (expr.getClass() == AssignExpression.class)     { ExprGenerator.AssignExpression((AssignExpression)expr, mv);                           }
        else if (expr.getClass() == BinaryExpression.class)     { ExprGenerator.BinaryExpression((BinaryExpression)expr, mv);                           }
        else if (expr.getClass() == CastExpression.class)       { ExprGenerator.CastExpression((CastExpression)expr, mv);                               }
        else if (expr.getClass() == FnCallExpression.class)     { ExprGenerator.FnCallExpression((FnCallExpression)expr, mv);                           }
        else if (expr.getClass() == IndexExpression.class)      { ExprGenerator.IndexExpression((IndexExpression)expr, mv);                             }
        else if (expr.getClass() == PrimaryExpression.class)    { ExprGenerator.PrimaryExpression((PrimaryExpression<Object>)expr, mv);                 }
        else if (expr.getClass() == StructAttrExpression.class) { ExprGenerator.StructAttrExpression((StructAttrExpression)expr, mv);                   }
        else if (expr.getClass() == StructInitExpression.class) { ExprGenerator.StructInitExpression((StructInitExpression)expr, mv);                   }
        else if (expr.getClass() == UnaryExpression.class)      { ExprGenerator.UnaryExpression((UnaryExpression)expr, mv);                             }
        else                                                    { Reporter.ReportErrorAndExit("Expression Gen Error on " + expr.toString(), 50);   }
    }

    private static void PrimaryExpression(PrimaryExpression<Object> expr, MethodVisitor mv) {
        if (expr.type == ExprType.VariableReference)
        {
            // Get Index from Symbol Table, and Load it
        } else {
            // Load Constant into Stack
        }    
    }


    private static void UnaryExpression(UnaryExpression expr, MethodVisitor mv) {
    }

    private static void StructInitExpression(StructInitExpression expr, MethodVisitor mv) {
    }

    private static void StructAttrExpression(StructAttrExpression expr, MethodVisitor mv) {
    }


    private static void IndexExpression(IndexExpression expr, MethodVisitor mv) {
    }

    private static void FnCallExpression(FnCallExpression expr, MethodVisitor mv) {
    }

    private static void CastExpression(CastExpression expr, MethodVisitor mv) {
    }

    private static void BinaryExpression(BinaryExpression expr, MethodVisitor mv) {
    }

    private static void AssignExpression(AssignExpression expr, MethodVisitor mv) {
    }

    private static void ArrayBody(ArrayBody expr, MethodVisitor mv) {
    }

    private static void ArrayInitExpression(ArrayInitExpression expr, MethodVisitor mv) {
    }












}
