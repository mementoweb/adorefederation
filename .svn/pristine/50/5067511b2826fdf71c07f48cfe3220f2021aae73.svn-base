#!/bin/sh
# Usage: arcfile-registry.sh <baseUrl> <arcfile>
# A script to get a registry value for specified arcfile

. env.sh

if [ "$#" -eq 0 ]
    then
    echo "Usage: arcfile-registry.sh <baseUrl> <arcfile>"
    exit 1
fi

echo java -classpath ${CLASSPATH} ${JAVA_OPTS} gov/lanl/arc/registry/OAIRegistryMain $1 $2
java -classpath ${CLASSPATH} ${JAVA_OPTS} gov/lanl/arc/registry/OAIRegistryMain $1 $2

exit 0
