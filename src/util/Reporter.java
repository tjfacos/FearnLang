package util;

public class Reporter {

    static String ANSI_RESET   = (char)27 + "[0m";
    static String ANSI_RED     = (char)27 + "[31m";
    static String ANSI_GREEN   = (char)27 + "[32m";
    
    static String ANSI_BOLD    = (char)27 + "[1m";


    
    static public void ReportErrorAndExit(String err, int code)
    {
        System.out.println(
            String.format(
                "FearnC: %s%ERROR: %s %s", 
                ANSI_BOLD,
                ANSI_RED,
                err,
                ANSI_RESET
            )
        );
        
        System.exit(code);
    }

    static public void ReportSuccessAndExit(String message)
    {
        System.out.println(
            String.format(
                "FearnC: %s%sSUCCESS: %s %s", 
                ANSI_BOLD,
                ANSI_GREEN,
                message,
                ANSI_RESET
            )
        );

        System.exit(0);
    }
}
