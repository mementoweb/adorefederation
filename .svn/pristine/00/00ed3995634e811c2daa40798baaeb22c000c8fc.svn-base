#!/bin/sh

if [ "$#" -eq 0 ]
    then
    echo "Usage: demo.sh  <adoreArchiveInstallationDir>"
    echo "i.e. demo.sh /usr/local/adore"
    exit 1
fi

sh ./adoreArchive.sh --config $1/adore-archive/etc/archive.properties --profile test --xmltape $1/adore-archive/etc/demo/tape/demoTape.xml --arcfile $1/adore-archive/etc/demo/arc/472de88d-5670-45df-9200-fb135189f00e.arc