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
JAVA_OPTS=-Xmx256M 