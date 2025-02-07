@echo off

if "%~1"=="" (
    echo Usage: FearnRun "<program-name>" "<args>" ...
    exit /b 1
)

java --enable-preview %*