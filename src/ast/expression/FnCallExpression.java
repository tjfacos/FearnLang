package ast.expression;

import static org.objectweb.asm.Opcodes.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.objectweb.asm.MethodVisitor;

import ast.type.ArraySpecifier;
import ast.type.PrimitiveSpecifier.PrimitiveDataType;
import ast.type.PrimitiveSpecifier;
import ast.type.TypeSpecifier;
import codegen.CodeGenerator;
import semantics.table.SymbolTable;
import util.Reporter;

public class FnCallExpression extends Expression {
    
    public String identifier;
    public ArrayList<Expression> arguments;
    
    static List<String> builtins = Arrays.asList("print", "input", "length", "slice");

    
    public FnCallExpression(String fn_name, ArrayList<Expression> args)
    {
        identifier = fn_name;
        arguments = args;
    }
    
    @Override
    public String toString()
    {
        return identifier + "(" + arguments.toString() + ")";
    }
    
    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        
        
        // Gen args, then INVOKESTATIC
        for (Expression arg : arguments) arg.GenerateBytecode(mv);
        
        String desc;
        
        switch (identifier){

            case "print":
                desc = "(Ljava/lang/Object;)V";
                break;

            case "input":
                desc = "(Ljava/lang/String;)Ljava/lang/String;";
                break;

            case "length":
                desc = "(Ljava/lang/Object;)Ljava/lang/Integer;";
                break;

            case "slice":
                String t = null;

                if (arguments.get(0).expression_type.equals(new PrimitiveSpecifier(PrimitiveDataType.STR))) t = "Ljava/lang/String;";
                else t = "[Ljava/lang/Object;";

                desc = String.format( "(%sLjava/lang/Integer;Ljava/lang/Integer;)%s", t, t );
                break;

            default:
                desc = CodeGenerator.GlobalSymbolTable.GetGlobalFuncDescriptor(identifier);
                break;

        }

        
        if (builtins.contains(identifier))
        {
            mv.visitMethodInsn(
                INVOKESTATIC, 
                "FearnRuntime", 
                identifier, 
                desc, 
                false
            );
            
            if (identifier.equals("slice") && desc.contains("Object"))
            {
                mv.visitTypeInsn(CHECKCAST, SymbolTable.GenBasicDescriptor(arguments.get(0).expression_type));
            }
            
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
        
        switch (identifier) {
            

            // input(str)
            case "input":
                
                if (arguments.size() != 1)
                {
                    Reporter.ReportErrorAndExit(toString() + ": Wrong number of arguements for " + identifier + " , expected 1.");
                }

                if (!arguments.get(0).validate(symTable).equals(new PrimitiveSpecifier(PrimitiveDataType.STR)))
                {
                    Reporter.ReportErrorAndExit(toString() + ": Wrong data type, expected String.");
                }
                
                expression_type =  new PrimitiveSpecifier(PrimitiveDataType.STR);
                return expression_type;
        

            // print(obj)
            case "print":
                if (arguments.size() != 1)
                {
                    Reporter.ReportErrorAndExit(toString() + ": Wrong number of arguements for " + identifier + " , expected 1.");
                }

                arguments.get(0).validate(symTable);

                return null;
            


            // length(<str|arr>)
            case "length":
                if (arguments.size() != 1)
                {
                    Reporter.ReportErrorAndExit(toString() + ": Wrong number of arguements for " + identifier + " , expected 1.");
                }

                if (!(
                    arguments.get(0).validate(symTable).equals(new PrimitiveSpecifier(PrimitiveDataType.STR)) ||
                    arguments.get(0).validate(symTable) instanceof ArraySpecifier
                ))
                {
                    Reporter.ReportErrorAndExit(toString() + ": Wrong arguement data type, expected string or array.");
                }

                expression_type =  new PrimitiveSpecifier(PrimitiveDataType.INT);
                return expression_type;



            // slice(<str|arr>, int, int);
            case "slice":
                if (arguments.size() != 3)
                {
                    Reporter.ReportErrorAndExit(toString() + ": Wrong number of arguements for " + identifier + " , expected 3.");
                }

                if (!(
                    arguments.get(0).validate(symTable).equals(new PrimitiveSpecifier(PrimitiveDataType.STR)) ||
                    arguments.get(0).validate(symTable) instanceof ArraySpecifier
                ))
                {
                    Reporter.ReportErrorAndExit(toString() + ": Wrong arguement data type, expected string or array.");
                }

                if (!(
                    arguments.get(1).validate(symTable).equals(new PrimitiveSpecifier(PrimitiveDataType.INT)) ||
                    arguments.get(2).validate(symTable).equals(new PrimitiveSpecifier(PrimitiveDataType.INT))
                ))
                {
                    Reporter.ReportErrorAndExit(toString() + ": Wrong arguement data type, expected int.");
                }

                expression_type =  arguments.get(0).expression_type;
                return expression_type;

                


            default:
                break;
        }

        // Handle Generic, user-defined functions

        ArrayList<TypeSpecifier> param_types = CodeGenerator.GlobalSymbolTable.GetFuncParameterSpecifiers(identifier);

        for (Expression arg : arguments)
        {
            arg_types.add(arg.validate(symTable));
        }

        if (arguments.size() != param_types.size())
        {
            Reporter.ReportErrorAndExit(toString() + ": Wrong number of arguements for " + identifier + " , expected" + param_types.size() + ".");
        }

        for (int i = 0; i < param_types.size(); i++)
        {
            if (!param_types.get(i).equals(arg_types.get(i)))
            {
                Reporter.ReportErrorAndExit(toString() + ": Wrong arguement type for " + arguments.get(i).toString() + ", expected " + param_types.get(i).toString());
            }
        }

        expression_type =  CodeGenerator.GlobalSymbolTable.GetTypeSpecifier(identifier, true);
        return expression_type;
    }

}
