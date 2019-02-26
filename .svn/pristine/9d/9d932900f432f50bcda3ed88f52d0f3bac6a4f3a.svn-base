<?xml version="1.0" encoding="UTF-8"?>
<!--
$Id: didl2pwc.xsl,v 1.6 2006/04/05 15:31:24 xliu Exp $
This script turns a didl into a pathways core compliant format,
as defined for 2006 interop meeting.

it's a variant of didl2pwc.xsl used for OpenURL resolver, 
there is no parameter passed in.

The url-encoding code is based on  URL-encoding demo
       Written by Mike J. Brown, mike@skew.org.
       http://skew.org/xml/stylesheets/url-encode/
-->


<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:didl="urn:mpeg:mpeg21:2002:02-DIDL-NS" xmlns:dii="urn:mpeg:mpeg21:2002:01-DII-NS" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:diadm="http://library.lanl.gov/2004-01/STB-RL/DIADM" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:diext="http://library.lanl.gov/2005-08/aDORe/DIDLextension/" xmlns:core="info:pathways/core#" xmlns:adore="http://library.lanl.gov/STB-RL/adore#"  xmlns:dcterms="http://purl.org/dc/terms/" xmlns:pre="http://www.loc.gov/standards/premis" exclude-result-prefixes="xsi xsl didl dii diext diadm pre dc dcterms adore">
<xsl:output method="xml" indent="yes" encoding="UTF-8"/>

<!--
XSLT variable
-->
<xsl:variable name="provider">info:sid/library.lanl.gov:pathways</xsl:variable>
<xsl:variable name="id_prefix">info:pathways/entity/</xsl:variable>
<xsl:variable name="didcreated" select ="/didl:DIDL/@diext:DIDLDocumentCreated"/>
<xsl:variable name="ascii"> !"#$%&amp;'()*+,-./0123456789:;&lt;=&gt;?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghijklmnopqrstuvwxyz{|}~</xsl:variable>
<xsl:variable name="latin1">&#160;&#161;&#162;&#163;&#164;&#165;&#166;&#167;&#168;&#169;&#170;&#171;&#172;&#173;&#174;&#175;&#176;&#177;&#178;&#179;&#180;&#181;&#182;&#183;&#184;&#185;&#186;&#187;&#188;&#189;&#190;&#191;&#192;&#193;&#194;&#195;&#196;&#197;&#198;&#199;&#200;&#201;&#202;&#203;&#204;&#205;&#206;&#207;&#208;&#209;&#210;&#211;&#212;&#213;&#214;&#215;&#216;&#217;&#218;&#219;&#220;&#221;&#222;&#223;&#224;&#225;&#226;&#227;&#228;&#229;&#230;&#231;&#232;&#233;&#234;&#235;&#236;&#237;&#238;&#239;&#240;&#241;&#242;&#243;&#244;&#245;&#246;&#247;&#248;&#249;&#250;&#251;&#252;&#253;&#254;&#255;</xsl:variable>
<xsl:variable name="safe">!'()*-.0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz~</xsl:variable>
<xsl:variable name="hex">0123456789ABCDEF</xsl:variable>

<!-- 
start processing
-->

<xsl:template name="item">
  <core:entity>
    <xsl:attribute name="rdf:about"> 
      <xsl:variable name="provider_urlencoded">
	<xsl:call-template name="url-encode">
	  <xsl:with-param name="str" select="$provider"/>
	</xsl:call-template>
      </xsl:variable>
      
      <xsl:variable name="id_urlencoded">
	<xsl:call-template name="url-encode">
	  <xsl:with-param name="str" select="didl:Descriptor/didl:Statement/dii:Identifier"/>
	</xsl:call-template>
      </xsl:variable>      
      <xsl:value-of select="concat($id_prefix,$provider_urlencoded,'/',$id_urlencoded)"/>

    </xsl:attribute>     
    <core:hasSemantic>
      <xsl:attribute name="rdf:resource">
	<xsl:choose>
	  <xsl:when test="didl:Descriptor/didl:Statement/diadm:Admin/dc:type = 'http://purl.org/dc/terms/bibliographicCitation'">
	    <xsl:value-of select="'info:pathways/semantic/bibliographic-citation'"/>
	  </xsl:when>
	  <xsl:otherwise>
	    <xsl:text>info:pathways/semantic/journal-article</xsl:text>
	  </xsl:otherwise>
	</xsl:choose>
      </xsl:attribute>
    </core:hasSemantic>  

    <core:hasIdentifier>
	<xsl:value-of select="didl:Descriptor/didl:Statement/dii:Identifier"/>
    </core:hasIdentifier>

    <core:hasProviderPersistence rdf:resource="info:pathways/persistence/persistent"/>
    
    <core:hasProviderInfo>
      <core:providerInfo>
	<core:preferredIdentifier>
	  <xsl:value-of select="didl:Descriptor/didl:Statement/dii:Identifier"/>
	</core:preferredIdentifier>
	<core:provider>info:sid/library.lanl.gov:pathways</core:provider>
      </core:providerInfo>
    </core:hasProviderInfo>
  
    <xsl:for-each select="didl:Item">
      <core:hasEntity>
	<xsl:call-template name="item"/>
      </core:hasEntity>
    </xsl:for-each>
    <xsl:for-each select="didl:Component">  
      <xsl:call-template name="component"/>   
    </xsl:for-each>
  </core:entity>
