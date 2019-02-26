#!/bin/sh
#$Id: write2tape.sh,v 1.1.1.1 2004/11/22 22:53:11 liu_x Exp $
#Harvest script from federator and create XMLTape
#A system directory should be created in advance for configurating harvester,
#including harvester.conf, data, log and lastharvesttime.txt
#Exit status: 0:OK, other status check gov/lanl/harvester/ErrorCode.java

#quit function: remove temporary files and write log 
#@usage quit(error code;  sysdir; datestamp)

function quit(){
    echo $1 >  "$2/log/$3/exitcode"   
    echo $3 : exit code $1 >> $2/log/summary
#delete lock file if existed
    rm  "$2/lock.txt" 
}

. env.sh

if [ "$#" -ne 1 ]
    then
    echo "Usage: write2tape.sh  <system dir>"
    exit 1
fi

umask 0022

#read configuration file
.  "$1/harvester.conf"
echo $classpath

#set OAI_set variable
set=${set:+--set $set}

dirname=`date +"%Y%m%d%H%M%S"`
#get absolute directory
bindir=`pwd`
cd $1
sysdir=`pwd`
cd $bindir
tmpdatadir=${sysdir}/data/.$dirname
datadir=${sysdir}/data/$dirname
logdir=${sysdir}/log/$dirname

#set-up
if [ ! -d $sysdir/log ] 
then
     mkdir $sysdir/log;
fi

if [ ! -d $sysdir/data ]
then
     mkdir $sysdir/data;
fi 

if [ ! -d $sysdir/disk1 ]
then
     mkdir $sysdir/disk1;
fi

#lock file
if test -f "$sysdir/lock.txt"
    then   
    echo "Another process is running"  
    exit 2
fi

touch $sysdir/lock.txt;
trap 'quit 3 $sysdir $dirname; echo enter trap; exit 3'


mkdir $tmpdatadir
mkdir $logdir

#read last harvesttime
if test -f "$sysdir/lastharvesttime.txt"
    then
    . "$sysdir/lastharvesttime.txt"
else
    lastharvesttime="1800-01-01T00:00:00Z"
fi

#read until time from federator
until=`java gov/lanl/harvester/Harvester --verb Identify --baseurl $baseURL 2>${logdir}/harvester.log | sed -n -e 's/.*<responseDate[^>]*>//' -e 's/<\/responseDate[^>]*>.*//p' `
echo $until;
matches=`echo -n $lastharvesttime |wc -c`
echo $matches
if [ "$matches" -eq 10 ]
    then
    until=`echo $until | cut -c1-10`
fi
echo $until 
#run the harvester, we must  change directory, otherwise tapeindex has absolute path
cd ${tmpdatadir}
(java  ${JAVA_OPTS} gov/lanl/harvester/Harvester --verb ${verb} --baseurl ${baseURL} --metadataprefix ${metadataprefix}  --from ${lastharvesttime} --until ${until} $set --tapeprefix tape --createtapes ${tmpdatadir} --maxsize 100000000 2>>${logdir}/harvester.log || echo 4 > "${logdir}/exitcode") 

if test -f "${logdir}/exitcode"
    then
    exitcode=`cat ${logdir}/exitcode`
    quit $exitcode $sysdir $dirname   
    exit 4 
else
#commit
    echo ok
    mv ${tmpdatadir}  ${datadir}
    for file in `ls ${datadir} `
      do
      echo $file
      ln -s  ${datadir}/$file ${sysdir}/disk1/$file
    done
    
    echo lastharvesttime=$until >$sysdir/lastharvesttime.txt
    quit 0 $sysdir $dirname
fi
exit 0
