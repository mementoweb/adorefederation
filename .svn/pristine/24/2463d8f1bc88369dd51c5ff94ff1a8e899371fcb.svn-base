<?xml version="1.0" encoding="UTF-8" standalone="yes"?>

  
    
    <xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.openarchives.org/OAI/2.0/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:ERRoL="http://errol.oclc.org/xmlregistry.oclc.org/errol/ListERRoLs" xmlns:customERRoLSchema="http://errol.oclc.org/oai:xmlregistry.oclc.org:errol/customERRoLSchema" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:oai_v11_Identify="http://www.openarchives.org/OAI/1.1/OAI_Identify" xmlns:oai_v11_LMDF="http://www.openarchives.org/OAI/1.1/OAI_ListMetadataFormats" xmlns:oai_v20="http://www.openarchives.org/OAI/2.0/" version="1.0">
  <xsl:output indent="yes" method="xml" standalone="yes" version="1.0"/>

  <!--
    global variables
  -->

  <xsl:param name="baseURL"/>
  <xsl:param name="repositoryIdentifier"/>

  <xsl:template match="/oai_v20:OAI-PMH">
    <xsl:apply-templates/>
  </xsl:template>

  <!--
    Transform the v2.0 error response
  -->

  <xsl:template match="oai_v20:error">
    <xsl:processing-instruction name="xml-stylesheet">href="/errol/resolver.xsl" type="text/xsl"</xsl:processing-instruction>
    <ERRoL:error xsi:schemaLocation="http://errol.oclc.org/xmlregistry.oclc.org/errol/ListERRoLs http://errol.oclc.org/xmlregistry.oclc.org/errol/ListERRoLs.xsd">
        <oai_v20:request>
          <xsl:attribute name="identifier">
            <xsl:value-of select="/oai_v20:OAI-PMH/oai_v20:request/@identifier"/>
          </xsl:attribute>
          <xsl:attribute name="verb">
            <xsl:value-of select="/oai_v20:OAI-PMH/oai_v20:request/@verb"/>
          </xsl:attribute>
          <xsl:value-of select="/oai_v20:OAI-PMH/oai_v20:request"/>
        </oai_v20:request>
        <oai_v20:error>
