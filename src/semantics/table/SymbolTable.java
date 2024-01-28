package semantics.table;

import java.util.ArrayList;


import ast.function.Parameter;
import ast.type.*;
import codegen.CodeGenerator;
import util.Reporter;

/* 
 * SymbolTable.java
 * 
 * This class represents a Symbol Table, used to keep
 * track of variables, functions, and structs used in a
 * program.
 * 
 * SymbolTable objects are composed of rows. The methods
 * are used to add rows, and query their types, owners, etc.
 * 
 * Its methods also serve the role of detectinb when a symbol
 * has not been declared, rasing a relevant error.
 * 
*/

public class SymbolTable {

    private ArrayList<Row> Rows = new ArrayList<Row>();


    /* General Methods */

    
    public void addRow(Row new_row)
    {
        // Add a Single Row object, checking there's no clash
        // with rows already in the table
        
        // Raise error if two symbols (of the same type) in the same 
        // scope (table) have the same identifier

        for (Row r : Rows)
        {
            if (new_row.getClass() == r.getClass() && r.identifier.equals(new_row.identifier))
            {
                Reporter.ReportErrorAndExit("Symbol " + new_row.identifier + " can only exist once within scope.", null);
            }
        }

        // Set owner
        // The owner represents the generated class the row belongs to
        // E.g. The owner of a function, defined in lib.test, will become
        // a method in the 'lib' class, and so its owner is 'lib
        
        new_row.owner = CodeGenerator.generatorStack.peek().programName;

        Rows.add(new_row);
    }

    // Add Rows from a Symbol Table (used to add rows symbols 
    // from imported files/modules)
    
    public void addRowsFromTable(SymbolTable table) {
        for (Row r : table.GetAllRows()) Rows.add(r);
    }
    
    // Return all rows
    public ArrayList<Row> GetAllRows()
    {
        return Rows;
    }


    /* GenBasicDescriptor
     * 
     * This generates the type descriptors for int, float, bool, and str
     * types in Fearn, which are translated to Integer, Double, Boolean, 
     * and String Java objects.
     * 
     * It also recursively build type descriptors for arrays, and generate 
     * struct descriptors using their class name ($IDENTIFIER).
     * 
     * These are generated from TypeSpecifier objects - which are used by FearnC 
     * to describe data types.
     * 
     * These type descriptor strings are used to specifiy the type of 
     * elements to the JVM.
     * 
     */


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
    
    // Retrieves the Type Specifier associated with a row in the table (e.g. variable 
    // data type, function return type).
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

    // Returns true if identifier is contained within the table
    public Boolean Contains(String id) {
        for (int i = 0; i < Rows.size(); i++)
        {
            if (Rows.get(i).identifier.equals(id))  { return true; }
        }
        
        return false;
    }

    // Get the owner associated with an identifier
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
            if (r.identifier.equals(id) && r instanceof FunctionRow)
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
