package io.github.fearnlang.util;

import io.github.fearnlang.ast.ASTNode;
import io.github.fearnlang.codegen.CodeGenerator;

/* 
 * Reporter.java
 * 
 * This is a container class for two functions:
 * 
 * ->   ReportErrorAndExit : Prints a red error message to
 *      the terminal, then exits the program. If a node has
 *      been passed, it also prints the node of the AST causing 
 *      the error, reconstructing the corresponding source code
 *      using ASTNode.toString() (DFT, adding to a string representing 
 *      the offending source code)
 * 
 * ->   ReportSuccess: Used to report when binaries have been built, 
 *      and where. These messages are printed in purple, or green if 
 *      the option to exit the program has been set to true. If so, 
 *      function also exits FearnC.
 * 
 */
public class Reporter {
    private static String ANSI_RESET   = (char)27 + "[0m";
    private static String ANSI_RED     = (char)27 + "[31m";
    private static String ANSI_GREEN   = (char)27 + "[32m";
    private static String ANSI_PURPLE  = (char)27 + "[35m";
    private static String ANSI_BOLD    = (char)27 + "[1m";

    public static void ReportErrorAndExit(String err, ASTNode offendingNode)
    {
        String program_name = CodeGenerator.ProgramNameStack.empty() ? "unknown" : CodeGenerator.ProgramNameStack.peek();

        if (offendingNode == null)
        System.out.println(
            String.format(
                "FearnC (%s): %s%sERROR: %s %s", 
                program_name,
                ANSI_BOLD,
                ANSI_RED,
                err,
                ANSI_RESET
            )
        );
        else 
        System.out.println(
            String.format(
                "FearnC (%s): %s%sERROR: %s - %s %s", 
                program_name,
                ANSI_BOLD,
                ANSI_RED,
                offendingNode.toString(),
                err,
                ANSI_RESET
            )
        );
        System.exit(1);
    }

    public static void ReportSuccess(String message, boolean exit)
    {
        String colour = ANSI_PURPLE;
        if (exit) { colour = ANSI_GREEN; }

        System.out.println(
            String.format(
                "FearnC: %s%s%s%s", 
                ANSI_BOLD,
                colour,
                message,
                ANSI_RESET
            )
        );

        if (exit) System.exit(0);
    }
}
