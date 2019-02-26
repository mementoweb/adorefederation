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
export ADORE_XMLTAPE_RESOLVERURL="http://adore05.lanl.gov:8080/adore-xmltape-resolver/resolver"

# Service Registry prefix of XMLTape OAI-PMH repository
export ADORE_XMLTAPE_ACCESSORURL="http://adore11.lanl.gov:8080/adore-archive-accessor/Handler/"

# Service Registry prefix of ARCfile OpenURL Resolver
export ADORE_ARCFILE_RESOLVERURL="http://adore12.lanl.gov:8080/adore-arcfile-resolver/resolver"

# Service Registry prefix of OpenURL XQuery Resolver
export ADORE_XMLTAPE_XQUERY_RESOLVERURL="http://adore07.lanl.gov:8080/adore-xmltape-xquery/resolver"

#put interface
export ADORE_SERVICE_REGISTRY_REGISTRYRECORDHANDLERURL="http://adore14.lanl.gov:8080/adore-service-registry/RegistryRecordHandler"

#oai interface
export ADORE_SERVICE_REGISTRY_OAIURL="http://adore14.lanl.gov:8080/Registry/oaicat/OAIHandler"

# XMLtape Registry PutRecord BaseURL
export ADORE_XMLTAPE_BASEURL="http://adore14.lanl.gov:8080/adore-xmltape-registry/OAIHandler"

# ARCfile Registry PutRecord BaseURL
export ADORE_ARCFILE_BASEURL="http://adore14.lanl.gov:8080/adore-arcfile-registry/OAIHandler"    

# XMLtape Registry PutRecord BaseURL
export ADORE_XMLTAPE_PUTRECORDURL="http://adore14.lanl.gov:8080/adore-xmltape-registry/PutRecordHandler" 

# ARCfile Registry PutRecord BaseURL
export ADORE_ARCFILE_PUTRECORDURL="http://adore14.lanl.gov:8080/adore-arcfile-registry/PutRecordHandler"
