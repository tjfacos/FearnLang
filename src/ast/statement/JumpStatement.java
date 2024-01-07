package ast.statement;

import org.objectweb.asm.MethodVisitor;

import semantics.table.SymbolTable;

public class JumpStatement extends Statement {
    public enum JumpType 
    {
        Continue,
        Break,
        Return
    }

    public JumpType type;

    public JumpStatement (JumpType t)
    {
        type = t;
    }

    @Override public String toString()
    {
        return "\n\t" + type.name() + ";";
    }

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GenerateBytecode'");
    }

    public void validate(SymbolTable symbolTable) {
        // TODO VerifyType
    }

}
