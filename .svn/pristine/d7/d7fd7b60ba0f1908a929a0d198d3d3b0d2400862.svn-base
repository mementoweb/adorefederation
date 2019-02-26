<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns:mods="http://www.loc.gov/mods/" 
    exclude-result-prefixes="xsl mods">
  <xsl:output method="html" version="1.0" encoding="UTF-8" omit-xml-declaration="yes" indent="yes"/>
  <xsl:template match="/">
  <xsl:variable name="content-id" select="mods:mods/@id" />
  <xsl:variable name="authors">
             <xsl:for-each select="//mods:mods/mods:name">
             <xsl:variable name="ipos" select="//mods:mods/mods:name[1]/position()"/>
             <xsl:if test="$ipos != position()"><xsl:text>, </xsl:text></xsl:if>
             <xsl:if test="mods:namePart[@type='family'] != ''"><xsl:value-of select="mods:namePart[@type='family']"/></xsl:if>
             <xsl:if test="mods:namePart[@type='given'] != ''">, <xsl:value-of select="substring(mods:namePart[@type='given'],1,1)"/>.</xsl:if>
           </xsl:for-each>
  </xsl:variable>
  <xsl:variable name="source">
       <xsl:value-of select="$authors"/> 
       <xsl:for-each select="//mods:mods/mods:relatedItem[1]">
        <xsl:if test="mods:part/mods:date[1] != ''">
           <xsl:text> (</xsl:text><xsl:value-of select="substring(mods:part/mods:date[1],1,4)"/><xsl:text>); </xsl:text>
        </xsl:if>
        <xsl:if test="mods:titleInfo/mods:title != ''">
           <xsl:value-of select="mods:titleInfo/mods:title"/>
        </xsl:if>
        <xsl:if test="mods:part/mods:detail[@type='volume']/mods:number != ''">
           <xsl:text>, Vol.</xsl:text><xsl:value-of select="mods:part/mods:detail[@type='volume']/mods:number"/>
        </xsl:if>
        <xsl:if test="mods:part/mods:detail[@type='issue']/mods:number != ''">
           <xsl:text> Iss.</xsl:text><xsl:value-of select="mods:part/mods:detail[@type='issue']/mods:number"/>
        </xsl:if>
        <xsl:if test="mods:part/mods:detail[@type='page']/mods:number != ''">
           <xsl:text>, p.</xsl:text><xsl:value-of select="mods:part/mods:detail[@type='page']/mods:number"/>
        </xsl:if>
       </xsl:for-each>
  </xsl:variable>
  <html>
    <head>
      <link rel="stylesheet" href="http://flasher.lanl.gov:8080/admin/adore-ore.css" />
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
      <title>CiteLikeThis Permalink Page for: <xsl:value-of select="substring(//mods:mods/mods:titleInfo/mods:title,0,200)"/></title>
      <script src="http://flasher.lanl.gov:8080/admin/copy.js"></script>
    </head>
    <body>
      <img src="http://flasher.lanl.gov:8080/admin/elogo.gif" alt="E-Library"/><br/><br/>
      <div id ="plink"><fieldset><table cellpadding="1" cellspacing="1" border="0"><tr><td valign="top" width="120"><span id="subtitle">Permalink:</span></td><td width="255"><input id="pl" type="text" size="146" name="Permalink" onclick="copy(document.getElementById('pl'));" onfocus="document.getElementById('pl').select();" value="http://libproto.lanl.gov:8080/adore-permalink/object?rft_id={$content-id}"/></td></tr></table></fieldset></div>
    
      <div id="bib"><fieldset><table cellpadding="1" cellspacing="1" border="0" width="100%">
      <tr><td valign="top" width="120"><span class="subtitle">Title:</span></td><td><span class="title"><xsl:value-of select="//mods:mods/mods:titleInfo/mods:title"/></span><hr/></td></tr>
      <tr><td valign="top" width="120"><span class="subtitle">Author(s):</span></td><td><span class="contributor"><xsl:value-of select="$authors"/></span><hr/></td></tr>
      <tr><td valign="top" width="120"><span class="subtitle">Citation:</span></td><td><input id="cit" type="text" size="146" name="cit" onclick="copy(document.getElementById('cit'));" onfocus="document.getElementById('cit').select();" value="{$source}"/></td></tr>
      <tr><td valign="top" width="120"><span class="subtitle">Abstract:</span></td><td bgcolor="white"><span class="pub"><xsl:value-of select="//mods:mods/mods:abstract"/></span><br/></td></tr></table></fieldset>
      </div>
    </body></html>
    
  </xsl:template>
</xsl:stylesheet>
