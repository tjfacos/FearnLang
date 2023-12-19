@REM Clear existing generated files
rmdir /Q /S gen
rmdir /Q /S build

@REM Generate ANTLR4 Files 
java -cp C:\\libraries\\javalibs\\antlr-4.13.1-complete.jar org.antlr.v4.Tool -o gen -no-listener FearnGrammar.g4

@REM Compile Classes
javac -cp C:\\libraries\\javalibs\\antlr-4.13.1-complete.jar -d build gen/Fearn*.java