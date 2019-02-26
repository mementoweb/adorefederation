<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim">

<!--fknudson 20070710 -->
<!-- certain dbs need mappings for 655 document types and treatment codes -->


<xsl:template name="handle655">
<xsl:param name="dataset"/>
<xsl:choose>
	<xsl:when test="$dataset = 'INSPEC'">
		<xsl:call-template name="inspecTreat"/>
		<xsl:call-template name="inspecDocType"/>
	</xsl:when>
	<xsl:when test="$dataset = 'EIX'">
		<xsl:call-template name="eixTreat"/>
		<xsl:call-template name="eixDocType"/>
	</xsl:when>
	<xsl:when test="contains($dataset, 'SCI') or contains($dataset, 'SOC') or contains($dataset , 'ART') or contains ($dataset, 'PRO')">
		<xsl:call-template name="isiDocType"/>
	</xsl:when>
	<xsl:otherwise>
		<xsl:for-each select= "marc:datafield[@tag='655']">
			<field name="subjects">
		<xsl:value-of select="./marc:subfield[@code='a']"/>
		</field>
</xsl:for-each>
</xsl:otherwise>	
</xsl:choose>
</xsl:template>


<xsl:template name="inspecTreat">
<!-- determine if doc type or treatment code-->
<xsl:for-each select="marc:datafield[@tag='655']/marc:subfield[@code='2'][contains(text(), 'tcg')]">
	<xsl:call-template name="parseTC">
			<xsl:with-param name="tc" select="../marc:subfield[@code='a']"/>
			<xsl:with-param name="db" select="'INSPEC'"/>
		</xsl:call-template>
</xsl:for-each>
</xsl:template>


<!-- map only rtname; number already converted to this field -->
<xsl:template name="inspecDocType">
	<xsl:for-each select="marc:datafield[@tag='655']/marc:subfield[@code='2'][contains(text(), 'rtname')]">
			<field name="subjects">
				<xsl:value-of select="../marc:subfield[@code='a']"/>
			</field>
	</xsl:for-each>
</xsl:template>
	
<xsl:template name="eixDocType">
	<xsl:for-each select="marc:datafield[@tag='655']/marc:subfield[@code='2'][contains(text(), 'DT')]">
		<xsl:variable name="dt" select="../marc:subfield[@code='a']"/>
		<field name="subjects">
			<xsl:value-of select="$docType//DocumentTypes[@db='EIX']/DocType[@code = $dt]"/>
        </field>  
      </xsl:for-each>
</xsl:template>

<xsl:template name="eixTreat">
	<xsl:for-each select="marc:datafield[@tag='655']/marc:subfield[@code='2'][contains(text(), 'TR')]">
		<xsl:call-template name="parseTC">
			<xsl:with-param name="tc" select="../marc:subfield[@code='a']"/>
			<xsl:with-param name="db" select="'EIX'"/>
		</xsl:call-template>
	</xsl:for-each>
</xsl:template>

<!--only map DT - do not map PT - publication type -->
<!--removing first single character -->
<xsl:template name="isiDocType">
	<xsl:for-each select="marc:datafield[@tag='655']/marc:subfield[@code='2'][contains(text(), 'DT')]">
		<field name="subjects">
			<xsl:value-of select="substring-after(../marc:subfield[@code='a'], ' ')"/>
        </field>  
      </xsl:for-each>
</xsl:template>


<!--some values need to be treated as individual values -->
<xsl:template name="parseTC">
	<xsl:param name="tc"/>
	<xsl:param name="counter" select="1"/>
	<xsl:param name="db"/>
	<xsl:choose>
		<xsl:when test="string-length($tc)+1 != $counter">
			<xsl:variable name="code" select="substring($tc,$counter,1)"/>
			<field name="subjects">
			<xsl:value-of select="$treatDoc//Treatment[@db=$db]/Treat[@code=$code]"/>
			</field>
			<xsl:call-template name="parseTC">
				<xsl:with-param name="tc" select="$tc"/>
				<xsl:with-param name="db" select="$db"/>
				<xsl:with-param name="counter" select="$counter+1"/>
			</xsl:call-template>
		</xsl:when>
	</xsl:choose>
</xsl:template>






</xsl:stylesheet>
