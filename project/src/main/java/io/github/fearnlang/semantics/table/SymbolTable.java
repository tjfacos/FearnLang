package io.github.fearnlang.semantics.table;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

import io.github.fearnlang.ast.function.Parameter;
import io.github.fearnlang.ast.type.*;
import io.github.fearnlang.codegen.CodeGenerator;
import io.github.fearnlang.util.Reporter;

/**
 * This class represents a Symbol Table, used to keep
 * track of variables, functions, and structs used in a
 * program.
 * 
 * SymbolTable objects are composed of rows. The methods
 * are used to add rows, and query their types, owners, etc.
 * 
 * Its methods also serve the role of detecting when a symbol
 * has not been declared, raising a relevant error. A majority
 * of the below methods will raise an error if their function
 * fails, using the ReportErrorAndExit() function.
 * 
 */
public class SymbolTable {

    // Enum to represent the type of symbol in the table
    public enum SymbolType {
        Variable,
        Function,
        Struct
    }

    // LinkedHashMap to store rows in the table
    // This is used to maintain the order of rows in the table
    private LinkedHashMap<String, Row> Rows = new LinkedHashMap<>();

    // Method to generate a key for a row in the table
    static private String Key(String identifier, SymbolType type) {
        return type.toString() + "." + identifier;
    }

    /* General Methods */

    /**
     * Adds a Single Row object to the table, checking there's no clash with rows
     * already in the table.
     * 
     * Reports error if two symbols (of the same type) in the same scope (table)
     * have the same identifier.
     * 
     * @param new_row
     */
    public void addRow(Row new_row, Boolean noOverrideOwner) {
        // Add a Single Row object, checking there's no clash
        // with rows already in the table

        // Raise error if two symbols (of the same type) in the same
        // scope (table) have the same identifier

        String key = Key(new_row.identifier, new_row.type);

        if (Rows.containsKey(key)) {
            Reporter.ReportErrorAndExit(
                    new_row.type.toString() + " " + new_row.identifier + " can only exist once within scope.",
                    null);
        }

        // Set owner
        // The owner represents the generated class the row belongs to
        // E.g. The owner of a function, defined in lib.test, will become
        // a method in the 'lib' class, and so its owner is 'lib'

        if (!noOverrideOwner)
            new_row.owner = CodeGenerator.ProgramNameStack.peek();

        Rows.put(key, new_row);
    }

    /**
     * Adds all rows from a SymbolTable to the table
     * 
     * @param table
     */
    public void addRowsFromTable(SymbolTable table) {
        for (Row r : table.GetAllRows())
            addRow(r, true);
    }

    /**
     * Returns all rows in the table
     * 
     * @return An ArrayList of all rows in the table
     */
    public ArrayList<Row> GetAllRows() {
        return new ArrayList<Row>(Rows.values());
    }

    /**
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
     * These type descriptor strings are used to specify the type of
     * elements to the JVM.
     * 
     * @param typeSpecifier The TypeSpecifier object to generate a descriptor for
     * @return The type descriptor string
     */
    static public String GenBasicDescriptor(TypeSpecifier typeSpecifier) {

        String type_descriptor = "";

        // Generate the type descriptor for a primitive type
        if (typeSpecifier.getClass() == PrimitiveSpecifier.class) {
            switch (((PrimitiveSpecifier) typeSpecifier).element_type) {
                case INT:
                    type_descriptor += "Ljava/lang/Integer;";
                    break;
                case FLOAT:
                    type_descriptor += "Ljava/lang/Double;";
                    break;
                case STR:
                    type_descriptor += "Ljava/lang/String;";
                    break;
                case BOOL:
                    type_descriptor += "Ljava/lang/Boolean;";
                    break;
                default:
                    break;
            }
        }

        // Generate the type descriptor for an array
        else if (typeSpecifier.getClass() == ArraySpecifier.class) {
            type_descriptor += "[".repeat(((ArraySpecifier) typeSpecifier).dimensionCount);
            type_descriptor += GenBasicDescriptor(((ArraySpecifier) typeSpecifier).element_type);
        }

        // Generate the type descriptor for an array body
        else if (typeSpecifier.getClass() == ArrayBodySpecifier.class) {
            type_descriptor += "[";
            type_descriptor += GenBasicDescriptor(((ArrayBodySpecifier) typeSpecifier).element_type);
        }

        // Generate the type descriptor for a struct
        else {
            type_descriptor += "L$" + ((StructInstanceSpecifier) typeSpecifier).name + ";";
        }

        return type_descriptor;

    }

