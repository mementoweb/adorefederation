#!/bin/sh
#$Id$
#usage: add.sh <collection> <identifier> <created time>
# 	
#e.g. add.sh info:sid/library.lanl.gov:biosis info:lanl-repo/xmltape/biosis_2006_wk36_dd5fa744-2e30-11db-aed0-b0a8c271beda 2002-01-01 
. env.sh

if [ $# -ne 3 ]
then
	echo "usage: add.sh <collection> <identifier> <created time>"
	exit 1	
fi


java -cp $classpath gov.lanl.ockham.client.adore.Add $*
