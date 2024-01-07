package ast.expression;

import static org.objectweb.asm.Opcodes.INVOKESTATIC;

import java.util.ArrayList;

import org.objectweb.asm.MethodVisitor;

import ast.type.PrimitiveDataType;
import ast.type.PrimitiveSpecifier;
import ast.type.TypeSpecifier;
import codegen.CodeGenerator;
import semantics.table.SymbolTable;
import util.Reporter;

public class FnCallExpression extends Expression {
    
    public String identifier;
    public ArrayList<Expression> arguements;
    
    public FnCallExpression(String fn_name, ArrayList<Expression> args)
    {
        identifier = fn_name;
        arguements = args;
    }

    @Override
    public String toString()
    {
        return "FCALL "+ identifier + "( " + arguements.toString() + " )";
    }

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        
        
        // Gen args, then INVOKESTATIC
        for (Expression arg : arguements) arg.GenerateBytecode(mv);
        
        String desc;
        
        switch (identifier){
            case "print":
                desc = "(Ljava/lang/Object;)V";
                break;
            case "input":
                desc = "(Ljava/lang/String;)Ljava/lang/String;";
                break;
            default:
                desc = CodeGenerator.GlobalSymbolTable.GetGlobalFuncDescriptor(identifier);
                break;
        }
        
        if (identifier.equals("print") || identifier.equals("input"))
        {
            mv.visitMethodInsn(
                INVOKESTATIC, 
                "FearnRuntime", 
                identifier, 
                desc, 
                false
            );
            return;
        }

        mv.visitMethodInsn(
            INVOKESTATIC, 
            CodeGenerator.mainProgramName, 
            identifier, 
            desc, 
            false
        );

    }

    @Override
    public TypeSpecifier validate(SymbolTable symTable) {
        // Check the function's signature (Parameters from Symbol Table) against to types of each arguement
        

        ArrayList<TypeSpecifier> arg_types = new ArrayList<TypeSpecifier>();
        
        if (identifier.equals("print") || identifier.equals("input"))
        {
            if (arguements.size() != 1)
            {
                Reporter.ReportErrorAndExit(toString() + ": Wrong number of arguements, expected 1.");
            }

            arguements.get(0).validate(symTable);

            if (identifier.equals("input"))
            {
            
                if (!arguements.get(0).expression_type.equals(new PrimitiveSpecifier(PrimitiveDataType.STR)))
                {
                    Reporter.ReportErrorAndExit(toString() + ": Wrong data type, expected String.");
                }
                
                expression_type =  new PrimitiveSpecifier(PrimitiveDataType.STR);
                return expression_type;
            }
            
            return null; // null represents a void type
            
        }


        ArrayList<TypeSpecifier> param_types = CodeGenerator.GlobalSymbolTable.GetFuncParameterSpecifiers(identifier);

        for (Expression arg : arguements)
        {
            arg_types.add(arg.validate(symTable));
        }

        if (arguements.size() != param_types.size())
        {
            Reporter.ReportErrorAndExit(toString() + ": Wrong number of arguements, expected" + param_types.size() + ".");
        }

        for (int i = 0; i < param_types.size(); i++)
        {
            if (!param_types.get(i).equals(arg_types.get(i)))
            {
                Reporter.ReportErrorAndExit(toString() + ": Wrong arguement type for " + arguements.get(i).toString() + ", expected " + param_types.get(i).toString());
            }
        }

        expression_type =  CodeGenerator.GlobalSymbolTable.GetTypeSpecifier(identifier, true);
        return expression_type;
    }

}
