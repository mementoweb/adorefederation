<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim">

<!-- fknudson 20070730 - grabs all "lists" of Inspec keywords as units -->
<!-- some subject groups are handled straight - no combing -->
<!-- one small difficulty here - cingd vs. ucingd - handled the same way -->


<xsl:template name="inspecKeywords">
	<xsl:call-template name="indexData"/>
	<xsl:call-template name="classCodes"/>
	<xsl:call-template name="numericalData"/>
	<xsl:call-template name="chemData"/>
</xsl:template>

<xsl:template name="indexData">
<xsl:for-each select="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'cindg')] | marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'aoig')]"> 
	<field name="subjects">
	<xsl:value-of select="../marc:subfield[@code='a']"/>
	</field>
</xsl:for-each>
</xsl:template>

<xsl:template name="classCodes">
<xsl:if test="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'ccg')]">
	<xsl:call-template name="outputList">
		<xsl:with-param name="ctr" select="1"/>
		<xsl:with-param name="listName" select="'cc'"/>
	</xsl:call-template>
</xsl:if>
</xsl:template>	

<xsl:template name="numericalData">
<xsl:if test="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'ndig')]">
	<xsl:call-template name="outputList">
		<xsl:with-param name="ctr" select="1"/>
		<xsl:with-param name="listName" select="'ndig'"/>
	</xsl:call-template>
</xsl:if>
</xsl:template>	

<xsl:template name="chemData">
<xsl:if test="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'cig')]">
	<xsl:call-template name="outputList">
		<xsl:with-param name="ctr" select="1"/>
		<xsl:with-param name="listName" select="'chemg'"/>
	</xsl:call-template>
</xsl:if>
</xsl:template>	



</xsl:stylesheet>
