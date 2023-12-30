package codegen;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;

import ast.Declaration;
import ast.Program;
import ast.Struct;
import ast.function.Function;

import semantics.table.SymbolTable;

import util.Reporter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class CodeGenerator {
    
    private Path buildPath;

    // Empty stack indicates global scope
    public static String currentFunc = null;
    
    public static String mainProgramName;
    public static SymbolTable GlobalSymbolTable;
    public static SymbolTable LocalSymbolTable;

      
    private void GenerateStructs(ArrayList<Struct> structs)
    {
        structs.forEach(
            (struct) -> {
                
                ClassWriter cw = new ClassWriter(0);
                
                cw.visit(
                    V1_8, 
                    ACC_PUBLIC + ACC_SUPER, 
                    "$"+struct.identifier, 
                    null, 
                    "java/lang/Object", 
                    null
                );

                MethodVisitor constructorVisitor = cw.visitMethod(
                    ACC_PUBLIC, 
                    "<init>", 
                    SymbolTable.GenStructConstructor(struct.declarations), 
                    null, 
                    null
                );

                constructorVisitor.visitCode();

                Integer i = 0;

                for (Declaration decl : struct.declarations)
                {
                    String descriptor = SymbolTable.GenBasicDescriptor(decl.type);

                    cw.visitField(
                        ACC_PUBLIC, 
                        decl.identifier, 
                        descriptor, 
                        null, 
                        null
                    );  
    
                        
                    // Generate Constructor Instructions that take in an arguement, and load it into the attribute, specified by decl
                    
                    // Load 'this' to operand stack
                    constructorVisitor.visitVarInsn(ALOAD, 0);
                    
                    // Load Arguement to operand stack
                    constructorVisitor.visitVarInsn(ALOAD, ++i);
                    
                    // Assign Arguement to Field
                    constructorVisitor.visitFieldInsn(
                        PUTFIELD, 
                        "$"+struct.identifier, 
                        decl.identifier, 
                        mainProgramName
                    );

                }

                constructorVisitor.visitInsn(RETURN);
                constructorVisitor.visitEnd();
                
                cw.visitEnd();
                
                Path destination = Paths.get(buildPath.toString(), String.format("$%s.class", struct.identifier));
                    
                        
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

        cw.visit(
            V1_8,
            ACC_PUBLIC + ACC_SUPER,
            mainProgramName,
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
                    decl.identifier, 
                    SymbolTable.GenBasicDescriptor(decl.type), 
                    null, 
                    null
                );

                if (decl.init_expression == null) { return; }
                
                decl.init_expression.GenerateBytecode(constructVisitor);
                constructVisitor.visitFieldInsn(
                    PUTSTATIC, 
                    mainProgramName, 
                    decl.identifier, 
                    SymbolTable.GenBasicDescriptor(decl.type)
                );

            }
        );
        
        constructVisitor.visitInsn(RETURN);

        // End Constructor Generation
        constructVisitor.visitMaxs(0, 0);
        constructVisitor.visitEnd();
                
                    
        // Write Functions as Methods
        for (Function func : functions)
        {
            MethodVisitor fnVisitor = cw.visitMethod(
                ACC_PUBLIC + ACC_STATIC, 
                func.identifier, 
                SymbolTable.GenFuncDescriptor(func.parameters, func.return_type), 
                null, 
                null
            );
            
            LocalSymbolTable = GlobalSymbolTable.GetFuncSymbolTable(func.identifier);

            fnVisitor.visitCode();

            func.body.GenerateBytecode(fnVisitor);
            
            // End Function Generation
            constructVisitor.visitMaxs(0, 0);
            fnVisitor.visitEnd();
        }
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
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
        

    public void Generate(Program root, SymbolTable symTab, String sPath)
    {

        GlobalSymbolTable = symTab;

        Path srcParent = Paths.get(sPath).getParent();
        mainProgramName = Paths.get(sPath).getFileName().toString().replace(".fearn", "");
        buildPath = Paths.get(srcParent.toString(), "build");

        Path finalProgramPath = Paths.get( buildPath.toString(), mainProgramName + ".class" ).toAbsolutePath();

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