<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:atom="http://www.w3.org/2005/Atom"
	xmlns:marc="http://www.loc.gov/MARC21/slim"
	xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="atom:feed">
		<html>
			<head>
				<title>
					aDORe List of Services for
					<xsl:value-of select="atom:title" />
				</title>
				<link rel="stylesheet" href="adore-atom.css" />
				<link rel="oai-ore-obtain" type="application/atom+xml" title="OAI-ORE Obtain" href="http://penguin.lanl.gov:8090/disseminator/service"/>
			</head>
			<body>
				<img src="img/elogo.gif" alt="E-Library" />
				<br /><br />
				<div class="feed-title">
					Title:
					<xsl:value-of select="atom:title" />
				</div>
				<div class="feed-contributor">
					Author(s):
					<xsl:value-of select="atom:contributor" />
				</div>
				<xsl:variable name="atom-id" select="atom:id" />
				<span class="rft_id" title= "{$atom-id}"/>
				<div class="feed-id">
					Id:
					<xsl:value-of select="atom:id" />
				</div>
				<br />
				<div class="text">List of Services:</div><br />
				<xsl:apply-templates select="atom:entry" />
			</body>
		</html>
	</xsl:template>
	<xsl:template match="atom:entry">
		<div class="entry">
			<xsl:variable name="content-src" select="atom:content/@src" />
			<a href="{$content-src}">
				<xsl:choose>
					<xsl:when
						test="atom:title='info:lanl-repo/svc/getDIDL'">
						DIDL
					</xsl:when>
					<xsl:when
						test="atom:title='info:lanl-repo/svc/getBibliographic'">
						MARC 21
					</xsl:when>
					<xsl:when
						test="atom:title='info:lanl-repo/svc/getBibliographic.dc'">
						Dublin Core
					</xsl:when>
					<xsl:when
						test="atom:title='info:lanl-repo/svc/getBibliographic.mods'">
						MODS
					</xsl:when>
					<xsl:when
						test="atom:title='info:lanl-repo/svc/getBibliographic.ori'">
						Original Bibliographic Record Provided by the
						Publisher
					</xsl:when>
				</xsl:choose>
			</a>
		</div>
		<br />
	</xsl:template>

  <xsl:template name="biosisKeywords">
    <xsl:call-template name="biosisMajorConcept"/>
    <xsl:call-template name="biosisConcept"/>
    <xsl:call-template name="biosisMiscSubject"/>
    <xsl:call-template name="biosisSuperTaxa"/>
    <xsl:call-template name="biosisBiosystematicCode"/>
    <xsl:call-template name="biosisOrganism"/>
    <xsl:call-template name="biosisTaxaNotes"/>
    <xsl:call-template name="biosisMethods"/>
    <xsl:call-template name="biosisOtherKW"/>
	
    <!--need other keyword fields like chemical and geological info and source-->
  </xsl:template>


  <xsl:template name="biosisMajorConcept">
    <xsl:variable name="mc" select="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'MajorConcept')]"/>
    <xsl:if test="$mc">
      <xsl:choose>
	  
        <xsl:when test="$exportFormat = 'ris'">
          <xsl:for-each select="$mc">
            <xsl:text>KW&#160;&#160;- </xsl:text>
            <xsl:value-of select="../marc:subfield[@code='a']"/>
            <br/>
          </xsl:for-each>
        </xsl:when>

        <xsl:when test="$exportFormat = ''">      
          <p>
            <span class="wpFullRecLabels">Major Concept: </span>
            <xsl:for-each select="$mc">
              <xsl:value-of select="../marc:subfield[@code='a']"/>
              <xsl:if test="position() != last()">
                <xsl:text>; </xsl:text>
              </xsl:if>
            </xsl:for-each>
          </p>
        </xsl:when>

      </xsl:choose>
    </xsl:if>
  </xsl:template>
  

  <xsl:template name="biosisConcept">
    <xsl:variable name="concept" select="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'ConceptCode')]"/>
    <xsl:if test="$concept">
      <xsl:choose>

        <xsl:when test="$exportFormat = 'ris'">
          <xsl:for-each select="$concept">
            <xsl:text>KW&#160;&#160;- </xsl:text>
            <xsl:value-of select="../marc:subfield[@code='a']"/>
            <br/>
          </xsl:for-each>
        </xsl:when>

        <xsl:when test="$exportFormat = ''">
          <p>
            <span class="wpFullRecLabels">Concept: </span>
            <xsl:for-each select="$concept">
              <xsl:value-of select="../marc:subfield[@code='a']"/>
              <xsl:if test="position() != last()">
                <xsl:text>; </xsl:text>
              </xsl:if>
            </xsl:for-each>
          </p>
        </xsl:when>

      </xsl:choose>
    </xsl:if>
  </xsl:template>


  <xsl:template name="biosisMiscSubject">
    <xsl:variable name="misc" select="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'MiscellaneousDescriptors')]"/>
    <xsl:if test="$misc">

      <xsl:choose>
        <xsl:when test="$exportFormat = 'ris'">
          <xsl:for-each select="$misc">
            <xsl:text>KW&#160;&#160;- </xsl:text>
            <xsl:value-of select="../marc:subfield[@code='a']"/>
            <br/>
          </xsl:for-each>
        </xsl:when>

        <xsl:when test="$exportFormat = ''">
          <p>
            <span class="wpFullRecLabels">Misc. Subject: </span>
            <xsl:for-each select="$misc">
              <xsl:value-of select="../marc:subfield[@code='a']"/>
              <xsl:if test="position() != last()">
                <xsl:text>; </xsl:text>
              </xsl:if>
            </xsl:for-each>
          </p>
        </xsl:when>

      </xsl:choose>
    </xsl:if>
  </xsl:template>

  
  <xsl:template name="biosisSuperTaxa">
    <xsl:variable name="superTaxa" select="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'SuperTaxa')]"/>
    <xsl:if test="$superTaxa">
      <xsl:choose>
        
        <xsl:when test="$exportFormat = 'ris'">
          <xsl:for-each select="$superTaxa">
            <xsl:text>KW&#160;&#160;- </xsl:text>
            <xsl:value-of select="../marc:subfield[@code='a']"/>
            <br/>
          </xsl:for-each>
        </xsl:when>

        <xsl:when test="$exportFormat = ''">
          <p>
            <span class="wpFullRecLabels">Super Taxa: </span>
            <xsl:for-each select="$superTaxa">
              <xsl:value-of select="../marc:subfield[@code='a']"/>
              <xsl:if test="position() != last()">
                <xsl:text>; </xsl:text>
              </xsl:if>
            </xsl:for-each>
          </p>
        </xsl:when>
        
      </xsl:choose>
    </xsl:if>
  </xsl:template>
  
  
  <xsl:template name="biosisBiosystematicCode">
    <xsl:variable name="biosystematicCode" select="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'OrgClassifier')]"/>
    <xsl:if test="$biosystematicCode">
      <xsl:choose>

        <xsl:when test="$exportFormat = 'ris'">
          <xsl:for-each select="$biosystematicCode">
            <xsl:text>KW&#160;&#160;- </xsl:text>
            <xsl:value-of select="../marc:subfield[@code='a']"/>
            <br/>
          </xsl:for-each>
        </xsl:when>

        <xsl:when test="$exportFormat = ''">
          <p>
            <span class="wpFullRecLabels">Biosystematic Code: </span>
            <xsl:for-each select="$biosystematicCode">
              <xsl:value-of select="../marc:subfield[@code='a']"/>
              <xsl:if test="position() != last()">
                <xsl:text>; </xsl:text>
              </xsl:if>
            </xsl:for-each>
          </p>
        </xsl:when>

      </xsl:choose>
    </xsl:if>
  </xsl:template>

  
  <xsl:template name="biosisOrganism">
    <xsl:variable name="organism" select="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'OrganismName')]"/>
    <xsl:if test="$organism">
      <xsl:choose>

        <xsl:when test="$exportFormat = 'ris'">
          <xsl:for-each select="$organism">
            <xsl:text>KW&#160;&#160;- </xsl:text>
            <xsl:value-of select="../marc:subfield[@code='a']"/>
            <br/>
          </xsl:for-each>
        </xsl:when>

        <xsl:when test="$exportFormat = ''">
          <p>
            <span class="wpFullRecLabels">Organism: </span>
            <xsl:for-each select="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'OrganismName')]">
              <xsl:value-of select="../marc:subfield[@code='a']"/>
              <xsl:if test="position() != last()">
                <xsl:text>; </xsl:text>
              </xsl:if>
            </xsl:for-each>
          </p>
        </xsl:when>
      </xsl:choose>
    </xsl:if>
  </xsl:template>

  
  <xsl:template name="biosisTaxaNotes">
    <xsl:variable name="taxaNotes" select="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'TaxaNotes')]"/>
    <xsl:if test="$taxaNotes">
      <xsl:choose>

        <xsl:when test="$exportFormat = 'ris'">
          <xsl:for-each select="$taxaNotes">
            <xsl:text>KW&#160;&#160;- </xsl:text>
            <xsl:value-of select="../marc:subfield[@code='a']"/>
            <br/>
          </xsl:for-each>
        </xsl:when>

        <xsl:when test="$exportFormat = ''">
          <p>
            <span class="wpFullRecLabels">Taxa Notes: </span>
            <xsl:for-each select="$taxaNotes">
              <xsl:value-of select="../marc:subfield[@code='a']"/>
              <xsl:if test="position() != last()">
                <xsl:text>; </xsl:text>
              </xsl:if>
            </xsl:for-each>
          </p>
        </xsl:when>

      </xsl:choose>
    </xsl:if>
  </xsl:template>


  <xsl:template name="biosisMethods">
    <xsl:variable name="methods" select="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'MethodsEquipmentData')]"/>
    <xsl:if test="$methods">
      <xsl:choose>

        <xsl:when test="$exportFormat = 'ris'">
          <xsl:for-each select="$methods">
            <xsl:text>KW&#160;&#160;- </xsl:text>
            <xsl:value-of select="../marc:subfield[@code='a']"/>
            <br/>
          </xsl:for-each>
        </xsl:when>

        <xsl:when test="$exportFormat = ''">
          <p>
            <span class="wpFullRecLabels">Methods &amp; Equipment: </span>
            <xsl:for-each select="marc:datafield[@tag='650']/marc:subfield[@code='2'][contains(text(),'MethodsEquipmentData')]">
              <xsl:value-of select="../marc:subfield[@code='a']"/>
              <xsl:if test="position() != last()">
                <xsl:text>; </xsl:text>
              </xsl:if>
            </xsl:for-each>
          </p>
        </xsl:when>

      </xsl:choose>
    </xsl:if>
  </xsl:template>
  
  
  <xsl:template name="biosisOtherKW">
    <xsl:variable name="other" select="marc:datafield[@tag='650']/marc:subfield[@code='2'][not(contains(text(),'MethodsEquipmentData') or contains(text(),'TaxaNotes') or contains(text(),'OrganismName') or contains(text(),'TypeofName') or contains(text(),'OrgDetail') or contains(text(),'OrgClassifier') or contains(text(),'SuperTaxa') or contains(text(),'MiscellaneousDescriptors') or contains(text(),'ConceptCode') or contains(text(),'MajorConcept'))]"/>
    <xsl:if test="$other">
      <xsl:choose>

        <xsl:when test="$exportFormat = 'ris'">
          <xsl:for-each select="$other">
            <xsl:text>KW&#160;&#160;- </xsl:text>
            <xsl:value-of select="../marc:subfield[@code='a']"/>
            <br/>
          </xsl:for-each>
        </xsl:when>

        <xsl:when test="$exportFormat = ''">
          <p>
            <span class="wpFullRecLabels">Other keywords: </span>
            <xsl:for-each select="$other">
              <xsl:value-of select="../marc:subfield[@code='a']"/>
              <xsl:if test="position() != last()">
                <xsl:text>; </xsl:text>
              </xsl:if>
            </xsl:for-each>
          </p>
        </xsl:when>

      </xsl:choose>
    </xsl:if>    
  </xsl:template>
  

  <xsl:template name="biosisDocType">
    <xsl:variable name="dt" select="marc:datafield[@tag='655']/marc:subfield[@code='2'][contains(text(),'DocumentTypeText') or contains(text(),'LiteratureTypeText')]"/>
    <xsl:if test="$dt">
      <xsl:choose>

        <xsl:when test="$exportFormat = 'ris'">
          <xsl:for-each select="$dt">
            <xsl:text>M3&#160;&#160;- </xsl:text>
            <xsl:value-of select="../marc:subfield[@code='a']"/>
            <br/>
          </xsl:for-each>
        </xsl:when>

        <xsl:when test="$exportFormat = ''">
          <p>
            <span class="wpFullRecLabels">Doc. Type: </span>
            <xsl:for-each select="$dt">
              <xsl:value-of select="../marc:subfield[@code='a']"/>
              <xsl:if test="position() != last()">
                <xsl:text>; </xsl:text>
              </xsl:if>
            </xsl:for-each>
          </p>
        </xsl:when>

      </xsl:choose>
    </xsl:if>
  </xsl:template>


</xsl:stylesheet>