    /**
     * Retrieves a row in the table. Throws an exception if it is not found.
     * 
     * @param id   The identifier of the row to retrieve
     * @param type The type of the row to retrieve
     * @return The row
     * @throws NoSuchElementException
     */
    private Row GetRow(String id, SymbolType type)
            throws NoSuchElementException {
        Row val = Rows.get(Key(id, type));

        if (val == null)
            throw new NoSuchElementException(id);
        else
            return val;
    }

    /**
     * Retrieves the TypeSpecifier associated with a row in the table (e.g. variable
     * data type, function return type).
     * 
     * @param id   The identifier of the row to retrieve
     * @param type The type of the row to retrieve
     * @return The TypeSpecifier object associated with the row
     */
    public TypeSpecifier GetTypeSpecifier(String id, SymbolType type) {

        try {
            if (type == SymbolType.Function)
                return ((FunctionRow) GetRow(id, type)).return_type;
            else
                return ((VariableRow) GetRow(id, type)).dataType;
        } catch (Exception e) {
            Reporter.ReportErrorAndExit(
                    "Definition for " + id + " not provided in scope.",
                    null);
        }

        return null;
    }

    /**
     * Checks if a row with a given identifier is in the table
     * 
     * @param id
     * @return
     */
    public Boolean Contains(String id, SymbolType type) {
        return Rows.containsKey(Key(id, type));
    }

    /**
     * Get the owner of a symbol in the table
     * 
     * @param id   The identifier of the symbol
     * @param type The type of the symbol
     * @return
     */
    public String GetOwner(String id, SymbolType type) {
        try {
            return GetRow(id, type).owner;
        } catch (Exception e) {
            Reporter.ReportErrorAndExit("Owner for " + id + " not found.", null);
        }

        return null;
    }

    /* Variable Methods */

    // Get the string type descriptor of a variable in the table
    public String GetVarDescriptor(String id) {
        try {
            return GetRow(id, SymbolType.Variable).descriptor;
        } catch (Exception e) {
            Reporter.ReportErrorAndExit("Unknown Variable " + id, null);
        }

        return null;
    }

    /**
     * Gets the index of a variable in the table
     * 
     * These indexes are used in Code Generation, as the JVM
     * stores local variable at indexes, separate to the stack.
     * 
     * Since each variable in a function's symbol table is stored
     * at a different index, starting from 0, the index in the table is
     * used as the index for the local variable in the stack frame, at
     * runtime.
     * 
     * @param id The identifier of the variable
     * @return The index of the variable in the table
     */
    public Integer GetIndex(String id) {

        String key = Key(id, SymbolType.Variable);

        int i = 0;

        for (String k : Rows.keySet()) {
            if (k.equals(key))
                return i;
            else
                i++;
        }

        Reporter.ReportErrorAndExit("BUILD ERROR : Unknown Variable " + id, null);

        return null;
    }

    /* Function Methods */

    /**
     * Generates the method descriptor for a function.
     * 
     * Functions in FearnLang are modelled as public static methods
     * of the class representing the module/script they are defined in
     * (e.g. a function f() => void, defined in program.fearn, would
     * become `public static void f()`, in the `program` class).
     * 
     * In the JVM, as with any typed element, each method has a descriptor
     * to define the types of its arguments, and its return type.
     * 
     * Below, The parameter's types are all generated and appended to the
     * descriptor, along with the descriptor for the return type. If a
     * function is void (return_type is null), `V` is used instead.
     * 
     * @param params      The parameters of the function
     * @param return_type The return type of the function
     * @return The method descriptor
     */
    static public String GenFuncDescriptor(ArrayList<Parameter> params, TypeSpecifier return_type) {
        String desc = "(";

        for (Parameter p : params)
            desc += GenBasicDescriptor(p.type);

        desc += ")";

        if (return_type == null)
            desc += "V";
        else
            desc += GenBasicDescriptor(return_type);

        return desc;

    }

