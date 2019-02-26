#!/bin/sh

# Set Path to Build Environment
ENVROOT=/lanl/adore

cp -p $ENVROOT/adore-archive/dist/*.tar.gz ./packages/
cp -p $ENVROOT/adore-arcfile/dist/*.tar.gz ./packages/
cp -p $ENVROOT/adore-arcfile-registry/dist/*.tar.gz ./packages/
cp -p $ENVROOT/adore-arcfile-resolver/dist/*.tar.gz ./packages/
cp -p $ENVROOT/adore-xmltape/dist/*.tar.gz ./packages/
cp -p $ENVROOT/adore-xmltape-registry/dist/*.tar.gz ./packages/
cp -p $ENVROOT/adore-harvester-oai/dist/*.tar.gz ./packages/
cp -p $ENVROOT/adore-repo-oaidb/dist/*.tar.gz ./packages/
cp -p $ENVROOT/adore-archive-accessor/dist/*.tar.gz ./packages/
cp -p $ENVROOT/adore-xmltape-resolver/dist/*.tar.gz ./packages/
cp -p $ENVROOT/adore-xmltape-xquery/dist/*.tar.gz ./packages/
