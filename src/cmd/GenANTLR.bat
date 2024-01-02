@REM Move into parser directory
cd parser

@REM Clear existing generated files
@RD /S /Q "./gen"
@REM @RD /S /Q "./build"

@REM Generate ANTLR4 Files 
java -cp C:\\libraries\\javalibs\\antlr-4.13.1-complete.jar org.antlr.v4.Tool -o gen -no-listener -visitor -package parser.gen FearnGrammar.g4

@REM Compile Classes
@REM javac -cp C:\\libraries\\javalibs\\antlr-4.13.1-complete.jar -d build gen/Fearn*.java