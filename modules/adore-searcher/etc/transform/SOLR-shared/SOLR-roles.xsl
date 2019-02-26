<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim">
 
<!-- mapping controlled vocab for 700, 710, 720 - hope to use as facet -->
<!-- do we need default value ? -->
<xsl:template name="roles">

<xsl:for-each select= "marc:datafield[@tag='700'] | marc:datafield[@tag='710'] | marc:datafield[@tag='720']">
	<field name="role">
	<xsl:value-of select="./marc:subfield[@code='e']"/>
	</field>
</xsl:for-each>
</xsl:template>

</xsl:stylesheet>
