<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.w3.org/1999/XSL/Transform http://www.w3.org/1999/XSL/Transform.xsd"
  xmlns:oai_v11="http://www.openarchives.org/OAI/1.1/OAI_GetRecord"
  xmlns:oai_v20="http://www.openarchives.org/OAI/2.0/"
  exclude-result-prefixes="oai_v20 oai_v11">
  <xsl:output method="xml" version="1.0" standalone="yes" indent="yes"/>

  <xsl:template match="/oai_v11:GetRecord">
    <xsl:apply-templates />
  </xsl:template>

  <xsl:template match="oai_v11:record">
    <xsl:apply-templates />
  </xsl:template>

  <xsl:template match="oai_v11:metadata">
    <xsl:copy-of select="*" />
  </xsl:template>

  <xsl:template match="oai_v11:*" />

  <xsl:template match="/oai_v20:OAI-PMH">
    <xsl:apply-templates />
  </xsl:template>

  <xsl:template match="oai_v20:GetRecord">
    <xsl:apply-templates />
  </xsl:template>

  <xsl:template match="oai_v20:record">
    <xsl:apply-templates />
  </xsl:template>

  <xsl:template match="oai_v20:metadata">
    <xsl:copy-of select="*" />
  </xsl:template>

  <xsl:template match="oai_v20:*" />

  <xsl:template match="*">
    <hello>unknown</hello>
  </xsl:template>
</xsl:stylesheet>
