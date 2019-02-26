<?xml version="1.0" encoding="utf-8"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
  xmlns:dc="http://purl.org/dc/elements/1.1/"
  xmlns:oai_dc="http://www.openarchives.org/OAI/2.0/oai_dc/">
  <xsl:output method="xml"
    indent="yes"
    omit-xml-declaration="yes"
    encoding="utf-8" />

  <xsl:template match="/rdf:RDF" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
    <xsl:apply-templates />
  </xsl:template>

  <xsl:template match="rdf:Description" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
    <oai_dc:dc xmlns:dc="http://purl.org/dc/elements/1.1/"
      xmlns:oai_dc="http://www.openarchives.org/OAI/2.0/oai_dc/"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://www.openarchives.org/OAI/2.0/oai_dc/ http://openarchives.org/OAI/2.0/oai_dc.xsd">
      <xsl:copy-of select="*" />
      <!--
      <xsl:apply-templates/>
      -->
    </oai_dc:dc>
  </xsl:template>

  <!--
  <xsl:template match="dc:*">
    <xsl:copy>
      <xsl:apply-templates/>
    </xsl:copy>
  </xsl:template>
  -->

</xsl:stylesheet>
