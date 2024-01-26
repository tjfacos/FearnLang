package ast.expression;

import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;

import ast.type.StructInstanceSpecifier;
import ast.type.TypeSpecifier;
import codegen.CodeGenerator;
import semantics.table.SymbolTable;
import util.Reporter;

public class StructAttrExpression extends Expression {
    
    public Expression instance;
    public String attribute;

    public String struct_name;
    public String attr_descriptor;
    
    public StructAttrExpression(Expression n, String attr)
    {
        instance = n;
        attribute = attr;
    }

    @Override
    public String toString()
    {
        return  instance.toString() + "." + attribute.toString();
    }

    @Override
    public void GenerateBytecode(MethodVisitor mv) {
        // Gen instance, then GETFIELD
        instance.GenerateBytecode(mv);

        mv.visitFieldInsn(
            GETFIELD, 
            "$" + struct_name, 
            attribute, 
            attr_descriptor
        );
    }

    @Override
    public TypeSpecifier validate(SymbolTable symTable) {
        // Get from StructRow in SymbolTable
    
        TypeSpecifier inst_type = instance.validate(symTable);
        
        if (inst_type.getClass() != StructInstanceSpecifier.class)
        {
            Reporter.ReportErrorAndExit(inst_type.toString() + " is not a struct.", this);
        }

        struct_name = ((StructInstanceSpecifier)inst_type).name;

        SymbolTable structTable = CodeGenerator.GlobalSymbolTable.GetStructSymbolTable(struct_name);

        if (!structTable.Contains(attribute))
        {
            Reporter.ReportErrorAndExit(struct_name + " has no attribute " + attribute, this);
        }

        attr_descriptor = structTable.GetVarDescriptor(attribute);

        expression_type = structTable.GetTypeSpecifier(attribute, false);

        return expression_type;

    }
}
