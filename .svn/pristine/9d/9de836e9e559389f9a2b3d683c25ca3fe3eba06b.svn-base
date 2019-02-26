#!/bin/sh
# R@ Dec 2005
# Usage: tapereader.sh [-i <identifier>]  [-s] <tapefile> <indexdir> <indexPlugin> 
#  -i    read record
#  -s    list status of the tape index 

# A script to manually test tape reader

tapehome=../
libpath=$tapehome/lib/

for line in `ls -1 $libpath | grep '.jar'`
do
    classpath="$classpath:$libpath$line"
done

java -cp $classpath gov/lanl/xmltape/index/TapeReaderApp $*


