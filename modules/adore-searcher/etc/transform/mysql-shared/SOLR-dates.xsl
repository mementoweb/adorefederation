<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim">
 
 <!--fknudson 20070717- maps date fields -->
 
 <!-- this will be used as facet index in SOLR -->
 <!--4 pipes are mapped to noDate - this constant can be changed -->
 
 <xsl:template name="pubDate">
	<xsl:variable name="date" select="substring(marc:controlfield[@tag='008'],8,4)"/>
	<xsl:choose>
		<xsl:when test="$date = '||||' ">
			<field name="pubDate">noDate</field>
		</xsl:when>
		<xsl:otherwise>
			<field name="pubDate"><xsl:value-of select="$date"/></field>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

</xsl:stylesheet>
