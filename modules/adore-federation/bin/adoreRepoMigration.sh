#!/bin/sh
#$Id: adoreRepoMigration.sh,v 1.1.1.1 2007/03/28 22:53:11 rchute Exp $

INGESTION_PROPERTIES=/lanl/adore/adore-federation/etc/adore.properties

. env.sh

if [ "$#" -eq 0 ]
    then
    echo "Usage: adoreRepoMigration.sh <collectionPrefix> <xmlPath>"
    exit 1
fi

PROFILE=$1
SRC=$2
PROC=1
X=0

if [ `uname` = "Darwin" ]
    then
    PROC=`sysctl -n hw.ncpu`
fi

if [ `uname` = "Linux" ]
    then
    PROC=`grep processor /proc/cpuinfo | wc -l`
fi

if [ `uname` = "SunOS" ]
    then
    PROC=`uname -X | grep 'NumCPU' | perl -pe 's/NumCPU = //g;'`
fi

if [ "$#" -eq 3 ]
    then
    PROC=$3
fi

echo "Simultaneous processes: " $PROC
echo "#" `date`  > processQueue
ls -1 $SRC | grep '.gz' >> processQueue
for line in `cat processQueue | grep '.gz'`
do

 a=`ps | grep "adoreFed" | wc -l`
 #echo $a
 while [ $PROC = $a ]
 do
    echo "Waiting..."
    sleep 60
    a = `ps | grep "adoreFed" | wc -l`
 done

 if [ `uname` = "SunOS" ]
  then
  echo $SRC/$line
  tapename=`echo $line | perl -pe 's/.gz//g;'`
  `gunzip $SRC/$line | nohup ./adoreFederation.sh --config ${INGESTION_PROPERTIES} --profile ${PROFILE} --xmltape - --stdInTapeName ${tapename} > ../logs/${tapename}.log 2>../logs/error.log  &`
  else
  echo $SRC/$line
  tapename=`echo $line | perl -pe 's/.gz//g;'`
  `zcat $SRC/$line | nohup ./adoreFederation.sh --config ${INGESTION_PROPERTIES} --profile ${PROFILE} --xmltape - --stdInTapeName ${tapename} > ../logs/${tapename}.log 2>../logs/error.log  &`
 fi
done
#echo ""  > processQueue
exit 0