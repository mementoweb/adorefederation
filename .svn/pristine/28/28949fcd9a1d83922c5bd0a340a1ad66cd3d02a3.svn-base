#!/bin/sh
#$Id: adoreArchive.sh,v 1.1.1.1 2005/11/10 22:53:11 rchute Exp $

. env.sh

if [ "$#" -eq 0 ]
    then
    echo "Usage: adoreArchive.sh  --config <archiveProp> --profile <collectionPrefix> --xmltape <xmlPath> --arcfile <arcFilePath> --recursive --stdInTapeName <tapeName> --noregister"
    exit 1
fi

echo java -classpath ${CLASSPATH} ${JAVA_OPTS} gov/lanl/archive/AdoreArchive $*
java -classpath ${CLASSPATH} ${JAVA_OPTS} gov/lanl/archive/AdoreArchive $*

exit 0
