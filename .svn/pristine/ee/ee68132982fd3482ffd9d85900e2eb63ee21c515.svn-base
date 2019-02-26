#!/bin/sh
#a script to manually test harvest operations
source env.sh

if [ "$#" -ne 2 ]
then
	echo "Usage: simple.sh <baseurl> <metadataprefix>"
	echo "example: simple.sh http://jsnc.library.caltech.edu/perl/oai2 oai_dc"
	exit 1
fi

java gov/lanl/harvester/Harvester --verb ListRecords --baseurl $1 --metadataprefix  $2


