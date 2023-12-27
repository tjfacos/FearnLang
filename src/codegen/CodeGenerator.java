package codegen;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import ast.Program;

import static org.objectweb.asm.Opcodes.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CodeGenerator {
    
    private ClassWriter cw = new ClassWriter(0);

    public void Generate(Program root, String srcPath) throws IOException
    {
        Path p = Paths.get(srcPath.replace(".fearn", ".class"));
        Files.write(p, serializeToBytes(new File(srcPath).getName().replace(".fearn", "")));
    }

    byte[] serializeToBytes(String outputClassName) 
    {
        cw.visit(
            V1_8, 
            ACC_PUBLIC + ACC_SUPER, 
            outputClassName,
            null, 
            "java/lang/Object", 
            null
        );
        
        // addStandardConstructor();
        addMainMethod();
        cw.visitEnd();
        return cw.toByteArray();
    }

    void addStandardConstructor() {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }

    void addMainMethod() {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mv.visitCode();
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("Hello World!");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(3, 3);
        mv.visitEnd();
    }

}
