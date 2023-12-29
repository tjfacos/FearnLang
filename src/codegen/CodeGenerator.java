package codegen;

import org.objectweb.asm.ClassWriter;

import ast.Program;
import ast.Struct;
import ast.type.ArraySpecifier;
import ast.type.PrimitiveDataType;
import ast.type.PrimitiveSpecifier;
import ast.type.StructInstanceSpecifier;
import ast.type.TypeSpecifier;
import util.Reporter;

import static org.objectweb.asm.Opcodes.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CodeGenerator {
    
    
    private Path buildPath;

    String GetTypeDescriptor(TypeSpecifier typeSpecifier)
    {
        
        String type_descriptor = "";

        if (typeSpecifier.getClass() == PrimitiveSpecifier.class)
        {
            switch ( ((PrimitiveSpecifier)typeSpecifier).element_type ) {
                case PrimitiveDataType.INT  : type_descriptor += "Ljava/lang/Integer;"      ; break;               
                case PrimitiveDataType.FLOAT: type_descriptor += "Ljava/lang/Float;"        ; break;               
                case PrimitiveDataType.STR  : type_descriptor += "Ljava/lang/String;"       ; break;               
                case PrimitiveDataType.BOOL : type_descriptor += "Ljava/lang/Boolean;"      ; break;                           
                default: break;
            }
        }
        
        else if (typeSpecifier.getClass() == ArraySpecifier.class)
        {
            type_descriptor += new String( new char[ ((ArraySpecifier)typeSpecifier ).dimensions ] ).replace("\0", "[");
            type_descriptor += GetTypeDescriptor(((ArraySpecifier)typeSpecifier).element_type);
        }
        
        
        else // Struct Instance
        {
            type_descriptor += "L$" + ( (StructInstanceSpecifier)typeSpecifier ).name + ";";
        }

        return type_descriptor;

    }


    public void Generate(Program root, String sPath)
    {

        Path srcPath = Paths.get(sPath).getParent();
        String mainProgramName = Paths.get(sPath).getFileName().toString();
        buildPath = Paths.get(srcPath.toString(), "build");

        Path finalProgramPath = Paths.get( buildPath.toString(), mainProgramName.replace(".fearn", ".class")).toAbsolutePath();

        File dir = new File(buildPath.toString());
        if ( !dir.exists() )
        {
            dir.mkdir();
        }

        // Generate Class files to represent structs
        GenerateStructs(root.structs);

        // Generate Main Program Class (defines globals and functions)
        // GenerateMainProgram(
        //     root.functions, 
        //     root.global_declarations,
        //     finalProgramPath
        // );

        Reporter.ReportSuccess(
            String.format(
                "Compilation Successful! \n\t -> Run `java %s` to run Program", 
                finalProgramPath
            ), 
            true
        );

    }

    void GenerateStructs(ArrayList<Struct> structs)
    {
        ClassWriter cw = new ClassWriter(0);

        structs.forEach(
            (struct) -> {
                
                cw.visit(
                    V1_8, 
                    ACC_PUBLIC + ACC_SUPER, 
                    "$"+struct.identifer, 
                    null, 
                    "java/lang/Object", 
                    null
                );

                struct.declarations.forEach(
                    (decl) -> {
                        cw.visitField(
                            ACC_PUBLIC, 
                            decl.identifer, 
                            GetTypeDescriptor(decl.type), 
                            null, 
                            null
                        );
                    
                        System.out.println(GetTypeDescriptor(decl.type));


                    }

                );

                cw.visitEnd();

                Path destination = Paths.get(buildPath.toString(), String.format("$%s.class", struct.identifer));
                
                
                try {
                    Files.write(destination, cw.toByteArray());
                } catch (IOException e) {
                    Reporter.ReportErrorAndExit("Struct Gen Error :- " + e.toString(), 30);;
                }

                Reporter.ReportSuccess(
                    "GENERATED Struct File : "+ destination.toAbsolutePath() + ";", 
                    false
                );
                
            }
        );   
    }
    

}
