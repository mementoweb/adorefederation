rem R@ Dec 2005

rem Usage: seqreader.bat [-anidm] <tapefile> [identifier]
rem  -a   list all
rem  -d   list datestamp
rem  -i   list identifiers
rem  -m   list metadata
rem  -n   list admin   
rem  -i   read record
rem  -s   list status of the tape index 

rem A script to manually test seq tape reader

set JAVA_OPTIONS=-Xmx1500M

set tapehome=..\
set libpath=%tapehome%\lib\
set classpath=.;%libpath%\adore-xmltape-1.0.2.jar;%libpath%\commons-cli-1.0.jar;%libpath%\log4j-1.2.8.jar;%libpath%\oaicat.jar;%libpath%\oaiharvester-2.9.jar;%libpath%\servlet.jar;%libpath%\xpp3-1.1.3.4.C.jar


java -classpath %classpath% %JAVA_OPTIONS% gov/lanl/xmltape/SeqTapeReaderApp %*


