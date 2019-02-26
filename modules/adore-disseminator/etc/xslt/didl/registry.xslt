<?xml version="1.0" encoding="UTF-8"?>
<!-- MPEG-21 DID (LANL PROFILE) TO METS (LANL PROFILE): NAMESPACE REGISTRY -->
<!-- jeroen bekaert - jbekaert@lanl.gov - October 1, 2004 -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<!--
   ###############################################################
   PART 01: SPECIFY METADATASECTION OF NAMESPACE
   ###############################################################
   ONLY 1 METADATASECTION PER NAMESPACE ALLOWED
   ###############################################################
   dmdSec - techMD - rightsMD - sourceMD - digiProvMD
   ###############################################################
   -->
	<xsl:template name="getNameOfMetadataSection">
		<xsl:param name="namespace"/>
		<xsl:choose>
			<xsl:when test="$namespace = 'http://library.lanl.gov/2004-01/STB-RL/DIADM'">
				<xsl:text>dmdSec</xsl:text>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<!--
   ###############################################################
   PART 02: SPECIFY METADATATYPE OF NAMESPACE
   ###############################################################
   MULTIPLE METADATATYPES PER NAMESPACE ALLOWED
   ###############################################################
   MARC - MODS - EAD - DC - NISOIMG - LC-AV - VRA - TEIHDR - DDI
   FGDC - OTHER
   ###############################################################
   -->
	<xsl:template name="getMetadataType">
		<xsl:param name="namespace"/>
		<xsl:choose>
			<xsl:when test="$namespace = 'http://www.loc.gov/mods/v3'">
				<xsl:text>MODS</xsl:text>
			</xsl:when>
			<xsl:when test="$namespace = 'http://www.loc.gov/mods/'">
				<xsl:text>MODS</xsl:text>
			</xsl:when>
			<xsl:when test="$namespace = 'http://www.loc.gov/MARC21/slim'">
				<xsl:text>MARC</xsl:text>
			</xsl:when>
			<xsl:when test="$namespace = 'http://purl.org/dc/elements/1.1/'">
				<xsl:text>DC</xsl:text>
			</xsl:when>
			<xsl:when test="$namespace = 'http://library.lanl.gov/2004-01/STB-RL/DIADM'">
				<xsl:text>DC</xsl:text>
			</xsl:when>
			<xsl:otherwise>OTHER</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
