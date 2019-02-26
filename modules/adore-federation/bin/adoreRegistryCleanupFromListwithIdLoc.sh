#!/bin/sh
#usage: adoreRegistryCleanupFromList.sh <fileContainingList>
#
#e.g. adoreRegistryCleanupFromList.sh ./toRemove.list

if [ "$#" -eq 0 ]
then
  echo "Usage: adoreRegistryCleanupFromList.sh <fileContainingList>"
  exit 1
fi

. registryCleanupEnv.sh

for line in `cat $1`;
do
  echo Processing $line
  ./idLocatorCleaner.sh  ../etc/idlocator.properties $line
  ./svcRegistryCleaner.sh $line
  ./registryCleaner.sh $line
done
