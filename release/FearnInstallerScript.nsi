; The name of the installer
Name "FearnInstaller"

; The default installation directory
InstallDir $PROGRAMFILES\FearnLang

; The text to prompt the user to enter a directory
DirText "This will install the Fearn Programmming Language on your computer. Choose a directory"

; ----------------------------------------------------------------------------------
; *************************** SECTION FOR INSTALLING *******************************
; ----------------------------------------------------------------------------------

Section "" ; 

; Set output path to the installation directory. Also sets the working
; directory for shortcuts
SetOutPath $INSTDIR\

NSISdl::download "https://github.com/tjfacos/FearnLang/raw/main/release/FearnC.jar" $INSTDIR\FearnC.jar
NSISdl::download "https://github.com/tjfacos/FearnLang/raw/main/release/FearnC.cmd" $INSTDIR\FearnC.cmd
NSISdl::download "https://github.com/tjfacos/FearnLang/raw/main/release/FearnRun.cmd" $INSTDIR\FearnRun.cmd

WriteUninstaller $INSTDIR\Uninstall.exe

MessageBox MB_OK "Installation was successful."

SectionEnd

; ----------------------------------------------------------------------------------
; ************************** SECTION FOR UNINSTALLING ******************************
; ----------------------------------------------------------------------------------

Section "Uninstall"
; remove all the files and folders
Delete $INSTDIR\Uninstall.exe ; delete self
Delete $INSTDIR\FearnC.jar
Delete $INSTDIR\FearnC.cmd
Delete $INSTDIR\FearnRun.cmd

RMDir $INSTDIR

SectionEnd
