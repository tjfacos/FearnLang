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
        Reporter.ReportErrorAndExit(
            "Parse :- line "+line+"; col "+charPositionInLine+": "+msg, 
            3
        );
    }
}
