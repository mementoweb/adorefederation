<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim">

<!--fknudson 20070710 -->
<!--Title display - uses titleSort - copyfield - handled in SOLR-->
<!--Name display - captures the first five authors; adds et al if more authors present -->
<!--grabbing $e from first 700/710/720 encountered - all names displayed must match that $e -->
<!--Source display -->
<!--fknudson 2007-08-28 - added 711 as possible source tag -->

<!--sourceDisplay - built on hierarchy of tags (present or absent)-->
<xsl:template name="sourceDisplay">
<xsl:choose>
	<xsl:when test="marc:datafield[@tag='773']/marc:subfield[@code='n'][contains(text(), 'Journal')]">
		<xsl:call-template name="outputDisplay">
			<xsl:with-param name="type" select="'Journal'"/>
		</xsl:call-template>
	</xsl:when>
	<xsl:when test="marc:datafield[@tag='773']/marc:subfield[@code='n'][contains(text(), 'Book')]">
		<xsl:call-template name="outputDisplay">
			<xsl:with-param name="type" select="'Book'"/>
		</xsl:call-template>
	</xsl:when>
	<xsl:when test="marc:datafield[@tag='773']/marc:subfield[@code='n'][contains(text(), 'Report')]">
		<xsl:call-template name="outputDisplay">
			<xsl:with-param name="type" select="'Report'"/>
		</xsl:call-template>
	</xsl:when>
	<xsl:when test="marc:datafield[@tag='773']/marc:subfield[@code='n'][contains(text(), 'Undetermined')]">
		<xsl:call-template name="outputDisplay">
			<xsl:with-param name="type" select="'Undetermined'"/>
		</xsl:call-template>
	</xsl:when>
	<xsl:when test="marc:datafield[@tag='711']">
		<xsl:call-template name="outputDisplay">
			<xsl:with-param name="type" select="'Conference'"/>
		</xsl:call-template>
		</xsl:when>
	<xsl:when test="marc:datafield[@tag='088']">
		<xsl:call-template name="outputDisplay">
			<xsl:with-param name="type" select="'088Report'"/>
		</xsl:call-template>
		</xsl:when>
	<xsl:when test="marc:datafield[@tag='013']">
		<xsl:call-template name="outputDisplay">
			<xsl:with-param name="type" select="'Patent'"/>
		</xsl:call-template>
	</xsl:when>
	<xsl:otherwise>
		<xsl:call-template name="outputDisplay">
			<xsl:with-param name="type" select="'Publisher'"/>
		</xsl:call-template>
	</xsl:otherwise>
</xsl:choose>
</xsl:template>


<xsl:template name="outputDisplay">
<xsl:param name="type"/>

<field name="displaySource">
<xsl:choose>
	<xsl:when test="($type = 'Journal') or ($type = 'Book') or ($type = 'Report') or ($type = 'Undetermined')">

		<xsl:value-of select="marc:datafield[@tag='773'][1]/marc:subfield[@code='n'][contains(text(), $type)]/../marc:subfield[@code='t']"/>
		<xsl:if test="marc:datafield[@tag='260'][1]/marc:subfield[@code='c']">
			<xsl:text> (</xsl:text>
			<xsl:value-of select="marc:datafield[@tag='260'][1]/marc:subfield[@code='c']"/>
			<xsl:text>)</xsl:text>
		</xsl:if>
		<xsl:if test="marc:datafield[@tag='773'][1]/marc:subfield[@code='g']">
			<xsl:text> </xsl:text>
			<xsl:value-of select="marc:datafield[@tag='773'][1]/marc:subfield[@code='g']"/>
		</xsl:if>
		<xsl:variable name="artNo">
			<xsl:call-template name="findArtNo"/>
		</xsl:variable>
		<xsl:if test="$artNo != ''">
			<xsl:text> </xsl:text>
			<xsl:value-of select="$artNo"/>
		</xsl:if>
	</xsl:when>
	<xsl:when test="$type='Patent'">
		<xsl:text>Patent </xsl:text>
		<xsl:if test="marc:datafield[@tag='013'][1]/marc:subfield[@code='b']">
			<xsl:value-of select="marc:datafield[@tag='013'][1]/marc:subfield[@code='b']"/>
			<xsl:text> </xsl:text>
		</xsl:if>
		<xsl:value-of select="marc:datafield[@tag='013'][1]/marc:subfield[@code='a']"/>
		<xsl:if test="marc:datafield[@tag='013'][1]/marc:subfield[@code='d']">
			<xsl:text> </xsl:text>
			<xsl:value-of select="marc:datafield[@tag='013'][1]/marc:subfield[@code='d']"/>
		</xsl:if>
	</xsl:when>
	<xsl:when test="$type='088Report'">
		<xsl:text>Report no. </xsl:text>
		<xsl:if test="marc:datafield[@tag='088'][1]/marc:subfield[@code='a']">
			<xsl:value-of select="marc:datafield[@tag='088'][1]/marc:subfield[@code='a']"/>
		</xsl:if>
		<xsl:if test="marc:datafield[@tag='260'][1]/marc:subfield[@code='c']">
			<xsl:text>, </xsl:text>
			<xsl:value-of select="marc:datafield[@tag='260'][1]/marc:subfield[@code='c']"/>
		</xsl:if>
	</xsl:when>	
	<xsl:when test="$type='Conference'">
		<xsl:if test="marc:datafield[@tag='711'][1]/marc:subfield[@code='a']">
			<xsl:value-of select="marc:datafield[@tag='711'][1]/marc:subfield[@code='a']"/>
		</xsl:if>
		<xsl:if test="marc:datafield[@tag='711'][1]/marc:subfield[@code='d']">
			<xsl:text>; </xsl:text>
			<xsl:value-of select="marc:datafield[@tag='711'][1]/marc:subfield[@code='d']"/>
		</xsl:if>
		<xsl:if test="marc:datafield[@tag='711'][1]/marc:subfield[@code='c']">
			<xsl:text>; </xsl:text>
			<xsl:value-of select="marc:datafield[@tag='711'][1]/marc:subfield[@code='c']"/>
		</xsl:if>
		<xsl:if test="marc:datafield[@tag='711'][1]/marc:subfield[@code='t']">
			<xsl:text>. </xsl:text>
			<xsl:value-of select="marc:datafield[@tag='711'][1]/marc:subfield[@code='t']"/>
		</xsl:if>
		</xsl:when>
	<xsl:otherwise>
		<xsl:if test="marc:datafield[@tag='260'][1]">
			<xsl:if test="marc:datafield[@tag='260'][1]/marc:subfield[@code='a']">
				<xsl:value-of select="marc:datafield[@tag='260'][1]/marc:subfield[@code='a']"/>
			</xsl:if>
			<xsl:if test="marc:datafield[@tag='260'][1]/marc:subfield[@code='b']">
				<xsl:text> : </xsl:text>
				<xsl:value-of select="marc:datafield[@tag='260'][1]/marc:subfield[@code='b']"/>
			</xsl:if>
			<xsl:if test="marc:datafield[@tag='260'][1]/marc:subfield[@code='c']">
				<xsl:text>, </xsl:text>
				<xsl:value-of select="marc:datafield[@tag='260'][1]/marc:subfield[@code='c']"/>
			</xsl:if>
		</xsl:if>
	</xsl:otherwise>
</xsl:choose>
</field>
</xsl:template>

</xsl:stylesheet>