</xsl:template>

   
<!--

process Component, we have several special rules with current DIDL
a) ignore HTML page
b) PDF file is treated as an entity without providerInfo

-->

<xsl:template name="component" >
  <!-- we ignore html page for the demo -->
  <xsl:choose>    
    <xsl:when test="didl:Descriptor/didl:Statement/pre:object/pre:objectCharacteristics/pre:format/pre:formatRegistry/pre:formatRegistryKey = 'info:lanl-repo/fmt/5'">
      <core:hasEntity>
	<core:entity>	
	  <core:hasSemantic rdf:resource="info:pathways/semeantic/article-fulltext"/>
	  <core:hasProviderPersistence rdf:resource="info:pathways/persistence/transient"/>
	  <xsl:call-template name="datastream"/>
	</core:entity>
      </core:hasEntity>
    </xsl:when>
    <xsl:when test="didl:Descriptor/didl:Statement/pre:object/pre:objectCharacteristics/pre:format/pre:formatRegistry/pre:formatRegistryKey != 'info:lanl-repo/fmt/464'">
      <xsl:call-template name="datastream"/>
    </xsl:when>
  </xsl:choose>
</xsl:template>


<xsl:template name="datastream">
    <core:hasDatastream>
      <core:datastream>
	<core:hasFormat>
	  <xsl:attribute name="rdf:resource">
	    
	    <xsl:choose>
	      <xsl:when test="didl:Descriptor/didl:Statement/pre:object/pre:objectCharacteristics/pre:format/pre:formatRegistry/pre:formatRegistryKey = 'info:lanl-repo/fmt/3'">
		<xsl:value-of select="'info:pathways/fmt/pronom/638'"/>
	      </xsl:when>
	      <xsl:when test="didl:Descriptor/didl:Statement/pre:object/pre:objectCharacteristics/pre:format/pre:formatRegistry/pre:formatRegistryKey = 'info:lanl-repo/fmt/521'">
		<xsl:value-of select="'info:pathways/fmt/pronom/638'"/>
	      </xsl:when>
	      <xsl:when test="didl:Descriptor/didl:Statement/pre:object/pre:objectCharacteristics/pre:format/pre:formatRegistry/pre:formatRegistryKey = 'info:lanl-repo/fmt/5'">
		<xsl:value-of select="'info:pathways/fmt/pronom/618'"/>
	      </xsl:when>
	      
	      <xsl:otherwise>
		<xsl:text>info:pathways/fmt/pronom/unknown</xsl:text>
	      </xsl:otherwise>
	    </xsl:choose>
	  </xsl:attribute>  
	</core:hasFormat>
	<core:hasLocation>
	    <xsl:value-of select="didl:Resource/@ref"/>
	</core:hasLocation>
      </core:datastream>
    </core:hasDatastream>
</xsl:template>


<!--
###############################################################
url-encode
###############################################################
-->


<xsl:template name="url-encode">
  <xsl:param name="str"/>
  <xsl:if test="$str">
    <xsl:variable name="first-char" select="substring($str,1,1)"/>
    <xsl:choose>
      <xsl:when test="contains($safe,$first-char)">
	<xsl:value-of select="$first-char"/>
      </xsl:when>
      <xsl:otherwise>
	<xsl:variable name="codepoint">
	  <xsl:choose>
	    <xsl:when test="contains($ascii,$first-char)">
	      <xsl:value-of select="string-length(substring-before($ascii,$first-char)) + 32"/>
	    </xsl:when>
	    <xsl:when test="contains($latin1,$first-char)">
	      <xsl:value-of select="string-length(substring-before($latin1,$first-char)) + 160"/>
	    </xsl:when>
	    <xsl:otherwise>
	      <xsl:message terminate="no">Warning: string contains a character that is out of range! Substituting "?".</xsl:message>
	      <xsl:text>63</xsl:text>
	    </xsl:otherwise>
	  </xsl:choose>
	</xsl:variable>
	<xsl:variable name="hex-digit1" select="substring($hex,floor($codepoint div 16) + 1,1)"/>
	<xsl:variable name="hex-digit2" select="substring($hex,$codepoint mod 16 + 1,1)"/>
	<xsl:value-of select="concat('%',$hex-digit1,$hex-digit2)"/>
	<xsl:variable name="RESULT" select="concat('%',$hex-digit1,$hex-digit2)"/>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:if test="string-length($str) &gt; 1">
      <xsl:call-template name="url-encode">
	<xsl:with-param name="str" select="substring($str,2)"/>
      </xsl:call-template>
    </xsl:if>
  </xsl:if>
</xsl:template>

</xsl:stylesheet>

