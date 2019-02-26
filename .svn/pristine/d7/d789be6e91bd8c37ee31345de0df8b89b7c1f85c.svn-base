#!/bin/sh
# Usage: registry-delete.sh <baseUrl> <record_uri>
# A script to get delete a registry value

. env.sh

if [ "$#" -eq 0 ]
    then
    echo "Usage: registry-delete.sh <baseUrl> <record_uri>"
    exit 1
fi

echo java -classpath ${CLASSPATH} ${JAVA_OPTS} gov.lanl.repo.RepoUtil -delete -repourl $1 -id $2 
java -classpath ${CLASSPATH} ${JAVA_OPTS} gov.lanl.repo.RepoUtil -delete -repourl  $1  -id $2 

exit 0
