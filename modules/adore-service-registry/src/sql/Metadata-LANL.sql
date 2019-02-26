USE service_registry;
INSERT INTO OAI_SCHEMAS (schemaId, name, title, prefix, oai_prefix, identifier, namespace_url, schema_url, namespaces, castor_mapping)
    VALUES (
    1
    ,'iesr'
    ,'iesr: JISC Information Environment Service Registry'
    ,'iesr'
    ,'oai_iesr'
    ,'info:iesr/schema/1/iesr-v2.6'
    ,'http://iesr.ac.uk/terms/#'
    ,'http://purl.lanl.gov/adore/schemas/2007-09/iesr.xsd'
    ,'dc=http://purl.org/dc/elements/1.1/,iesr=http://iesr.ac.uk/terms/#,dcterms=http://purl.org/dc/terms/,rslpcd=http://purl.org/rslp/terms#'
    ,'/resources/crosswalks/IESRDescription.xml'
    );

INSERT INTO OAI_SCHEMAS (schemaId, name, title, prefix, oai_prefix, identifier, namespace_url, schema_url, namespaces, castor_mapping)
    VALUES (
    2
    ,'dc'
    ,'dc: Dublin Core Elements'
    ,'dc'
    ,'oai_dc'
    ,'info:srw/schema/1/dc-v1.1'
    ,'http://purl.org/dc/elements/1.1/'
    ,'http://dublincore.org/schemas/xmls/simpledc20021212.xsd'
    ,'dc=http://purl.org/dc/elements/1.1/'
    ,''
    );

-- add crosswalk between iesr and dc

