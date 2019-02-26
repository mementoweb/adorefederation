#!/bin/sh

JAVA_OPTS="-Xmx256M"

JARS=../lib/solr-lanl-0.9.jar:../lib/apache-solr-1.2.0.jar:../lib/commons-codec-1.3.jar:../lib/commons-csv-0.1-SNAPSHOT.jar:../lib/commons-fileupload-1.2.jar:../lib/commons-io-1.2.jar:../lib/easymock.jar:../lib/lucene-analyzers-2007-05-20_00-04-53.jar:../lib/lucene-core-2007-05-20_00-04-53.jar:../lib/lucene-highlighter-2007-05-20_00-04-53.jar:../lib/lucene-snowball-2007-05-20_00-04-53.jar:../lib/lucene-spellchecker-2007-05-20_00-04-53.jar:../lib/post.jar:../lib/servlet-api-2.4.jar:../lib/xpp3-1.1.3.4.O.jar

echo === indexing $1 === 
java -Xmx1024M -Xms200M -Djava.util.logging.config.file=logging.properties -Dsolr.solr.home=.. $JAVA_OPTS -cp $JARS gov.lanl.adore.solr.XMLtapeSolrIndexer $*
