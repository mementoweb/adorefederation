<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim">

<!-- fknudson 20070718 -->

<!--using controlled vocabulary and presence of tags to set document type -->
<!--if all tests fail - assign book as default -->
<!--document can have multiple values -->
<!--all documents should have at least one value; default value is book -->

<xsl:template name="docType">

<xsl:variable name="docList">

<xsl:if test="marc:datafield[@tag='013']">
	<field name="docType">Patent</field>
</xsl:if>
<xsl:if test="marc:datafield[@tag='088']">
	<field name="docType">Report</field>
</xsl:if>
<xsl:if test="marc:datafield[@tag='711']">
	<field name="docType">Conference</field>
</xsl:if>
	
	
<xsl:if test="marc:datafield[@tag='773']/marc:subfield[@code='n'][contains(text(), 'ournal')]">
	<field name="docType">Journal</field>
</xsl:if>
<xsl:if test="marc:datafield[@tag='773']/marc:subfield[@code='n'][contains(text(), 'ook')]">
	<field name="docType">Book</field>
</xsl:if>
<xsl:if test="marc:datafield[@tag='773']/marc:subfield[@code='n'][contains(text(), 'Report')]">
	<field name="docType">Report</field>
</xsl:if>
</xsl:variable>

<xsl:variable name="finalDoc">
<!-- add default if no value is present -->
<xsl:choose>
	<xsl:when test="$docList = '' ">
	<field name="docType">Book</field>
	</xsl:when>
	<xsl:otherwise>
		<xsl:copy-of select="$docList"/>
	</xsl:otherwise>
</xsl:choose>
</xsl:variable>

<xsl:copy-of select="$finalDoc"/>

</xsl:template>
</xsl:stylesheet>
