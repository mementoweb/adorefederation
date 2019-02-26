<?xml version="1.0"?>
<!--

 Copyright (c) 2006 - Ghent University. 
 local customization introduced by LANL RL proto team

-->
<xsl:stylesheet
    xmlns:didl="urn:mpeg:mpeg21:2002:02-DIDL-NS"
    xmlns:diadm="http://library.lanl.gov/2004-01/STB-RL/DIADM"
    xmlns:dii="urn:mpeg:mpeg21:2002:01-DII-NS"
    xmlns:dc="http://purl.org/dc/elements/1.1/"

    xmlns:dcterms="http://purl.org/dc/terms/"
    xmlns:pre="http://www.loc.gov/standards/premis"
    xmlns:oai_dc="http://www.openarchives.org/OAI/2.0/oai_dc/"

    xmlns:oai="http://www.openarchives.org/OAI/2.0/"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    version="1.0">
    
    <xsl:template match="/">
        <html>
         <head>
           <title>DIDL display</title>
        <style type="text/css">@import url(style/portal.css);</style>
        <link rel="stylesheet" type="text/css" href="style/print.css" media="print"></link>
           <style type="text/css">
           <![CDATA[
        table.didl {
        width: 600px;
        }

        table.didl tr td {
        border: 1px solid #000;
        }

        table.item tr td {
        background-color: #E6F4FF !important;
        border: 1px solid #000;
        }

        table.component tr td {
        background-color: #BFE4FF !important;
        }

        table.descriptor tr {
        border: 1px solid #000;
        }

        table.descriptor tr td {
        color: #000000;
        background-color: #BFE4CC !important;
        border: 0px solid #000;
        }

        table.statement tr td {
        background-color: #80C9FF !important;
        }

        table.resource tr td {
        color: #000000;
        background-color: #FFD980 !important;
        }
        a.resource_ref , a.resource_ref:link, a.resource_ref:visited, a.resource_ref:active {
        color: #000000 !important;
        }
            #content, #contentsmall { margin: 80px 20px 0 50px; }
            #siteid { background: #fff; }
            #siteid img { left: 0; }
            #footer { margin: 30px 20px 0 50px; }
           ]]>
           </style>
            <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
         </head>
         <body>
          <h4>
           DIDL display
          </h4>

          <div id="content">
            <xsl:apply-templates select="//didl:DIDL"/>
          </div>

          <!-- Top bar 1 -->
          <div class="hidden">
           <div id="siteid"><img src="img/logo-sm.gif" alt="Prototype Logo" align="center"/>
	      proto@lanl.gov</div>
   	</div>
         </body>
        </html>
    </xsl:template>

    <xsl:template match="oai:error">
           <h1>Erez OAI-PMH service</h1>
           <p>
           The OAI server responded with the following message:
           </p>
           <p>
                <i><xsl:value-of select="//oai:error/@code"/> - <xsl:value-of select="//oai:error"/></i>
           </p>
           <p>
                Maybe you want to try one of the following requests:
                <ul>
                <li>
                <xsl:element name="a">
                <xsl:attribute name="href"><xsl:value-of select="/oai:OAI-PMH/oai:request"/>?verb=Identify</xsl:attribute>
                Identify
                </xsl:element>
                </li>
                <li>
                <xsl:element name="a">
                <xsl:attribute name="href"><xsl:value-of select="/oai:OAI-PMH/oai:request"/>?verb=ListMetadataFormats</xsl:attribute>
                ListMetadataFormats
                </xsl:element>
                </li>
                <li>
                <xsl:element name="a">
                <xsl:attribute name="href"><xsl:value-of select="/oai:OAI-PMH/oai:request"/>?verb=ListSets</xsl:attribute>
                ListSets
                </xsl:element>
                </li>

                <li>
                <xsl:element name="a">
                <xsl:attribute name="href"><xsl:value-of select="/oai:OAI-PMH/oai:request"/>?verb=ListIdentifiers&amp;metadataPrefix=didl</xsl:attribute>
                ListIdentifiers
                </xsl:element>
                </li>
                <li>
                <xsl:element name="a">
                <xsl:attribute name="href"><xsl:value-of select="/oai:OAI-PMH/oai:request"/>?verb=ListRecords&amp;metadataPrefix=didl</xsl:attribute>
                ListRecords
                </xsl:element>
                </li>
                <li>
                 <xsl:element name="form">
                  <xsl:attribute name="action"><xsl:value-of select="/oai:OAI-PMH/oai:request"/></xsl:attribute>
                  <input type="hidden" name="verb" value="GetRecord"/>
                  <input type="hidden" name="metadataPrefix" value="didl"/>
                  <input type="text" name="identifier" value="info:ugent-repo/erez/" size="40"/>
                  <input type="submit" value="GetRecord"/>
                 </xsl:element>
                </li>
                </ul>
            </p>
    </xsl:template>

    <xsl:template match="oai:Identify">
           <h1>Erez OAI-PMH service</h1>

           <p>
           Welcome to the repository here are some details for this repository:
           </p>

           <p>
           <table>
                <xsl:for-each select="//oai:Identify/*">
                <tr>
                 <td><xsl:value-of select="name(.)"/>:</td>
                 <td><xsl:value-of select="."/></td>
                </tr>
                </xsl:for-each>
           </table>
           </p>

           <p>
            Feel free to use any of the following services:
            <ul>
                <li>
                <xsl:element name="a">
                <xsl:attribute name="href"><xsl:value-of select="/oai:OAI-PMH/oai:request"/>?verb=Identify</xsl:attribute>
                Identify
                </xsl:element>
                </li>
                <li>
                <xsl:element name="a">
                <xsl:attribute name="href"><xsl:value-of select="/oai:OAI-PMH/oai:request"/>?verb=ListMetadataFormats</xsl:attribute>
                ListMetadataFormats
                </xsl:element>
                </li>
                <li>
                <xsl:element name="a">
                <xsl:attribute name="href"><xsl:value-of select="/oai:OAI-PMH/oai:request"/>?verb=ListSets</xsl:attribute>
                ListSets
                </xsl:element>
                </li>

                <li>
                <xsl:element name="a">
                <xsl:attribute name="href"><xsl:value-of select="/oai:OAI-PMH/oai:request"/>?verb=ListIdentifiers&amp;metadataPrefix=didl</xsl:attribute>
                ListIdentifiers
                </xsl:element>
                </li>
                <li>
                <xsl:element name="a">
                <xsl:attribute name="href"><xsl:value-of select="/oai:OAI-PMH/oai:request"/>?verb=ListRecords&amp;metadataPrefix=didl</xsl:attribute>
                ListRecords
                </xsl:element>
                </li>
                <li>
                 <xsl:element name="form">
                  <xsl:attribute name="action"><xsl:value-of select="/oai:OAI-PMH/oai:request"/></xsl:attribute>
                  <input type="hidden" name="verb" value="GetRecord"/>
                  <input type="hidden" name="metadataPrefix" value="didl"/>
                  <input type="text" name="identifier" size="40" value="info:ugent-repo/aleph/rug01-"/>
                  <input type="submit" value="GetRecord"/>
                 </xsl:element>
                </li>
                </ul>
           </p>
    </xsl:template>

    <xsl:template match="oai:ListMetadataFormats">
           <h1>Erez OAI-PMH service</h1>
           <p>
             We support the following formats:
             <ul>
              <xsl:for-each select="//oai:metadataFormat">
                <li>
                  <b><xsl:value-of select="./oai:metadataPrefix"/></b> -
                  [<xsl:value-of select="./oai:metadataNamespace"/>] - XSD schema
                  available
                  <xsl:element name="a">
                   <xsl:attribute name="href"><xsl:value-of select="./oai:schema"/></xsl:attribute>
                   here
                  </xsl:element>
                  .<br/>
                  <xsl:element name="a">
                    <xsl:attribute name="href"><xsl:value-of select="/oai:OAI-PMH/oai:request"/>?verb=ListIdentifiers&amp;metadataPrefix=<xsl:value-of select="./oai:metadataPrefix"/></xsl:attribute>
                    ListIdentifiers
                  </xsl:element>

                  |

                  <xsl:element name="a">
                    <xsl:attribute name="href"><xsl:value-of select="/oai:OAI-PMH/oai:request"/>?verb=ListRecords&amp;metadataPrefix=<xsl:value-of select="./oai:metadataPrefix"/></xsl:attribute>
                    ListRecords
                  </xsl:element>

                </li>
              </xsl:for-each>
             </ul>
           </p>
           <xsl:if test="//oai:resumptionToken">
                <xsl:element name="a">
                <xsl:attribute name="href"><xsl:value-of select="/oai:OAI-PMH/oai:request"/>?verb=<xsl:value-of select="/oai:OAI-PMH/oai:request/@verb"/>&amp;resumptionToken=<xsl:value-of select="//oai:resumptionToken"/></xsl:attribute>
                <h1>Next</h1>
                </xsl:element>
           </xsl:if>
    </xsl:template>

    <xsl:template match="oai:ListIdentifiers">
           <h1>Erez OAI-PMH service</h1>
           <xsl:apply-templates select="//oai:header"/>
           <xsl:if test="//oai:resumptionToken">
                <xsl:element name="a">
                <xsl:attribute name="href"><xsl:value-of select="/oai:OAI-PMH/oai:request"/>?verb=<xsl:value-of select="/oai:OAI-PMH/oai:request/@verb"/>&amp;resumptionToken=<xsl:value-of select="//oai:resumptionToken"/></xsl:attribute>
                <h1>Next</h1>
                </xsl:element>
           </xsl:if>
    </xsl:template>

    <xsl:template match="oai:header">
        <p>
          <table border="1">
           <tr><td width="150">
         <xsl:element name="a">
          <xsl:attribute name="href"><xsl:value-of select="/oai:OAI-PMH/oai:request"/>?verb=GetRecord&amp;metadataPrefix=didl&amp;identifier=<xsl:value-of select="oai:identifier"/></xsl:attribute>
         <font size="+1" color="#336699"><xsl:value-of select="oai:identifier"/></font><br/>
         </xsl:element>
           Date: <xsl:value-of select="oai:datestamp"/>
          </td>
          </tr>
         </table>
        </p>
    </xsl:template>

    <xsl:template match="oai:ListRecords">
           <h1>Erez OAI-PMH service</h1>
           <xsl:apply-templates select="//oai:record"/>

           <xsl:if test="//oai:resumptionToken">
                <xsl:element name="a">
                <xsl:attribute name="href"><xsl:value-of select="/oai:OAI-PMH/oai:request"/>?verb=<xsl:value-of select="/oai:OAI-PMH/oai:request/@verb"/>&amp;resumptionToken=<xsl:value-of select="//oai:resumptionToken"/></xsl:attribute>
                <h1>Next</h1>
                </xsl:element>
           </xsl:if>
    </xsl:template>


    <xsl:template match="oai:GetRecord">
        <h1>OAI-PMH service</h1>
        <xsl:apply-templates select="//oai:record"/>
    </xsl:template>

    <xsl:template match="oai:record">
        <p>
         <table border="1">
          <tr>
          <td valign="top">
           <font size="+1" color="#336699"><xsl:value-of select="oai:header/oai:identifier"/></font><br/>
           Date: <xsl:value-of select="oai:header/oai:datestamp"/><br/>
          </td></tr>
         </table>
           <br/>
           <b>Record:</b><br/>
           <xsl:apply-templates select="oai:metadata/didl:DIDL"/>
           <br/>
        </p>
    </xsl:template>

    <xsl:template match="didl:DIDL">
        <table class="didl">
         <tr>
          <td>
           <b>DIDL</b>
          <xsl:apply-templates select="didl:Item"/>
          </td>
         </tr>
        </table>
    </xsl:template>

    <xsl:template match="didl:Item">
        <table class="item">
         <tr>
          <td>
           <b>Item</b>
          <xsl:apply-templates select="didl:Descriptor"/>
          <xsl:apply-templates select="didl:Item"/>
          <xsl:apply-templates select="didl:Component"/>
          </td>
         </tr>
        </table>
    </xsl:template>

    <xsl:template match="didl:Descriptor">
        <table class="descriptor">
         <tr>
          <td>
           <b>Descriptor</b>
          <xsl:apply-templates select="didl:Descriptor"/>
          <xsl:apply-templates select="didl:Statement"/>
          </td>
         </tr>
        </table>
    </xsl:template>

    <xsl:template match="didl:Statement">
        <table class="statement">
         <tr>
                <td>
            <xsl:choose>
             <xsl:when test="namespace-uri(./*) = 'urn:mpeg:mpeg21:2002:01-DII-NS'">
                  <font size="-1">[dii]</font><br/>
                  <xsl:value-of select="name(./*)"/> -&gt; <xsl:value-of select="./*"/>
             </xsl:when>
             <xsl:when test="namespace-uri(./*) = 'http://library.lanl.gov/2004-01/STB-RL/DIADM'">
                  <font size="-1">[diadm]</font><br/>
                  <xsl:for-each select="./diadm:Admin/*">
                   <xsl:value-of select="name(.)"/> -&gt; <xsl:value-of select="."/><br/>
                  </xsl:for-each>
             </xsl:when>
            </xsl:choose>
                </td>
         </tr>
        </table>
    </xsl:template>

    <xsl:template match="didl:Component">
        <table class="component">
         <tr>
          <td>
           <b>Component</b>
          <xsl:apply-templates select="didl:Descriptor"/>
          <xsl:apply-templates select="didl:Resource"/>
          </td>
         </tr>
        </table>
    </xsl:template>

    <xsl:template match="didl:Resource">
        <table class="resource">
         <tr>
          <td>
            <xsl:choose>
             <xsl:when test="@ref">
                <font size="-1">[ref]</font><br/>
                mimeType: <xsl:value-of select="@mimeType"/><br/>
                location: <xsl:element name="a"><xsl:attribute name="class">resource_ref</xsl:attribute><xsl:attribute name="href">http://leopard.lanl.gov:8080/adore-disseminator/service?url_ver=Z39.88-2004&amp;svc_id=info:lanl-repo/svc/getDatastream&amp;rft_id=<xsl:value-of select="@ref"/></xsl:attribute>[click to view]</xsl:element>
             </xsl:when>
             <xsl:when test="@encoding">
                <font size="-1">[inline binary]</font><br/>
                mimeType: <xsl:value-of select="@mimeType"/><br/>
                encoding: <xsl:value-of select="@encoding"/><br/>
             </xsl:when>
             <xsl:otherwise>
                <font size="-1">[inline]</font><br/>
                mimeType: <xsl:value-of select="@mimeType"/><br/>
                namespace: <xsl:value-of select="namespace-uri(./*)"/><br/>
                <xsl:for-each select="./*/*">
                   <xsl:value-of select="name(.)"/> -&gt; <xsl:value-of select="."/><br/>
                </xsl:for-each>
             </xsl:otherwise>
            </xsl:choose>
          </td>
         </tr>
        </table>
    </xsl:template>

</xsl:stylesheet>
