package io.github.fearnlang.stdlib;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import io.github.fearnlang.ast.function.Parameter;
import io.github.fearnlang.ast.type.PrimitiveSpecifier;
import io.github.fearnlang.ast.type.PrimitiveSpecifier.PrimitiveDataType;
import io.github.fearnlang.codegen.CodeGenerator;
import io.github.fearnlang.semantics.table.FunctionRow;
import io.github.fearnlang.semantics.table.SymbolTable;
import io.github.fearnlang.util.Reporter;

public class ImportStdLib {
    /**
     * Get the standard library for a given identifier
     * 
     * 
     * @param id The identifier of the standard library to import
     * @return SymbolTable The symbol table of the standard library
     */

    private static Map<String, PrimitiveSpecifier> stringSpecifierMap = new HashMap<String, PrimitiveSpecifier>() {
        {
            put("string", new PrimitiveSpecifier(PrimitiveDataType.STR));
            put("int", new PrimitiveSpecifier(PrimitiveDataType.INT));
            put("float", new PrimitiveSpecifier(PrimitiveDataType.FLOAT));
            put("bool", new PrimitiveSpecifier(PrimitiveDataType.BOOL));
            put("void", null);
        }
    };

    @SuppressWarnings("unchecked")
    public static SymbolTable GetStdLib(String id) {

        SymbolTable table = new SymbolTable();

        ArrayList<Parameter> params;

        Yaml yaml = new Yaml();
        InputStream inputStream = ImportStdLib.class.getClassLoader().getResourceAsStream("stdlib.yaml");

        if (inputStream == null) {
            Reporter.ReportErrorAndExit("Could not find standard library specification file.", null);
        }

        Map<String, Object> spec = yaml.load(inputStream);

        Map<String, Object> lib = (Map<String, Object>) spec.get(id);

        if (lib == null) {
            Reporter.ReportErrorAndExit("Standard library " + id + " does not exist.", null);
        }

        CodeGenerator.ProgramNameStack.push(lib.get("program_name").toString());

        for (String func_name : lib.keySet()) {
            Map<String, Object> func = (Map<String, Object>) lib.get(func_name);

            params = new ArrayList<>();
            ArrayList<String> param_list = (ArrayList<String>) func.get("params");
            for (String param : param_list) {
                params.add(
                        new Parameter(
                                "",
                                stringSpecifierMap.get(param)));
            }

            table.addRow(
                    new FunctionRow(
                            func_name,
                            params,
                            stringSpecifierMap.get((String)func.get("return")),
                            null));
        }

        CodeGenerator.ProgramNameStack.pop();

        return table;

        // // Switch makes standard library easy to expand
        // switch (id) {
        // case "io" -> {
        // // Set Program Name
        // // This sets the row's owner, ensuring the bytecode for calling these
        // // function calls refer the the correct package io.github.fearnlang.and class
        // CodeGenerator.ProgramNameStack.push("io/github/fearnlang/stdlib/io");

        // // Add Print Function

        // // Create new parameter list
        // params = new ArrayList<>();

        // // Add string parameter
        // params.add(new Parameter(
        // "", new PrimitiveSpecifier(PrimitiveDataType.STR)));

        // // Add to symbol table, with identifier print, and null return
        // // type and local symbol table (irrelevant as this function has
        // // no Fearn implementation)
        // table.addRow(
        // new FunctionRow(
        // "print",
        // params,
        // null,
        // null));

        // // Add Input Function

        // // params remains the same (as both print and input take a single,
        // // string argument)

        // table.addRow(
        // new FunctionRow(
        // "input",
        // params,
        // new PrimitiveSpecifier(PrimitiveDataType.STR),
        // null));

        // }

        // case "maths" -> {

        // CodeGenerator.ProgramNameStack.push("io/github/fearnlang/stdlib/maths");

        // // Add PI() -> value of PI
        // params = new ArrayList<>();
        // table.addRow(
        // new FunctionRow(
        // "PI",
        // params,
        // new PrimitiveSpecifier(PrimitiveDataType.FLOAT),
        // null));

        // // Add Eulers() -> value of Euler's number
        // table.addRow(
        // new FunctionRow(
        // "Eulers",
        // params,
        // new PrimitiveSpecifier(PrimitiveDataType.FLOAT),
        // null));

        // // Add sin, cos, and tan functions
        // params = new ArrayList<>();
        // params.add(new Parameter("", new
        // PrimitiveSpecifier(PrimitiveDataType.FLOAT)));
        // table.addRow(
        // new FunctionRow(
        // "sin",
        // params,
        // new PrimitiveSpecifier(PrimitiveDataType.FLOAT),
        // null));

        // table.addRow(
        // new FunctionRow(
        // "cos",
        // params,
        // new PrimitiveSpecifier(PrimitiveDataType.FLOAT),
        // null));

        // table.addRow(
        // new FunctionRow(
        // "tan",
        // params,
        // new PrimitiveSpecifier(PrimitiveDataType.FLOAT),
        // null));

        // }

        // case "random" -> {
        // CodeGenerator.ProgramNameStack.push("io/github/fearnlang/stdlib/RandomNumbers");

        // // Add random -> Random double between 0 and 1
        // params = new ArrayList<>();
        // table.addRow(
        // new FunctionRow("random", params,
        // new PrimitiveSpecifier(PrimitiveDataType.FLOAT), null));

        // // Add randomFromRange -> Random integer from range
        // params = new ArrayList<>();
        // params.add(new Parameter("", new PrimitiveSpecifier(PrimitiveDataType.INT)));
        // params.add(new Parameter("", new PrimitiveSpecifier(PrimitiveDataType.INT)));
        // table.addRow(
        // new FunctionRow("randomInRange", params,
        // new PrimitiveSpecifier(PrimitiveDataType.INT), null));

        // }

        // default -> Reporter.ReportErrorAndExit("Standard library " + id + " does not
        // exist.", null);

        // }

        // // Pop Generator, to return to primary program
        // CodeGenerator.ProgramNameStack.pop();

        // // Return Symbol Table to primary compilation process
        // return table;
    }
}
