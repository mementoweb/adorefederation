<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<xsl:stylesheet
  xmlns="http://www.openarchives.org/OAI/2.0/"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:oai_dc="http://www.openarchives.org/OAI/2.0/oai_dc/"
  xmlns:dc="http://purl.org/dc/elements/1.1/"
  xmlns:urlEncoder="xalan://java.net.URLEncoder"
  xmlns:xoaiconfig="http://errol.oclc.org/oai:xmlregistry.oclc.org:xoai/config"
  xmlns:java="http://xml.apache.org/xalan/java"
  xmlns:redirect="org.apache.xalan.xslt.extensions.Redirect"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  extension-element-prefixes="redirect java"
  exclude-result-prefixes="xoaiconfig urlEncoder"
  version="1.0">
  
  <xsl:output indent="yes" method="xml"/>

  <xsl:param name="baseURL" select="/xoaiconfig:repository/xoaiconfig:baseURL"/>
  <xsl:param name="metadataPrefix" select="/xoaiconfig:repository/xoaiconfig:metadataPrefix"/>
  <xsl:param name="set" select="/xoaiconfig:repository/xoaiconfig:set"/>
  <xsl:param name="from"/>
  <xsl:param name="until"/>
  
  <xsl:template match="/xoaiconfig:repository">
    <xsl:variable name="setArg">
      <xsl:if test="$set and string-length($set)&gt;0">
        <xsl:text>&amp;set=</xsl:text>
        <xsl:value-of select="$set"/>
      </xsl:if>
    </xsl:variable>
    <xsl:variable name="fromArg">
      <xsl:if test="$from">
        <xsl:text>&amp;from=</xsl:text>
        <xsl:value-of select="$from"/>
      </xsl:if>
    </xsl:variable>
    <xsl:variable name="untilArg">
      <xsl:if test="$until">
        <xsl:text>&amp;until=</xsl:text>
        <xsl:value-of select="$until"/>
      </xsl:if>
    </xsl:variable>
    
    <xoaih:harvest xmlns:xoaih="http://errol.oclc.org/oai:xmlregistry.oclc.org:xoai/xoaiharvester">
      <xsl:apply-templates select="document(concat($baseURL,'?verb=Identify'))" mode="copy" />
      <OAI-PMH xsi:schemaLocation="http://www.openarchives.org/OAI/2.0/ http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd">
        <responseDate />
        <request metadataPrefix="oai_dc" verb="ListRecords">
          <xsl:value-of select="$baseURL" />
        </request>
        <ListRecords>
          <xsl:apply-templates select="document(concat($baseURL,'?verb=ListRecords&amp;metadataPrefix=',$metadataPrefix,$setArg,$fromArg,$untilArg))" mode="process" />
        </ListRecords>
      </OAI-PMH>
    </xoaih:harvest>
  </xsl:template>
  
  <!-- Process OAI responses -->
  
  <xsl:template match="/oai20:OAI-PMH" mode="copy" xmlns:oai20="http://www.openarchives.org/OAI/2.0/">
    <xsl:variable name="resumptionToken" select="urlEncoder:encode(normalize-space(*/oai20:resumptionToken))"/>
    <xsl:copy-of select="." />

    <xsl:apply-templates select="error"/>
    
    <xsl:if test="$resumptionToken">
      <xsl:message>
        <xsl:value-of select="$resumptionToken"/>
      </xsl:message>
      <xsl:apply-templates select="document(concat(oai20:request, '?verb=', oai20:request/@verb, '&amp;resumptionToken=', $resumptionToken))" mode="copy" />
    </xsl:if>
  </xsl:template>
  
  <!-- Process OAI responses -->
  
  <xsl:template match="/oai20:OAI-PMH" mode="process" xmlns:oai20="http://www.openarchives.org/OAI/2.0/">
    <xsl:variable name="resumptionToken" select="urlEncoder:encode(*/oai20:resumptionToken)"/>
    <xsl:apply-templates select="oai20:error"/>

    <xsl:for-each select="oai20:ListRecords/oai20:record[not(oai20:header/@status='deleted')]">
      <xsl:variable name="repoID" select="translate(substring-after(oai20:header/oai20:identifier,'oai:id-registry.uiuc.edu:'),':','_')" />
      <xsl:variable name="baseURL" select="oai20:metadata/oai_dc:dc/dc:identifier[1]" />
      <xsl:message>
        <xsl:value-of select="$repoID" />
      </xsl:message>
      <xsl:if test="document(concat($baseURL,'?verb=Identify'))/oai20:OAI-PMH">
        <xsl:copy-of select="." />
        <!--
        <redirect:write select="concat($repoID,'.xml')">
          <xoai:harvest xmlns:xoai="http://errol.oclc.org/oai:xmlregistry.oclc.org:xoai/xoaiharvester" />
        </redirect:write>
        -->
      </xsl:if>
    </xsl:for-each>

    <xsl:if test="$resumptionToken">
      <xsl:message>
        <xsl:value-of select="$resumptionToken"/>
      </xsl:message>
      <xsl:apply-templates select="document(concat(oai20:request, '?verb=', oai20:request/@verb, '&amp;resumptionToken=', $resumptionToken))" mode="process" />
    </xsl:if>
  </xsl:template>

  <!-- report problems -->
  
  <xsl:template match="error[not(../oai20:request/@verb='ListSets')]"
    xmlns:oai20="http://www.openarchives.org/OAI/2.0/">
    <xsl:message>
      <xsl:value-of select="@code"/>
      <xsl:text> : </xsl:text>
      <xsl:value-of select="."/>
    </xsl:message>
  </xsl:template>
    
  <!-- strip out stylesheet references -->
  
  <xsl:template match="processing-instruction('xml-stylesheet')"/>

  <xsl:template match="*" />
  
</xsl:stylesheet>
