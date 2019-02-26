#!/bin/sh
#a script to determine index space usage
tapehome=../
libpath=$tapehome/lib/

for line in `ls -1 $libpath | grep '.jar'`
do
    classpath="$classpath:$libpath$line"
done

java -Xmx512M -classpath $classpath com.sleepycat.je.util.DbSpace -h $1 -r