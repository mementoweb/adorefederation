<?xml version="1.0" encoding="UTF-8"?>
<!-- MPEG-21 DID (LANL PROFILE) TO METS (LANL PROFILE) -->
<!-- jeroen bekaert - jbekaert@lanl.gov - October 1, 2004 -->
<!-- updated Aug 25, 2005, Xiaoming Liu 
1. remove DIP
2. support the profile of nested Items (without Container)
3. fix several other bugs as mentioned by Jeroen
-->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xmlns:xlink="http://www.w3.org/TR/xlink" 
    xmlns:mets="http://www.loc.gov/METS/" 
    xmlns:didl="urn:mpeg:mpeg21:2002:02-DIDL-NS" 
    xmlns:diext="http://library.lanl.gov/2005-08/aDORe/DIDLextension/" 
    xmlns:dii="urn:mpeg:mpeg21:2002:01-DII-NS" 
    exclude-result-prefixes="xlink xsi xsl didl diext">
	<!--
   ###############################################################
   PART 01: INCLUDE REGISTRY
   ###############################################################
   -->
	<xsl:include href="registry.xslt"/>
	<!--
   ###############################################################
   PART 02: STRIP WHITESPACE
   ###############################################################
   -->
	<xsl:strip-space elements="*"/>
	<!--
   ###############################################################
   PART 03: XSLT OUTPUT METHOD
   ###############################################################
   -->
	<xsl:output method="xml" version="1.0" encoding="UTF-8" omit-xml-declaration="no" indent="yes"/>
	<!--
   ###############################################################
   PART 04: XSLT PARAMETERS
   ###############################################################
   -->
	<xsl:param name="DID">urn:mpeg:mpeg21:2002:02-DIDL-NS</xsl:param>
	<xsl:param name="DII">urn:mpeg:mpeg21:2002:01-DII-NS</xsl:param>	
	<!--
   ###############################################################
   PART 05: START PROCESSING
   ###############################################################
   -->
	<xsl:template match="/">
		<!--
   ###############################################################
   PART 06: BUILD ROOTELEMENT
   ###############################################################
  -->
		<xsl:element name="mets:mets">
			<xsl:attribute name="xsi:schemaLocation">http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd</xsl:attribute>
			<xsl:if test="/didl:DIDL/@DIDLDocumentId">
				<xsl:attribute name="OBJID"><xsl:value-of select="/didl:DIDL/@DIDLDocumentId"/></xsl:attribute>
			</xsl:if>
			<xsl:if test="/didl:DIDL/@diext:DIDLDocumentCreated">
				<xsl:call-template name="METSHDR"/>
			</xsl:if>
			<xsl:if test="//didl:Descriptor/didl:Statement[child::*[namespace-uri() != $DID][namespace-uri() != $DII]]">
				<xsl:call-template name="DMDSEC"/>
			</xsl:if>
			<xsl:if test="//didl:Component">
				<xsl:call-template name="FILESEC"/>
			</xsl:if>
			<xsl:call-template name="STRUCTMAP"/>
		</xsl:element>
	</xsl:template>
	<!--
   ###############################################################
   PART 07: BUILD METSHDR
   ###############################################################
   -->
	<xsl:template name="METSHDR">
		<xsl:element name="mets:metsHdr">
			<xsl:attribute name="CREATEDATE"><xsl:value-of select="/didl:DIDL/@diext:DIDLDocumentCreated"/></xsl:attribute>
		</xsl:element>
	</xsl:template>
	<!--
   ###############################################################
   PART 08: BUILD DMDSEC/ADMSEC
   ###############################################################
   -->
	<xsl:template name="DMDSEC">
		<xsl:for-each select="//didl:Descriptor/didl:Statement[child::*[namespace-uri() != $DID][namespace-uri() != $DII]]">
			<xsl:variable name="MDSection">
				<xsl:call-template name="getNameOfMetadataSection">
					<xsl:with-param name="namespace" select="namespace-uri(child::*)"/>
				</xsl:call-template>
			</xsl:variable>
			<xsl:choose>
				<xsl:when test="$MDSection = 'techMD'">
					<xsl:element name="mets:amdSec">
						<xsl:attribute name="ID"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
						<xsl:element name="mets:techMD">
							<xsl:call-template name="MDWRAP"/>
						</xsl:element>
					</xsl:element>
				</xsl:when>
				<xsl:when test="$MDSection = 'rightsMD'">
					<xsl:element name="mets:amdSec">
						<xsl:attribute name="ID"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
						<xsl:element name="mets:rightsMD">
							<xsl:call-template name="MDWRAP"/>
						</xsl:element>
					</xsl:element>
				</xsl:when>
				<xsl:when test="$MDSection = 'sourceMD'">
					<xsl:element name="mets:amdSec">
						<xsl:attribute name="ID"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
						<xsl:element name="mets:sourceMD">
							<xsl:call-template name="MDWRAP"/>
						</xsl:element>
					</xsl:element>
				</xsl:when>
				<xsl:when test="$MDSection = 'digiProvMD'">
					<xsl:element name="mets:amdSec">
						<xsl:attribute name="ID"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
						<xsl:element name="mets:digiProvMD">
							<xsl:call-template name="MDWRAP"/>
						</xsl:element>
					</xsl:element>
				</xsl:when>
				<xsl:otherwise>
					<xsl:element name="mets:dmdSec">
						<xsl:attribute name="ID"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
						<xsl:choose>
							<xsl:when test="@ref">
								<xsl:element name="mets:mdRef">
									<xsl:attribute name="LOCTYPE"><xsl:text>URI</xsl:text></xsl:attribute>
									<xsl:attribute name="MDTYPE"><xsl:call-template name="getMetadataType"><xsl:with-param name="namespace" select="namespace-uri(child::*)"/></xsl:call-template></xsl:attribute>
									<xsl:attribute name="MIMETYPE"><xsl:value-of select="@mimeType"/></xsl:attribute>
								</xsl:element>
							</xsl:when>
							<xsl:otherwise>
								<xsl:call-template name="MDWRAP"/>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:element>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
	</xsl:template>
	<!--
   ###############################################################
   PART 09: BUILD MDWRAP
   ###############################################################
   -->
	<xsl:template name="MDWRAP">
		<xsl:element name="mets:mdWrap">
			<xsl:attribute name="MDTYPE"><xsl:call-template name="getMetadataType"><xsl:with-param name="namespace" select="namespace-uri(child::*)"/></xsl:call-template></xsl:attribute>
			<xsl:attribute name="MIMETYPE"><xsl:value-of select="@mimeType"/></xsl:attribute>
			<xsl:choose>
				<xsl:when test="@encoding">
					<xsl:element name="mets:binData">
						<xsl:value-of select="."/>
					</xsl:element>
				</xsl:when>
				<xsl:otherwise>
					<xsl:element name="mets:xmlData">
						<xsl:copy-of select="*" copy-namespaces="no"/>
					</xsl:element>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:element>
	</xsl:template>
	<!--
   ###############################################################
   PART 10: BUILD FILESEC
   ###############################################################
   -->
	<xsl:template name="FILESEC">
		<xsl:element name="mets:fileSec">
			<xsl:element name="mets:fileGrp">
				<xsl:for-each select="//didl:Component">
					<xsl:element name="mets:file">
						<xsl:attribute name="ID"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
						<xsl:attribute name="MIMETYPE"><xsl:value-of select="didl:Resource/@mimeType"/></xsl:attribute>
						<xsl:for-each select="didl:Resource">
							<xsl:choose>
								<xsl:when test="@ref">
									<xsl:element name="mets:FLocat">
									  <xsl:attribute name="LOCTYPE"><xsl:text>URL</xsl:text></xsl:attribute>
									  <xsl:attribute name="xlink:href"><xsl:value-of select="@ref"/></xsl:attribute>
									
									</xsl:element>
								</xsl:when>
								<xsl:otherwise>
									<xsl:element name="mets:FContent">
										<xsl:choose>
											<xsl:when test="@encoding">
												<xsl:element name="mets:binData">
													<xsl:value-of select="."/>
												</xsl:element>
											</xsl:when>
											<xsl:otherwise>
												<xsl:element name="mets:xmlData">
													<xsl:copy-of select="*" copy-namespaces="no"/>
												</xsl:element>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:element>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:for-each>
					</xsl:element>
				</xsl:for-each>
			</xsl:element>
		</xsl:element>
	</xsl:template>
	<!--
   ###############################################################
   PART 11: BUILD STRUCTMAP
   ###############################################################
   -->
	<xsl:template name="STRUCTMAP">
		<xsl:element name="mets:structMap">
		  <xsl:for-each select="/didl:DIDL">		    
		      <xsl:call-template name="ITEMDIV"/>		   
		  </xsl:for-each>
		</xsl:element>
	</xsl:template>
	
