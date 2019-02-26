<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim">

<!-- fknudson 20070730 - maps all ISI subjects; with special handling for subject codes -->


<xsl:template name="isiKeywords">
	<xsl:call-template name="isiSubjectCodes"/>
	<xsl:call-template name="isiDEandIDs"/>
</xsl:template>



<!-- remove product code and subject category code before mapping -->
<xsl:template name="isiSubjectCodes">
	<xsl:for-each select= "marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(), 'SC')]">
		<field name="subjects">
		<xsl:value-of select="substring-after(substring-after(../marc:subfield[@code='a'], ' '), ' ')"/>
		</field>
	</xsl:for-each>
</xsl:template>


<!-- map these fields straight -->
<xsl:template name="isiDEandIDs">
	<xsl:for-each select= "marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(), 'DE') or contains(text(), 'ID')]">	
		<field name="subjects">
		<xsl:value-of select="../marc:subfield[@code='a']"/>
		</field>
	</xsl:for-each>
</xsl:template>

</xsl:stylesheet>
