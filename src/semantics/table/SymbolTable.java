package semantics.table;

import java.util.ArrayList;


import ast.function.Parameter;
import ast.type.*;
import codegen.CodeGenerator;
import util.Reporter;


public class SymbolTable {

    private ArrayList<Row> Rows = new ArrayList<Row>();


    /* General Methods */
    public void addRow(Row new_row)
    {
        // Raise error if two variables in the same function has the same identifier
        for (Row r : Rows)
        {
            if (new_row.getClass() == r.getClass() && r.identifier.equals(new_row.identifier))
            {
                Reporter.ReportErrorAndExit("Symbol " + new_row.identifier + " can only exist once within scope.", null);
            }
        }

        new_row.owner = CodeGenerator.generatorStack.peek().programName;

        Rows.add(new_row);
    }

    public void addRowsFromTable(SymbolTable table) {
        for (Row r : table.GetAllRows()) Rows.add(r);
    }
    
    public ArrayList<Row> GetAllRows()
    {
        return Rows;
    }


    
    static public String GenBasicDescriptor(TypeSpecifier typeSpecifier)
    {
        
        String type_descriptor = "";
        
        if (typeSpecifier.getClass() == PrimitiveSpecifier.class)
        {
            switch ( ((PrimitiveSpecifier)typeSpecifier).element_type ) {
                case INT  : type_descriptor += "Ljava/lang/Integer;"                        ; break;               
                case FLOAT: type_descriptor += "Ljava/lang/Double;"                         ; break;               
                case STR  : type_descriptor += "Ljava/lang/String;"                         ; break;               
                case BOOL : type_descriptor += "Ljava/lang/Boolean;"                        ; break;                           
                default: break;
            }
        }
        
        else if (typeSpecifier.getClass() == ArraySpecifier.class)
        {
            type_descriptor += new String( new char[ ((ArraySpecifier)typeSpecifier ).dimensions ] ).replace("\0", "[");
            type_descriptor += GenBasicDescriptor(((ArraySpecifier)typeSpecifier).element_type);
        }

        else if (typeSpecifier.getClass() == ArrayBodySpecifier.class)
        {
            type_descriptor += "[";
            type_descriptor += GenBasicDescriptor(((ArrayBodySpecifier)typeSpecifier).element_type);
        }
        
        
        else // Struct Instance
        {
            type_descriptor += "L$" + ( (StructInstanceSpecifier)typeSpecifier ).name + ";";
        }
        
        return type_descriptor;
        
    }
    

    public TypeSpecifier GetTypeSpecifier(String id, Boolean isFunction) {
        
        for (Row r : Rows)
        {
            if (r.identifier.equals(id) && r.getClass() == FunctionRow.class && isFunction)
            {
                return ((FunctionRow)r).return_type;
            }
            
            else if (r.identifier.equals(id) && !isFunction) 
            { 
                return ((VariableRow)r).typeSpecifier; 
            }
        }
        
        Reporter.ReportErrorAndExit("Type Specifier for " + id + " not found.", null);
        
        return null;
    }

    public Boolean Contains(String id) {
        for (int i = 0; i < Rows.size(); i++)
        {
            if (Rows.get(i).identifier.equals(id))  { return true; }
        }
        
        return false;
    }

    public String GetOwner(String id, Boolean isFunction)
    {
        for (Row r : Rows)
        {
            if (r.identifier.equals(id) && r instanceof FunctionRow && isFunction)
            {
                return r.owner;
            }
            
            else if (r.identifier.equals(id) && !isFunction) 
            { 
                return r.owner; 
            }
        }
        
        Reporter.ReportErrorAndExit("Owner for " + id + " not found.", null);
        
        return null;
    }

    /* Variable Methods */
    public String GetVarDescriptor(String id) {
        for (Row r : Rows)
        {
            if (r.identifier.equals(id) && r.getClass() == VariableRow.class)
            {
                return ((VariableRow)r).descriptor;
            }
        }
        
        Reporter.ReportErrorAndExit("Unknown Variable " + id, null);
        
        return null;
    }

