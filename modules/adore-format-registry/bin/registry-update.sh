#!/bin/sh
# Usage: registry-update.sh <baseUrl> <format_uri> <xupdateFilePath>
# A script to update an registry value
# $1 - url of repository like "http://cox.lanl.gov:8080/adore-format-registry/PutRecordHandler"# $2 - identifier to update# $3 - location of xupdate File "examples/xupdate.xml" 

. env.sh

if [ "$#" -eq 0 ]
    then
    echo "Usage: registry-update.sh <baseUrl> <format_uri> <xupdateFilePath>"
    exit 1
fi

echo java -classpath ${CLASSPATH} ${JAVA_OPTS} gov.lanl.repo.RepoUtil -update -repourl $1 -id $2 -xupdate $3
java -classpath ${CLASSPATH} ${JAVA_OPTS} gov.lanl.repo.RepoUtil -update -repourl $1 -id $2 -xupdate $3

exit 0
