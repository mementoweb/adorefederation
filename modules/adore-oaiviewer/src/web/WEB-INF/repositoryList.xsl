<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
  <xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/1999/xhtml" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:oai="http://www.openarchives.org/OAI/2.0/" xmlns:oai_dc="http://www.openarchives.org/OAI/2.0/oai_dc/"  xmlns:pr="http://library.lanl.gov/2004-10/STB-RL/PMPrequest/" xmlns:idx="http://library.lanl.gov/2004-10/STB-RL/r-index" xmlns:xoaih="http://errol.oclc.org/oai:xmlregistry.oclc.org:xoai/xoaiharvester" exclude-result-prefixes="oai_dc oai dc xsi" version="1.0" xsi:schemaLocation="http://www.w3.org/1999/XSL/Transform http://www.w3.org/1999/XSL/Transform.xsd">
      
      <xsl:output doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN" doctype-system="http://www.w3.org/TR/html4/loose.dtd" encoding="UTF-8" indent="yes" method="html" standalone="yes" version="4.0"/>

      <xsl:key match="oai:record" name="names" use="substring(pr:identifier[1],1,1)"/>

      <xsl:param name="registryERRoL"/>
      <xsl:param name="repoIDPrefix" select="'oai:id-registry.uiuc.edu:'"/>
      <xsl:param name="localERRoLURL" select="'http://errol.oclc.org'"/>

      <xsl:template match="/idx:list">
        <html>
          <head>
            <meta content="text/html; charset=utf-8" http-equiv="Content-Type"/>
            <title>OAI Viewer: Select a repository</title>
            <link href="http://www.oclc.org/common/css/main_oclc.css" rel="stylesheet" type="text/css"/>
            <link href="http://www.oclc.org/common/css/print_oclc.css" media="print" rel="stylesheet" type="text/css"/>
          </head>
          <body bgcolor="#FFFFFF" leftmargin="0" marginheight="0" marginwidth="0" topmargin="0">
            <a name="top"/> 
            <div align="center">
              <table cellspacing="0" id="page">
                <tr>
                  <td>
                    <table cellpadding="0" cellspacing="0" id="pageheader" width="100%">
                      <tr bgcolor="#FF7600">
                        <td>
                          <a href="http://www.oclc.org/research/projects/oairesolver/" target="_blank">
<img alt="OAI Viewer" height="100" src="http://alcme.oclc.org/xmlregistry/images/logo.gif" width="400"/>
</a>
                        </td>
                        <td align="right" style="text-align: right;">
                          <a href="http://www.oclc.org/research/" target="_blank">
<img alt="A project of OCLC Research" height="100" src="http://alcme.oclc.org/xmlregistry/images/research.gif" width="200"/>
</a>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
                <tr>
                  <td id="contentareafull">
                    <table cellspacing="0" id="pagecontent">
                      <tr>
                        <td>
                          <p id="oclcprint">
                            <img alt="OCLC" height="36" src="http://www.oclc.org/common/images/logos/oclclogo_black_sm.gif" width="67"/>
                          </p>
                          <div>
                            <table cellpadding="0" cellspacing="0" width="100%">
                              <tr>
                                <td colspan="2">
<xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text> 
</td>
                              </tr> 
                              <tr>
                                <td id="contentarea" style="padding: 0 20px;" width="100%">
                                  <img alt="OAI Viewer progress" border="0" height="90" src="http://alcme.oclc.org/xmlregistry/images/progress_step01.gif" usemap="#mapProgress" width="300"/>
                                  <map name="mapProgress">
                                    <area alt="Select a repository" coords="0,0,60,90" nohref="nohref" shape="rect"/>
                                    <area alt="Filter sets and formats" coords="75,0,150,90" nohref="nohref" shape="rect"/>
                                    <area alt="Browse list records" coords="162,0,227,90" nohref="nohref" shape="rect"/>
                                    <area alt="View record" coords="250,0,300,90" nohref="nohref" shape="rect"/>
                                  </map>
                                  <table cellspacing="0" class="datatable" width="100%">
                                    <!-- loop through 1st records in each alphabetic set -->
                                    <xsl:for-each select="idx:r-index">
                                              <xsl:apply-templates select="."/>
                                            </xsl:for-each>
                                         
                                  </table>
                                  <xsl:if test="$registryERRoL and string-length($registryERRoL)&gt;0">
                                    <p>
                                      <xsl:text>Repositories are courtesy of </xsl:text>
                                      	LANL
                                    </p>
                                  </xsl:if>
                                </td>
                              </tr>
                            </table>
                          </div>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </div>
          </body>
        </html>
      </xsl:template>
      
      <xsl:template match="idx:r-index">
        <a>
          <xsl:attribute name="href">
            <xsl:value-of select="$localERRoLURL"/>
            <xsl:text>/</xsl:text>
            <xsl:value-of select="idx:shortname[1]"/>
            <xsl:text>.html</xsl:text>
          </xsl:attribute>
          <xsl:value-of select="idx:baseURL[1]"/>
        </a>
      
        <a>
          <xsl:attribute name="href">
            <xsl:value-of select="$localERRoLURL"/>
            <xsl:text>/</xsl:text>
            <xsl:value-of select="idx:shortname[1]"/>
            <xsl:text>?verb=Identify</xsl:text>
          </xsl:attribute>
          <xsl:text>Identify</xsl:text>
        </a><xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text> 
	 <xsl:value-of select="idx:type"/> <xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text> 
	<xsl:value-of select="idx:collection"/><xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text> 
         <xsl:value-of select="idx:created"/>
        <br/>
      
      </xsl:template>

    </xsl:stylesheet>

