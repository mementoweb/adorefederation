<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim">

<!--fknudson 20070710 -->
<!--capture all subjects for searching -->
<!-- fknudson 20070802 - excluding 245s starting with !!! from index, display, and sort -->

<xsl:template name="keywordFields">
<xsl:param name="dataset"/>
<xsl:choose>
	<xsl:when test="$dataset = 'BIOSIS'">
		<xsl:call-template name="biosisKeywords"/>
	    <xsl:call-template name="otherSubjectTags"/>
	</xsl:when>
	<xsl:when test="$dataset = 'INSPEC'">
		<xsl:call-template name="inspecKeywords"/>
	<xsl:call-template name="otherSubjectTags"/>
	</xsl:when>
	<xsl:when test="contains($dataset, 'SCI') or contains($dataset, 'SOC') or contains($dataset , 'ART') or contains ($dataset, 'PRO')">
		<xsl:call-template name="isiKeywords"/>
		<xsl:call-template name="otherSubjectTags"/>
	</xsl:when>
	<xsl:otherwise>
		<xsl:for-each select= "marc:datafield[@tag='600'] | marc:datafield[@tag='610'] | marc:datafield[@tag='630'] | marc:datafield[@tag='650'] | marc:datafield[@tag='084'] | marc:datafield[@tag='513']">
			<field name="subjects">
			<xsl:value-of select="./marc:subfield[@code='a']"/>
			</field>
	</xsl:for-each>
</xsl:otherwise>	
</xsl:choose>

</xsl:template>


<!--used by datasets that have special processing for 650 tags -->
<xsl:template name="otherSubjectTags">
<xsl:for-each select= "marc:datafield[@tag='600'] | marc:datafield[@tag='610'] | marc:datafield[@tag='630'] | marc:datafield[@tag='084'] | marc:datafield[@tag='513']">
		<field name="subjects">
		<xsl:value-of select="./marc:subfield[@code='a']"/>
		</field>
</xsl:for-each>
</xsl:template>


</xsl:stylesheet>
