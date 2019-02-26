<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:marc="http://www.loc.gov/MARC21/slim">

<!-- fknudson 20070730 - grabs all "lists" of Biosis keywords as units -->


<xsl:template name="biosisKeywords">
<xsl:call-template name="majorConcept"/>
<xsl:call-template name="taxonomicData"/>
<xsl:call-template name="geneNameData"/>
<xsl:call-template name="chemicalData"/>
<xsl:call-template name="diseaseData"/>
<xsl:call-template name="sequenceData"/>
<xsl:call-template name="partsData"/>
<xsl:call-template name="methodsData"/>
<xsl:call-template name="geographicData"/>
<xsl:call-template name="concept"/>
<xsl:call-template name="miscSubject"/>
</xsl:template>


<xsl:template name="majorConcept">
<xsl:if test="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'MajorConcept')]">
	<xsl:call-template name="outputList">
		<xsl:with-param name="ctr" select="1"/>
		<xsl:with-param name="listName" select="'MajorConceptList'"/>
	</xsl:call-template>
</xsl:if>
</xsl:template>

<xsl:template name="taxonomicData">
<xsl:if test="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'TaxonomicData')]">
	<xsl:call-template name="outputList">
		<xsl:with-param name="ctr" select="1"/>
		<xsl:with-param name="listName" select="'TaxonomicList'"/>
	</xsl:call-template>
</xsl:if>
</xsl:template>


<xsl:template name="geneNameData">
<xsl:if test="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'GeneNameData')]">
	<xsl:call-template name="outputList">
		<xsl:with-param name="ctr" select="1"/>
		<xsl:with-param name="listName" select="'GeneNameList'"/>
	</xsl:call-template>
</xsl:if>
</xsl:template>

<xsl:template name="chemicalData">
<xsl:if test="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'ChemicalData')]">
	<xsl:call-template name="outputList">
		<xsl:with-param name="ctr" select="1"/>
		<xsl:with-param name="listName" select="'CASRegistryList'"/>
	</xsl:call-template>
	<xsl:call-template name="outputList">
		<xsl:with-param name="ctr" select="1"/>
		<xsl:with-param name="listName" select="'ChemicalList'"/>
	</xsl:call-template>
</xsl:if>
</xsl:template>

<xsl:template name="diseaseData">
<xsl:if test="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'DiseaseData')]">
	<xsl:call-template name="outputList">
		<xsl:with-param name="ctr" select="1"/>
		<xsl:with-param name="listName" select="'DiseaseList'"/>
	</xsl:call-template>
</xsl:if>
</xsl:template>


<xsl:template name="sequenceData">
<xsl:if test="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'SequenceData')]">
	<xsl:call-template name="outputList">
		<xsl:with-param name="ctr" select="1"/>
		<xsl:with-param name="listName" select="'SequenceDataList'"/>
	</xsl:call-template>
</xsl:if>
</xsl:template>

<xsl:template name="partsData">
<xsl:if test="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'PartsStructuresData')]">
	<xsl:call-template name="outputList">
		<xsl:with-param name="ctr" select="1"/>
		<xsl:with-param name="listName" select="'PartsStructuresList'"/>
	</xsl:call-template>
</xsl:if>
</xsl:template>

<xsl:template name="methodsData">
<xsl:if test="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'MethodsEquipmentData')]">
	<xsl:call-template name="outputList">
		<xsl:with-param name="ctr" select="1"/>
		<xsl:with-param name="listName" select="'MethodsEquipmentList'"/>
	</xsl:call-template>
</xsl:if>
</xsl:template>

<xsl:template name="geographicData">
<xsl:if test="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'GeographicData')]">
	<xsl:call-template name="outputList">
		<xsl:with-param name="ctr" select="1"/>
		<xsl:with-param name="listName" select="'GeographicList'"/>
	</xsl:call-template>
</xsl:if>
</xsl:template>

<xsl:template name="miscSubject">
<xsl:if test="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'MiscellaneousDescriptors')]">
	<xsl:call-template name="outputList">
		<xsl:with-param name="ctr" select="1"/>
		<xsl:with-param name="listName" select="'MiscDescriptorsList'"/>
	</xsl:call-template>
	<xsl:call-template name="outputList">
		<xsl:with-param name="ctr" select="1"/>
		<xsl:with-param name="listName" select="'MDKeyWordString'"/>
	</xsl:call-template>
</xsl:if>
</xsl:template>


<xsl:template name="concept">
<xsl:if test="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'ConceptCode')]">
	<xsl:call-template name="outputList">
		<xsl:with-param name="ctr" select="1"/>
		<xsl:with-param name="listName" select="'ConceptCode'"/>
	</xsl:call-template>
</xsl:if>
</xsl:template>


</xsl:stylesheet>
