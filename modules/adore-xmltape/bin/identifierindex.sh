#!/bin/sh
# R@ Feb 2007
# Usage: ./identifierindex.sh [-s] <tapefile> <indexdir> <indexPlugin>    
#
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

java -classpath $classpath $JAVA_OPTIONS gov/lanl/xmltape/identifier/index/IdentifierIndexerApp $*


