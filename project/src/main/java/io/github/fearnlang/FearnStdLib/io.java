package io.github.fearnlang.FearnStdLib;

import java.util.Scanner;

public class io {

    static Scanner scan = new Scanner(System.in);

    /**
     * Prints a string to the console
     * 
     * @param a The string to print
     */
    public static void print(String a) {
        System.out.println(a);
    }

    /**
     * Prompts the user for input and returns the input
     * 
     * @param prompt
     * @return The user's input
     */
    public static String input(String prompt) {
        String in;
        System.out.print(prompt);
        in = scan.nextLine();
        return in;
    }
}
