import sys
import os

import shutil
import time
import json
import re
from subprocess import STDOUT, PIPE
import argparse
import shutil
import subprocess
from subprocess import check_output, CalledProcessError, STDOUT
from datetime import datetime



SHOW_COMMAND=False
    

def trace(*args):
    result = ""
    for x in args:
        result += x
    print(result)    
        
def runProcess(command, args=[],wait=True):
    args = [command] + args
    def cmd_args_to_str(cmd_args):
        return ' '.join([arg if not ' ' in arg else '"%s"' % arg for arg in cmd_args])

    global SHOW_COMMAND
    if SHOW_COMMAND:
        trace("Execute -> ",cmd_args_to_str(args))
    proc = subprocess.Popen(args,
        stdout = subprocess.PIPE,
        stderr = subprocess.PIPE,
    )
    
    stdout, stderr = proc.communicate()
    if wait:
        proc.wait()
    return proc.returncode, stdout, stderr
    
def cmd_args_to_str(cmd_args):
    return ' '.join([arg if not ' ' in arg else '"%s"' % arg for arg in cmd_args])




#jar cf jar-file input-file(s)  

jarFile="json"
folder="/media/djoker/code/linux/python/projects/JavaFX/libs/json"

filesList=[]

for root, dirs, files in os.walk(folder):
    for file in files:
        if file.endswith(".class"):
            print(os.path.join(root, file))   
            filesList.append(os.path.join(root, file))

args=[]
args.append("cf")
args.append(jarFile)
#addToClass=outFolder
for f in filesList:
    args.append(f)

#args.append(outFolder+":"+outFolder+OSP+"camsview")
#args.append(addToClass)
#args.append(appName)

trace(" Create Jar ...")

code, out, err=runProcess("jar",args)
if code!=0:
    trace("Error  Nuning :"+err.decode("utf-8") )
    
trace(out.decode("utf-8"))