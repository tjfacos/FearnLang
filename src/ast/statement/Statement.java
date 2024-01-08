package ast.statement;

import java.util.ArrayList;
import java.util.UUID;

import org.objectweb.asm.MethodVisitor;

import ast.ASTNode;
import ast.type.TypeSpecifier;
import codegen.CodeGenerator;
import semantics.table.SymbolTable;

public abstract class Statement extends ASTNode {

    String ID = UUID.randomUUID().toString();
    @Override public abstract String toString();
    public abstract void GenerateBytecode(MethodVisitor mv);

    public abstract void validate(SymbolTable symbolTable);

    protected Object[] GetLocalDecriptors()
    {
        ArrayList<String> descriptors = new ArrayList<String>();
        
        for (TypeSpecifier spec : CodeGenerator.LocalSymbolTable.GetAllVarTypeSpecifiers())
        {
                String desc = SymbolTable.GenBasicDescriptor(spec);
                
                if (!desc.startsWith("["))
                {
                    desc = desc.substring(1, desc.length() - 1);
                }
                
                descriptors.add(desc);
        }

        Object[] locals = descriptors.toArray();
        
        return locals;
    }

}
