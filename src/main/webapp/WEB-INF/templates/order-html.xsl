<?xml version="1.0" encoding="MacRoman"?>

<!--
    Document   : order-html.xsl
    Created on : 16. Dezember 2012, 18:51
    Author     : pgrund
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:rb="http://schemas.restbucks.com"
                xmlns:dap="http://schemas.restbucks.com/dap"
                version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
                <title>Order Information</title>
            </head>
            <body>
                <xsl:apply-templates/>
            </body>
        </html>   
    </xsl:template>
    
    <xsl:template match="rb:order" >            
        <div class="order">
            <p class="location">
                <xsl:value-of select="rb:location"/>
            </p>
            <ul>
                <xsl:apply-templates select="rb:items"/>
            </ul>
            <xsl:apply-templates select="dap:link"/>
        </div> 
    </xsl:template>
    
    <xsl:template match="rb:items">
        <li class="item">
            <p class="name">
                <xsl:value-of select="name"/>
            </p>
            <p class="quantity">
                <xsl:value-of select="quantity"/>
            </p>
            <p class="milk">
                <xsl:value-of select="milk"/>
            </p>
            <p class="size">
                <xsl:value-of select="size"/>
            </p>
        </li>
    </xsl:template>    

    <xsl:template match="dap:link">
        <a href="{@href}" rel="{@rel}">
            <xsl:choose>
                <xsl:when test="@title">
                    <xsl:value-of select="@title"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="substring-after(@rel,'.com/')"/>
                </xsl:otherwise>    
            </xsl:choose>
        </a>
        <br/>
    </xsl:template>
</xsl:stylesheet>
