#!/bin/sh

# Set Path to Build Environment
ENVROOT=/lanl/adore/modules

cp -p $ENVROOT/adore-deref/dist/*.tar.gz ./packages/
cp -p $ENVROOT/adore-harvester-oai/dist/*.tar.gz ./packages/
cp -p $ENVROOT/adore-xmlsig/dist/*.tar.gz ./packages/
cp -p $ENVROOT/oai-resource/dist/*.tar.gz ./packages/
