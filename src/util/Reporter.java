package util;

public class Reporter {

    static String ANSI_RESET   = (char)27 + "[0m";
    static String ANSI_RED     = (char)27 + "[31m";
    static String ANSI_GREEN   = (char)27 + "[32m";
    static String ANSI_PURPLE  = (char)27 + "[35m";
    
    static String ANSI_BOLD    = (char)27 + "[1m";


    
    static public void ReportErrorAndExit(String err)
    {
        System.out.println(
            String.format(
                "FearnC: %s%sERROR: %s %s", 
                ANSI_BOLD,
                ANSI_RED,
                err,
                ANSI_RESET
            )
        );
        
        System.exit(1);
    }

    static public void ReportSuccess(String message, boolean exit)
    {
        String x = ANSI_PURPLE;
        if (exit) { x = ANSI_GREEN; }

        System.out.println(
            String.format(
                "FearnC: %s%s%s%s", 
                ANSI_BOLD,
                x,
                message,
                ANSI_RESET
            )
        );

        if (exit)
        {
            System.exit(0);
        }
    }
}
