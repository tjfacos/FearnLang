// ANTLR4 Dependencies
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;


// Java IO Dependencies
import java.io.FileInputStream;
import java.io.InputStream;

class FearnC
{

    static private void ReportErrorAndExit(String err, int code)
    {
        System.out.println("FearnC: " + (char)27 + "[31m" + "ERROR:" + err + (char)27 + "[0m");
        System.exit(code);
    }

    
    public static void main(String []args)
    {
        if ( args.length == 0) {
            ReportErrorAndExit("NO SOURCE FILE", 1);
        }

        InputStream is = null;
        CharStream input = null;

        try {
            is = new FileInputStream(args[0]);
            input = CharStreams.fromStream(is);
        } catch (Exception e) {
            ReportErrorAndExit("FILE " + args[0] + " NOT FOUND", 2);
        }

        

    }
};