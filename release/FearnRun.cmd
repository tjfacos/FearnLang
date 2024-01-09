@echo off

if [%1]==[] goto usage

java --enable-preview %1
goto :eof

:usage
@echo Usage: FearnRun <program-name>
exit /B 1