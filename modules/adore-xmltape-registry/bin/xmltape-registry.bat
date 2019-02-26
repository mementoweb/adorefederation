rem R@ Dec 2005

rem Usage: xmltape-registry.bat <baseUrl> <tapeName>
rem A script to get registry value for specified tape

set tapehome=..
set libpath=%tapehome%\lib
set classpath=.;.:%libpath%\adore-repo-oaidb-1.0.2.jar:%libpath%\adore-xmltape-1.0.2.jar;%libpath%\adore-xmltape-indexbdb-1.0.2.jar;%libpath%\adore-xmltape-registry-1.0.2.jar;%libpath%\commons-collections.jar;%libpath%\commons-dbcp.jar;%libpath%\commons-pool-1.1.jar;%libpath%\jaxen-core.jar;%libpath%\jaxen-jdom.jar;%libpath%\jdom.jar;%libpath%\log4j-1.2.8.jar;%libpath%\mysql-connector-java-5.1.5-bin.jar;%libpath%\oaicat.jar;%libpath%\oaiharvester-2.9.jar;%libpath%\saxpath-1.5.jar;%libpath%\xercesImpl.jar

echo java -classpath %classpath% gov/lanl/xmltape/registry/OAIRegistryMain  %1 %2
java -classpath %classpath% gov/lanl/xmltape/registry/OAIRegistryMain  %1 %2


