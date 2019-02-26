<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim" xmlns:didl="urn:mpeg:mpeg21:2002:02-DIDL-NS" xmlns:diext="http://library.lanl.gov/2006-09/STB-RL/DIEXT" xmlns:diadm="http://library.lanl.gov/2005-08/aDORe/DIADM/" xmlns:dii="urn:mpeg:mpeg21:2002:01-DII-NS" xmlns:dc="http://purl.org/dc/elements/1.1/" exclude-result-prefixes="marc didl diext diadm dii dc">
	<!--stylesheets to extract SOLR xml from MARC XML-->
	<!-- fknudson 20071031 -->
	<!-- fknudson - cite records  -->

	<xsl:output method="xml"  omit-xml-declaration="yes"/>
	
	<xsl:include href="../SOLR-shared/SOLR-citeDisplay.xsl"/>
	<xsl:include href="../SOLR-shared/SOLR-citeType.xsl"/>
    <xsl:include href="../SOLR-shared/SOLR-dates.xsl"/>
	<xsl:include href="../SOLR-shared/SOLR-displayName.xsl"/>
	<xsl:include href="../SOLR-shared/SOLR-displaySource.xsl"/>
	<xsl:include href="../SOLR-shared/SOLR-docType.xsl"/>
	<xsl:include href="../SOLR-shared/SOLR-IDs.xsl"/>
	<xsl:include href="../SOLR-shared/SOLR-names.xsl"/>
	<xsl:include href="../SOLR-shared/SOLR-numbers.xsl"/>
	<xsl:include href="../SOLR-shared/SOLR-source.xsl"/>
	<xsl:include href="../SOLR-shared/SOLR-semantic.xsl"/>
	<xsl:include href="../SOLR-shared/SOLR-titles.xsl"/>
	<xsl:include href="../SOLR-shared/SOLR-citedSource.xsl"/>
	
	<xsl:template match="/">
		<!--<record>   for testing -->
			<xsl:for-each select="didl:DIDL">
				<!-- build dataset name - 887 details not available in marc collection -->
				<xsl:variable name="datasets">
					<xsl:call-template name="datasetID"/>
				</xsl:variable>
				<xsl:variable name="mainDisplayFields">
			           <xsl:for-each select="didl:Item/didl:Component[1]/didl:Resource[1]/marc:record">
							<xsl:call-template name="nameDisplay"/>
							<xsl:call-template name="sourceDisplay"/>
							<xsl:call-template name="citeDisplayTitle"/>
							<xsl:call-template name="pubDate"/>
						</xsl:for-each>
				</xsl:variable>		
				<xsl:for-each select="didl:Item/didl:Component[2]/didl:Resource[1]/marc:collection/marc:record">
					<xsl:call-template name="createCite">
						<xsl:with-param name="dbList" select="$datasets"/>
						<xsl:with-param name="displayFields" select="$mainDisplayFields"/>
					</xsl:call-template>
				</xsl:for-each>
			</xsl:for-each>
		<!--</record>-->
	</xsl:template>
	
	<xsl:template name="createCite">
		<xsl:param name="dbList"/>
		<xsl:param name="displayFields"/>
		<doc>
			<xsl:call-template name="citeRecID"/>
			<xsl:copy-of select="$dbList"/>
			<xsl:copy-of select="$displayFields"/>
			<xsl:call-template name="citedNames"/>
			<xsl:call-template name="citedSource"/>
			
		</doc>
	</xsl:template>
</xsl:stylesheet>
