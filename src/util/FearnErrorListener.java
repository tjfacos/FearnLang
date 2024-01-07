package util;

import org.antlr.v4.runtime.*;

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
            message = "Unable to parse line. Check for missing characters, like semi-colons (;).";
        }

        Reporter.ReportErrorAndExit(
            "Parse Error : line "+line+"; col "+charPositionInLine+": "+message
        );
    }
}
