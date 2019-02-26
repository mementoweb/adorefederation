<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:atom="http://www.w3.org/2005/Atom">
<xsl:output method="xml"  encoding="UTF-8"  />
<xsl:param name="entryid"/>

<xsl:template  match="atom:entry">
 <xsl:copy-of select='.[atom:id=$entryid]'/>
</xsl:template>

<xsl:template match="atom:feed">
<xsl:apply-templates select="atom:entry" />
</xsl:template>

</xsl:stylesheet>
