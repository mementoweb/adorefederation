rem R@ Dec 2005

rem Usage: tapecreate.bat [options] -i <input> -o <tape> -p <properties>
rem  input - directory/file name
rem  tape - output xmltape
rem  properties - Tape Creation Properties file

rem A script to create XMLTape repositories.

set JAVA_OPTIONS=-Xmx1500M

set tapehome=..\
set libpath=%tapehome%\lib\
set classpath=.;%libpath%\adore-xmltape-1.0.2.jar;%libpath%\commons-cli-1.0.jar;%libpath%\log4j-1.2.8.jar;%libpath%\oaicat.jar;%libpath%\oaiharvester-2.9.jar;%libpath%\servlet.jar;%libpath%\xpp3-1.1.3.4.C.jar


java -classpath %classpath% %JAVA_OPTIONS% -Djava.util.logging.config.file=module.conf gov/lanl/xmltape/create/TapeCreate %*



