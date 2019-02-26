<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim" xmlns:didl="urn:mpeg:mpeg21:2002:02-DIDL-NS" xmlns:diext="http://library.lanl.gov/2006-09/STB-RL/DIEXT" xmlns:diadm="http://library.lanl.gov/2005-08/aDORe/DIADM/" xmlns:dii="urn:mpeg:mpeg21:2002:01-DII-NS" xmlns:dc="http://purl.org/dc/elements/1.1/" >
 
 <!--fknudson 20070724 -->
 
 <!-- this captures the main semantic value of the doc and associated semantic values -->

<xsl:template name="mainSemantic">
<xsl:param name="component"/>
	<field name="semantic"><xsl:value-of select="/didl:DIDL/didl:Item/didl:Component[$component]/didl:Descriptor[3]/didl:Statement/diadm:DIADM/dc:type"/></field>
</xsl:template>

<xsl:template name="associatedSemantic">
<xsl:for-each select="//dc:type[not(.=preceding::dc:type)]">
	<field name="resourceSemantic"><xsl:value-of select="."/></field>
</xsl:for-each>
</xsl:template>

</xsl:stylesheet>

