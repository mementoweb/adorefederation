<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim">

<!--fknudson 20071031-->
<!--map cited source fields for searching -->

 
<xsl:template name="citedSource">
<xsl:choose>
	<xsl:when test="marc:datafield[@tag='013']">	
		<xsl:call-template name="cite013"/>
		
		<xsl:call-template name="citeDate"/>
</xsl:when>
<xsl:otherwise>
	<xsl:call-template name="cite773"/>
	<xsl:call-template name="citeDate"/>
	<xsl:call-template name="citeAllDisplay"/>
	<xsl:call-template name="citeAll"/>
</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!--builds citedPatent and citeAll fields -->
<xsl:template name="cite013">
<xsl:for-each select="marc:datafield[@tag='013']">
		<field name="citedSource">
			<xsl:value-of select="./marc:subfield[@code='b']"/>
			<xsl:text> </xsl:text>
			<xsl:value-of select="./marc:subfield[@code='a']"/>
			<xsl:text> </xsl:text>
			<xsl:value-of select="./marc:subfield[@code='d']"/>
			<xsl:text> </xsl:text>
			<xsl:value-of select="./marc:subfield[@code='c']"/>
		</field>
</xsl:for-each>
		<field name="citedDisplay">
				<xsl:if test="marc:datafield[@tag='700']/marc:subfield[@code='a']">
					<xsl:value-of select="marc:datafield[@tag='700']/marc:subfield[@code='a']"/>
					<xsl:text> - </xsl:text>	
					<xsl:text> Patent </xsl:text>	
				</xsl:if>
				<xsl:if test="marc:datafield[@tag='013']/marc:subfield[@code='d']">
					<xsl:text> (</xsl:text>
					<xsl:value-of select="marc:datafield[@tag='013']/marc:subfield[@code='d']"/>
					<xsl:text>) </xsl:text>
				</xsl:if>	
				<xsl:value-of select="marc:datafield[@tag='013']/marc:subfield[@code='b']"/>
				<xsl:if test="marc:datafield[@tag='013']/marc:subfield[@code='a']">
					<xsl:text> </xsl:text>
					<xsl:value-of select="marc:datafield[@tag='013']/marc:subfield[@code='a']"/>
				</xsl:if>
				<xsl:if test="marc:datafield[@tag='013']/marc:subfield[@code='c']">
					<xsl:text> </xsl:text>
					<xsl:value-of select="marc:datafield[@tag='013']/marc:subfield[@code='c']"/>
				</xsl:if>
		</field>
		<field name="citedAll">
			<xsl:if test="marc:datafield[@tag='700']/marc:subfield[@code='a']">
				<xsl:value-of select="marc:datafield[@tag='700']/marc:subfield[@code='a']"/>
				<xsl:text> Patent </xsl:text>	
			</xsl:if>
			<xsl:if test="marc:datafield[@tag='013']/marc:subfield[@code='d']">
				<xsl:value-of select="marc:datafield[@tag='013']/marc:subfield[@code='d']"/>
				<xsl:text> </xsl:text>
			</xsl:if>	
			<xsl:value-of select="marc:datafield[@tag='013']/marc:subfield[@code='b']"/>
			<xsl:if test="marc:datafield[@tag='013']/marc:subfield[@code='a']">
				<xsl:text> </xsl:text>
				<xsl:value-of select="marc:datafield[@tag='013']/marc:subfield[@code='a']"/>
			</xsl:if>
			<xsl:if test="marc:datafield[@tag='013']/marc:subfield[@code='c']">
				<xsl:text> </xsl:text>
				<xsl:value-of select="marc:datafield[@tag='013']/marc:subfield[@code='c']"/>
			</xsl:if>
		</field>

</xsl:template>

<xsl:template name="cite773">
<!--grab article number from 024 if present -->
<xsl:variable name="citedArtNo">
	<xsl:call-template name="findArtNo"/>
</xsl:variable>

<xsl:if test="marc:datafield[@tag='773']">
	<field name="citedSource">
		<xsl:value-of select="marc:datafield[@tag='773']/marc:subfield[@code='t']"/>
	</field>
</xsl:if>

<xsl:if test="marc:datafield[@tag='363']/marc:subfield[@code='a']">
<field name="citedVolume">
	<xsl:value-of select="marc:datafield[@tag='363']/marc:subfield[@code='a']"/>
	</field>

</xsl:if>	

