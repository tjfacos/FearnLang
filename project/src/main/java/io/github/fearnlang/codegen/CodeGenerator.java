package io.github.fearnlang.codegen;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.MethodNode;

import static org.objectweb.asm.Opcodes.*;

import io.github.fearnlang.ast.Declaration;
import io.github.fearnlang.ast.Program;
import io.github.fearnlang.ast.Struct;
import io.github.fearnlang.ast.function.Function;
import io.github.fearnlang.ast.type.TypeSpecifier;
import io.github.fearnlang.semantics.table.SymbolTable;

import io.github.fearnlang.util.Reporter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;

/* CodeGenerator.java
 * 
 * This class is the primary class used to generate object code.
 * 
 * It has multiple public, static properties used during semantic
 * analysis and code generation, which is useful for ensuring data
 * is available globally, for any point in the program to access, or
 * modify if needs be. 
 * 
 *  ->  buildPath: The path to the build directory. This is set once,
 *      at the start of compilation, and used by every CodeGenerator 
 *      object, to ensure all classes are generated in the same place
 *  ->  GeneratorStack: A stack of generator objects, used to easily 
 *      reference the generator currently in use, specifically to ensure
 *      the correct program name is being used (each import will have its 
 *      own name, which becomes the name of the class generated to represent 
 *      it; to ensure that the owners of class elements refer to the correct 
 *      class, it's vital the program name of the program currently being 
 *      generated is accessible at all times).
 *  ->  GlobalSymbolTable: The symbol table for the global scope of the 
 *      program. Vital for locating details on global elements of a program
 *      (e.g. functions, structs, global variables).
 *  ->  LocalSymbolTable: The symbol table for the function currently being 
 *      generated. This is vital for getting variable descriptors and indexes.
 *  ->  CurrentReturnType: Return type for function being generated. This is
 *      used to verify the expression types for return statements.
 *  ->  loopDepth: Used during semantic analysis to ensure break/continue 
 *      statements are used within loops (when loopDepth > 0)
 *  ->  LabelStack: A stack used during code generation, to provide break/continue
 *      statements with the most recent loop labels, to reference where in the 
 *      program to jump
 */

public class CodeGenerator {
    
    // Names of Programs being generated, which is used as the identifier 
    // for the program class, are stored to a stack
    public static Stack<String> ProgramNameStack = new Stack<String>();
    private static Path buildPath = null;

    public static Path getBuildPath() {
        return buildPath;
    }

    public static void setBuildPath(Path path) {
        if (buildPath == null) {
            buildPath = path;
        } else {
            return;
        }
    }

    public static SymbolTable GlobalSymbolTable;
    public static SymbolTable LocalSymbolTable;

    public static TypeSpecifier CurrentReturnType;

