#!/bin/sh
# R@ Dec 2005
# Usage: ./tapeindex.sh [-s] <tapefile> <indexdir> <indexPlugin>    
#  -s   index setSpec
#  -p   setSpec Properties
#  tapefile - Absolute path to xmltape
#  indexdir - Absolute path to dir idx files should be written to
#  indexPlugin - Classname of index type 

# A script to manually index a xmltape

JAVA_OPTIONS=-Xmx1500M

tapehome=../
libpath=$tapehome/lib/
for line in `ls -1 $libpath | grep '.jar'`
do
    classpath="$classpath:$libpath$line"
done

java -classpath $classpath $JAVA_OPTIONS gov/lanl/xmltape/index/TapeIndexerApp $*


