#!/bin/sh
# Usage: registryCleaner.sh <baseUrl> <repo_uri>
# A script to get delete a registry value

. registryCleanupEnv.sh

if [ "$#" -eq 0 ]
    then
    echo "Usage: registryCleaner.sh <baseUrl> <repo_uri>"
    exit 1
fi

echo java -classpath ${CLASSPATH} ${JAVA_OPTS} gov.lanl.repo.RepoUtil -delete -repourl $1 -id $2 
java -classpath ${CLASSPATH} ${JAVA_OPTS} gov.lanl.repo.RepoUtil -delete -repourl  $1  -id $2 

exit 0
