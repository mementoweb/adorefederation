#!/bin/sh
# setup environment variables for shell script

if [ `uname` = "Darwin" ] ; then
  export PATH=/System/Library/Frameworks/JavaVM.framework/Versions/1.5/Home/bin:$PATH
  java -version
fi

# Define APP_HOME dynamically
LAUNCHDIR=$PWD
cd ..
APP_HOME=`pwd`
cd $LAUNCHDIR
libpath=$APP_HOME/lib
for line in `ls -1 $libpath | grep '.jar'`
  do
  classpath="$classpath:$libpath/$line"
done
classpath=.:$classpath

CLASSPATH=$classpath
JAVA_OPTS=-Xmx1500M 

#prefix are used to automatically create locator in registry

# Service Registry prefix of XMLTaoe OpenURL Resolver
export ADORE_XMLTAPE_RESOLVERURL="$ADORE_XMLTAPE_RESOLVERURL"

# Service Registry prefix of XMLTape OAI-PMH repository
export ADORE_XMLTAPE_ACCESSORURL="$ADORE_XMLTAPE_ACCESSORURL"

# Service Registry prefix of ARCfile OpenURL Resolver
export ADORE_ARCFILE_RESOLVERURL="$ADORE_ARCFILE_RESOLVERURL"

# Service Registry prefix of OpenURL XQuery Resolver
export ADORE_XMLTAPE_XQUERY_RESOLVERURL="$ADORE_XMLTAPE_XQUERY_RESOLVERURL"

#put interface
export ADORE_SERVICE_REGISTRY_REGISTRYRECORDHANDLERURL="$ADORE_SERVICE_REGISTRY_REGISTRYRECORDHANDLERURL"

#oai interface
export ADORE_SERVICE_REGISTRY_OAIURL="$ADORE_SERVICE_REGISTRY_OAIURL"
