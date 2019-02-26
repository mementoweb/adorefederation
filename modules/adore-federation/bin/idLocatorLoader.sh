#!/bin/bash
# ./loader.sh
# $Id$
#
# R@ Sept 2006
# Usage: ./loader.sh <config> <baseurl> <tapeid>
# Example: ./loader.sh ./etc/idlocator.properties http://localhost:8080/adore-xmltape-resolver/resolver/ info:lanl-repo/xmltape/2004_09e1f27a-239e-11da-9e1e-d8ccd1d6c8f2

. env.sh

# General
CONFIG=$1
BASEURL=$2
TAPEID=$3

# Application
echo java -classpath ${CLASSPATH} ${JAVA_OPTS} gov.lanl.locator.DbLoader ${CONFIG} ${BASEURL} ${TAPEID}
java -classpath ${CLASSPATH} ${JAVA_OPTS} gov.lanl.locator.DbLoader ${CONFIG} ${BASEURL} ${TAPEID}

exit 0
