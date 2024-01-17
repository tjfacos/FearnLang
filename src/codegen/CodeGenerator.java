package codegen;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.MethodNode;

import static org.objectweb.asm.Opcodes.*;

import ast.Declaration;
import ast.Program;
import ast.Struct;
import ast.function.Function;
import ast.type.TypeSpecifier;
import optimisation.NodeOptimiser;
import semantics.table.SymbolTable;

import util.Reporter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

public class CodeGenerator {
    
    private Path buildPath;

    
    public static String mainProgramName;
    public static SymbolTable GlobalSymbolTable;
    public static SymbolTable LocalSymbolTable;
    public static TypeSpecifier CurrentReturnType;
    public static String CurrentFuncIdentifier;

    public static Stack<Label> LabelStack = new Stack<Label>();

      
    private void GenerateStructs(ArrayList<Struct> structs)
    {
        structs.forEach(
            (struct) -> {
                
                ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                
                classWriter.visit(
                    V19, 
                    ACC_PUBLIC | ACC_SUPER, 
                    "$"+struct.identifier, 
                    null, 
                    "java/lang/Object", 
                    null
                );

                MethodVisitor cv = classWriter.visitMethod(
                    ACC_PUBLIC, 
                    "<init>", 
                    GlobalSymbolTable.GetGlobalStructDescriptor(struct.identifier), 
                    null, 
                    null
                );

                cv.visitCode();
                cv.visitVarInsn(ALOAD, 0);
                cv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);

                Integer i = 0;

                for (Declaration decl : struct.declarations)
                {
                    String descriptor = SymbolTable.GenBasicDescriptor(decl.type);

                    classWriter.visitField(
                        ACC_PUBLIC, 
                        decl.identifier, 
                        descriptor, 
                        null, 
                        null
                    );  
    
                        
                    // Generate Constructor Instructions that take in an arguement, and load it into the attribute, specified by decl
                    
                    // Load 'this' to operand stack
                    cv.visitVarInsn(ALOAD, 0);
                    
                    // Load Arguement to operand stack
                    cv.visitVarInsn(ALOAD, ++i);
                    
                    // Assign Arguement to Field
                    cv.visitFieldInsn(
                        PUTFIELD, 
                        "$"+struct.identifier, 
                        decl.identifier, 
                        SymbolTable.GenBasicDescriptor(decl.type)
                    );
                }

                cv.visitInsn(RETURN);
                cv.visitMaxs(0, 0);
                cv.visitEnd();
                
                classWriter.visitEnd();
                
                Path destination = Paths.get(buildPath.toString(), String.format("$%s.class", struct.identifier));
                    
                        
                try {
                    Files.write(destination, classWriter.toByteArray());
                } catch (IOException e) {
                    Reporter.ReportErrorAndExit("Struct Gen Error :- " + e.toString());;
                }
                
                Reporter.ReportSuccess(
                    "GENERATED Struct File : "+ destination.toAbsolutePath() + ";", 
                    false
                );                    
            }
        
        );   
    }
    
    
    private void GenerateMainProgram(ArrayList<Function> functions, ArrayList<Declaration> global_declarations, Path finalProgramPath) {
        
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);


        classWriter.visit(
            V19,
            ACC_PUBLIC | ACC_SUPER,
            mainProgramName,
            null, 
            "java/lang/Object", 
            null
        );
            
        // Generate Initial State of Global Variables (sv = StateVisitor)
        MethodVisitor sv = classWriter.visitMethod(
            ACC_STATIC, 
            "<clinit>", 
            "()V", 
            null, 
            null
        );
        
        // Begin Defining Code
        sv.visitCode();

        // Add Global Declarations as public fields
        global_declarations.forEach(
            (decl) -> {
                classWriter.visitField(
                    ACC_PUBLIC | ACC_STATIC, 
                    decl.identifier, 
                    SymbolTable.GenBasicDescriptor(decl.type), 
                    null, 
                    null
                );

                if (decl.init_expression == null) { return; }
                
                decl.init_expression.GenerateBytecode(sv);
                sv.visitFieldInsn(
                    PUTSTATIC, 
                    mainProgramName, 
                    decl.identifier, 
                    SymbolTable.GenBasicDescriptor(decl.type)
                );
            }
        );
        
        sv.visitInsn(RETURN);

        // End Constructor Generation
        sv.visitMaxs(0, 0);
        sv.visitEnd();


        // Generate Default Constructor
        MethodVisitor cv = classWriter.visitMethod(
            ACC_PUBLIC, 
            "<init>", 
            "()V", 
            null, 
            null
        );
        
        
        cv.visitVarInsn(ALOAD, 0);
        cv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        cv.visitInsn(RETURN);
        cv.visitMaxs(0, 0);
        cv.visitEnd();

            
        // Write Functions as Methods


        for (Function func : functions)
        {
            
            MethodNode fnNode = new MethodNode(
                ACC_PUBLIC | ACC_STATIC, 
                func.identifier, 
                SymbolTable.GenFuncDescriptor(func.parameters, func.return_type), 
                null, 
                null
            );
            
            
            LocalSymbolTable = GlobalSymbolTable.GetFuncSymbolTable(func.identifier);
            CurrentFuncIdentifier = func.identifier;

            
            // Initialise all local variables to null
            for (int i = func.parameters.size(); i < LocalSymbolTable.GetAllVarTypeSpecifiers().size(); i++)
            {
                fnNode.visitInsn(ACONST_NULL);
                fnNode.visitVarInsn(ASTORE, i);
            }


            func.body.GenerateBytecode(fnNode);

            // Add a return instruction if void, and no return statement 
            // already included
            if (func.is_void && !func.body.includesJump)
            {
                fnNode.visitInsn(RETURN);
            }
            
            // End Function Generation
            fnNode.visitMaxs(0, 0);
            fnNode.visitEnd();
            
            new NodeOptimiser().Optimise(fnNode);


            MethodVisitor fnVisitor = classWriter.visitMethod(
                ACC_PUBLIC | ACC_STATIC, 
                func.identifier, 
                SymbolTable.GenFuncDescriptor(func.parameters, func.return_type), 
                null, 
                null
            );


            fnNode.accept(fnVisitor);
            
        }
                    
        classWriter.visitEnd();
        
        byte[] bytecode = classWriter.toByteArray();

        try {
            Files.write(finalProgramPath, bytecode);
        } catch (IOException e) {
            Reporter.ReportErrorAndExit("Program Gen Error :- " + e.toString());;
        }
        
        Reporter.ReportSuccess(
            "GENERATED Program     : "+ finalProgramPath.toAbsolutePath() + ";", 
            false
        );
            
    }

    
    public void Generate(Program root, SymbolTable symTab, String sPath)
    {

        GlobalSymbolTable = symTab;

        Path srcParent = Paths.get(sPath).toAbsolutePath().getParent();
        mainProgramName = Paths.get(sPath).getFileName().toString().replace(".fearn", "");
        buildPath = Paths.get(srcParent.toString(), "build");

        Path finalProgramPath = Paths.get( buildPath.toString(), mainProgramName + ".class" ).toAbsolutePath();

        File dir = new File(buildPath.toString());
        dir.mkdir();
        
        for (File file: Objects.requireNonNull(dir.listFiles())) {
            file.delete();
        }


        // Copy Runtime file to build directory
        try {
            Path RuntimePath = Paths.get(System.getenv("FEARNPATH"), "runtime", "FearnRuntime.class");
            Files.copy(RuntimePath, buildPath.resolve(Paths.get("FearnRuntime.class")));
        } catch (Exception e) {
            Reporter.ReportErrorAndExit("Error Copying Runtime to build directory. Please define FEARNPATH at Compiler root.");;
        }


        // Generate Class files to represent structs
        GenerateStructs(root.structs);

        
        // Generate Main Program Class (defines globals and functions)
        GenerateMainProgram(
            root.functions, 
            root.global_declarations,
            finalProgramPath
        );

        Path parent = Paths.get(sPath).getParent();
        String pathString;

        if (parent != null) pathString = parent.resolve("build").toString();
        else pathString = "build";

        Reporter.ReportSuccess(
            String.format(
                "Compilation Successful! \n\t -> Run `cd %s ; FearnRun %s [args...]` to run Program", 
                pathString,
                mainProgramName
            ), 
            true
        );
    }
}