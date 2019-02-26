#!/bin/sh
#$Id: adoreRegistryChecker.sh,v 1.1.1.1 2006/09/28 22:53:11 rchute Exp $

. env.sh

if [ "$#" -eq 0 ]
    then
    echo "Usage: adoreRegistryChecker.sh  --config <adoreProp> --profile <collectionPrefix> --xmltape <xmlPath> --arcfile <arcFilePath> --recover"
    exit 1
fi

echo java -classpath ${CLASSPATH} ${JAVA_OPTS} gov.lanl.adore.diag.AdoreRegistryChecker $*
java -classpath ${CLASSPATH} ${JAVA_OPTS} gov.lanl.adore.diag.AdoreRegistryChecker $*

exit 0
