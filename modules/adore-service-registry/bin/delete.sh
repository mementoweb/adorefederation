#!/bin/sh
#$Id$
#usage: delete.sh <identifier> 
# 	
#e.g. delete.sh info:lanl-repo/xmltape/biosis_2006_wk36_dd5fa744-2e30-11db-aed0-b0a8c271beda  
. env.sh

if [ $# -ne 1 ]
then
	echo "usage: delete.sh <identifier>"
	exit 1	
fi


java -cp $classpath gov.lanl.ockham.client.adore.Delete $*
