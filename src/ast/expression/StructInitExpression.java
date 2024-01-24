package ast.expression;

import java.util.ArrayList;

import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;

import ast.type.StructInstanceSpecifier;
import ast.type.TypeSpecifier;
import codegen.CodeGenerator;
import semantics.table.SymbolTable;
import util.Reporter;

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
        return "new " + name + arguments.toString().replace("[", "(").replace("]", ")");
    }

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
