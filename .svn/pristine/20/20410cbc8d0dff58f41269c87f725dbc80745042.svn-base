rem ./extractResource.bat
rem R@ Dec 2005
rem  %1 arcname
rem  %2 id
rem  %3 exportdir
rem  %4 exportfilename
rem Usage: ./extractResource.bat <arcFilePath>
rem Example: ./extractResource.bat c:/lanl/data/arc/sci2006.arc info:lanl-repo/ds/ed49ae2b-74d1-41bd-963f-aa4c10ebd805 c:/tmp exportedResource

rem General
set MODLIB=..
set arcfilepath=%1
set id=%2
set exportdir=%3
set exportfilename=%4
set classpath=%MODLIB%\adore-arcfile-1.1.jar;%MODLIB%\heritrix-1.4.0.jar;%MODLIB%\log4j-1.2.8.jar;%MODLIB%\mg4j-0.9.1.jar

rem Application
java -Xmx300M -classpath .;$classpath gov.lanl.arc.heritrixImpl.ARCFileUtilities --verb get --arcfile %arcfilepath% --id %id% --dir %exportdir% --filename %exportfilename%