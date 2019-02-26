#!/bin/sh
INSTALL=/lanl/projects/installers/oaiResource
TMP=/Users/rchute/tmp

cd $INSTALL  
sh ./cleanUp.sh
sh ./getCurrentPackages.sh
ant -f ./buildInstaller.xml
mkdir $TMP/oaiResource
mkdir $TMP/oaiResourceInstaller
cp $INSTALL/oaiResourceInstaller*.tar.gz $TMP/oaiResourceInstaller/
cd $TMP/oaiResourceInstaller
tar -xzf oaiResource*.tar.gz
sh ./install.sh
rm -rRf $TMP/oaiResourceInstaller