<xsl:value-of select="."/>
</oai_v20:error>
    </ERRoL:error>
  </xsl:template>

  <!--
    Transform the v2.0 ListMetadataFormats response
  -->

  <xsl:template match="oai_v20:ListMetadataFormats">
    <xsl:variable name="identifier">
      <xsl:value-of select="/oai_v20:OAI-PMH/oai_v20:request/@identifier"/>
    </xsl:variable>

    <xsl:processing-instruction name="xml-stylesheet">href="/errol/resolver.xsl" type="text/xsl"</xsl:processing-instruction>
    <ERRoL:ERRoLList xsi:schemaLocation="http://errol.oclc.org/xmlregistry.oclc.org/errol/ListERRoLs http://errol.oclc.org/xmlregistry.oclc.org/errol/ListERRoLs.xsd">
      <xsl:if test="string-length($identifier)&gt;0">
        <oai_v20:identifier>
          <xsl:value-of select="$identifier"/>
        </oai_v20:identifier>
      </xsl:if>
      <xsl:call-template name="constantExpressions">
        <xsl:with-param name="identifier">
          <xsl:value-of select="$identifier"/>
        </xsl:with-param>
      </xsl:call-template>
      <xsl:if test="string-length($identifier)&gt;0">
        <!--
        <xsl:apply-templates>
          <xsl:with-param name="identifier">
            <xsl:value-of select="$identifier" />
          </xsl:with-param>
          <xsl:with-param name="connector">?</xsl:with-param>
        </xsl:apply-templates>
        -->
        <xsl:apply-templates>
          <xsl:with-param name="identifier">
            <xsl:value-of select="$identifier"/>
          </xsl:with-param>
          <xsl:with-param name="connector">.</xsl:with-param>
        </xsl:apply-templates>
      </xsl:if>
    </ERRoL:ERRoLList>
  </xsl:template>

  <!--
    Transform the v1.1 ListMetadataFormates response
  -->

  <xsl:template match="/oai_v11_LMDF:ListMetadataFormats">
    <xsl:variable name="identifier">
      <xsl:variable name="temp">
        <xsl:value-of select="substring-after(oai_v11_LMDF:requestURL,'identifier=')"/>
      </xsl:variable>
      <xsl:choose>
        <xsl:when test="contains($temp, '&amp;')">
          <xsl:value-of select="substring-before($temp, '&amp;')"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="$temp"/>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>

    <xsl:choose>
      <xsl:when test="oai_v11_LMDF:metadataFormat">
        <xsl:processing-instruction name="xml-stylesheet">href="/errol/resolver.xsl" type="text/xsl"</xsl:processing-instruction>
        <ERRoL:ERRoLList xsi:schemaLocation="http://errol.oclc.org/xmlregistry.oclc.org/errol/ListERRoLs http://errol.oclc.org/xmlregistry.oclc.org/errol/ListERRoLs.xsd">
          <xsl:if test="string-length($identifier)&gt;0">
            <oai_v11_LMDF:identifier>
              <xsl:value-of select="$identifier"/>
            </oai_v11_LMDF:identifier>
          </xsl:if>
          <xsl:call-template name="constantExpressions">
            <xsl:with-param name="identifier">
              <xsl:value-of select="$identifier"/>
            </xsl:with-param>
          </xsl:call-template>
          <xsl:if test="string-length($identifier)&gt;0">
            <!--
            <xsl:apply-templates>
              <xsl:with-param name="identifier">
                <xsl:value-of select="$identifier" />
              </xsl:with-param>
              <xsl:with-param name="connector">?</xsl:with-param>
            </xsl:apply-templates>
            -->
            <xsl:apply-templates>
              <xsl:with-param name="identifier">
                <xsl:value-of select="$identifier"/>
              </xsl:with-param>
              <xsl:with-param name="connector">.</xsl:with-param>
            </xsl:apply-templates>
          </xsl:if>
        </ERRoL:ERRoLList>
      </xsl:when>
      <xsl:otherwise>
        <xsl:processing-instruction name="xml-stylesheet">href="/errol/resolver.xsl" type="text/xsl"</xsl:processing-instruction>
        <ERRoL:error xsi:schemaLocation="http://errol.oclc.org/xmlregistry.oclc.org/errol/ListERRoLs http://errol.oclc.org/xmlregistry.oclc.org/errol/ListERRoLs.xsd">
          <oai_v11_LMDF:requestURL>
