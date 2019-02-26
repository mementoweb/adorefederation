#!/bin/sh
# R@ Dec 2005
# Usage: seqreader.bat [-anidm] <tapefile> [identifier]
#  -a   list all
#  -d   list datestamp
#  -i   list identifiers
#  -m   list metadata
#  -n   list admin   
#  -i   read record
#  -s   list status of the tape index 

# A script to manually test seq tape reader

JAVA_OPTIONS=-Xmx1500M

tapehome=../
libpath=$tapehome/lib/

for line in `ls -1 $libpath | grep '.jar'`
do
    classpath="$classpath:$libpath$line"
done

java -classpath $classpath $JAVA_OPTIONS gov/lanl/xmltape/SeqTapeReaderApp $*


