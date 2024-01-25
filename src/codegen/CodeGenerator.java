package codegen;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.MethodNode;

import static org.objectweb.asm.Opcodes.*;

import ast.Declaration;
import ast.Program;
import ast.Struct;
import ast.function.Function;
import ast.type.TypeSpecifier;
import semantics.table.SymbolTable;

import util.Reporter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;

public class CodeGenerator {
    
    public static Path buildPath;

    public String programName;
    
    public static Stack<CodeGenerator> generatorStack = new Stack<>();

    public static SymbolTable GlobalSymbolTable;
    public static SymbolTable LocalSymbolTable;
    public static TypeSpecifier CurrentReturnType;
    public static String CurrentFuncIdentifier;

    public static Integer loopDepth = 0;

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
    
                        
                    // Generate Constructor Instructions that take in an argument, and load it into the attribute, specified by decl
                    
                    // Load 'this' to operand stack
                    cv.visitVarInsn(ALOAD, 0);
                    
                    // Load argument to operand stack
                    cv.visitVarInsn(ALOAD, ++i);
                    
                    // Assign argument to Field
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
                    Reporter.ReportErrorAndExit("Struct Gen Error :- " + e.toString(), null);;
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
            generatorStack.peek().programName,
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
                    generatorStack.peek().programName, 
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


        for (Function function : functions)
        {
            MethodVisitor function_visitor = new FearnMethodVisitor(ASM9, 
                classWriter.visitMethod(
                    ACC_PUBLIC | ACC_STATIC, 
                    function.identifier, 
                    SymbolTable.GenFuncDescriptor(function.parameters, function.return_type), 
                    null, 
                    null
                )
            );

            MethodNode function_node = new MethodNode(ASM9);
            
            LocalSymbolTable = GlobalSymbolTable.GetFuncSymbolTable(function.identifier);
            CurrentFuncIdentifier = function.identifier;

            function_node.visitCode();

            
            // Initialise all local variables to null
            for (int i = function.parameters.size(); i < LocalSymbolTable.GetAllVarTypeSpecifiers().size(); i++)
            {
                function_node.visitInsn(ACONST_NULL);
                function_node.visitVarInsn(ASTORE, i);
            }


            function.body.GenerateBytecode(function_node);

            // Add a return instruction if void, and no return statement 
            // already included
            if (function.is_void && !function.body.includesJump)
            {
                function_node.visitInsn(RETURN);
            }
            
            // End Function Generation
            function_node.visitMaxs(0, 0);
            function_node.visitEnd();

            // Eliminate redundant casting
            CastOptimiser.EliminateRedundantCasts(function_node);

            // Write all instructions to method visitor
            function_node.accept(function_visitor);
        }
                    
        classWriter.visitEnd();
        
        byte[] bytecode = classWriter.toByteArray();

        try {
            Files.write(finalProgramPath, bytecode);
        } catch (IOException e) {
            Reporter.ReportErrorAndExit("Program Gen Error :- " + e.toString(), null);;
        }
        
        Reporter.ReportSuccess(
            "GENERATED Program     : "+ finalProgramPath.toAbsolutePath() + ";", 
            false
        );
            
    }

    
    public void Generate(Program root, SymbolTable symTab)
    {

        GlobalSymbolTable = symTab;

        Path finalProgramPath = buildPath.resolve(generatorStack.peek().programName + ".class").toAbsolutePath();

        File dir = new File(buildPath.toString());

        if (dir.exists()) dir.delete();
        dir.mkdir();

        // Generate Class files to represent structs
        GenerateStructs(root.structs);

        
        // Generate Main Program Class (defines globals and functions)
        GenerateMainProgram(
            root.functions, 
            root.global_declarations,
            finalProgramPath
        );

    }


    public void SetBuildPath(String path) {
        buildPath = Paths.get(path).toAbsolutePath().getParent().resolve("build");
    }
    
    public void SetProgramName(String path)
    {
        programName = Paths.get(path).getFileName().toString().replace(".fearn", "");
    }
}