# set the name of the installer
Outfile "FearnInstaller.exe"
 
InstallDir $PROGRAMFILES\FearnLang

; The text to prompt the user to enter a directory
DirText "This will install the Fearn Programming Language on your computer. Choose a directory"

RequestExecutionLevel admin

Section
 
  ; define the output path for this file
  SetOutPath $INSTDIR

  File /r FearnC.jar
  File /r FearnC.cmd
  File /r FearnRun.cmd

  ; Add INSTDIR to the system's PATH
  StrCpy $0 $INSTDIR
  StrCmp $0 "" 0 +3
    StrCpy $1 "$0;$0" ; append the directory twice to handle the case when PATH is empty
    System::Call 'kernel32::SetEnvironmentVariable(t "Path", t "$1")'

  ; Set the CLASSPATH environment variable to include the .jar file
  System::Call 'kernel32::SetEnvironmentVariable(t "CLASSPATH", t "$INSTDIR\FearnC.jar;%CLASSPATH%")'

  ; define uninstaller name
  WriteUninstaller $INSTDIR\uninstaller.exe
 
SectionEnd

 
; create a section to define what the uninstaller does.
; the section will always be named "Uninstall"
Section "Uninstall"
  
  ; Delete installed file
  Delete $INSTDIR\FearnC.jar
  Delete $INSTDIR\FearnC.cmd
  Delete $INSTDIR\FearnRun.cmd
  
  ; Delete the uninstaller
  Delete $INSTDIR\uninstaller.exe
  
  ; Delete the directory
  RMDir $INSTDIR

SectionEnd
