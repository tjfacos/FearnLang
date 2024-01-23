import shutil
import os

shutil.move("src.jar", os.environ['FEARNPATH'] + "\\FearnC.jar")

print("SUCCESS")