<!--

 ###############################################################
   PART 12: BUILD Item Div
   ###############################################################
   -->
<xsl:template name="ITEMDIV">	
  <xsl:element name="mets:div">
    <xsl:call-template name="DIV"/>
    <xsl:for-each select="didl:Item">     
      <xsl:call-template name="ITEMDIV"/>
    </xsl:for-each>
	      
    <xsl:for-each select="didl:Component">
      <xsl:element name="mets:div">
	<xsl:call-template name="DIV"/>
	<xsl:element name="mets:fptr">
	  <xsl:attribute name="FILEID"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
	</xsl:element>
      </xsl:element>
    </xsl:for-each>
  </xsl:element>	  
</xsl:template>


	<!--
   ###############################################################
   PART 13: BUILD DIVS
   ###############################################################
   -->
	<xsl:template name="DIV">
		<xsl:attribute name="ID"><xsl:value-of select="@id"/></xsl:attribute>
		<xsl:if test="didl:Descriptor/didl:Statement/dii:Identifier">
			<xsl:attribute name="CONTENTIDS"><xsl:variable name="number2" select="count(didl:Descriptor/didl:Statement/dii:Identifier)"/><xsl:for-each select="didl:Descriptor/didl:Statement/dii:Identifier"><xsl:choose><xsl:when test="position() + 1 = $number2"><xsl:value-of select="concat(.,' ')"/></xsl:when><xsl:otherwise><xsl:value-of select="."/></xsl:otherwise></xsl:choose></xsl:for-each></xsl:attribute>
		</xsl:if>
		<xsl:variable name="DMDIDS">
			<xsl:for-each select="didl:Descriptor/didl:Statement[child::*[namespace-uri() != $DID][namespace-uri() != $DII]]">
				<xsl:variable name="MDSection">
					<xsl:call-template name="getNameOfMetadataSection">
						<xsl:with-param name="namespace" select="namespace-uri(child::*)"/>
					</xsl:call-template>
				</xsl:variable>
				<xsl:if test="($MDSection != 'techMD') and ($MDSection != 'sourceMD')and ($MDSection != 'rightsMD') and ($MDSection != 'digiProv')">
					<xsl:value-of select="concat(generate-id(.),' ')"/>
				</xsl:if>
			</xsl:for-each>
		</xsl:variable>
		<xsl:variable name="ADMIDS">
			<xsl:for-each select="didl:Descriptor/didl:Statement[child::*[namespace-uri() != $DID][namespace-uri() != $DII]]">
				<xsl:variable name="MDSection">
					<xsl:call-template name="getNameOfMetadataSection">
						<xsl:with-param name="namespace" select="namespace-uri(child::*)"/>
					</xsl:call-template>
				</xsl:variable>
				<xsl:if test="($MDSection = 'techMD') or ($MDSection = 'sourceMD') or ($MDSection = 'rightsMD') or ($MDSection = 'digiProv')">
					<xsl:value-of select="concat(generate-id(.),' ')"/>
				</xsl:if>
			</xsl:for-each>
		</xsl:variable>
		<xsl:if test="$DMDIDS != ''">
			<xsl:attribute name="DMDID"><xsl:value-of select="substring($DMDIDS, 1, string-length($DMDIDS)-1)"/></xsl:attribute>
		</xsl:if>
		<xsl:if test="$ADMIDS != ''">
			<xsl:attribute name="ADMID"><xsl:value-of select="substring($ADMIDS, 1, string-length($ADMIDS)-1)"/></xsl:attribute>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
