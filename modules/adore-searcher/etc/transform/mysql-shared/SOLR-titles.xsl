<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim">

<!-- fknudson@lanl.gov 20070827 -->
<!--maps article titles, conference titles, sort titles -->

<xsl:template name="titleFields">
	<xsl:call-template name="articleTitleFields"/>
	<xsl:call-template name="conferenceTitle"/>
	<xsl:call-template name="sortTitle"/>
</xsl:template>


<xsl:template name="articleTitleFields">
<xsl:for-each select= "marc:datafield[@tag='242'] | marc:datafield[@tag='245'] | marc:datafield[@tag='246'][not(contains(text(), '!!!'))]">
	<field name="title">
	<xsl:value-of select="./marc:subfield[@code='a']"/>
	<xsl:if test="./marc:subfield[@code='b']">
	<xsl:text> </xsl:text>
	<xsl:value-of select="./marc:subfield[@code='b']"/>
	</xsl:if>
	</field>
</xsl:for-each>
</xsl:template>


<xsl:template name="conferenceTitle">
<!-- no added punctuation - separating by spaces - not worrying about trailing spaces -->
<xsl:for-each select="marc:datafield[@tag='711']">
	<field name="title">
		<xsl:value-of select="./marc:subfield[@code='a']"/>
		<xsl:text> </xsl:text>
		<xsl:value-of select="./marc:subfield[@code='d']"/>
		<xsl:text> </xsl:text>
		<xsl:value-of select="./marc:subfield[@code='c']"/>
		<xsl:text> </xsl:text>
		<xsl:value-of select="./marc:subfield[@code='t']"/>
	</field>
</xsl:for-each>
</xsl:template>



<xsl:template name="sortTitle">
<xsl:if test="marc:datafield[@tag='245']/marc:subfield[@code='a'][not(contains(text(), '!!!'))]">
	<field name="titleSort">
		<xsl:value-of select="marc:datafield[@tag='245']/marc:subfield[@code='a']"/>
		<xsl:if test="marc:datafield[@tag='245']/marc:subfield[@code='b']">
		<xsl:text> : </xsl:text>
		<xsl:value-of select="marc:datafield[@tag='245']/marc:subfield[@code='b']"/>
		</xsl:if>
		<xsl:if test="marc:datafield[@tag='245']/marc:subfield[@code='n']">
		<xsl:text> : </xsl:text>
		<xsl:value-of select="marc:datafield[@tag='245']/marc:subfield[@code='n']"/>
		</xsl:if>
	</field>
</xsl:if>
</xsl:template>

<!-- fknudson 20071127 -->
<!-- used for cites - DisplayTitle field - eliminates need for copyField of titleSort -->
<xsl:template name="citeDisplayTitle">
<xsl:if test="marc:datafield[@tag='245']/marc:subfield[@code='a'][not(contains(text(), '!!!'))]">
	<field name="displayTitle">
		<xsl:value-of select="marc:datafield[@tag='245']/marc:subfield[@code='a']"/>
		<xsl:if test="marc:datafield[@tag='245']/marc:subfield[@code='b']">
		<xsl:text> : </xsl:text>
		<xsl:value-of select="marc:datafield[@tag='245']/marc:subfield[@code='b']"/>
		</xsl:if>
		<xsl:if test="marc:datafield[@tag='245']/marc:subfield[@code='n']">
		<xsl:text> : </xsl:text>
		<xsl:value-of select="marc:datafield[@tag='245']/marc:subfield[@code='n']"/>
		</xsl:if>
	</field>
</xsl:if>
</xsl:template>

</xsl:stylesheet>
