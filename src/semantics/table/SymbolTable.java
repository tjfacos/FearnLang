package semantics.table;

import java.util.ArrayList;

import ast.Declaration;
import ast.function.Parameter;
import ast.type.*;
import util.Reporter;


public class SymbolTable {

    private ArrayList<Row> Symbols = new ArrayList<Row>();

    public void addSymbol(String id, Row new_row)
    {
        // Raise error if two variables in the same function has the same identifier
        for (Row r : Symbols)
        {
            if (new_row.getClass() == VariableRow.class && r.getClass() == VariableRow.class && r.identifier.equals(new_row.identifier))
            {
                Reporter.ReportErrorAndExit("Symbol " + r.identifier + " can only exist once within scope.");
            }
        }

        Symbols.add(new_row);
    }

    
    static public String GenBasicDescriptor(TypeSpecifier typeSpecifier)
    {
        
        String type_descriptor = "";
        
        if (typeSpecifier.getClass() == PrimitiveSpecifier.class)
        {
            switch ( ((PrimitiveSpecifier)typeSpecifier).element_type ) {
                case PrimitiveDataType.INT  : type_descriptor += "Ljava/lang/Integer;"                        ; break;               
                case PrimitiveDataType.FLOAT: type_descriptor += "Ljava/lang/Double;"                         ; break;               
                case PrimitiveDataType.STR  : type_descriptor += "Ljava/lang/String;"                         ; break;               
                case PrimitiveDataType.BOOL : type_descriptor += "Ljava/lang/Boolean;"                        ; break;                           
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
    
    
    public static String GenStructDescriptor(ArrayList<Declaration> declarations) 
    {
        String desc = "(";

        for (Declaration decl : declarations)
        {
            desc += GenBasicDescriptor(decl.type);
        }

        desc += ")V";
        return desc;
    }


    public String GetGlobalVarDescriptor(String id) {
        for (Row r : Symbols)
        {
            if (r.identifier.equals(id) && r.getClass() == VariableRow.class)
            {
                return ((VariableRow)r).descriptor;
            }
        }

        Reporter.ReportErrorAndExit("Unknown Variable " + id);

        return null;
    }


    public SymbolTable GetFuncSymbolTable(String id) {
        for (Row r : Symbols)
        {
            if (r.identifier.equals(id) && r.getClass() == FunctionRow.class)
            {
                return ((FunctionRow)r).localSymbolTable;
            }
        }
        
        Reporter.ReportErrorAndExit("Symbol Table for function " + id + " not found.");
        
        return null;
    }
    

    public Integer GetIndex(String id) {
        for (int i = 0; i < Symbols.size(); i++)
        {
            if (Symbols.get(i).identifier.equals(id))  { return i; }
        }
        
        Reporter.ReportErrorAndExit("Unknown Variable " + id);

        return null;
    }


    public Boolean Contains(String id) {
        for (int i = 0; i < Symbols.size(); i++)
        {
            if (Symbols.get(i).identifier.equals(id))  { return true; }
        }
        
        return false;
    }


    public String GetGlobalFuncDescriptor(String id) {
        
        for (Row r : Symbols)
        {
            if (r.identifier.equals(id) && r.getClass() == FunctionRow.class)
            {
                return ((FunctionRow)r).descriptor;
            }
        }
        
        Reporter.ReportErrorAndExit("Descriptor for function " + id + " not found.");
        
        return null;
    }


    public TypeSpecifier GetTypeSpecifier(String id) {
        
        for (Row r : Symbols)
        {
            if (r.identifier.equals(id))
            {
                if (r.getClass() == FunctionRow.class)  { return ((FunctionRow)r).return_type;  }
                else /* r is VariableRow */             { return ((VariableRow)r).typeSpecifier;}
            }
        }
        
        Reporter.ReportErrorAndExit("Type Specifier for " + id + " not found.");
        
        return null;
    }



}
