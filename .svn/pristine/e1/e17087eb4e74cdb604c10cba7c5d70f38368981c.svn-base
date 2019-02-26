#!/bin/sh
# Usage: registry-delete.sh <baseUrl> <objectXml>
# A script to put an registry value

. env.sh

if [ "$#" -eq 0 ]
    then
    echo "Usage: registry-put.sh <baseUrl> <objectXml>"
    exit 1
fi

echo java -classpath ${CLASSPATH} ${JAVA_OPTS} gov.lanl.repo.RepoUtil -put -repourl $1  -xmlfile $2 
java -classpath ${CLASSPATH} ${JAVA_OPTS} gov.lanl.repo.RepoUtil -put -repourl $1  -xmlfile $2 

exit 0
