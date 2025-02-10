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
     * Map which relates the string names of types to a PrimitiveSpecifier
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
    
    /**
     * Get the standard library for a given identifier
     * 
     * @param id The identifier of the standard library to import
     * @return SymbolTable The symbol table of the standard library
     */
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

        Map<String, Object> functions = (Map<String, Object>) lib.get("functions");

        for (String func_name : functions.keySet()) {
            Map<String, Object> func = (Map<String, Object>) functions.get(func_name);

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
                            stringSpecifierMap.get((String) func.get("return")),
                            null));
        }

        CodeGenerator.ProgramNameStack.pop();

        return table;

    }
}
