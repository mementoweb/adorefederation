<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim">

<!--fknudson 20070710 -->
<!--map source fields for searching -->
<!--pulled from 013, 524, 580 773, and 856 -->
<!--fknudson 20070928 - added journalExact template -->

 
<xsl:template name="sourceFields">
	<xsl:call-template name="source013"/>
	<xsl:call-template name="source524"/>
	<xsl:call-template name="source580"/>
	<xsl:call-template name="source773"/>
	<xsl:call-template name="source856"/>
	<xsl:call-template name="journalExact"/>
</xsl:template>


<xsl:template name="source013">
<xsl:for-each select="marc:datafield[@tag='013']">
		<field name="source">
			<xsl:value-of select="./marc:subfield[@code='b']"/>
			<xsl:text> </xsl:text>
			<xsl:value-of select="./marc:subfield[@code='a']"/>
			<xsl:text> </xsl:text>
			<xsl:value-of select="./marc:subfield[@code='d']"/>
			<xsl:text> </xsl:text>
			<xsl:value-of select="./marc:subfield[@code='c']"/>
		</field>
</xsl:for-each>
</xsl:template>
	

<!-- grab 524 for ECD and LANLTRS -->
<xsl:template name="source524">
<xsl:for-each select="marc:datafield[@tag='524']">
	<field name="source">
	<xsl:value-of select="./marc:subfield[@code='a']"/>
	</field>
</xsl:for-each>
</xsl:template>

<!--grab 580 for Biosis -->
<xsl:template name="source580">
<xsl:for-each select="marc:datafield[@tag='580']">
	<field name="source">
	<xsl:value-of select="./marc:subfield[@code='a']"/>
	</field>
</xsl:for-each>
</xsl:template>


<xsl:template name="source773">
<!--grab article number from 024 if present -->
<xsl:variable name="artNo">
	<xsl:call-template name="findArtNo"/>
</xsl:variable>

<xsl:if test="marc:datafield[@tag='773']">
<!-- for searching -->
<xsl:for-each select="marc:datafield[@tag='773']">
<field name="source">
	<xsl:value-of select="marc:subfield[@code='t']"/>
	<xsl:text> </xsl:text>
	<xsl:value-of select="marc:subfield[@code='p']"/>
	<xsl:text>  </xsl:text>
	<xsl:value-of select="marc:subfield[@code='d']"/>
	<xsl:text>  </xsl:text>
	<xsl:value-of select="marc:subfield[@code='g']"/>
	<xsl:text>  </xsl:text>
	<xsl:for-each select="marc:subfield[@code='x'] | marc:subfield[@code='y'] | marc:subfield[@code='z']">
		<xsl:value-of select="translate(normalize-space(.),' ', '')"/>
		<xsl:text>  </xsl:text>
	</xsl:for-each>
	<xsl:if test="marc:subfield[@code='n'][. = 'Journal'] and  $artNo != ''">
		<xsl:value-of select="$artNo"/>
	</xsl:if>
</field>
</xsl:for-each>
</xsl:if>


</xsl:template>

<xsl:template name="findArtNo">
<xsl:variable name="temp">
<xsl:for-each select="marc:datafield[@tag='024']/marc:subfield[@code='2'][contains(text(), 'EI : AR')] | marc:datafield[@tag='024']/marc:subfield[@code='2'][contains(text(), 'EIX : AR')] | marc:datafield[@tag='024']/marc:subfield[@code='2'][contains(text(), 'ISI : AR')] | marc:datafield[@tag='024']/marc:subfield[@code='2'][contains(text(), 'ISI : RS')]">
	<xsl:value-of select="../marc:subfield[@code='a']"/>
</xsl:for-each>
</xsl:variable>
<xsl:variable name="final">
	<xsl:if test="contains($temp, 'ARTN')">
		<xsl:value-of select="substring-after($temp, 'ARTN ')"/>
	</xsl:if>
</xsl:variable>
<xsl:value-of select="$final"/>
</xsl:template>


<xsl:template name="source856">
	<xsl:for-each select="marc:datafield[@tag='856']">
		<field name="source">
			<xsl:value-of select="./marc:subfield[@code='u']"/>
		</field>
	</xsl:for-each>
</xsl:template>

<xsl:template name="journalExact">
	<xsl:for-each select="marc:datafield[@tag='773']/marc:subfield[@code='n'][contains(text(),'ournal')]">
		<field name="journalExact">
			<xsl:value-of select="../marc:subfield[@code='t']"/>
		</field>
	</xsl:for-each>
</xsl:template>


</xsl:stylesheet>
