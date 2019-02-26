<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim">

<!--fknudson 20070812 -->
<!--specifically for ISI cites - should be faster -->
<!--Title display - uses titleSort - copyfield - handled in SOLR-->
<!--Capture 700, 710, or 720 -->
<!-- fknudson 20070910 added 710 to select statement - was missing corporate authors; left 720 just for completeness -->

<xsl:template name="citeDisplay">
	<xsl:for-each select="marc:datafield[@tag='700'] | marc:datafield[@tag='710'] | marc:datafield[@tag='720']"> 
	<field name="displayName">
	<xsl:value-of select="./marc:subfield[@code='a']"/>
	</field>
	</xsl:for-each>
	<xsl:choose>
	<xsl:when test="marc:datafield[@tag='773']">
		<xsl:call-template name="outputDisplay">
			<xsl:with-param name="type" select="marc:datafield[@tag='773'][1]/marc:subfield[@code='n']"/>
		</xsl:call-template>
	</xsl:when>
	<xsl:otherwise>
		<xsl:call-template name="outputDisplay">
			<xsl:with-param name="type" select="'Patent'"/>
		</xsl:call-template>
	</xsl:otherwise>
</xsl:choose>
</xsl:template>

</xsl:stylesheet>
