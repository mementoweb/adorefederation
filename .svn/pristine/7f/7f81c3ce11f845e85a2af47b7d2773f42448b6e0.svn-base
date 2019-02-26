#!/bin/sh
#$Id$
#usage: svcRegistryCleaner.sh <identifier> 
# 	
#e.g. delete.sh info:lanl-repo/xmltape/biosis_2006_wk36_dd5fa744-2e30-11db-aed0-b0a8c271beda  
. registryCleanupEnv.sh

if [ $# -ne 1 ]
then
	echo "usage: svcRegistryCleaner.sh <identifier>"
	exit 1	
fi


java -cp $classpath gov.lanl.ockham.client.adore.Delete $*
