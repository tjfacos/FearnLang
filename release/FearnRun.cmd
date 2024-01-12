@echo off

if [%1]==[] goto usage

java --enable-preview %*
goto :eof

:usage
@echo Usage: FearnRun <program-name>
exit /B 1