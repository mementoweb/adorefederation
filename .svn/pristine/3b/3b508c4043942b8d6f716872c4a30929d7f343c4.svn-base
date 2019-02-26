<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim">
 
<!-- fknudson 20070829 - mapping implicit cite code -->
<!-- only appears in ISI A&H -->
<xsl:template name="citeType">

<xsl:if test="marc:datafield[@tag='773']/marc:subfield['o']">
	<xsl:choose>
		<xsl:when test="marc:datafield[@tag='773']/marc:subfield['o'][.='C']">
			<field name="citeType">Implicit cite</field>
		</xsl:when>
		<xsl:when test="marc:datafield[@tag='773']/marc:subfield['o'][.='I']">
			<field name="citeType">Illustration</field>
		</xsl:when>
		<xsl:when test="marc:datafield[@tag='773']/marc:subfield['o'][.='M']">
			<field name="citeType">Music score</field>
		</xsl:when>
	</xsl:choose>
</xsl:if>
</xsl:template>

</xsl:stylesheet>
