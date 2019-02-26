#!/bin/sh
#a script to read an identifier from an index
tapehome=../
libpath=$tapehome/lib/

for line in `ls -1 $libpath | grep '.jar'`
do
    classpath="$classpath:$libpath$line"
done

java -Xmx512M -Xms512M -cp $classpath gov/lanl/xmltape/identifier/index/IdentifierIndexReaderApp $*
