@echo off

set jarPath=%~dp0\FearnC.jar

echo Deleting existing FearnC.jar ... 

del "%jarPath%"

echo Complete

echo Downloading latest FearnC.jar ... 

powershell -command "Invoke-WebRequest -Uri https://github.com/tjfacos/FearnLang/raw/main/release/FearnC.jar -Outfile '%jarPath%' -UseBasicParsing"

echo Complete

echo Successfully updated FearnLang Compiler!