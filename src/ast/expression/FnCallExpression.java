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

/* FnCallExpression.java
 * 
 * Represents a Function Call in the AST. 
 * 
 * Fields:
 *  ->  identifier: Function identifier 
 *  ->  arguments: The expressions used as arguments in the function call
 */

public class FnCallExpression extends Expression {
    
    public String identifier;
    public ArrayList<Expression> arguments;
    
    // Flag used to indicate if a function is written in Universal Function Notation
    // e.g. x.myFunction()
    // It is only used in toString()
    public Boolean isUFN = false;

    // List of built-in functions
    // These aren't added to the SymbolTable simply because they take
    // multiple data types as input (e.g. length() take 1 argument, 
    // which is either an array or a string)
    static List<String> builtins = Arrays.asList("length", "slice");

    public FnCallExpression(String fn_name, ArrayList<Expression> args)
    {
        identifier = fn_name;
        arguments = args;
    }
    
    @Override
    public String toString()
    {
        if (isUFN) 
        {
            String str_rep = arguments.get(0).toString() + "." + identifier;
            arguments.remove(0);
            str_rep += arguments.toString()
                .replace("[", "(")
                .replace("]", ")");
            return str_rep;
        }
        else 
            return identifier + arguments.toString().replace("[", "(").replace("]", ")");
    }
    
    /* To generate bytecode, generate all arguments.
     * 
     * Then, if the function call is one of the built-ins, set the descriptor to the 
     * correct value, and call the method, using INVOKESTATIC and the FearnRuntime.
     *  ->  If slice is used, returned array must be cast back, using the array's type 
     *      descriptor
     * 
     * Otherwise, use INVOKESTATIC, using the identifier, descriptor, and owner from 
     * the SymbolTable.
     * 
     */
    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        
        
        // Gen args, then INVOKESTATIC
        for (Expression arg : arguments) arg.GenerateBytecode(mv);
        
        String desc;
        
        switch (identifier){

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
            CodeGenerator.GlobalSymbolTable.GetOwner(identifier, true), 
            identifier, 
            desc, 
            false
        );
        

    }

    /* To validate...
     *  ->  If the function is one of the built-ins, check the number of arguments and
     *      their types, raising an error if necessary
     *  ->  Otherwise, using the parameters from the SymbolTable to validate the 
     *      arguments (number and types)
     *  ->  Set expression_type to return type of function, and return this
     * 
     */
    @Override
    public TypeSpecifier validate(SymbolTable symTable) {
        
        // Check the function's signature (Parameters from Symbol Table) against to types of each argument

        ArrayList<TypeSpecifier> arg_types = new ArrayList<TypeSpecifier>();
        
        switch (identifier) {

            // length(<str|arr>)
            case "length":
                if (arguments.size() != 1)
                {
                    Reporter.ReportErrorAndExit("Wrong number of arguments for " + identifier + " , expected 1.", this);
                }

                if (!(
                    arguments.get(0).validate(symTable).equals(new PrimitiveSpecifier(PrimitiveDataType.STR)) ||
                    arguments.get(0).validate(symTable) instanceof ArraySpecifier
                ))
                {
                    Reporter.ReportErrorAndExit("Wrong argument data type, expected string or array.", this);
                }

                expression_type =  new PrimitiveSpecifier(PrimitiveDataType.INT);
                return expression_type;



            // slice(<str|arr>, int, int);
            case "slice":
                if (arguments.size() != 3)
                {
                    Reporter.ReportErrorAndExit("Wrong number of arguments for " + identifier + " , expected 3.", this);
                }

                if (!(
                    arguments.get(0).validate(symTable).equals(new PrimitiveSpecifier(PrimitiveDataType.STR)) ||
                    arguments.get(0).validate(symTable) instanceof ArraySpecifier
                ))
                {
                    Reporter.ReportErrorAndExit("Wrong argument data type, expected string or array.", this);
                }

                if (!(
                    arguments.get(1).validate(symTable).equals(new PrimitiveSpecifier(PrimitiveDataType.INT)) ||
                    arguments.get(2).validate(symTable).equals(new PrimitiveSpecifier(PrimitiveDataType.INT))
                ))
                {
                    Reporter.ReportErrorAndExit("Wrong argument data type, expected int.", this);
                }

                expression_type =  arguments.get(0).expression_type;
                return expression_type;

                


            default:
                break;
        }

        ArrayList<TypeSpecifier> param_types = CodeGenerator.GlobalSymbolTable.GetFuncParameterSpecifiers(identifier);

        for (Expression arg : arguments)
        {
            arg_types.add(arg.validate(symTable));
        }

        if (arguments.size() != param_types.size())
        {
            Reporter.ReportErrorAndExit(
                "Wrong number of arguments for " + identifier + " , expected" + param_types.size(),
                this
            );
        }

        for (int i = 0; i < param_types.size(); i++)
        {
            if (!param_types.get(i).equals(arg_types.get(i)))
            {
                Reporter.ReportErrorAndExit(
                    arguments.get(i).toString() + " is of the wrong type, expected " + param_types.get(i).toString(), 
                    this
                );
            }
        }

        expression_type =  CodeGenerator.GlobalSymbolTable.GetTypeSpecifier(identifier, true);
        return expression_type;
    }

}
