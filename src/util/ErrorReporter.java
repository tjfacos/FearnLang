package util;

public class ErrorReporter {
    static public void ReportErrorAndExit(String err, int code)
    {
        System.out.println("FearnC: " + (char)27 + "[31m" + "ERROR:" + err + (char)27 + "[0m");
        System.exit(code);
    }
}
