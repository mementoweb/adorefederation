<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim">

<!--fknudson 20070710 -->
<!--Title display - uses titleSort - copyfield - handled in SOLR-->
<!--Name display - captures the first five authors; adds et al if more authors present -->
<!--grabbing $e from first 700/710/720 encountered - all names displayed must match that $e -->

<xsl:template name="nameDisplay">

<!--ugly data check for ECD -->
<xsl:variable name="skip720">
	<xsl:if test="marc:datafield[@tag='720']/marc:subfield[@code='a'][contains(text(),'Not Available')]">
		<xsl:value-of select="1"/></xsl:if>
</xsl:variable>

<xsl:choose>
	<xsl:when test="marc:datafield[@tag='700']/marc:subfield[@code='e'][contains(text(), 'Author') or contains(text(), 'Inventor')  or contains(text(), 'Patent') or  contains(text(),  'Recipient')] and (marc:datafield[@tag='700'][1]/marc:subfield[@code='a'] != '')">
	
		<xsl:variable name="list700" select="marc:datafield[@tag='700']/marc:subfield[@code='e'][contains(text(), 'Author') or contains(text(), 'Inventor')  or contains(text(), 'Patent') or  contains(text(),  'Recipient')]"/>
	
	<xsl:call-template name="personAuthor">
		<xsl:with-param name="nameList" select="$list700[position() &lt; 6]"/>
		<xsl:with-param name="number" select="count($list700)"/>
	</xsl:call-template>

	</xsl:when>
	
	<xsl:when test="($skip720 != '1') and marc:datafield[@tag='720']/marc:subfield[@code='e'][contains(text(), 'Author') or contains(text(), 'Patent') or  contains(text(),  'Recipient') or contains(text(),  'Creator')]">
	
		<xsl:variable name="list720" select="marc:datafield[@tag='720']/marc:subfield[@code='e'][contains(text(), 'Author')  or contains(text(), 'Patent') or  contains(text(),  'Creator') or contains(text(), 'Recipient')]"/>

		<xsl:call-template name="personAuthor">
			<xsl:with-param name="nameList" select="$list720[position() &lt; 6]"/>
			<xsl:with-param name="number" select="count($list720)"/>
		</xsl:call-template>
	</xsl:when>
	
	<xsl:when test="marc:datafield[@tag='710']/marc:subfield[@code='e'][contains(text(), 'Author') or contains(text(), 'Patent') or contains(text(), 'Originator')]">
	
		<xsl:variable name="list710" select="marc:datafield[@tag='710']/marc:subfield[@code='e'][contains(text(), 'Author')  or contains(text(), 'Patent') or contains(text(), 'Originator')]"/>

		<xsl:call-template name="personAuthor">
			<xsl:with-param name="nameList" select="$list710[position() &lt; 6]"/>
			<xsl:with-param name="number" select="count($list710)"/>
		</xsl:call-template>
	</xsl:when>	
</xsl:choose>

</xsl:template>


<xsl:template name="personAuthor">
<xsl:param name="nameList"/>
<xsl:param name="number"/>	

<field name="displayName">

<xsl:for-each select="$nameList">

		<xsl:if test="../marc:subfield[@code='a'][not(.=preceding::marc:subfield[@code='a'])]">
		
		<xsl:if test="position() != '1'">
			<xsl:text> ; </xsl:text>
		</xsl:if>
		
		<xsl:value-of select="../marc:subfield[@code='a']"/>
		<xsl:if test="../marc:subfield[@code='c']">
				<xsl:text>, </xsl:text>
				<xsl:value-of select="../marc:subfield[@code='c']"/>
		</xsl:if>
		<xsl:if test="../marc:subfield[@code='q']">
				<xsl:text> (</xsl:text>
				<xsl:value-of select="../marc:subfield[@code='q']"/>
				<xsl:text>)</xsl:text>
		</xsl:if>
		
	</xsl:if>

</xsl:for-each>
<xsl:if test="$number &gt; '5'">
		<xsl:text> ; et al. </xsl:text>
</xsl:if>
</field>
</xsl:template>


</xsl:stylesheet>
