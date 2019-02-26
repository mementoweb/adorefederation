<?xml version='1.0' encoding='UTF-8'?>
<xsl:stylesheet version='1.0'
    xmlns:xsl='http://www.w3.org/1999/XSL/Transform'
>

  <xsl:output media-type="text/html"/>
  <xsl:variable name="queryValue" select="response/lst[@name='responseHeader']/lst/str[@name='q']"/>
  <xsl:variable name="title" select="concat('Solr search results (',response/result/@numFound,' documents)')"/>
  <xsl:variable name="offset" select="response/lst[@name='responseHeader']/lst/str[@name='start']"/>
  <xsl:variable name="rpp" select="response/lst[@name='responseHeader']/lst/str[@name='rows']"/>
  <xsl:variable name="duration" select="response/lst[@name='responseHeader']/int[@name='QTime']"/>
  <xsl:variable name="hitCount" select="response/result/@numFound"/>
  <xsl:variable name="temp1">
    <xsl:choose>
      <xsl:when test="number($offset + $rpp) > number($hitCount)">
        <xsl:value-of select="$hitCount" />
      </xsl:when>
      <xsl:when test="number($hitCount) > number($rpp)">
        <xsl:value-of select="$offset + $rpp - 1" />
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$hitCount" />
      </xsl:otherwise>
    </xsl:choose>
   </xsl:variable>


<xsl:template match='/'>
<HTML>
<HEAD>
<TITLE>SolrSearcher</TITLE>
<style>
<!-- 
body,td,div,.p,a{font-family:arial,sans-serif }
div,td{color:#000}
.f,.fl:link{color:#6f6f6f}
a:link,.w,a.w:link,.w a:link{color:#00c}
a:visited,.fl:visited{color:#551a8b}
a:active,.fl:active{color:#f00}
.t a:link,.t a:active,.t a:visited,.t{color:#000}
.t{background-color:#bbcced}
.k{background-color:#249}
.j{width:34em}
.h{color:#249;font-size:14px}
.i,.i:link{color:#a90a08}
.a,.a:link{color:#008000}
.z{display:none}
div.n {margin-top: 1ex}
.n a{font-size:10pt; color:#000}
.n .i{font-size:10pt; font-weight:bold}
.q a:visited,.q a:link,.q a:active,.q {color: #00c; }
.b{font-size: 12pt; color:#00c; font-weight:bold}
.ch{cursor:pointer;cursor:hand}
h3{font-weight:normal;font-size=100%}
.sem{display:inline;margin:0;font-size:100%;font-weight:inherit}
-->
</style>
<script type="text/javascript" src="geturl.js"/>
</HEAD>
<BODY bgcolor="#ffffff" marginheight="3" topmargin="3">

<script type="text/javascript">
<xsl:text>

function goURL(newLoc)
{
   if (newLoc != "")
   {
     //alert(newLoc) 
     location.href = newLoc
   }
 }
</xsl:text>

</script> 

<table border="0" cellpadding="0" cellspacing="0" width="100%">
<tr>
<td valign="top"><a href="http://libary.lanl.gov">
<img alt="Go to Research Library Home" border="0" height="40" src="http://flasher.lanl.gov:8080/admin/images/elogo.gif" vspace="12" width="38"/></a>
<td height="14" valign="middle">
<form name="search" action="select" method="get">
<input type="hidden" name="stylesheet" value="google"/>
<input type="hidden" name="start" value="0"/>
<input type="hidden" name="rows" value="10"/>
<input type="hidden" name="facet" value="true"/>
<input type="hidden" name="facet.limit" value="10"/>
<input type="hidden" name="facet.mincount" value="1"/>
<input type="hidden" name="facet.field" value="dataset"/>
<input type="hidden" name="facet.field" value="pubDate"/>
<input type="hidden" name="facet.field" value="docType"/>
<input maxLength="256" name="q" size="55" title="SolrSearcher" value="{$queryValue}"/>
<input type="submit" value="Search"/>
</form>
</td></td>
</tr>
</table>
<style><!-- .fl:link{color:#7777CC} --></style>

<table bgcolor="#bbcced" border="0" cellpadding="0" cellspacing="0" width="100%">
<tr>
<td bgcolor="#bbcced"><img alt="" height="1" width="1"/></td>
<td align="right" bgcolor="#bbcced">Results <b><xsl:value-of select="$offset"/> </b> - <b><xsl:value-of select="$temp1"/> </b> of <b><xsl:value-of select="$hitCount"/> </b> for <b><xsl:value-of select="$queryValue"/> </b> (<b><xsl:value-of select="$duration"/> </b> ms)</td>
<td bgcolor="#bbcced"><img alt="" height="1" width="1"/></td></tr></table><br/>
<table border="0">
  <tr>
    <td valign="top" width="110"><table cellspacing="0" border="0" cellpadding="0">
      <tr>
        <td> </td>
        <td>
          <table border="0">
            <tr>
              <td nowrap="true"><font size="-1">
        <b> 
        <xsl:element name="A">
        <xsl:attribute name="href">#</xsl:attribute>
        <xsl:attribute name="onClick">javascript:document.search.submit();</xsl:attribute>
        All Results
        </xsl:element></b></font></td>
            </tr>
            <tr height="2">
              <td><img width="1" height="1" alt=""/></td>
            </tr>
            <xsl:apply-templates select="response/lst[@name='facet_counts']"/>
           </table>
         </td>
       <td>  </td>
       <td width="1" bgcolor="#c9d7f1" rowspan="3"><img width="1" height="1" alt=""/></td>
       <td>  </td>
     </tr>
   </table>
 </td>
 <td valign="top">
        <xsl:apply-templates select="response/result/doc"/>
 </td>
 </tr>
 </table>
</BODY>
</HTML>
  </xsl:template>
  
  <xsl:template match="lst[@name='facet_fields']/lst">
    <tr>
       <td nowrap="true"><font size="-1">
          <xsl:variable name="key" select="@name" /> 
          <b><xsl:value-of select="@name"/></b>
       </font></td>
    </tr>
    <xsl:apply-templates/>
    <br/>
  </xsl:template>
  
  <xsl:template match="int">
    <tr>
       <td nowrap="true"><font size="-1">
        <xsl:element name="A">
	<xsl:attribute name="href">#</xsl:attribute>
	<xsl:attribute name="onClick">javascript:goURL(location.href + '&amp;fq=<xsl:value-of select="../@name"/>:<xsl:value-of select="@name"/>') 
</xsl:attribute> 
         <xsl:value-of select="@name"/></xsl:element> (<xsl:value-of select="."/>)
       </font></td>
    </tr>
  </xsl:template>
  
  <xsl:template match="doc">
    <div class="doc">
      <table width="100%">
          <tr>
             <td valign="top">
        <span class="g">
        <xsl:value-of select="str[@name='displayTitle']"/>
        </span>
        <br/>
        <span class="a">
        <font size="-1">
          <xsl:value-of select="str[@name='displayName']"/>
           - 
          <xsl:value-of select="str[@name='displaySource']"/>
          <br/>
          <xsl:value-of select="str[@name='recID']"/>
        </font>
        </span>
        <br/><br/>
             </td>
          </tr>
      </table>
    </div>
  </xsl:template>

</xsl:stylesheet>
