# set the name of the installer
Outfile "FearnInstaller.exe"
 
InstallDir $PROGRAMFILES\FearnLang

; The text to prompt the user to enter a directory
DirText "This will install My Cool Program on your computer. Choose a directory"

RequestExecutionLevel admin

Section
 
  # define the output path for this file
  SetOutPath $INSTDIR

  File /r FearnC.jar
  File /r FearnC.cmd
  File /r FearnRun.cmd

  # define uninstaller name
  WriteUninstaller $INSTDIR\uninstaller.exe
 
SectionEnd

 
# create a section to define what the uninstaller does.
# the section will always be named "Uninstall"
Section "Uninstall"
  
  # Delete installed file
  Delete $INSTDIR\FearnC.jar
  Delete $INSTDIR\FearnC.cmd
  Delete $INSTDIR\FearnRun.cmd
  
  # Delete the uninstaller
  Delete $INSTDIR\uninstaller.exe
  
  # Delete the directory
  RMDir $INSTDIR

SectionEnd
