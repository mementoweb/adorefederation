<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim">

<!-- outputs all items within "List" -->
<xsl:template name="outputList">
<xsl:param name="ctr"/>   <!--counter-->
<xsl:param name="listName"/>  <!--specific listName -->
<xsl:variable name="thisPath">
	<xsl:value-of select="concat($listName, '[',$ctr,']')"/>
</xsl:variable>

<xsl:if test="(marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(), $thisPath)])">
	<field name="subjects">
	<xsl:for-each select="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(), $thisPath)]">
		<xsl:value-of select="../marc:subfield[@code='a']"/>
		<xsl:text> </xsl:text>
	</xsl:for-each>
	</field>
	<xsl:call-template name="outputList">
		<xsl:with-param name="ctr" select="$ctr + 1"/>
		<xsl:with-param name="listName" select="$listName"/>
	</xsl:call-template>
</xsl:if>
</xsl:template>



</xsl:stylesheet>
