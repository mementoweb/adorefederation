#!/bin/sh
INSTALL=/lanl/projects/installers/adoreArchive
TMP=/Users/rchute/tmp

cd $INSTALL  
sh ./cleanUp.sh
sh ./getCurrentPackages.sh
ant -f ./buildInstaller.xml
mkdir $TMP/adoreArchive
mkdir $TMP/adoreArchiveInstaller
cp $INSTALL/adoreArchiveInstaller*.tar.gz $TMP/adoreArchiveInstaller/
cd $TMP/adoreArchiveInstaller
tar -xzf adoreArchiveInstaller*.tar.gz
sh ./install.sh
rm -rRf $TMP/adoreArchiveInstaller


