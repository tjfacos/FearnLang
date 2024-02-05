package util;

import org.antlr.v4.runtime.*;

/* 
 * FearnErrorLister.java
 * 
 * Utility class to catch ANTLR-generated parse errors,
 * and passes them through to ReportErrorAndExit(), along
 * with the line and column numbers, to help the developer 
 * fix the error.
 * 
 */

public class FearnErrorListener extends BaseErrorListener{

    @Override
    public void syntaxError(
        Recognizer<?, ?> recognizer,
        Object offendingSymbol,
        int line, int charPositionInLine,
        String msg,
        RecognitionException e
    )
    {

        String message = msg;

        if (message.startsWith("no viable alternative at input"))
        {
            message = "Unable to parse line. Check for missing characters, like semicolons (;).";
        }

        Reporter.ReportErrorAndExit(
            "Parse Error : line "+line+"; col "+charPositionInLine+": "+message, null
        );
    }
}
