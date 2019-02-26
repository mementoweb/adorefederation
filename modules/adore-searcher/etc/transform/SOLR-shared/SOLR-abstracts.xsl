<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim">
 
<!-- fknudson 20070827 -->
<!-- maps 520 - may be multiple occurrences -->

<xsl:template name="abstract">

<xsl:for-each select= "marc:datafield[@tag='520']">
	<field name="abstract">
	<xsl:value-of select="./marc:subfield[@code='a']"/>
	</field>
</xsl:for-each>
</xsl:template>

</xsl:stylesheet>
