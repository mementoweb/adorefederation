<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim">

<!--fknudson 20070710 -->
<!--map first author - used for facet search ; selects 1st 700 or first 710 or first 720 -->
<!--then maps all authors -->
<!--then builds display authors - TO DO  -->

<!--map $a and  $c of 700 -->
<!-- authorFirst is a sort or facet field , not worrying about punctuation - will be stripped by SOLR - fknudson - 2007-07-16 -->
<!-- fknudson - 2007-07-20 - not mapping $c-  prefix, suffix -->
<!-- fknudson - 2007-07-22 - ordered recommended by Irma -->
<!-- fknudson - 20070928 - changed field name to authorFirstSort - facet use not working -->
<!--fknudson 20071031 - split into separate templates - one for authorFirstSort ; one for names-->


<xsl:template name="nameFields">
	<xsl:call-template name="authorFirst"/>
	<xsl:call-template name="allNames"/>
</xsl:template>


<xsl:template name="authorFirst">

<xsl:choose>
	<xsl:when test="marc:datafield[@tag='700']">
		<field name="authorFirstSort">
		<xsl:value-of select="marc:datafield[@tag='700'][1]/marc:subfield[@code='a']"/>
		<xsl:if test="./marc:subfield[@code='c']">
			<xsl:text>, </xsl:text>
			<xsl:value-of select="./marc:subfield[@code='c']"/>
		</xsl:if>
		</field>
	</xsl:when>
	<xsl:when test="marc:datafield[@tag='720']">
		<field name="authorFirstSort">
		<xsl:value-of select="marc:datafield[@tag='720'][1]/marc:subfield[@code='a']"/>
		<xsl:if test="./marc:subfield[@code='c']">
			<xsl:text>, </xsl:text>
			<xsl:value-of select="./marc:subfield[@code='c']"/>
		</xsl:if>		
		</field>
	</xsl:when>	
	<xsl:when test="marc:datafield[@tag='710']">
		<field name="authorFirstSort">
		<xsl:value-of select="marc:datafield[@tag='710'][1]/marc:subfield[@code='a']"/>		
		</field>
	</xsl:when>
</xsl:choose>

</xsl:template>


<xsl:template name="allNames">
<xsl:for-each select= "marc:datafield[@tag='700'] | marc:datafield[@tag='710'] | marc:datafield[@tag='720']">
	<field name="name">
	<xsl:value-of select="./marc:subfield[@code='a']"/>
		<xsl:if test="./marc:subfield[@code='c']">
			<xsl:text>, </xsl:text>
			<xsl:value-of select="./marc:subfield[@code='c']"/>
		</xsl:if>
		<xsl:if test="./marc:subfield[@code='u']">
			<xsl:text>, </xsl:text>
			<xsl:value-of select="./marc:subfield[@code='u']"/>
		</xsl:if>
		<xsl:if test="./marc:subfield[@code='g']">
			<xsl:text>, </xsl:text>
			<xsl:value-of select="./marc:subfield[@code='g']"/>
		</xsl:if>
	</field>
	<xsl:if test="./marc:subfield[@code='q']">
		<xsl:call-template name="buildFull">
			<xsl:with-param name="name" select="."/>
		</xsl:call-template>
	</xsl:if>
</xsl:for-each>
</xsl:template>


<xsl:template name="buildFull">
	<xsl:param name="name"/>
	<field name="name">
	<xsl:value-of select="concat(substring-before(./marc:subfield[@code='a'], ','), ', ', ./marc:subfield[@code='q'])"/>
	<xsl:if test="./marc:subfield[@code='c']">
			<xsl:text>, </xsl:text>
			<xsl:value-of select="./marc:subfield[@code='c']"/>
	</xsl:if>
	</field>
</xsl:template>

<!--built just for cites - different field name - much simpler construction-->
<xsl:template name="citedNames">
<xsl:for-each select= "marc:datafield[@tag='700'] | marc:datafield[@tag='710'] | marc:datafield[@tag='720']">
	<field name="citedName">
	<xsl:value-of select="./marc:subfield[@code='a']"/>
	</field>
</xsl:for-each>
</xsl:template>




</xsl:stylesheet>
