<?xml version='1.0' encoding='UTF-8'?>
<xsl:stylesheet version='1.0'
    xmlns:xsl='http://www.w3.org/1999/XSL/Transform'
>

  <xsl:output media-type="text/html"/>
  
  <xsl:variable name="title" select="concat('Solr search results (',response/result/@numFound,' documents)')"/>
  
  <xsl:template match='/'>
    <html>
      <head>
        <title><xsl:value-of select="$title"/></title>
        <xsl:call-template name="css"/>
      </head>
      <body>
        <h1><xsl:value-of select="$title"/></h1>
        <div class="note">
          This has been formatted by the default query-to-html.xsl transform - use your own XSLT
          to get a nicer page
        </div>
        <xsl:apply-templates select="response/result/doc"/>
      </body>
    </html>
  </xsl:template>
  
  <xsl:template match="doc">
    <div class="doc">
      <table width="100%">
        <xsl:apply-templates/>
      </table>
    </div>
  </xsl:template>

  <xsl:template match="doc/*">
    <tr>
      <td class="name">
        <xsl:value-of select="@name"/>
      </td>
      <td class="value">
        <xsl:value-of select="."/>
      </td>
    </tr>
  </xsl:template>

  <xsl:template match="*"/>
  
  <xsl:template name="css">
    <style type="text/css">
      body { font-family: "Lucida Grande", sans-serif }
      .doc { margin-top: 1em; border-top: solid grey 1px; }
      td.name { font-style: italic; font-size:80%; }
      td { vertical-align: top; }
      .note { font-size:80%; }
    </style>
  </xsl:template>

</xsl:stylesheet>
