#!/bin/bash
# ./arcfile-index.sh
#
# R@ Sept 2005
# $1 arcname
# Usage: ./arcfile-index.sh <arcFilePath>
# Example: ./arcfile-index.sh /lanl/data/arc/sci2006.arc

. env.sh

if [ "$#" -eq 0 ]
    then
    echo "Usage: arcfile-index.sh <arcFilePath>"
    exit 1
fi

ARCFILEPATH=$1

# Application
echo "Generating ARCfile index"
java -classpath ${CLASSPATH} ${JAVA_OPTS} gov.lanl.arc.heritrixImpl.ARCFileUtilities --verb index --arcfile ${ARCFILEPATH}