INSERT INTO OAI_CROSSWALKS (crosswalk_id, start_schema, end_schema, xsl)
    VALUES (
    1
    ,1
    ,2
    ,'<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
     xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
     xmlns:dc="http://purl.org/dc/elements/1.1/"
     xmlns:iesr="http://iesr.ac.uk/terms/#"
     xmlns:dcmitype="http://purl.org/dc/dcmitype/"
     xmlns:rslpcd="http://purl.org/rslp/terms#"
     xmlns:dcterms="http://purl.org/dc/terms/">
    <xsl:output method="xml"/>


    <xsl:template match="iesr:iesrDescription">
        <dc:dc>
        <xsl:apply-templates/>
        </dc:dc>
    </xsl:template>

    <xsl:template match="dc:*">
        <xsl:if test="not(string-length(.) = 0)"><xsl:copy><xsl:value-of select="."/></xsl:copy></xsl:if>
    </xsl:template>

    <xsl:template match="iesr:Agent">
        <xsl:apply-templates/>
    </xsl:template>
    <xsl:template match="iesr:logo">
        <xsl:if test="not(string-length(.) = 0)"><dc:description><xsl:value-of select="."/></dc:description></xsl:if>
    </xsl:template>
    <xsl:template match="iesr:email">
        <xsl:if test="not(string-length(.) = 0)"><dc:description><xsl:value-of select="."/></dc:description></xsl:if>
    </xsl:template>
    <xsl:template match="iesr:address">
        <xsl:if test="not(string-length(.) = 0)"><dc:description><xsl:value-of select="."/></dc:description></xsl:if>
    </xsl:template>
    <xsl:template match="iesr:postcode">
        <xsl:if test="not(string-length(.) = 0)"><dc:description><xsl:value-of select="."/></dc:description></xsl:if>
    </xsl:template>
    <xsl:template match="iesr:country">
        <xsl:if test="not(string-length(.) = 0)"><dc:description><xsl:value-of select="."/></dc:description></xsl:if>
    </xsl:template>
    <xsl:template match="iesr:phone">
        <xsl:if test="not(string-length(.) = 0)"><dc:description><xsl:value-of select="."/></dc:description></xsl:if>
    </xsl:template>
    <xsl:template match="iesr:owns">
       <xsl:if test="not(string-length(.) = 0)"> <dc:relation><xsl:value-of select="."/></dc:relation></xsl:if>
    </xsl:template>
    <xsl:template match="iesr:administers">
        <xsl:if test="not(string-length(.) = 0)"><dc:relation><xsl:value-of select="."/></dc:relation></xsl:if>
    </xsl:template>
    <xsl:template match="rslpcd:seeAlso">
        <xsl:if test="not(string-length(.) = 0)"><dc:relation><xsl:value-of select="."/></dc:relation></xsl:if>
    </xsl:template>

    <xsl:template match="iesr:Collection">
        <xsl:apply-templates/>
    </xsl:template>
    <xsl:template match="dcterms:abstract">
        <xsl:if test="not(string-length(.) = 0)"><dc:description><xsl:value-of select="."/></dc:description></xsl:if>
    </xsl:template>
    <xsl:template match="dcterms:alternative">
        <xsl:if test="not(string-length(.) = 0)"><dc:title><xsl:value-of select="."/></dc:title></xsl:if>
    </xsl:template>
    <xsl:template match="dcterms:extent">
        <xsl:if test="not(string-length(.) = 0)"><dc:format><xsl:value-of select="."/></dc:format></xsl:if>
    </xsl:template>
    <xsl:template match="iesr:hasService">
        <xsl:if test="not(string-length(.) = 0)"><dc:relation><xsl:value-of select="."/></dc:relation></xsl:if>
    </xsl:template>
    <xsl:template match="iesr:owner">
        <xsl:if test="not(string-length(.) = 0)"><dc:relation><xsl:value-of select="."/></dc:relation></xsl:if>
    </xsl:template>
    <xsl:template match="dcterms:isPartOf">
        <xsl:if test="not(string-length(.) = 0)"><dc:relation><xsl:value-of select="."/></dc:relation></xsl:if>
    </xsl:template>
    <xsl:template match="rslpcd:hasAssociation">
        <xsl:if test="not(string-length(.) = 0)"><dc:relation><xsl:value-of select="."/></dc:relation></xsl:if>
    </xsl:template>
    <xsl:template match="dcterms:isReferencedBy">
        <xsl:if test="not(string-length(.) = 0)"><dc:relation><xsl:value-of select="."/></dc:relation></xsl:if>
    </xsl:template>
    <xsl:template match="dcterms:spatial">
        <xsl:if test="not(string-length(.) = 0)"><dc:coverage><xsl:value-of select="."/></dc:coverage></xsl:if>
    </xsl:template>
    <xsl:template match="dcterms:temporal">
        <xsl:if test="not(string-length(.) = 0)"><dc:coverage><xsl:value-of select="."/></dc:coverage></xsl:if>
    </xsl:template>
    <xsl:template match="rslpcd:contentsDateRange">
        <xsl:if test="not(string-length(.) = 0)"><dc:coverage><xsl:value-of select="."/></dc:coverage></xsl:if>
    </xsl:template>
    <xsl:template match="dcterms:educationLevel">
        <xsl:if test="not(string-length(.) = 0)"><dc:coverage><xsl:value-of select="."/></dc:coverage></xsl:if>
    </xsl:template>
    <xsl:template match="iesr:useRights">
        <xsl:if test="not(string-length(.) = 0)"><dc:rights><xsl:value-of select="."/></dc:rights></xsl:if>
    </xsl:template>
    <xsl:template match="dcterms:accessRights">
        <xsl:if test="not(string-length(.) = 0)"><dc:rights><xsl:value-of select="."/></dc:rights></xsl:if>
    </xsl:template>
    <xsl:template match="iesr:usesControlledList">
    </xsl:template>

    <xsl:template match="iesr:supportsStandard">
        <xsl:if test="not(string-length(.) = 0)"><dc:type><xsl:value-of select="."/></dc:type></xsl:if>
    </xsl:template>
    <xsl:template match="rslpcd:locator">
        <xsl:if test="not(string-length(.) = 0)"><dc:identifier><xsl:value-of select="."/></dc:identifier></xsl:if>
    </xsl:template>
    <xsl:template match="iesr:mediator">
        <xsl:if test="not(string-length(.) = 0)"><dc:relation><xsl:value-of select="."/></dc:relation></xsl:if>
    </xsl:template>
    <xsl:template match="rslpcd:administrator">
        <xsl:if test="not(string-length(.) = 0)"><dc:relation><xsl:value-of select="."/></dc:relation></xsl:if>
    </xsl:template>
    <xsl:template match="iesr:interface">
        <xsl:if test="not(string-length(.) = 0)"><dc:relation><xsl:value-of select="."/></dc:relation></xsl:if>
    </xsl:template>
    <xsl:template match="iesr:serves">
        <xsl:if test="not(string-length(.) = 0)"><dc:relation><xsl:value-of select="."/></dc:relation></xsl:if>
    </xsl:template>
    <xsl:template match="iesr:accesscontrol">
    </xsl:template>
    <xsl:template match="dcterms:output">
    </xsl:template>
</xsl:stylesheet>');
    