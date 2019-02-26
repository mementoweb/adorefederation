rem R@ Dec 2005

rem Usage: arcfile-registry.bat <baseUrl> <arcfile>
rem A script to get registry value for specified arcfile

set archome=..
set libpath=%archome%\lib
set classpath=.;.:%libpath%\adore-repo-oaidb-1.1.jar:%libpath%\adore-arcfile-1.1.jar;%libpath%\adore-arcfile-registry-1.1.jar;%libpath%\commons-collections.jar;%libpath%\commons-dbcp.jar;%libpath%\commons-pool-1.1.jar;%libpath%\jaxen-core.jar;%libpath%\jaxen-jdom.jar;%libpath%\jdom.jar;%libpath%\log4j-1.2.8.jar;%libpath%\mysql-connector-java-5.1.5-bin.jar;%libpath%\oaicat.jar;%libpath%\oaiharvester-2.9.jar;%libpath%\saxpath-1.5.jar;%libpath%\xercesImpl.jar

echo java -classpath %classpath% gov/lanl/arc/registry/OAIRegistryMain %1 %2
java -classpath %classpath% gov/lanl/arc/registry/OAIRegistryMain %1 %2


