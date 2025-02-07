package io.github.tjfacos.ast;

import static org.objectweb.asm.Opcodes.ASTORE;

import org.objectweb.asm.MethodVisitor;

import io.github.tjfacos.ast.expression.Expression;
import io.github.tjfacos.ast.type.TypeSpecifier;
import io.github.tjfacos.codegen.CodeGenerator;
import io.github.tjfacos.semantics.table.SymbolTable;
import io.github.tjfacos.util.Reporter;

/* Declaration.java
 * 
 * Represents a Variable Declaration in the AST.
 * 
 * Fields:
 *  ->  identifier: the string identifier of the variable, in the source code
 *  ->  type: TypeSpecifier for the data type of the variable
 *  ->  init_expression: The expression used to initialise the variable (may be null)
 */

public class Declaration extends ASTNode {
    
    public String identifier;
    public TypeSpecifier type;
    public Expression init_expression;
    
    // Standard Constructor
    public Declaration(String id, TypeSpecifier t, Expression e)
    {
        identifier = id;
        type = t;
        init_expression = e;
    }

    // String representation takes the same form as a declaration in source code
    // It reclusively calls the toString methods for the type, and init expression
    // The ternary expression only includes the init_expression if it is not null
    @Override public String toString()
    {
        return (init_expression == null) ?
                "let " + identifier + " : "+ type.toString() + ";"
            :   "let " + identifier + " : "+ type.toString() + " = " + init_expression.toString() + ";"
        ;
    }

    // GenerateBytecode() generates the init_expression bytecode (leaving its value 
    // at the top of the operand stack), and then stores it at the variable index 
    // indicated by the LocalSymbolTable.
    //  ->  This is ONLY called to generate local variables within functions (globals 
    //      are handled in CodeGenerator.java), so no need to handle global variables
    public void GenerateBytecode(MethodVisitor mv) 
    {
        if (init_expression != null) {
            init_expression.GenerateBytecode(mv);
            mv.visitVarInsn(ASTORE, CodeGenerator.LocalSymbolTable.GetIndex(identifier));
        }
    }

    // validate() compares the expression_type of the init_expression to the type of the 
    // variable. If they don't match, an error is raise. Otherwise, declaration is valid 
    // and the method returns. The method will return immediately if init_expression is 
    // null, as a declaration without initialisation cannot be invalid (at this stage, 
    // errors such as two variables in the same function having the same name are caught 
    // by the SymbolTable during AST Construction)
    public void validate(SymbolTable symbolTable) {
        if (init_expression == null) return;
        TypeSpecifier exprType   = init_expression.validate(symbolTable);
        if (!type.equals(exprType))
        {
            Reporter.ReportErrorAndExit(
                "Cannot assign " + exprType.toString() + " to " + type.toString(), 
                this
            );
        }
    }
}