    public Integer GetIndex(String id) {
        for (int i = 0; i < Rows.size(); i++)
        {
            if (Rows.get(i).identifier.equals(id))  { return i; }
        }
        
        Reporter.ReportErrorAndExit("Unknown Variable " + id, null);
        
        return null;
    }

    /* Function Methods */
    static public String GenFuncDescriptor(ArrayList<Parameter> params, TypeSpecifier return_type)
    {
        String desc = "(";
        
        for (Parameter p : params)
        {
            desc += GenBasicDescriptor(p.type);
        }
        
        desc += ")";
        
        if (return_type == null)
        {
            desc += "V";
        } else {
            desc += GenBasicDescriptor(return_type);
        }
        
        return desc;
        
    }
    

    public SymbolTable GetFuncSymbolTable(String id) {
        for (Row r : Rows)
        {
            if (r.identifier.equals(id) && r.getClass() == FunctionRow.class)
            {
                return ((FunctionRow)r).localSymbolTable;
            }
        }
        
        Reporter.ReportErrorAndExit("Symbol Table for function " + id + " not found.", null);
        
        return null;
    }
    
    
    public String GetGlobalFuncDescriptor(String id) {
        
        for (Row r : Rows)
        {
            if (r.identifier.equals(id) && r.getClass() == FunctionRow.class)
            {
                return ((FunctionRow)r).descriptor;
            }
        }
        
        Reporter.ReportErrorAndExit("Descriptor for function " + id + " not found.", null);
        
        return null;
    }
    
    
    public ArrayList<TypeSpecifier> GetFuncParameterSpecifiers(String id)
    {
        ArrayList<TypeSpecifier> t_list = new ArrayList<TypeSpecifier>();
        
        for (Row r : Rows)
        {
            if (r.identifier.equals(id) && r.getClass() == FunctionRow.class)
            {
                for (Parameter p : ((FunctionRow)r).parameters)
                {
                    t_list.add(p.type);
                }
                return t_list;
            }
        }
        
        Reporter.ReportErrorAndExit("Function " + id + " is not defined.", null);
        
        return t_list;
    }
    

    /* Struct Methods */
    public ArrayList<TypeSpecifier> GetStructAttributeSpecifiers(String id)
    {
        for (Row r : Rows)
        {
            if (r.identifier.equals(id) && r.getClass() == StructRow.class)
            {
                SymbolTable symTable = ((StructRow)r).localSymbolTable;

                return symTable.GetAllVarTypeSpecifiers();
            }
        }

        Reporter.ReportErrorAndExit("Struct " + id + " not defined.", null);

        return null;

    }
    
    public static String GenStructDescriptor(SymbolTable localTable) 
    {
        String desc = "(";
    
        for (TypeSpecifier t : localTable.GetAllVarTypeSpecifiers())
        {
            desc += GenBasicDescriptor(t);
        }
    
        desc += ")V";
        return desc;
    }
    
    public ArrayList<TypeSpecifier> GetAllVarTypeSpecifiers()
    {
        ArrayList<TypeSpecifier> t_list = new ArrayList<TypeSpecifier>();
        
        for (Row r : Rows)
        {
            if (r.getClass() == VariableRow.class)
            {
                t_list.add(((VariableRow)r).typeSpecifier);
            }
        }

        return t_list;
    }

    public String GetGlobalStructDescriptor(String id) {
        
        for (Row r : Rows)
        {
            if (r.identifier.equals(id) && r.getClass() == StructRow.class)
            {
                return ((StructRow)r).descriptor;
            }
        }
        
        Reporter.ReportErrorAndExit("Descriptor for struct " + id + " not found.", null);
        
        return null;
    }

    public SymbolTable GetStructSymbolTable(String id) {
        for (Row r : Rows)
        {
            if (r.identifier.equals(id) && r.getClass() == StructRow.class)
            {
                return ((StructRow)r).localSymbolTable;
            }
        }
        
        Reporter.ReportErrorAndExit("Symbol Table for struct " + id + " not found.", null);
        
        return null;
    }

    
}