    /**
     * Get the function's local symbol table, containing its local variables.
     * 
     * @param id The identifier of the function
     * @return The function's local symbol table
     */
    public SymbolTable GetFuncSymbolTable(String id) {

        try {
            return ((FunctionRow) GetRow(id, SymbolType.Function)).localSymbolTable;
        } catch (Exception e) {
            Reporter.ReportErrorAndExit("Symbol Table for function " + id + " not found.", null);
        }

        return null;
    }

    /**
     * Get the method descriptor for a function
     * 
     * @param id The identifier of the function
     * @return The method descriptor
     */
    public String GetGlobalFuncDescriptor(String id) {

        try {
            return GetRow(id, SymbolType.Function).descriptor;
        } catch (Exception e) {
            Reporter.ReportErrorAndExit("Descriptor for function " + id + " not found.", null);
        }

        return null;
    }

    /**
     * Gets the TypeSpecifier objects associated with the arguments of a function
     * This is called when the program has attempted to call a function, and raises
     * an error if the function has not been found (indicating the program has not
     * defined it)
     * 
     * @param id The identifier of the function
     * @return An ArrayList of TypeSpecifier objects, representing the function's
     *         arguments
     */
    public ArrayList<TypeSpecifier> GetFuncParameterSpecifiers(String id) {
        ArrayList<TypeSpecifier> t_list = new ArrayList<TypeSpecifier>();

        try {
            for (Parameter p : ((FunctionRow) GetRow(id, SymbolType.Function)).parameters)
                t_list.add(p.type);
            return t_list;
        } catch (Exception e) {
            Reporter.ReportErrorAndExit("Function " + id + " is not defined.", null);
        }

        return t_list;

    }

    /* Struct Methods */

    /**
     * Get the TypeSpecifiers for the attributes of a struct (used to check the
     * types of the values used to initialise a struct)
     * 
     * @param id
     * @return
     */
    public ArrayList<TypeSpecifier> GetStructAttributeSpecifiers(String id) {
        try {
            return ((StructRow) GetRow(id, SymbolType.Struct)).localSymbolTable.GetAllVarTypeSpecifiers();
        } catch (Exception e) {
            Reporter.ReportErrorAndExit("Struct " + id + " not defined.", null);
        }

        return null;

    }

    /**
     * Generate method descriptor for constructor for the class
     * that represents the struct
     * 
     * E.g. Given a struct ...
     * 
     * struct myStruct
     * {
     * let x : int;
     * }
     * 
     * ... a class called `$myStruct` is generated (a `$` is added to differentiate
     * between program classes and struct classes), with its constructor being ...
     * 
     * public $myStruct(Integer var0)
     * 
     * @param localTable The struct's local symbol table
     * @return The method descriptor for the constructor
     */
    public static String GenStructDescriptor(SymbolTable localTable) {
        String desc = "(";

        for (TypeSpecifier t : localTable.GetAllVarTypeSpecifiers()) {
            desc += GenBasicDescriptor(t);
        }

        desc += ")V";
        return desc;
    }

    /**
     * Get the TypeSpecifiers of all variables in a table
     * @return An ArrayList of TypeSpecifier objects
     */
    public ArrayList<TypeSpecifier> GetAllVarTypeSpecifiers() {
        ArrayList<TypeSpecifier> t_list = new ArrayList<TypeSpecifier>();

        for (Row r : Rows.values()) {
            if (r.getClass() == VariableRow.class) {
                t_list.add(((VariableRow) r).dataType);
            }
        }

        return t_list;
    }

    /**
     * Get the method descriptor for the constructor of a struct
     * @param id The identifier of the struct
     * @return The method descriptor
     */
    public String GetGlobalStructDescriptor(String id) {

        try {
            return GetRow(id, SymbolType.Struct).descriptor;
        } catch (Exception e) {
            Reporter.ReportErrorAndExit("Descriptor for struct " + id + " not found.", null);
        }

        return null;
    }

    // Get Struct's local Symbol Table (containing its attributes)
    public SymbolTable GetStructSymbolTable(String id) {

        try {
            return ((StructRow) GetRow(id, SymbolType.Struct)).localSymbolTable;
        } catch (Exception e) {
            Reporter.ReportErrorAndExit("Symbol Table for struct " + id + " not found.", null);
        }

        return null;
    }
}