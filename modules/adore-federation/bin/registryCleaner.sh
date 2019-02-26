#!/bin/sh
#usage: registryGetFileLocation.sh <identifier>
#
#e.g. registryGetFileLocation.sh info:lanl-repo/xmltape/biosis_2006_wk36_dd5fa744-2e30-11db-aed0-b0a8c271beda
. registryCleanupEnv.sh

type=`echo $1 | cut -d'/' -f2`
#echo $type

file=null

if [ $type = 'xmltape' ]
then
#echo "Cleanup for XMLtape: $1"
result=`wget -q -O - ${ADORE_XMLTAPE_BASEURL}?verb=GetRecord\&metadataPrefix=tape\&identifier=${1}`
file=`echo $result | perl -n -e '/XMLtapeFile>file:\/\/(.*)<\/XMLtapeFile/ && print "$1\n"'`
idxDir=`echo $result | perl -n -e '/XMLtapeIndex>file:\/\/(.*)<\/XMLtapeIndex/ && print "$1\n"'`
echo rm -f $file >> ToBeDeleted.sh
echo Added $file to the list of files to be deleted 
echo rm -fRr $idxDir >> ToBeDeleted.sh
echo Added $idxDir to the list of files to be deleted
./registryDelete.sh $ADORE_XMLTAPE_PUTRECORDURL $1
fi

if [ $type = 'arc' ]
then
#echo "Cleanup for ARCfile: $1"
result=`wget -q -O - ${ADORE_ARCFILE_BASEURL}?verb=GetRecord\&metadataPrefix=arc\&identifier=${1}`
file=`echo $result | perl -n -e '/ARCfileFile>file:\/\/(.*)<\/ARCfileFile/ && print "$1\n"'`
idxFile=`echo $result | perl -n -e '/ARCfileIndex>file:\/\/(.*)<\/ARCfileIndex/ && print "$1\n"'`
echo rm -f $file >> ToBeDeleted.sh
echo Added $file to the list of files to be deleted
echo rm -fRr $idxFile >> ToBeDeleted.sh
echo Added $idxFile to the list of files to be deleted
./registryDelete.sh $ADORE_ARCFILE_PUTRECORDURL $1
fi

#echo $file
