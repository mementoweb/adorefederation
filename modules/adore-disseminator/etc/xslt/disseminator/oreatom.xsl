<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="2.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:atom="http://www.w3.org/2005/Atom">
        <xsl:template match="atom:feed">
                <html>
                        <head>
                                <title>
                                        aDORe List of Services for
                                        <xsl:value-of select="atom:title" />
                                </title>
                                <link rel="stylesheet" href="adore-atom.css" />
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
                        <xsl:variable name="content-src" select="atom:link/@href" />
                        <a href="{$content-src}">
                                 <xsl:value-of select="atom:title" />          
                        </a>
                </div>
                <br />
        </xsl:template>
</xsl:stylesheet>
