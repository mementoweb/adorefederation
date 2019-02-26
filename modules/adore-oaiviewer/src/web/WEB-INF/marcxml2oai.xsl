<?xml version="1.0" encoding="utf-8"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:marcxml="http://www.loc.gov/MARC21/slim">
  <xsl:output method="xml"
              indent="yes"
              omit-xml-declaration="yes"
              encoding="utf-8" />

<xsl:template match="/marcxml:collection">
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="marcxml:record">
  <xsl:copy>
    <xsl:attribute name="xsi:schemaLocation">http://www.loc.gov/MARC21/slim http://www.loc.gov/standards/marcxml/schema/MARC21slim.xsd</xsl:attribute>
    <xsl:copy-of select="*"/>
  </xsl:copy>
</xsl:template>
</xsl:stylesheet>