    public static Integer loopDepth = 0;
    public static Stack<Label> LabelStack = new Stack<Label>();
    


    
    /** 
     * Method to generate struct class files, for each struct in a program. 
     * 
     * The method iterates thorough each struct (in a provided ArrayList) ...
     *  1)  A new class writer object to created, to write the struct class
     *  2)  A method visitor for the constructor (cv) is created, to write 
     *      the constructor method. This is created using the descriptor 
     *      from the global symbol table
     *  3)  The constructor invokes Java's default object constructor first
     *  4)  For each attribute (declaration) in the struct, add a public field
     *      to the struct class, and assigns the correct parameter of the 
     *      constructor to that property
     *  5)  Add RETURN instruction to end of constructor
     *  6)  Output byte array, representing struct class, into a new .class file
     *      in the buildPath directory
     *  7)  Report successful generation of struct class file
     * 
     * @param structs A list of structs
     */
    private static void GenerateStructs(ArrayList<Struct> structs)
    {
        structs.forEach(
            (struct) -> {
                
                ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                
                classWriter.visit(
                    V21, 
                    // Defines access
                    ACC_PUBLIC | ACC_SUPER, 
                    // Define class name 
                    // (Fearn struct class names have a $ prefix, to distinguish them from program files)
                    "$"+struct.identifier, 
                    null, 
                    // All Java objects derive from the base Object class 
                    "java/lang/Object", 
                    null
                );

                MethodVisitor cv = classWriter.visitMethod(
                    ACC_PUBLIC, 
                    "<init>", // `<init>` indicates the constructor
                    GlobalSymbolTable.GetGlobalStructDescriptor(struct.identifier), 
                    null, 
                    null
                );

                cv.visitCode();

                // Invoke default constructor
                cv.visitVarInsn(ALOAD, 0);
                cv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);

                Integer i = 0;

                for (Declaration decl : struct.declarations)
                {

                    // Create public field for attribute
                    String descriptor = SymbolTable.GenBasicDescriptor(decl.type);
                    classWriter.visitField(
                        ACC_PUBLIC, 
                        decl.identifier, 
                        descriptor, 
                        null, 
                        null
                    );  
    
                    // Generate Constructor Instructions that take in an argument, 
                    // and load it into the attribute, specified by decl
                    
                    // Load 'this' (the struct instance itself) to operand stack
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
    
    /** 
     * Method to generate program class files 
     *  1)  Use a class writer to create a public, static class, representing the program
     *  2)  Create a static block (<clinit>), to define the default states of global variables
     *  3)  For each global variable in the program ...
     *      ->  Add a public, static field to the class
     *      ->  If an initialisation expression has been provided, generate the expression's 
     *          bytecode (putting the value of the expression at the top of the operand stack), 
     *          and put the value into the field of the program class
     *  4)  Add a default constructor, which simply invokes the default object constructor
     *  5)  For each function in the program ...
     *      ->  Add a method representing it to the program class (uses SymbolTable to get method 
     *          descriptor), and create a FearnMethodVisitor to generate code for it
     *      ->  Create a MethodNode for the function (effectively a list of bytecode instructions)
     *      ->  Set LocalSymbolTable to the symbol table for this function
     *      ->  Loop through all local variable indexes, initialising them all to null
     *      ->  Generate function body bytecode, writing the instructions to the MethodNode
     *      ->  Eliminate Redundant Casting in the MethodNode (see CastOptimiser.java)
     *      ->  Use the FearnMethodVisitor to visit every instruction in the MethodNode, adding 
     *          them to the actual class method
     *  6)  Write program class to .class file
     *  7)  Report Success
     * 
     * @param functions A list of functions
     * @param global_declarations A list of global declarations
     * @param finalProgramPath The path to the program class file
     */

    private static void GenerateProgram(
        ArrayList<Function> functions, 
        ArrayList<Declaration> global_declarations, 
        Path finalProgramPath
    ) {
        
        // Create new class writer, to write program class
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

        // Class is a public, static class, with its name being the programName
        classWriter.visit(
            V21,
            ACC_PUBLIC | ACC_SUPER,
            ProgramNameStack.peek(),
            null, 
            "java/lang/Object", 
            null
        );
        

        // Generate Initial State of Global Variables (sv = StateVisitor)
        MethodVisitor sv = classWriter.visitMethod(
            ACC_STATIC, 
            "<clinit>",  // Indicates static block
            "()V", // Takes no arguments, and has void return type
            null, 
            null
        );
        
        MethodNode state_node = new MethodNode();
        
        // Begin Defining Code
        state_node.visitCode();
        
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
                
                decl.init_expression.GenerateBytecode(state_node);
                state_node.visitFieldInsn(
                    PUTSTATIC, 
                    ProgramNameStack.peek(), 
                    decl.identifier, 
                    SymbolTable.GenBasicDescriptor(decl.type)
                );
            }
        );
        
        // Add return instruction
        state_node.visitInsn(RETURN);

        // End Static Block Generation
        state_node.visitMaxs(0, 0);
        state_node.visitEnd();

        // Eliminate redundant casting
        CastOptimiser.EliminateRedundantCasts(state_node);

        // Write all instructions to method visitor
        state_node.accept(sv);

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
            // Create new FearnMethodVisitor (see FearnMethodVisitor.java)
            // Also, visit new public static method with class writer
            // Gets Function method descriptor from symbol table to do this
            MethodVisitor function_visitor = new FearnMethodVisitor(ASM9, 
                classWriter.visitMethod(
                    ACC_PUBLIC | ACC_STATIC, 
                    function.identifier, 
                    SymbolTable.GenFuncDescriptor(function.parameters, function.return_type), 
                    null, 
                    null
                )
            );

            // Create MethodNode (a derived class of a method visitor, that's 
            // effectively a list of instruction nodes, that can be iterated 
            // through and optimised)
            MethodNode function_node = new MethodNode(ASM9);

            // Set Local Symbol Table
            LocalSymbolTable = GlobalSymbolTable.GetFuncSymbolTable(function.identifier);

            function_node.visitCode();
            
            // Initialise all local variables to null
            // Needs to start at function.parameters.size() to prevent nullifying the values of parameters
            for (int i = function.parameters.size(); i < LocalSymbolTable.GetAllVarTypeSpecifiers().size(); i++)
            {
                // Load null value
                function_node.visitInsn(ACONST_NULL);
                // Assign value to variable at index i
                function_node.visitVarInsn(ASTORE, i);
            }

            // Generate body of bytecode
            function.body.GenerateBytecode(function_node);

            // Add a return instruction if void, and no return statement already included
            if (function.is_void && !function.body.includesReturn) function_node.visitInsn(RETURN);
            
            // End Function Generation
            function_node.visitMaxs(0, 0);
            function_node.visitEnd();

            // Eliminate redundant casting
            CastOptimiser.EliminateRedundantCasts(function_node);

            // Write all instructions to method visitor
            function_node.accept(function_visitor);
        }
                    
        classWriter.visitEnd();
        
        // Write program .class file
        try {
            Files.write(finalProgramPath, classWriter.toByteArray());
        } catch (IOException e) {
            Reporter.ReportErrorAndExit("Program Gen Error :- " + e.toString(), null);;
        }
        
        // Report Success
        Reporter.ReportSuccess(
            "GENERATED Program     : "+ finalProgramPath.toAbsolutePath() + ";", 
            false
        );
            
    }

    // Generate Program, from AST root and global SymbolTable
    public static void Generate(Program root, SymbolTable symTab)
    {

        GlobalSymbolTable = symTab;

        // Initialise LocalSymbolTable to an empty table
        LocalSymbolTable = new SymbolTable();

        // Get path to program class file
        Path finalProgramPath = buildPath.resolve(ProgramNameStack.peek() + ".class").toAbsolutePath();

        // Create new build directory
        File dir = new File(buildPath.toString());
        dir.mkdir();

        // Generate Class files to represent structs
        GenerateStructs(root.structs);

        // Generate Program Class (defines globals and functions)
        GenerateProgram(
            root.functions, 
            root.global_declarations,
            finalProgramPath
        );
    }
}