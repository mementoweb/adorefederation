#!/bin/sh
# R@ Dec 2005
# Usage: tapecreate.sh [options] -i <input> -o <tape> -p <properties>
#  input - directory/file name (Use - for stdin)
#  tape  - output xmltape
#  properties - Tape Creation Properties file

# A script to create XMLTape repositories.

JAVA_OPTIONS=-Xmx1500M

tapehome=../
libpath=$tapehome/lib/

for line in `ls -1 $libpath | grep '.jar'`
do
    classpath="$classpath:$libpath$line"
done

java -classpath $classpath $JAVA_OPTIONS -Djava.util.logging.config.file=module.conf gov/lanl/xmltape/create/TapeCreate $*


