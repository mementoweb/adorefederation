<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim" xmlns:didl="urn:mpeg:mpeg21:2002:02-DIDL-NS" xmlns:diext="http://library.lanl.gov/2006-09/STB-RL/DIEXT" xmlns:diadm="http://library.lanl.gov/2005-08/aDORe/DIADM/" xmlns:dii="urn:mpeg:mpeg21:2002:01-DII-NS" xmlns:dc="http://purl.org/dc/elements/1.1/" exclude-result-prefixes="marc didl diext diadm dii dc">
	<!--stylesheets to extract SOLR xml from MARC XML-->
	<!-- fknudson 20070710 -->
	<!-- fknudson - green version - different approach to indexing  -->

	<xsl:output method="xml"  omit-xml-declaration="yes"/>
	
	<xsl:include href="../mysql-shared/SOLR-abstracts.xsl"/>
	<xsl:include href="../mysql-shared/SOLR-citeDisplay.xsl"/>
	<xsl:include href="../mysql-shared/SOLR-citeType.xsl"/>
    <xsl:include href="../mysql-shared/SOLR-dates.xsl"/>
	<xsl:include href="../mysql-shared/SOLR-displayName.xsl"/>
	<xsl:include href="../mysql-shared/SOLR-displaySource.xsl"/>
	<xsl:include href="../mysql-shared/SOLR-docType.xsl"/>
	<xsl:include href="../mysql-shared/SOLR-IDs.xsl"/>
	<xsl:include href="../mysql-shared/SOLR-keywords.xsl"/>
	<xsl:include href="../mysql-shared/SOLR-keyword-Biosis.xsl"/>
	<xsl:include href="../mysql-shared/SOLR-keyword-Inspec.xsl"/>
	<xsl:include href="../mysql-shared/SOLR-keyword-ISI.xsl"/>
	<xsl:include href="../mysql-shared/SOLR-language.xsl"/>
	<xsl:include href="../mysql-shared/SOLR-LANLflag.xsl"/>
	<xsl:include href="../mysql-shared/SOLR-list.xsl"/>
	<xsl:include href="../mysql-shared/SOLR-names.xsl"/>
	<xsl:include href="../mysql-shared/SOLR-numbers.xsl"/>
	<xsl:include href="../mysql-shared/SOLR-roles.xsl"/>
	<xsl:include href="../mysql-shared/SOLR-source.xsl"/>
	<xsl:include href="../mysql-shared/SOLR-semantic.xsl"/>
	<xsl:include href="../mysql-shared/SOLR-titles.xsl"/>
	<xsl:include href="../mysql-shared/SOLR-655.xsl"/>
	
	
	<!--files used for mapping treatment codes and document types -->
	<xsl:variable name="treatDoc" select="document('../mysql-shared/TreatmentCodes.xml')"/>
	<xsl:variable name="docType" select="document('../mysql-shared/DocumentTypes.xml')"/>
	
	<xsl:template match="/">
			<xsl:for-each select="didl:DIDL">
				<!-- build dataset name - 887 details not available in marc collection -->
				<xsl:variable name="datasets">
					<xsl:call-template name="datasetID"/>
				</xsl:variable>
				
				<xsl:for-each select="didl:Item/didl:Component[1]/didl:Resource[1]/marc:record">
					<xsl:call-template name="createRec">
						<xsl:with-param name="dbList" select="$datasets"/>
					</xsl:call-template>
				</xsl:for-each>
				<xsl:for-each select="didl:Item/didl:Component[2]/didl:Resource[1]/marc:collection/marc:record">
					<xsl:call-template name="createCite">
						<xsl:with-param name="dbList" select="$datasets"/>
					</xsl:call-template>
				</xsl:for-each>
			</xsl:for-each>
	</xsl:template>
	
	
	
	
	<xsl:template name="createRec">
		<xsl:param name="dbList"/>
		<doc>
			<xsl:call-template name="recID"/>
			<xsl:copy-of select="$dbList"/>
			<xsl:call-template name="nameFields"/>
			<xsl:call-template name="titleFields"/>
			<xsl:call-template name="sourceFields"/>
			<xsl:call-template name="abstract"/>
			<xsl:call-template name="keywordFields">
				<xsl:with-param name="dataset" select="$dbList"/>
			</xsl:call-template>
			<xsl:call-template name="handle655">
				<xsl:with-param name="dataset" select="$dbList"/>
			</xsl:call-template>
			<xsl:call-template name="pubDate"/>
	    	<xsl:call-template name="language"/>
			<!--<xsl:call-template name="roles"/>-->
			<xsl:call-template name="docType"/>
			
			<xsl:call-template name="LANLflag"/>
			<xsl:call-template name="mainSemantic">
		        <xsl:with-param name="component" select="1"/>
			</xsl:call-template>
			<xsl:call-template name="associatedSemantic"/>
			<xsl:call-template name="numberFields"/>
			<xsl:call-template name="nameDisplay"/>
			<xsl:call-template name="sourceDisplay"/>
		</doc>
	</xsl:template>
	
	
	<xsl:template name="createCite">
		<xsl:param name="dbList"/>
		<doc>
			<xsl:call-template name="citeRecID"/>
			<xsl:copy-of select="$dbList"/>
			<xsl:call-template name="nameFields"/>
			<xsl:call-template name="sourceFields"/>
			<xsl:call-template name="pubDate"/>
			<xsl:call-template name="docType"/>
			<xsl:call-template name="citeType"/>
			<xsl:call-template name="citeDisplay"/>
			<!--<xsl:call-template name="roles"/>-->
			<xsl:call-template name="mainSemantic">
				<xsl:with-param name="component" select="2"/>
			</xsl:call-template>
			<xsl:call-template name="associatedSemantic"/>
			<xsl:call-template name="numberFields"/>
		</doc>
	</xsl:template>
</xsl:stylesheet>
