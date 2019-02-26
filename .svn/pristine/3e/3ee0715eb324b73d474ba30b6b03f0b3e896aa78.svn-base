<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim" xmlns:didl="urn:mpeg:mpeg21:2002:02-DIDL-NS" xmlns:diext="http://library.lanl.gov/2006-09/STB-RL/DIEXT" xmlns:diadm="http://library.lanl.gov/2005-08/aDORe/DIADM/" xmlns:dii="urn:mpeg:mpeg21:2002:01-DII-NS" xmlns:dc="http://purl.org/dc/elements/1.1/" >
 
 <!--fknudson 20070724 -->
 
 <!-- this will be used as facet index in SOLR -->
 <!-- captures LANLauthors and LANLcontent-->
 
 <xsl:template name="LANLflag">
	 <xsl:for-each select="/didl:DIDL/didl:Item/didl:Descriptor[2]/didl:Statement/diadm:DIADM/dc:subject">
			 <xsl:if test="contains(., 'LANL')">
				 <field name="LANLflag">LANLauthor</field>
			</xsl:if>
			<xsl:if test="contains(., 'LosAlamos')">
				 <field name="LANLflag">LosAlamos</field>
			</xsl:if>
	</xsl:for-each>
</xsl:template>

</xsl:stylesheet>

