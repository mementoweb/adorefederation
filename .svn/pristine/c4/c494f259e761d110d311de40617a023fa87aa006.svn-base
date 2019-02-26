<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
  <xsl:include href="../fk/parseEncoding.xsl"/>
  <xsl:include href="../fk/netricsCount.xsl"/>

  <xsl:output method="html" indent="yes"/>

  <xsl:template match="info">

    <html xmlns:atom="http://www.w3.org/2005/Atom">
      <head>
        <title>
          <xsl:value-of select="concat('[', recID, '] ' , title)"/>
        </title> 
        <link rel="stylesheet" href="/plink.css"/>
      </head>
      <body>
        <div id="header">
          <a href="http://library.lanl.gov/"><img alt="Research Library" src="/plink-lib-logo.gif" /></a>
        </div>
        <div id="definition">
          <xsl:text>A permalink (or permanent link) is a URL that points to a specific record or resource.</xsl:text>
          <ul>
            <li>Permalinks remain unchanged indefinitely</li>
            <li>Permalinks are considered the best way to bookmark a record</li>
            <li>The additional resources provided on this page offer alternate views of the record and/or resources associated with the record</li>
          </ul>
        </div>
        <div id="plink">
          <table>
            <tr>
              <td class="label">
                <span class="plink">Permalink:</span>
              </td>
              <td>
                <input type="text" id="pl" name="Permalink"  onfocus="document.getElementById('pl').select();"> 
                  <xsl:attribute name="value"><xsl:value-of select="pdisplay"/></xsl:attribute>
                </input>
              </td>
            </tr>
          </table>
        </div>
        <div id="bib">
          <table>
            <tr class="title">
              <td class="label">
                <span>Title:</span>
              </td>
              <td>
                <span class="title">
                  <xsl:call-template name="parseEntities">
		    <xsl:with-param name="string" select="title"/>
                  </xsl:call-template>
                </span>
              </td>
            </tr>
            <tr class="contributor">
              <td class="label">
                <span>Author(s):</span>
              </td>
              <td>
                <span class="contributor">
                  <xsl:for-each select="authors/author">
                    <xsl:call-template name="parseEntities">
                      <xsl:with-param name="string" select="."/>
                    </xsl:call-template>
                  </xsl:for-each>
                </span>
              </td>
            </tr>
            <tr class="source">
              <td class="label">
                <span>Source:</span>
              </td>
              <td>
                <span class="source">
                  <xsl:call-template name="parseEntities">
                     <xsl:with-param name="string" select="source"/>
                  </xsl:call-template>
                </span>
              </td>
            </tr>
            <xsl:if test="abstract">
              <tr class="abstract">
                <td class="label">
                  <span>Abstract:</span>
                </td>
                <td>
                  <span class="abstract">
                    <xsl:call-template name="parseEntities">
                      <xsl:with-param name="string" select="abstract"/>
                    </xsl:call-template>
                  </span>
                </td>
              </tr>
            </xsl:if>
            <!-- adding cite count - fk - 11/20/08--> 
            <!-- if resources present - ok to present cite count --> 
            <xsl:if test="resources/resource">
		<tr class="cite">
                <td class="label">
                  <span>Times cited: </span>
                </td>
                <td>
                <span class="cite">
                  <xsl:call-template name="citeCount">
		     <xsl:with-param name="id" select="recID"/>
                  </xsl:call-template>
                </span>
              </td>
              </tr>
           </xsl:if> 
          </table>
        </div>
        <xsl:if test="resources/resource">
          <div id="allsrv">
            <table>
              <tr class="services">
                <td class="label">
                  <span>Resources:</span>
                </td>
                <td>
                  <table>
                    <tr>
                      <td class="service">
                        <span class="services">Citation: </span>
                      </td>
                      <td>
                      <xsl:variable name="id" >
                         <xsl:value-of select="'citation'"/>
                      </xsl:variable>
                      <input type="text" onfocus="document.getElementById('{$id}').select();">
                          <xsl:attribute name="id"><xsl:value-of select="$id"/></xsl:attribute>
                          <xsl:attribute name="value"><xsl:value-of select="citation"/></xsl:attribute>
                        </input>
                      </td>
                    </tr>
                    <xsl:for-each select="resources/resource">
                      <tr>
                        <td class="service">
                          <span class="services">
                            <a>
                              <xsl:attribute name="href"><xsl:value-of select="link"/></xsl:attribute>
                              <xsl:value-of select="name"/>
                            </a>
                          </span>
                        </td>
                        <td>
                          <xsl:variable name="id">
                            <xsl:value-of select="id"/>
                          </xsl:variable>
                          <input type="text" onfocus="document.getElementById('{$id}').select();">
                            <xsl:attribute name="id"><xsl:value-of select="id"/></xsl:attribute>
                            <xsl:attribute name="value"><xsl:value-of select="display"/></xsl:attribute>
                          </input>
                        </td>
                      </tr>
                    </xsl:for-each>
                  </table>
                </td>
              </tr>
            </table>
          </div>
        </xsl:if>
        
        <div id="footer">
          <table>
            <tr>
              <td rowspan="3">
                <a href="http://www.lanl.gov" target="otherWindow">
                  <img src="/plink-lanl-logo.jpg" alt="LANL Logo" />
                </a>
              </td>
              <td>
                <h1>Los Alamos National Laboratory  </h1>
                <h2>Est. 1943</h2>
              </td>
            </tr>
            <tr>
              <td>
                <h2>
                  Operated by Los Alamos National Security, LLC for the 
                  <a href="http://www.energy.gov/">U.S. Department of Energy's</a>
                   NNSA
                </h2>
              </td>
            </tr>
            <tr>
              <td>
                <a href="http://www.lanl.gov/">Outside</a>
                <xsl:text> | </xsl:text>
                <a href="http://int.lanl.gov/copyright.shtml#copyright">
                  Copyright 2007 Los Alamos National Security, LLC All rights reserved
                </a>
                <xsl:text> | </xsl:text>
                <a href="http://int.lanl.gov/copyright.shtml#disclaimers">Disclaimer/Privacy</a>
                <xsl:text> | </xsl:text>
                <a href="http://library.lanl.gov/libinfo/privacy.htm">LIbrary Privacy Policy</a>
              </td>
            </tr>
          </table>
        </div>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