<xsl:value-of select="oai_v11_LMDF:requestURL"/>
</oai_v11_LMDF:requestURL>
          <oai_v20:error>Identifier not found in repository</oai_v20:error>
        </ERRoL:error>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <!--
    Create constant ERRoLs
  -->
  
  <xsl:template name="constantExpressions">
    <xsl:param name="identifier"/>
    <ERRoL:baseURL>
      <xsl:value-of select="$baseURL"/>
    </ERRoL:baseURL>
    <ERRoL:baseURLERRoL>
      <dc:identifier>
        <xsl:text>http://localhost:8080/errol/</xsl:text>
        <xsl:choose>
          <xsl:when test="$repositoryIdentifier">
            <xsl:value-of select="$repositoryIdentifier"/>
          </xsl:when>
          <xsl:when test="starts-with($identifier,'oai:')">
            <xsl:value-of select="substring-before(substring-after($identifier,'oai:'),':')"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:text>ERRoLException</xsl:text>
          </xsl:otherwise>
        </xsl:choose>
      </dc:identifier>
      <dc:title>An HTTP redirect to the baseURL for this repository identifier</dc:title>
      <dc:description>Alternate baseURL for the repository. This might be particularly useful for repositories that use the clickable stylesheet.</dc:description>
    </ERRoL:baseURLERRoL>

    <ERRoL:repositoryServiceERRoL>
      <dc:identifier>
        <xsl:text>http://localhost:8080/errol/</xsl:text>
        <xsl:choose>
          <xsl:when test="$repositoryIdentifier">
            <xsl:value-of select="$repositoryIdentifier"/>
          </xsl:when>
          <xsl:when test="starts-with($identifier,'oai:')">
            <xsl:value-of select="substring-before(substring-after($identifier,'oai:'),':')"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="ERRoLException"/>
          </xsl:otherwise>
        </xsl:choose>
        <xsl:text>.ListERRoLs</xsl:text>
      </dc:identifier>
      <dc:title>Complete ERRoL List (Repository level)</dc:title>
      <dc:description>An XML response containing all available ERRoLs</dc:description>
    </ERRoL:repositoryServiceERRoL>

    <xsl:apply-templates select="document(concat($baseURL,'?verb=Identify'))/oai_v20:OAI-PMH/oai_v20:Identify/oai_v20:description/customERRoLSchema:repositoryService"/>

    <ERRoL:repositoryServiceERRoL>
      <dc:identifier>
        <xsl:text>http://localhost:8080/errol/</xsl:text>
        <xsl:choose>
          <xsl:when test="$repositoryIdentifier">
            <xsl:value-of select="$repositoryIdentifier"/>
          </xsl:when>
          <xsl:when test="starts-with($identifier,'oai:')">
            <xsl:value-of select="substring-before(substring-after($identifier,'oai:'),':')"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="ERRoLException"/>
          </xsl:otherwise>
        </xsl:choose>
        <xsl:text>.html</xsl:text>
      </dc:identifier>
      <dc:title>A surrogate for the baseURL returning HTML instead of OAI-PMH responses. Supports the ability to include OAI arguments. If no OAI parameters are included, it brings up an integrated user interface for the repository.</dc:title>
    </ERRoL:repositoryServiceERRoL>

    <ERRoL:repositoryServiceERRoL>
      <dc:identifier>
        <xsl:text>http://localhost:8080/errol/</xsl:text>
        <xsl:choose>
          <xsl:when test="$repositoryIdentifier">
            <xsl:value-of select="$repositoryIdentifier"/>
          </xsl:when>
          <xsl:when test="starts-with($identifier,'oai:')">
            <xsl:value-of select="substring-before(substring-after($identifier,'oai:'),':')"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="ERRoLException"/>
          </xsl:otherwise>
        </xsl:choose>
        <xsl:text>.xoaiconfig</xsl:text>
      </dc:identifier>
      <dc:title>The XOAIHarvester configuration file for this repository</dc:title>
    </ERRoL:repositoryServiceERRoL>

    <!--
    <ERRoL:repositoryServiceERRoL>
      <dc:identifier>
        <xsl:text>http://localhost:8080/errol/</xsl:text>
        <xsl:choose>
          <xsl:when test="$repositoryIdentifier">
            <xsl:value-of select="$repositoryIdentifier" />
          </xsl:when>
          <xsl:when test="starts-with($identifier,'oai:')">
            <xsl:value-of select="substring-before(substring-after($identifier,'oai:'),':')" />
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="ERRoLException" />
          </xsl:otherwise>
        </xsl:choose>
        <xsl:text>.Harvest</xsl:text>
      </dc:identifier>
      <dc:title>Harvest service for this repository</dc:title>
    </ERRoL:repositoryServiceERRoL>
    -->

    <ERRoL:repositoryServiceERRoL>
      <dc:identifier>
        <xsl:text>http://localhost:8080/errol/</xsl:text>
        <xsl:choose>
          <xsl:when test="$repositoryIdentifier">
            <xsl:value-of select="$repositoryIdentifier"/>
          </xsl:when>
          <xsl:when test="starts-with($identifier,'oai:')">
            <xsl:value-of select="substring-before(substring-after($identifier,'oai:'),':')"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="ERRoLException"/>
          </xsl:otherwise>
        </xsl:choose>
        <xsl:text>.rss</xsl:text>
      </dc:identifier>
      <dc:title>An RSS feed for the contents of this repository</dc:title>
    </ERRoL:repositoryServiceERRoL>
    
    <ERRoL:repositoryServiceERRoL>
      <dc:identifier>
        <xsl:text>http://localhost:8080/errol/</xsl:text>
        <xsl:choose>
          <xsl:when test="$repositoryIdentifier">
            <xsl:value-of select="$repositoryIdentifier"/>
          </xsl:when>
          <xsl:when test="starts-with($identifier,'oai:')">
            <xsl:value-of select="substring-before(substring-after($identifier,'oai:'),':')"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="ERRoLException"/>
          </xsl:otherwise>
        </xsl:choose>
        <xsl:text>.Identify</xsl:text>
      </dc:identifier>
      <dc:title>An HTML display of the OAI Identify response</dc:title>
    </ERRoL:repositoryServiceERRoL>
    
    <ERRoL:repositoryServiceERRoL>
      <dc:identifier>
        <xsl:text>http://localhost:8080/errol/</xsl:text>
        <xsl:choose>
          <xsl:when test="$repositoryIdentifier">
            <xsl:value-of select="$repositoryIdentifier"/>
          </xsl:when>
          <xsl:when test="starts-with($identifier,'oai:')">
            <xsl:value-of select="substring-before(substring-after($identifier,'oai:'),':')"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="ERRoLException"/>
          </xsl:otherwise>
        </xsl:choose>
        <xsl:text>.v10</xsl:text>
      </dc:identifier>
      <dc:title>An OAI proxy translating the server responses into OAI-PMH v1.0 version responses. (Not yet implemented)</dc:title>
    </ERRoL:repositoryServiceERRoL>
    <ERRoL:repositoryServiceERRoL>
      <dc:identifier>
        <xsl:text>http://localhost:8080/errol/</xsl:text>
        <xsl:choose>
          <xsl:when test="$repositoryIdentifier">
            <xsl:value-of select="$repositoryIdentifier"/>
          </xsl:when>
          <xsl:when test="starts-with($identifier,'oai:')">
            <xsl:value-of select="substring-before(substring-after($identifier,'oai:'),':')"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="ERRoLException"/>
          </xsl:otherwise>
        </xsl:choose>
        <xsl:text>.v11</xsl:text>
      </dc:identifier>
      <dc:title>An OAI proxy translating the server responses into OAI-PMH v1.1 version responses. (Not yet implemented)</dc:title>
    </ERRoL:repositoryServiceERRoL>
    <ERRoL:repositoryServiceERRoL>
      <dc:identifier>
        <xsl:text>http://localhost:8080/errol/</xsl:text>
        <xsl:choose>
          <xsl:when test="$repositoryIdentifier">
            <xsl:value-of select="$repositoryIdentifier"/>
          </xsl:when>
          <xsl:when test="starts-with($identifier,'oai:')">
            <xsl:value-of select="substring-before(substring-after($identifier,'oai:'),':')"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="ERRoLException"/>
          </xsl:otherwise>
        </xsl:choose>
        <xsl:text>.v20</xsl:text>
      </dc:identifier>
      <dc:title>An OAI proxy translating the server responses into OAI-PMH v2.0 version responses.</dc:title>
    </ERRoL:repositoryServiceERRoL>
    <ERRoL:repositoryServiceERRoL>
      <dc:identifier>
        <xsl:text>http://localhost:8080/errol/</xsl:text>
        <xsl:choose>
          <xsl:when test="$repositoryIdentifier">
            <xsl:value-of select="$repositoryIdentifier"/>
          </xsl:when>
          <xsl:when test="starts-with($identifier,'oai:')">
            <xsl:value-of select="substring-before(substring-after($identifier,'oai:'),':')"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="ERRoLException"/>
          </xsl:otherwise>
        </xsl:choose>
        <xsl:text>.resource</xsl:text>
      </dc:identifier>
      <dc:title>Web Resource (requires an 'identifier=...' parameter)</dc:title>
      <dc:description>An HTTP redirect to the first resolvable dc:identifier in the target repository's oai_dc metadata record for this identifier (requires an 'identifier=...' parameter.)</dc:description>
    </ERRoL:repositoryServiceERRoL>
    <xsl:if test="string-length($identifier)&gt;0">
    <ERRoL:itemERRoL>
      <dc:identifier>
        <xsl:text>http://localhost:8080/errol/</xsl:text>
        <xsl:if test="$repositoryIdentifier">
          <xsl:value-of select="$repositoryIdentifier"/>
          <xsl:text>/</xsl:text>
        </xsl:if>
        <xsl:value-of select="$identifier"/>
        <xsl:text>.html</xsl:text>
      </dc:identifier>
      <dc:title>Integrated Record Display</dc:title>
      <dc:description>Brings up the oai_dc record for this item in the integrated user interface.</dc:description>
    </ERRoL:itemERRoL>
    <ERRoL:extendedERRoL>
      <dc:identifier>
        <xsl:text>http://localhost:8080/errol/</xsl:text>
        <xsl:if test="$repositoryIdentifier">
          <xsl:value-of select="$repositoryIdentifier"/>
          <xsl:text>/</xsl:text>
        </xsl:if>
        <xsl:value-of select="$identifier"/>
        <xsl:text>.ListERRoLs</xsl:text>
      </dc:identifier>
      <dc:title>Complete ERRoL List</dc:title>
      <dc:description>An XML response containing all available ERRoLs</dc:description>
    </ERRoL:extendedERRoL>

    <xsl:apply-templates select="document(concat($baseURL, '?verb=Identify'))/oai_v20:OAI-PMH/oai_v20:Identify/oai_v20:description/customERRoLSchema:itemService">
      <xsl:with-param name="identifier">
