<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:didl="urn:mpeg:mpeg21:2002:02-DIDL-NS" xmlns:dii="urn:mpeg:mpeg21:2002:01-DII-NS" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:diadm="http://library.lanl.gov/2004-01/STB-RL/DIADM" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:diext="http://library.lanl.gov/2004-04/STB-RL/DIEXT" xmlns:model="info:pathways/model#" xmlns:adore="http://library.lanl.gov/STB-RL/adore#"  xmlns:dcterms="http://purl.org/dc/terms/" xmlns:pre="http://www.loc.gov/standards/premis" exclude-result-prefixes="xsi xsl didl dii diext diadm pre">
<xsl:output method="xml" indent="yes" encoding="UTF-8"/>
<xsl:param name="conf"/>
<xsl:variable name="location" select="concat($conf/conf/openurl-resolver,'?url_ver=Z39.88-2004&amp;svc_id=info:pathways/svc/DO.didl&amp;rft_id=', //didl:Item/didl:Descriptor/didl:Statement/dii:Identifier)"/>
<xsl:variable name="item" select="didl:Item"/>
<xsl:variable name="component" select="didl:Component"/>
<xsl:variable name="collection" select="//didl:Container/didl:Descriptor/didl:Statement/diadm:Admin/dc:format"/>
<xsl:variable name="content-id" select="didl:Descriptor/didl:Statement/dii:Identifier"/>

<!-- 
start processing
-->

<xsl:template match="/didl:DIDL">
  <rdf:RDF>    
    <xsl:for-each select="didl:Item">
      <xsl:call-template name="item"/>   
    </xsl:for-each>
  </rdf:RDF>
</xsl:template>

<xsl:template name="item">
  <model:entity>
    <xsl:attribute name="rdf:about"> 
      <xsl:value-of select="didl:Descriptor/didl:Statement/dii:Identifier"/>
    </xsl:attribute>    

    <xsl:if test="didl:Descriptor/didl:Statement/diadm:Admin/dcterms:isPartOf">
      <adore:isMemberOf>
        <xsl:attribute name="rdf:resource">
          <xsl:value-of select="didl:Descriptor/didl:Statement/diadm:Admin/dcterms:isPartOf"/>
        </xsl:attribute>
      </adore:isMemberOf>

      <model:hasRepresentation>
	<model:represenation>
	  <model:hasFormat rdf:resource="info:lanl-repo/fmt/didl"/>
	  <model:hasLocation>
	    <xsl:attribute name="rdf:resource">
	      <xsl:value-of select="$location"/>
	    </xsl:attribute>  
	  </model:hasLocation>
	</model:represenation>
      </model:hasRepresentation>
    </xsl:if>

    <xsl:for-each select="didl:Item">
      <model:hasEntity>
	<xsl:call-template name="item"/>
      </model:hasEntity>
    </xsl:for-each>
    <xsl:for-each select="didl:Component">  
      <xsl:call-template name="component"/>   
    </xsl:for-each>
  </model:entity>
</xsl:template>

   
<!--
process Component
-->

<xsl:template name="component" >
  <model:hasEntity>
    <model:entity>
    <xsl:attribute name="rdf:about"> 
      <xsl:value-of select="didl:Descriptor/didl:Statement/dii:Identifier"/>
    </xsl:attribute> 

    <model:hasRepresentation>
      <model:represenation>
	<model:hasFormat>
	  <xsl:attribute name="rdf:resource">
	    
	    <xsl:value-of select="didl:Descriptor/didl:Statement/pre:object/pre:objectCharacteristics/pre:format/pre:formatRegistry/pre:formatRegistryKey"/>
	  </xsl:attribute>  
	</model:hasFormat>
	<model:hasLocation>
	    <xsl:attribute name="rdf:resource">
	      <xsl:value-of select="didl:Resource/@ref"/>
	    </xsl:attribute>  	 
	</model:hasLocation>
      </model:represenation>
    </model:hasRepresentation> 
    </model:entity>
</model:hasEntity>
</xsl:template>


</xsl:stylesheet>

