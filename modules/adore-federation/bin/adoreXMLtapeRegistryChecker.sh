#!/bin/sh
#$Id: adoreXMLtapeRegistryChecker.sh,v 1.1.1.1 2006/09/28 22:53:11 rchute Exp $

. env.sh

if [ "$#" -eq 0 ]
    then
    echo "Usage: adoreXMLtapeRegistryChecker.sh  --config <adoreProp> --profile <collectionPrefix> --xmltape <xmlPath>"
    exit 1
fi

echo java -classpath ${CLASSPATH} ${JAVA_OPTS} gov.lanl.adore.diag.AdoreXMLtapeRegistryChecker $*
java -classpath ${CLASSPATH} ${JAVA_OPTS} gov.lanl.adore.diag.AdoreXMLtapeRegistryChecker $*

exit 0
