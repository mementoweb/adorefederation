#!/bin/sh

# find the command called's root  e.g.  ./build/
dir=`expr $0 : '\(.*\)install.sh'`

#  if it we did not call ./  change to the directory we called
if [ "$dir" != "./" ] ; then
   if [ "$dir" != "" ] ;  then
      echo changing to $dir
      cd "$dir" ;
   fi
fi

if [ "$JAVA_HOME" = "" ] ; then
	echo set JAVA_HOME;
	exit 1;
fi

version=`$JAVA_HOME/bin/java -version 2>&1 | grep 'java version' 2>&1 | awk '{print $3}' | sed 's/\"//g'`
check=`expr "$version" : '\(.*1.5\)'`
echo $version
if [ "$check" = "" ] ; then
        echo JAVA_HOME must be set to Java Version 1.5;
        exit 1;
fi


CLASSPATH=./installlib/xercesImpl.jar:./installlib/xml-apis.jar:./installlib/ant-installer.jar
CLASSPATH=$CLASSPATH:./installlib/ant.jar:./installlib/ant-launcher.jar:./installlib/ant-contrib.jar
CLASSPATH=$CLASSPATH:./installclasspath
CLASSPATH=$CLASSPATH:./installlib/jgoodies-edited-1_2_2.jar
CLASSPATH=$CLASSPATH:./installlib/sysout.jar:$JAVA_HOME/lib/tools.jar

COMMAND=$JAVA_HOME/bin/java
INTERFACE=swing
if [ "$1" = "text" ] ; then
	INTERFACE=text;
fi

$COMMAND -classpath $CLASSPATH  org.tp23.antinstaller.runtime.ExecInstall $INTERFACE .
