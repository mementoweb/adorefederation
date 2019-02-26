<?xml version="1.0" encoding="UTF-8"?>
<!--
match the whole DIDL to an PWCRDF file
-->


<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"  xmlns:didl="urn:mpeg:mpeg21:2002:02-DIDL-NS" exclude-result-prefixes="xsl didl">
<xsl:output method="xml" indent="yes" encoding="UTF-8"/>
<xsl:include href="didl2pwc.xsl"/>

<!-- 
start processing
-->

<xsl:template match="/didl:DIDL">
  <xsl:for-each select="didl:Item">
      <rdf:RDF  xsi:schemaLocation="http://www.w3.org/1999/02/22-rdf-syntax-ns#  http://www.openarchives.org/OAI/2.0/rdf.xsd">       
	<xsl:call-template name="item"/>     
      </rdf:RDF>
  </xsl:for-each>
</xsl:template>
</xsl:stylesheet>
