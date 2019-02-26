#!/bin/bash
#
# $Id$
# $1 /mnt/scratch/aps_test 
# A system directory should be created in advance with ingest.conf,lastingest.txt and log4j.properties

if [ "$#" -ne 1 ]
    then
    echo "Usage: run.sh  <deref system dir>"
    exit 1
fi 

classpath=.
libpath=../lib/
for line in `ls -1 $libpath | grep '.jar'` 
do 
    classpath="$classpath:$libpath$line" 
done 
 

 java  -classpath .:$classpath -Xmx1500M gov.lanl.ingest.oaitape.DirIngester $1     
