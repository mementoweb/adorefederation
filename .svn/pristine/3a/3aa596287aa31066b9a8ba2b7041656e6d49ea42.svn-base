#!/bin/bash
# ./cleaner.sh
# $Id$
#
# R@ Jan 2007
# Usage: ./cleaner.sh <config> <repo_uri>
# Example: ./cleaner.sh ./etc/idlocator.properties info:lanl-repo/xmltape/2004_09e1f27a-239e-11da-9e1e-d8ccd1d6c8f2

. env.sh

# General
CONFIG=$1
REPOURI=$2

# Application
echo java -classpath ${CLASSPATH} ${JAVA_OPTS} gov.lanl.locator.DbCleaner ${CONFIG} ${REPOURI}
java -classpath ${CLASSPATH} ${JAVA_OPTS} gov.lanl.locator.DbCleaner ${CONFIG} ${REPOURI}

exit 0
