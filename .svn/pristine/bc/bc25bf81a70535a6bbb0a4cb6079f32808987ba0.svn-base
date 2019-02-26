#!/bin/sh
# R@ Dec 2005
# Usage: createdidltape.sh -i <input> -o <tape>
#  input - directory/file name (Use - for stdin)
#  tape  - output xmltape

# A script to create DIDL XMLTape

if [ "$#" -ne 2 ]
	then 
	echo "Usage: createdidltape.sh -i <input> -o <tape> -p <properties>"
	exit 1
fi

tapecreate.sh --RegexIdentifier='DIDLDocumentId="([^"]+)"' -e 'diext:DIDLDocumentCreated="([^"]+)"' --RegexRecordMatch='(<didl:DIDL.*?</didl:DIDL>)' $*

