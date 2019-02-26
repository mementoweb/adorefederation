<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim">

<!--maps number fields, 013, 020, 020,024, 030, 037, 088, 536, 773$x, 773$y, 773$z -->
<!--fknudson 20070828 - including 010 -->


<xsl:template name="numberFields">
	
	<xsl:for-each select="marc:datafield[@tag='013']">
		<field name="number">
			<xsl:value-of select="./marc:subfield[@code='b']"/>
			<xsl:text> </xsl:text>
			<xsl:value-of select="./marc:subfield[@code='a']"/>
		</field>
	</xsl:for-each> 
	
	<!--removing all spaces from 010, 020, 022, 024, 030 -->
	<xsl:for-each select="marc:datafield[@tag='010'] | marc:datafield[@tag='020'] | marc:datafield[@tag='022'] | marc:datafield[@tag='024'] | marc:datafield[@tag='030']">
			<field name="number">
				<xsl:value-of select="translate(normalize-space(./marc:subfield[@code='a']),' ', '')"/>
			</field>
	</xsl:for-each>
	
		
	<!--037 handled separately - code a may not exist -->
	<xsl:for-each select="marc:datafield[@tag='037']">
	<xsl:if test="./marc:subfield[@code='a'] != '' ">
			<field name="number">
				<xsl:value-of select="./marc:subfield[@code='b']"/>
				<xsl:value-of select="./marc:subfield[@code='a']"/>
			</field>
	</xsl:if>
	</xsl:for-each>
	
	<!-- leaving spaces in 088 -->
	<xsl:for-each select="marc:datafield[@tag='088']">
			<field name="number">
				<xsl:value-of select="./marc:subfield[@code='a']"/>
			</field>
	</xsl:for-each>
	
	<!-- 536 -->
	<xsl:for-each select="marc:datafield[@tag='536']">
		<field name="number">
				<xsl:value-of select="./marc:subfield[@code='b']"/>
			</field>
		</xsl:for-each>
		
	<!-- 773 issn, coden, isbn ; can be multiple values -->
	<xsl:for-each select="marc:datafield[@tag='773']/marc:subfield[@code='x'] | marc:datafield[@tag='773']/marc:subfield[@code='y'] | marc:datafield[@tag='773']/marc:subfield[@code='z'] ">
		<field name="number">
			<xsl:value-of select="translate(normalize-space(.),' ', '')"/>
		</field>
	</xsl:for-each>
	
</xsl:template>




</xsl:stylesheet>