<xsl:value-of select="$identifier"/>
</xsl:with-param>
    </xsl:apply-templates>

    <ERRoL:extendedERRoL>
      <dc:identifier>
        <xsl:text>http://localhost:8080/errol/</xsl:text>
        <xsl:if test="$repositoryIdentifier">
          <xsl:value-of select="$repositoryIdentifier"/>
          <xsl:text>/</xsl:text>
        </xsl:if>
        <xsl:value-of select="$identifier"/>
        <xsl:text>.resource</xsl:text>
      </dc:identifier>
      <dc:title>Web Resource</dc:title>
      <dc:description>An HTTP redirect to the first resolvable dc:identifier in the target repository's oai_dc metadata record for this identifier</dc:description>
    </ERRoL:extendedERRoL>
    <ERRoL:extendedERRoL>
      <dc:identifier>
        <xsl:text>http://localhost:8080/errol/</xsl:text>
        <xsl:if test="$repositoryIdentifier">
          <xsl:value-of select="$repositoryIdentifier"/>
          <xsl:text>/</xsl:text>
        </xsl:if>
        <xsl:value-of select="$identifier"/>
        <xsl:text>.display</xsl:text>
      </dc:identifier>
      <dc:title>Framed integrated display of the repository, metadata, and resource.</dc:title>
    </ERRoL:extendedERRoL>
    <!--
    <ERRoL:extendedERRoL>
      <dc:identifier>
        <xsl:text>http://localhost:8080/errol/</xsl:text>
        <xsl:if test="$repositoryIdentifier">
          <xsl:value-of select="$repositoryIdentifier" />
          <xsl:text>/</xsl:text>
        </xsl:if>
        <xsl:value-of select="$identifier" />
        <xsl:text>.ListMetadataFormats</xsl:text>
      </dc:identifier>
      <dc:title>OAI ListMetadataFormats Response</dc:title>
      <dc:description>An HTTP redirect to the target repository for an OAI ListMetadataFormats response for this identifier</dc:description>
    </ERRoL:extendedERRoL>
    -->
    </xsl:if>
  </xsl:template>

  <!--
    callable function
  -->

  <xsl:template name="writeERRoL">
    <xsl:param name="identifier"/>
    <xsl:param name="metadataPrefix"/>
    <xsl:param name="connector"/>
    <ERRoL:extendedERRoL>
      <dc:identifier>
        <xsl:text>http://localhost:8080/errol/</xsl:text>
        <xsl:if test="$repositoryIdentifier">
          <xsl:value-of select="$repositoryIdentifier"/>
          <xsl:text>/</xsl:text>
        </xsl:if>
        <xsl:value-of select="$identifier"/>
        <xsl:value-of select="$connector"/>
        <xsl:value-of select="$metadataPrefix"/>
      </dc:identifier>
      <xsl:choose>
        <xsl:when test="$connector='.'">
          <dc:title>
            <xsl:text>Raw Metadata Response (</xsl:text>
            <xsl:value-of select="$metadataPrefix"/>
            <xsl:text>)</xsl:text>
          </dc:title>
          <dc:description>
            <xsl:text>The raw XML content extracted from the target repository for an OAI GetRecord response for this identifier (</xsl:text>
            <xsl:value-of select="$metadataPrefix"/>
            <xsl:text>)</xsl:text>
          </dc:description>
        </xsl:when>
        <!--
        <xsl:when test="$connector='?'">
          <dc:title>
            <xsl:text>OAI GetRecord Response (</xsl:text>
            <xsl:value-of select="$metadataPrefix" />
            <xsl:text>)</xsl:text>
          </dc:title>
          <dc:description>
            <xsl:text>An HTTP redirect to the target repository for an OAI GetRecord response for this identifier (</xsl:text>
            <xsl:value-of select="$metadataPrefix" />
            <xsl:text>)</xsl:text>
          </dc:description>
        </xsl:when>
        -->
      </xsl:choose>
    </ERRoL:extendedERRoL>
  </xsl:template>

  <xsl:template match="oai_v11_LMDF:metadataFormat">
    <xsl:param name="identifier"/>
    <xsl:param name="connector"/>
    <xsl:call-template name="writeERRoL">
      <xsl:with-param name="identifier">
        <xsl:value-of select="$identifier"/>
      </xsl:with-param>
      <xsl:with-param name="metadataPrefix">
        <xsl:value-of select="oai_v11_LMDF:metadataPrefix"/>
      </xsl:with-param>
      <xsl:with-param name="connector">
        <xsl:value-of select="$connector"/>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:template>

  <xsl:template match="oai_v20:metadataFormat">
    <xsl:param name="identifier"/>
    <xsl:param name="connector"/>
    <xsl:call-template name="writeERRoL">
      <xsl:with-param name="identifier">
        <xsl:value-of select="$identifier"/>
      </xsl:with-param>
      <xsl:with-param name="metadataPrefix">
        <xsl:value-of select="oai_v20:metadataPrefix"/>
      </xsl:with-param>
      <xsl:with-param name="connector">
        <xsl:value-of select="$connector"/>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:template>

  <xsl:template match="customERRoLSchema:repositoryService">
    <xsl:if test="$repositoryIdentifier">
      <ERRoL:customRepositoryServiceERRoL>
        <dc:identifier>
          <xsl:text>http://localhost:8080/errol/</xsl:text>
          <xsl:value-of select="$repositoryIdentifier"/>
          <xsl:text>.</xsl:text>
          <xsl:value-of select="customERRoLSchema:extension"/>
        </dc:identifier>
        <dc:title>
<xsl:value-of select="customERRoLSchema:title"/>
</dc:title>
      </ERRoL:customRepositoryServiceERRoL>
    </xsl:if>
  </xsl:template>

  <xsl:template match="customERRoLSchema:itemService">
    <xsl:param name="identifier"/>
    <xsl:if test="$repositoryIdentifier">
      <ERRoL:customItemServiceERRoL>
        <dc:identifier>
          <xsl:text>http://localhost:8080/errol/</xsl:text>
          <xsl:value-of select="$repositoryIdentifier"/>
          <xsl:text>/</xsl:text>
          <xsl:value-of select="$identifier"/>
          <xsl:text>.</xsl:text>
          <xsl:value-of select="customERRoLSchema:extension"/>
        </dc:identifier>
        <dc:title>
<xsl:value-of select="customERRoLSchema:title"/>
</dc:title>
      </ERRoL:customItemServiceERRoL>
    </xsl:if>
  </xsl:template>

  <xsl:template match="customERRoLSchema:identifierService">
  </xsl:template>

  <xsl:template match="oai_v20:*"/>
  
  <xsl:template match="*"/>

</xsl:stylesheet>

