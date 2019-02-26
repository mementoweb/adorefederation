#!/bin/bash
# ./idLocatorClient.sh
# $Id$
#
# R@ Sept 2006
# Usage: ./idLocatorClient.sh <config> <commaDelimitedIdList>
# Example: ./idLocatorClient.sh ./etc/idlocator.properties info:doi/10.1007/s10610-004-3412-1
# Output: <id>,<repoId>,<date>

# General
moddir=..
config=$1
idList=$2

libpath=${moddir}/lib/
classpath=.
for line in `ls -1 $libpath | grep '.jar'` 
do 
    classpath="${classpath}:${libpath}$line" 
done 

#echo $classpath

# Application
java -classpath .:$classpath gov.lanl.locator.IdLocatorClient $config $idList
