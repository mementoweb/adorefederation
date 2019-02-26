@echo off

set JAVA_OPTIONS=-Xmx1500M

echo Launching Adore Federation Ingestion ...
echo "Usage: adoreFederation.bat --config <adoreProp> --profile <collectionPrefix> --xml <xmlPath> --arcfile <arcFilePath> --recursive --stdInTapeName <tapeName>"

%JAVA_HOME%\bin\java %JAVA_OPTIONS% -classpath .;..\lib\adore-federation-1.1.jar;..\lib\adore-archive-1.1.jar;..\lib\adore-arcfile-1.1.jar;..\lib\adore-arcfile-registry-1.1.jar;..\lib\adore-repo-oaidb-1.1.jar;..\lib\adore-xmltape-1.1.jar;..\lib\adore-xmltape-registry-1.1.jar;..\lib\heritrix-1.4.0.jar;..\lib\jaxen-1.1.8.jar;..\lib\jdom.jar;..\lib\log4j-1.2.8.jar;..\lib\mg4j-0.9.1.jar;..\lib\oaicat.jar;..\lib\oaicat.jar;..\lib\xercesImpl.jar;..\lib\xpp3-1.1.3.4.C.jar gov/lanl/adore/repo/AdoreRepository %*
