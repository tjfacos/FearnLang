package io.github.tjfacos.ast.expression;

import java.util.ArrayList;

import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;

import io.github.tjfacos.ast.type.StructInstanceSpecifier;
import io.github.tjfacos.ast.type.TypeSpecifier;
import io.github.tjfacos.codegen.CodeGenerator;
import io.github.tjfacos.semantics.table.SymbolTable;
import io.github.tjfacos.util.Reporter;

/* StructInitExpression.java
 * 
 * This represents an initialisation of a struct instance
 * 
 * Fields:
 *  ->  name: The name of the struct being instantiated
 *  ->  arguments: The expressions used for the struct's initial state
 */

public class StructInitExpression extends Expression {
    
    public String name;
    public ArrayList<Expression> arguments;
    
    public StructInitExpression(String n, ArrayList<Expression> args)
    {
        name = n;
        arguments = args;
    }

    @Override
    public String toString()
    {
        return "new " + name + "(" + arguments.toString().substring(1, arguments.toString().length() - 1) + ")";
    }

    /* To generate bytecode, the NEW instruction is used to create an instance of the 
     * struct class ($name). This instance is duplicated, and the arguments are generated,
     * leaving their values on top of the stack. Finally, the struct class's constructor is
     * invoked, to set the state of the object. This requires the descriptor from the Global
     * Symbol Table.
     */
    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        mv.visitTypeInsn(NEW, "$" + name);
        mv.visitInsn(DUP);
        for (Expression arg : arguments)
        {
            arg.GenerateBytecode(mv);
        }
        mv.visitMethodInsn(
            INVOKESPECIAL, 
            "$" + name, 
            "<init>", 
            CodeGenerator.GlobalSymbolTable.GetGlobalStructDescriptor(name), 
            false
        );
    }

    /* To validate, the arguments are validated, to ensure they are the right type,
     * and that there are the correct number. If so, a StructInstanceSpecifier is set
     * to expression_type and returned.
     */
    @Override 
    public TypeSpecifier validate(SymbolTable symTable) {
        
        // Checks args, then return type of struct

        ArrayList<TypeSpecifier> attr_types = CodeGenerator.GlobalSymbolTable.GetStructAttributeSpecifiers(name);

        if (attr_types.size() != arguments.size())
        {
            Reporter.ReportErrorAndExit("Wrong number of arguments, expected " + attr_types.size(), this);
        }

        for (int i = 0; i< attr_types.size(); i++)
        {
            if (!arguments.get(i).validate(symTable).equals(attr_types.get(i)))
            {
                Reporter.ReportErrorAndExit("Wrong argument type for " + arguments.get(i).toString() + ", expected " + attr_types.get(i).toString(), this);
            }
        }

        expression_type = new StructInstanceSpecifier(name);

        return expression_type;
    }
}
