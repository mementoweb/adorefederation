@echo off
REM #!/bin/sh
if  "%JAVA_HOME%" == "" goto nojava

set CLASSPATH=.\installlib\xercesImpl.jar;.\installlib\xml-apis.jar;.\installlib\ant-installer.jar
set CLASSPATH=%CLASSPATH%;.\installlib\ant.jar;.\installlib\ant-launcher.jar;.\installlib\ant-contrib.jar
set CLASSPATH=%CLASSPATH%;.\installclasspath
set CLASSPATH=%CLASSPATH%;.\installlib\jgoodies-edited-1_2_2.jar
set CLASSPATH=%CLASSPATH%;.\installlib\metouia.jar
set CLASSPATH=%CLASSPATH%;.\installlib\sysout.jar

if "%1" == "text" goto settext
if "%1" == "swing" goto setswing
goto setswing

:settext
set COMMAND=%JAVA_HOME%\bin\java
set INTERFACE=text
goto install

:setswing
set COMMAND=%JAVA_HOME%\bin\javaw
set INTERFACE=swing
goto install

:install

start %COMMAND% -classpath %CLASSPATH%  org.tp23.antinstaller.runtime.ExecInstall %INTERFACE% .
goto end

:nojava
echo you must install java to run this applicaiton
goto end


:end
