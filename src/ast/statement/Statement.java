package ast.statement;

import java.util.UUID;

import org.objectweb.asm.MethodVisitor;

import ast.ASTNode;
import semantics.table.SymbolTable;

public abstract class Statement extends ASTNode {

    String ID = UUID.randomUUID().toString();
    @Override public abstract String toString();
    public abstract void GenerateBytecode(MethodVisitor mv);

    public abstract void validate(SymbolTable symbolTable);

}
