package ast.expression;

import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;

import ast.type.StructInstanceSpecifier;
import ast.type.TypeSpecifier;
import codegen.CodeGenerator;
import semantics.table.SymbolTable;
import util.Reporter;

/* StructAttrExpression.java
 * 
 * This represents an access to a named attribute of a struct instance.
 * 
 * Fields:
 *  ->  instance: Expression that resolves to an instance of a struct
 *  ->  attribute: The string name of the attribute being accessed
 *  ->  struct_name: The name of the struct, that instance is an instance of
 *  ->  attr_descriptor: The JVM descriptor of the attribute's type
 */

public class StructAttrExpression extends Expression {
    
    public Expression instance;
    public String attribute;

    protected String struct_name;
    protected String attr_descriptor;
    
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

    /* To generate bytecode, simply generate the instance (putting it on top of the stack),
     * then use the GETFIELD instruction, with the name of the struct class ($name), and the
     * attribute's identifier and descriptor.
     */
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

    /* To validate, 
     *  ->  Get the instance type, and ensure it is a struct 
     *  ->  Get the struct's Symbol Table from the Global Symbol Table
     *  ->  Verify the attribute exists in the table
     *  ->  Set attr_descriptor, using the struct's Symbol Table
     *  ->  Set expression_type to the TypeSpecifier associated with that attribute, and 
     *      return this
     */
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
