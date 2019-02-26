#!/bin/bash
# ./extractResource.sh
# $Id$
#
# R@ Sept 2005
# $1 arcname
# Usage: ./extractResource.sh <arcFilePath> <id> <exportDir> <exportFileName>
# Example: ./extractResource.sh /lanl/data/arc/sci2006.arc info:lanl-repo/ds/ed49ae2b-74d1-41bd-963f-aa4c10ebd805 /tmp exportedResource

. env.sh

if [ "$#" -eq 0 ]
    then
    echo "Usage: extractResource.sh <arcFilePath> <id> <exportDir> <exportFileName>"
    exit 1
fi

ARCFILEPATH=$1
RESOURCEURI=$2
EXPORTDIR=$3
EXPORTFILE=$4

#INDEXFILE=${ARCFILEPATH##/*/}  # Strip Filepath
INDEXFILE=${ARCFILEPATH%.arc}   # Strip Arc Suffix
INDEXFILE=${INDEXFILE}.cdx   # Add Cdx Suffix
#echo ${INDEXFILE}

# Application
if [ ! -f ${INDEXFILE} ]
then
     echo "Generating ARCfile index @ ${INDEXFILE}"
     java -classpath ${CLASSPATH} ${JAVA_OPTS} gov.lanl.arc.heritrixImpl.ARCFileUtilities --verb index --arcfile ${ARCFILEPATH}
fi 

java -classpath ${CLASSPATH} ${JAVA_OPTS} gov.lanl.arc.heritrixImpl.ARCFileUtilities --verb get --arcfile ${ARCFILEPATH} --id ${RESOURCEURI} --dir ${EXPORTDIR} --filename ${EXPORTFILE}
