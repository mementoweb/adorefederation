<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim">
 
 <!--fknudson 20070717- maps 041 field -->
 
 <!-- this will be used as facet index in SOLR -->
 
<!-- map all 041s used for facet -->

<xsl:template name="language">

<xsl:for-each select="marc:datafield[@tag='041']">
		<field name="language">
			<xsl:value-of select="./marc:subfield[@code='a']"/>
		</field>
</xsl:for-each>
</xsl:template>

</xsl:stylesheet>
