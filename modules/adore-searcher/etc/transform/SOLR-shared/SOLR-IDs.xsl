<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim" xmlns:didl="urn:mpeg:mpeg21:2002:02-DIDL-NS" xmlns:diext="http://library.lanl.gov/2006-09/STB-RL/DIEXT" xmlns:diadm="http://library.lanl.gov/2005-08/aDORe/DIADM/" xmlns:dii="urn:mpeg:mpeg21:2002:01-DII-NS">
 
 <!--fknudson 20070710 - grabs record id -->
 
 <xsl:variable name="lowerCase">abcdefghijklmnopqrstuvwxyz</xsl:variable>
	<xsl:variable name="upperCase">ABCDEFGHIJKLMNOPQRSTUVWXYZ</xsl:variable>
	
 <xsl:template name="recID">
	<field name="recID">
	 <xsl:value-of select="/didl:DIDL/didl:Item/didl:Descriptor[1]/didl:Statement/dii:Identifier"/>
	 </field>
</xsl:template>

<!--builds recID for cites - -->
<xsl:template name="citeRecID">
	<xsl:variable name="prefixDB" select="translate(marc:datafield[@tag='040']/marc:subfield[@code='a'], $upperCase, $lowerCase)"/>
	<field name="recID">
	 <xsl:value-of select="concat('info:lanl-repo/', $prefixDB, '/', marc:datafield[@tag='035']/marc:subfield[@code='a'])"/>	 
	 </field>
</xsl:template>




<!-- grabs database name; if equals ISI - then checks 887 for specific values-->
<!-- the if tests allow for duplicate values from the vendor but unique values going out to solr -->
 <xsl:template name="datasetID">
	 <xsl:variable name="dbList">
	<xsl:variable name="dbName" select="didl:Item/didl:Component[1]/didl:Resource[1]/marc:record/marc:datafield[@tag=040]/marc:subfield[@code='a']"/>
	
	<xsl:choose>
		<xsl:when test="$dbName = 'ISI' ">
			<!-- SCI test -->
			<xsl:if test="
			(didl:Item/didl:Component[1]/didl:Resource[1]/marc:record/marc:datafield[@tag='887']/marc:subfield[@code='2'][contains(text(), 'ISI : CF')]/../marc:subfield[@code='a'][. = '[Adminmetadata : dc:accessRights]D']) or (didl:Item/didl:Component[1]/didl:Resource[1]/marc:record/marc:datafield[@tag='887']/marc:subfield[@code='2'][contains(text(), 'ISI : CF')]/../marc:subfield[@code='a'][. = '[Adminmetadata : dc:accessRights]K']) or (didl:Item/didl:Component[1]/didl:Resource[1]/marc:record/marc:datafield[@tag='887']/marc:subfield[@code='2'][contains(text(), 'ISI : SL')]/../marc:subfield[@code='a'][. = '[Adminmetadata : dc:accessRights]D']) or (didl:Item/didl:Component[1]/didl:Resource[1]/marc:record/marc:datafield[@tag='887']/marc:subfield[@code='2'][contains(text(), 'ISI : SL')]/../marc:subfield[@code='a'][. = '[Adminmetadata : dc:accessRights]K'])">
				<field name="dataset">SCI</field>
			</xsl:if>
			
			<!-- SOC test -->
			<xsl:if test="(didl:Item/didl:Component[1]/didl:Resource[1]/marc:record/marc:datafield[@tag='887']/marc:subfield[@code='2'][contains(text(), 'ISI : CF')]/../marc:subfield[@code='a'][. = '[Adminmetadata : dc:accessRights]J']) or (didl:Item/didl:Component[1]/didl:Resource[1]/marc:record/marc:datafield[@tag='887']/marc:subfield[@code='2'][contains(text(), 'ISI : CF')]/../marc:subfield[@code='a'][. ='[Adminmetadata : dc:accessRights]SS']) or (didl:Item/didl:Component[1]/didl:Resource[1]/marc:record/marc:datafield[@tag='887']/marc:subfield[@code='2'][contains(text(), 'ISI : SL')]/../marc:subfield[@code='a'][. = '[Adminmetadata : dc:accessRights]J']) or (didl:Item/didl:Component[1]/didl:Resource[1]/marc:record/marc:datafield[@tag='887']/marc:subfield[@code='2'][contains(text(), 'ISI : SL')]/../marc:subfield[@code='a'][. = '[Adminmetadata : dc:accessRights]SS'])">
				<field name="dataset">SOC</field>
			</xsl:if>
			
			<!-- ART test - -->	
			<xsl:if test="
			(didl:Item/didl:Component[1]/didl:Resource[1]/marc:record/marc:datafield[@tag='887']/marc:subfield[@code='2'][contains(text(), 'ISI : CF')]/../marc:subfield[@code='a'][. = '[Adminmetadata : dc:accessRights]H']) or 
			(didl:Item/didl:Component[1]/didl:Resource[1]/marc:record/marc:datafield[@tag='887']/marc:subfield[@code='2'][contains(text(), 'ISI : SL')]/../marc:subfield[@code='a'][. = '[Adminmetadata : dc:accessRights]H'])">
				<field name="dataset">ART</field>
			</xsl:if>

		</xsl:when>
		<xsl:otherwise>
			<field name="dataset"><xsl:value-of select="$dbName"/></field>
		</xsl:otherwise>
	</xsl:choose>
</xsl:variable>

<xsl:copy-of select="$dbList"/>
</xsl:template>


</xsl:stylesheet>
