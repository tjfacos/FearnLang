package codegen;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import ast.Declaration;
import ast.Program;
import ast.Struct;
import ast.function.Function;
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
import java.util.Objects;

// TODO : SYMBOL TABLE (NOW)

public class CodeGenerator {
    
    
    private Path buildPath;

    private String GetTypeDescriptor(TypeSpecifier typeSpecifier)
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

    
    private void GenerateStructs(ArrayList<Struct> structs)
    {
        structs.forEach(
            (struct) -> {
                
                ClassWriter cw = new ClassWriter(0);
                
                cw.visit(
                    V1_8, 
                    ACC_PUBLIC + ACC_SUPER, 
                    "$"+struct.identifer, 
                    null, 
                    "java/lang/Object", 
                    null
                );
                    
                MethodVisitor stateVisitor = cw.visitMethod(
                    ACC_STATIC, 
                    "<clinit>", 
                    "()V", 
                    null, 
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

                        if (decl.init_expression == null) { return; }
                
                        decl.init_expression.GenerateBytecode(stateVisitor);
                        stateVisitor.visitFieldInsn(PUTFIELD, "$"+struct.identifer, decl.identifer, GetTypeDescriptor(decl.type));
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
    
    
    private void GenerateMainProgram(ArrayList<Function> functions, ArrayList<Declaration> global_declarations, Path finalProgramPath) {
        
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        String ProgramName = finalProgramPath.getFileName().toString().replace(".class", "");


        cw.visit(
            V1_8,
            ACC_PUBLIC + ACC_SUPER,
            ProgramName,
            null, 
            "java/lang/Object", 
            null
        );
            
        // Generate Constructor for Global Variables
        MethodVisitor constructVisitor = cw.visitMethod(
            ACC_STATIC, 
            "<clinit>", 
            "()V", 
            null, 
            null
        );
        
        // Begin Defining Code
        constructVisitor.visitCode();
        
        // Add Global Declarations as public fields
        global_declarations.forEach(
            (decl) -> {
                cw.visitField(
                    ACC_PUBLIC + ACC_STATIC, 
                    decl.identifer, 
                    GetTypeDescriptor(decl.type), 
                    null, 
                    null
                );

                if (decl.init_expression == null) { return; }
                
                decl.init_expression.GenerateBytecode(constructVisitor);
                constructVisitor.visitFieldInsn(PUTSTATIC, ProgramName, decl.identifer, GetTypeDescriptor(decl.type));

            }
        );
        
        constructVisitor.visitInsn(RETURN);

        // End Constructor Generation
        constructVisitor.visitMaxs(0, 0);
        constructVisitor.visitEnd();
                
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
        cw.visitEnd();
        
        
        
        
        
        
        
        try {
            Files.write(finalProgramPath, cw.toByteArray());
        } catch (IOException e) {
            Reporter.ReportErrorAndExit("Program Gen Error :- " + e.toString(), 30);;
        }
        
        Reporter.ReportSuccess(
            "GENERATED Program     : "+ finalProgramPath.toAbsolutePath() + ";", 
            false
            );
            
        }
        





    public void Generate(Program root, String sPath)
    {

        Path srcPath = Paths.get(sPath).getParent();
        String mainProgramName = Paths.get(sPath).getFileName().toString();
        buildPath = Paths.get(srcPath.toString(), "build");

        Path finalProgramPath = Paths.get( buildPath.toString(), mainProgramName.replace(".fearn", ".class")).toAbsolutePath();

        File dir = new File(buildPath.toString());
        dir.mkdir();
        
        for (File file: Objects.requireNonNull(dir.listFiles())) {
            file.delete();
        }


        // Generate Class files to represent structs
        GenerateStructs(root.structs);

        // Generate Main Program Class (defines globals and functions)
        GenerateMainProgram(
            root.functions, 
            root.global_declarations,
            finalProgramPath
        );

        Reporter.ReportSuccess(
            String.format(
                "Compilation Successful! \n\t -> Run `java %s` to run Program", 
                finalProgramPath
            ), 
            true
        );

    }
        
}