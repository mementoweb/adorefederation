#!/bin/sh
#$Id: OAIResource.sh,v 1.1.1.1 2004/11/22 22:53:11 rchute Exp $

. env.sh

if [ "$#" -ne 2 ]
    then
    echo "Usage: OAIResource.sh  <envProp> <projectProp>"
    exit 1
fi

echo java -classpath ${CLASSPATH} ${JAVA_OPTS} gov/lanl/oai/resource/OAIResource --env $1 --project $2
java -classpath ${CLASSPATH} ${JAVA_OPTS} gov/lanl/oai/resource/OAIResource --env $1 --project $2

exit 0
