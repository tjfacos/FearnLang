package FearnStdLib;

import java.util.Scanner;

public class io {
    
    static Scanner scan = new Scanner(System.in);

    public static void print(String a)
    {
        System.out.println(a);
    }


    public static String input(String prompt)
    {
        String in;
        
        System.out.print(prompt); 
        in = scan.nextLine();

        return in;
    }
}
