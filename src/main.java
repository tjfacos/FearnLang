// ANTLR4 Dependencies
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;


// Java IO Dependencies
import java.io.FileInputStream;
import java.io.InputStream;

class FearnC
{
    public static void Test() {
        
    }
    
    
    
    public static void main(String []args)
    {
        String inputFile = null;
        if ( args.length > 0) {
            inputFile = args[0];
        } else {
            System.out.println("FearnC: " + (char)27 + "[31m" + "ERROR: NO SOURCE FILE");
            System.exit(1);
        }

        System.out.println(inputFile);
    }
};