<?xml version="1.0" encoding="UTF-8"?>
<!--
$Id: didl2pwc_match.xsl,v 1.1 2006/03/30 19:51:29 xliu Exp $
This script turns a didl into a pathways core compliant format,
as defined for 2006 interop meeting.

todo: 
(1)finalize values used for semantic
(2) use pronom format identifier

-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:didl="urn:mpeg:mpeg21:2002:02-DIDL-NS" xmlns:dii="urn:mpeg:mpeg21:2002:01-DII-NS"  xmlns:core="info:pathways/core#" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"  exclude-result-prefixes="xsi xsl didl dii">
<xsl:output method="xml" indent="yes" encoding="UTF-8"/>
<xsl:include href="didl2pwc.xsl"/>
<xsl:param name="requestId"/>
<!-- 
start processing
-->

<xsl:template match="/didl:DIDL">   
  <xsl:for-each select="//didl:Item">
    <xsl:if test="didl:Descriptor/didl:Statement/dii:Identifier=$requestId">
      <rdf:RDF>       
	<xsl:call-template name="item"/>     
      </rdf:RDF>
    </xsl:if>
  </xsl:for-each>
</xsl:template>
</xsl:stylesheet>

