#!/bin/sh
#a script to manually test harvest operations
tapehome=../
libpath=$tapehome/lib/

for line in `ls -1 $libpath | grep '.jar'`
do
    classpath="$classpath:$libpath$line"
done

java -cp $classpath gov/lanl/xmltape/index/berkeleydbImpl/TapeReaderApp $*


