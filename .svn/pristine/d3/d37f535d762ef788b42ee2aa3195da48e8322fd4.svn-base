
rem ./arcfile-index.cmd

rem R@ Dec 2005
rem  %1 arcname
rem Usage: ./arcfile-index.cmd <arcFilePath>
rem Example: ./arcfile-index.cmd c:/lanl/data/arc/sci2006.arc

set MODNAME=adore-arcfile

rem General
MODLIB=..
arcfilepath=%1
set classpath=%MODLIB%\adore-arcfile-1.1.jar;%MODLIB%\heritrix-1.4.0.jar;%MODLIB%\log4j-1.2.8.jar;%MODLIB%\mg4j-0.9.1.jar

rem Application
java -Xmx300M -classpath .;$classpath gov.lanl.arc.heritrixImpl.ARCFileUtilities --verb index --arcfile %arcfilepath%
