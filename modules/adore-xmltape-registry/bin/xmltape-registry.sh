#!/bin/sh
# Usage: xmltape-registry.sh <baseUrl> <tape_uri>
# A script to get registry value for specified tape

. env.sh

if [ "$#" -eq 0 ]
    then
    echo "Usage: xmltape-registry.sh <baseUrl> <tape_uri>"
    exit 1
fi

echo java -classpath ${CLASSPATH} ${JAVA_OPTS} gov/lanl/xmltape/registry/OAIRegistryMain 
java -classpath ${CLASSPATH} ${JAVA_OPTS} gov/lanl/xmltape/registry/OAIRegistryMain $1 $2

exit 0
