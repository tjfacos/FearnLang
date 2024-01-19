import shutil
import os

dst = os.environ['VSCODEEXTPATH'] + "\\fearnlang-vscode-support"

if os.path.exists(dst):
        shutil.rmtree(dst)

def return_ignore(*args):
        return [".vscode"]

shutil.copytree("../fearnlang-vscode-support", dst, ignore=return_ignore, dirs_exist_ok=True)

print("SUCCESS")