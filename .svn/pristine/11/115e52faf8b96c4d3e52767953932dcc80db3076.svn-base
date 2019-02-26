<?xml version="1.0" encoding="UTF-8"?>
<!-- identifiers.xst -->
<!-- written by Jeroen Bekaert, Los Alamos National Laboratory, 2004 -->
<!--
Compliant with XSLT v.2.0 - W3C Working Draft 15 November 2002
For processing: use Saxon 7.7
-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://library.lanl.gov/2003-11/lww/listOfIdentifiers" xmlns:didl="urn:mpeg:mpeg21:2002:02-DIDL-NS" xmlns:dii="urn:mpeg:mpeg21:2002:01-DII-NS" xmlns:dip="urn:mpeg:mpeg21:2002:01-DIP-NS" xmlns:dc="http://purl.org/dc/elements/1.1/"  xmlns:diadm="http://library.lanl.gov/2005-08/aDORe/DIADM/" exclude-result-prefixes="xsi xsl">
	<!--
###############################################################
Part 01 : Strip Whitespace
###############################################################
-->
	<xsl:strip-space elements="*"/>
	<!--
###############################################################
Part 02 : XSLT Output Method
###############################################################
   -->
	<xsl:output method="xml" version="4.0" encoding="UTF-8" omit-xml-declaration="no" indent="yes"/>
	<!--
###############################################################
Part 03 : XSLT Variables
###############################################################
   -->

	<xsl:variable name="repo-id" select="didl:Descriptor/didl:Statement/dii:Identifier"/>
	<xsl:variable name="content-id" select="didl:Descriptor/didl:Statement/diadm:Admin/dc:identifier"/>
	<xsl:variable name="XML-id" select="@id"/>
	<!--
###############################################################
Part 04 : Start Processing
###############################################################
   -->
	<xsl:template match="/didl:DIDL">
		<xsl:element name="identifiers">
			<xsl:for-each select="didl:Item">
				<xsl:call-template name="item"/>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>

	<!--
###############################################################
Part 05 : item
###############################################################
  -->
	<xsl:template name="item">
		<xsl:element name="item">
			<xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
			<xsl:for-each select="didl:Descriptor/didl:Statement/dii:Identifier">
				<xsl:call-template name="content-id"/>
			</xsl:for-each>
			<xsl:for-each select="didl:Item">			
			  <xsl:call-template name="item"/>
			</xsl:for-each>
			<xsl:for-each select="didl:Component">  
			  <xsl:call-template name="component"/>   
			</xsl:for-each>
		</xsl:element>
	</xsl:template>

<!--
###############################################################
Part 06 : Component
###############################################################
  -->
	<xsl:template name="component">
		<xsl:element name="component">
			<xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
			<xsl:for-each select="didl:Descriptor/didl:Statement/dii:Identifier">
				<xsl:call-template name="content-id"/>
			</xsl:for-each>		
			<xsl:for-each select="didl:Component">  
			  <xsl:call-template name="component"/>   
			</xsl:for-each>
		</xsl:element>
	</xsl:template>

	<!--
###############################################################
Part 07 : repo-id
###############################################################
  -->
	<xsl:template name="repo-id">
		<xsl:element name="repo-id">
			<xsl:value-of select="."/>
		</xsl:element>
	</xsl:template>
	<!--
###############################################################
Part 08 : content-id
###############################################################
  -->
	<xsl:template name="content-id">
		<xsl:element name="content-id">
			<xsl:value-of select="."/>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>
