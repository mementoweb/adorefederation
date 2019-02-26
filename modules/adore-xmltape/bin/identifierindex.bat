rem R@ Feb 2007

rem Usage: identifierindex.bat [-s] <tapefile> <indexdir> <indexPlugin> 
rem  tapefile - Absolute path to xmltape
rem  indexdir - Absolute path to dir idx files should be written to
rem  indexPlugin - Classname of index type 

rem A script to manually index a xmltape

set JAVA_OPTIONS=-Xmx1500M

set tapehome=..\
set libpath=%tapehome%\lib\
set classpath=.;%libpath%\adore-xmltape-1.1.jar;%libpath%\commons-cli-1.0.jar;%libpath%\log4j-1.2.8.jar;%libpath%\oaicat.jar;%libpath%\oaiharvester-2.9.jar;%libpath%\servlet.jar;%libpath%\xpp3-1.1.3.4.C.jar


java -classpath %classpath% %JAVA_OPTIONS% gov/lanl/xmltape/identifier/index/IdentifierIndexerApp %*


