#!/bin/sh
#$Id$
#usage: list.sh <collection|service>
# 	
#e.g.  list.sh collection
#	list.sh service
. env.sh

if [ $# -ne 1 ]
then
	echo "usage: list.sh <collection|service>"
	exit 1	
fi


java -cp $classpath gov.lanl.ockham.client.adore.List $*
