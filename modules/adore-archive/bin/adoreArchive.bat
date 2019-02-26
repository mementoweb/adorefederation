@echo off

set JAVA_OPTIONS=-Xmx1500M

echo Launching Adore Archive...
echo "Usage: adoreArchive.bat --config <archiveProp> --profile <collectionPrefix> --xmltape <xmlPath> --arcfile <arcFilePath> --recursive --stdInTapeName <tapeName> --noregister"

%JAVA_HOME%\bin\java %JAVA_OPTIONS% -classpath .;..\lib\adore-archive-1.1.jar;..\lib\adore-arcfile-1.1.jar;..\lib\adore-arcfile-registry-1.1.jar;..\lib\adore-repo-oaidb-1.1.jar;..\lib\adore-xmltape-1.1.jar;..\lib\adore-xmltape-registry-1.1.jar;..\lib\adore-xmltape-resolver-1.1.jar;..\lib\heritrix-1.4.0.jar;..\lib\jaxen-1.1.8.jar;..\lib\jdom.jar;..\lib\log4j-1.2.8.jar;..\lib\mg4j-0.9.1.jar;..\lib\oaicat.jar;..\lib\oaicat.jar;..\lib\xercesImpl.jar;..\lib\xpp3-1.1.3.4.C.jar gov/lanl/archive/AdoreArchive %*
