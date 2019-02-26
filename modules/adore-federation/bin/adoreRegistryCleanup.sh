#!/bin/sh
#usage: adoreRegistryCleanup.sh <log_file_of_failed_tape>
#
#e.g. adoreRegistryCleanup.sh /tape04/isi/logs/adore/migration/arc03-isi-migr/ISI_IP3A88AC_c4c02fce-5a3b-11db-8622-b0a8c271beda.log

if [ "$#" -eq 0 ]
then
  echo "Usage: adoreRegistryCleanup.sh <log_file_of_failed_tape>"
  exit 1
fi

. registryCleanupEnv.sh

for line in `egrep 'Adding ' $1 | cut -d' ' -f6`;
do
  echo Processing $line
  ./idLocatorCleaner.sh  ../etc/idlocator.properties $line
  ./svcRegistryCleaner.sh $line
  ./registryCleaner.sh $line
done