<xsl:if test="marc:datafield[@tag='363']/marc:subfield[@code='p']">
<field name="citedPage">
	<xsl:value-of select="marc:datafield[@tag='363']/marc:subfield[@code='p']"/>
</field>
</xsl:if>

<!-- currently only have 024 with artno in them -->

<xsl:if test="marc:datafield[@tag='024']">
<field name="citedArtNo">
		<xsl:value-of select="substring(marc:datafield[@tag='024']/marc:subfield[@code='a'], 6)"/>
</field>
</xsl:if>
</xsl:template>


<xsl:template name="citeDate">
	<xsl:variable name="date" select="substring(marc:controlfield[@tag='008'],8,4)"/>
	<xsl:choose>
		<xsl:when test="$date = '||||' ">
			<field name="citedDate">noDate</field>
		</xsl:when>
		<xsl:otherwise>
			<field name="citedDate"><xsl:value-of select="$date"/></field>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>


<!--template to create "all" field for records with 773 - instead of using copyField -->
<xsl:template name="citeAllDisplay">
	<field name="citedDisplay">
		<xsl:for-each select="marc:datafield[@tag='700'] | marc:datafield[@tag='710'] | marc:datafield[@tag='720']">
		<xsl:value-of select="./marc:subfield[@code='a']"/>	
		<xsl:text> - </xsl:text>
		</xsl:for-each>
		<xsl:if test="marc:datafield[@tag='773']/marc:subfield[@code='t']">
			<xsl:value-of select="marc:datafield[@tag='773']/marc:subfield[@code='t']"/>
		</xsl:if>
		<xsl:variable name="date" select="substring(marc:controlfield[@tag='008'],8,4)"/>
		<xsl:if test="$date != '||||' ">
				<xsl:text> (</xsl:text>
                <xsl:value-of select="$date"/>
                <xsl:text>)</xsl:text>
         </xsl:if>
		<xsl:if test="marc:datafield[@tag='363']/marc:subfield[@code='a']">
			<xsl:text> v.</xsl:text>
			<xsl:value-of select="marc:datafield[@tag='363']/marc:subfield[@code='a']"/>
		</xsl:if>
		<xsl:if test="marc:datafield[@tag='363']/marc:subfield[@code='p']">
			<xsl:text> p.</xsl:text>
			<xsl:value-of select="marc:datafield[@tag='363']/marc:subfield[@code='p']"/>
		</xsl:if>
		<xsl:if test="marc:datafield[@tag='024']">
			<xsl:text> p.</xsl:text>
			<xsl:value-of select="substring(marc:datafield[@tag='024']/marc:subfield[@code='a'], 6)"/>
		</xsl:if>
		
	</field>
</xsl:template>

<!--template to create "all" field for records with 773 - instead of using copyField -->
<xsl:template name="citeAll">
	<field name="citedAll">
		<xsl:for-each select="marc:datafield[@tag='700'] | marc:datafield[@tag='710'] | marc:datafield[@tag='720']">
		<xsl:value-of select="./marc:subfield[@code='a']"/>	
		</xsl:for-each>
		<xsl:text> </xsl:text>
		<xsl:if test="marc:datafield[@tag='773']/marc:subfield[@code='t']">
			<xsl:value-of select="marc:datafield[@tag='773']/marc:subfield[@code='t']"/>
		</xsl:if>
		<xsl:text> </xsl:text>
		<xsl:variable name="date" select="substring(marc:controlfield[@tag='008'],8,4)"/>
		<xsl:choose>
			<xsl:when test="$date = '||||' ">
				<xsl:text>nodate</xsl:text>
			</xsl:when>
			<xsl:otherwise> <xsl:value-of select="$date"/></xsl:otherwise>
		</xsl:choose>
		<xsl:text> </xsl:text>
		<xsl:if test="marc:datafield[@tag='363']/marc:subfield[@code='a']">
			<xsl:value-of select="marc:datafield[@tag='363']/marc:subfield[@code='a']"/>
		</xsl:if>
		<xsl:if test="marc:datafield[@tag='363']/marc:subfield[@code='p']">
		<xsl:text> </xsl:text>
			<xsl:value-of select="marc:datafield[@tag='363']/marc:subfield[@code='p']"/>
		</xsl:if>
		<xsl:if test="marc:datafield[@tag='024']">	
			<xsl:text> </xsl:text>
			<xsl:value-of select="substring(marc:datafield[@tag='024']/marc:subfield[@code='a'], 6)"/>
		
		</xsl:if>
	</field>
</xsl:template>

</xsl:stylesheet>
