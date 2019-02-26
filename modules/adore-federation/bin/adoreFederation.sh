#!/bin/sh
#$Id: adoreFederation.sh,v 1.1.1.1 2006/09/28 22:53:11 rchute Exp $

. env.sh

if [ "$#" -eq 0 ]
    then
    echo "Usage: adoreFederation.sh  --config <adoreProp> --profile <collectionPrefix> --xmltape <xmlPath> --arcfile <arcFilePath> --recursive  --stdInTapeName <tapeName>"
    exit 1
fi

echo java -classpath ${CLASSPATH} ${JAVA_OPTS} gov/lanl/adore/repo/AdoreRepository $*
java -classpath ${CLASSPATH} ${JAVA_OPTS} gov/lanl/adore/repo/AdoreRepository $*

exit 0
