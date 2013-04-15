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
        <html lang="en">
            <head>
                <meta charset="utf-8"/>
                <title>RESTBucks - Order Information</title>
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                <meta name="description" content="information about single RESTBucks order"/>
                <meta name="author" content="RESTBucks system"/>

                <!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
                <!--[if lt IE 9]>
                  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
                <![endif]-->

                <!-- Le styles -->
                <link href="/css/bootstrap.css" rel="stylesheet"/>
                <style type="text/css">
                    body {
                    padding-top: 60px;
                    padding-bottom: 40px;
                    }
                    .sidebar-nav {
                    padding: 9px 0;
                    }
                </style>
                <link href="/css/bootstrap-responsive.css" rel="stylesheet"/>

                <!-- Le fav and touch icons -->
                <link rel="shortcut icon" href="/images/favicon.ico"/>
                <link rel="apple-touch-icon" href="/images/apple-touch-icon.png"/>
                <link rel="apple-touch-icon" sizes="72x72" href="/images/apple-touch-icon-72x72.png"/>
                <link rel="apple-touch-icon" sizes="114x114" href="/images/apple-touch-icon-114x114.png"/>
            </head>

            <body>
                <div class="navbar navbar-fixed-top">
                    <div class="navbar-inner">
                        <div class="container-fluid">
                            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                            </a>
                            <a class="brand" href="#">RESTBucks</a>
                            <div class="nav-collapse">
                                <ul class="nav">
                                    <li>
                                        <a href="/restbucks.html#">Info</a>
                                    </li>
                                    <li>
                                        <a href="/restbucks.html#model">Model</a>
                                    </li>
                                    <li>
                                        <a href="/restbucks.html#hypermedia">Hypermedia</a>
                                    </li>
                                </ul>
                                <p class="navbar-text pull-right">Logged in as <a href="#">username</a></p>
                            </div><!--/.nav-collapse -->
                        </div>
                    </div>
                </div>

                <div class="container-fluid">
                    <div class="row-fluid">
                        <div class="span3">
                            <div class="well sidebar-nav">
                                <ul class="nav nav-list">
                                    <li class="nav-header">Available Actions Explained</li>
                                    <xsl:for-each select="//dap:link">
                                        <li class="nav-header">
                                            <a href="{@rel}">
                                                <xsl:value-of select="@title"/>
                                            </a>
                                        </li>
                                    </xsl:for-each>
                                </ul>
                            </div><!--/.well -->
                        </div><!--/span-->
                        <div class="span9">
                            <div class="hero-unit">
                                <h1>RESTBucks Order Information</h1>                     
                                <p>
                                    <a class="btn btn-primary btn-large">Learn more ...</a>
                                </p>
                            </div>
                            <div class="row-fluid tabbable">
                                <ul class="nav-tabs">
                                    <xsl:apply-templates select="//dap:link" mode="nav"/>
                                </ul>
                                <div class="tab-content">
                                    <xsl:apply-templates select="//dap:link" mode="content"/>
                                </div>
                         
                            </div><!--/row-->
                        </div><!--/span-->
                    </div><!--/row-->
                </div><!--/container-->

                <!-- Le javascript
                ================================================== -->
                <!-- Placed at the end of the document so the pages load faster -->
                <script src="/js/jquery.js"></script>
                <script src="/js/bootstrap-transition.js"></script>
                <script src="/js/bootstrap-alert.js"></script>
                <script src="/js/bootstrap-modal.js"></script>
                <script src="/js/bootstrap-dropdown.js"></script>
                <script src="/js/bootstrap-scrollspy.js"></script>
                <script src="/js/bootstrap-tab.js"></script>
                <script src="/js/bootstrap-tooltip.js"></script>
                <script src="/js/bootstrap-popover.js"></script>
                <script src="/js/bootstrap-button.js"></script>
                <script src="/js/bootstrap-collapse.js"></script>
                <script src="/js/bootstrap-carousel.js"></script>
                <script src="/js/bootstrap-typeahead.js"></script>

            </body>
        </html>
    </xsl:template>
    
    <xsl:template match="rb:order" >            
            <ul>
                <xsl:apply-templates select="rb:items"/>
            </ul>
            <p class="location">
                <xsl:value-of select="location"/>
            </p>
    </xsl:template>
    
    <xsl:template match="rb:order" mode="update">
        <form action="{./dap:link[@title='update']/@href}" method="post" 
              id="updateOrder" class="form-horizontal" 
              enctype="application/vnd.restbucks+xml">
            <fieldset>
                <legend>Items</legend>
                <xsl:apply-templates select="//rb:items" mode="update"/>
            </fieldset>
              
            <div class="control-group">
                <label class="control-label" for="location">Location</label>
                <div class="controls">
                    <select id="location" name="location">
                        <option value="TAKE_AWAY">
                            <xsl:if test="location = 'TAKE_AWAY'">
                                <xsl:attribute name="checked"/>
                            </xsl:if>
                            take away
                        </option> 
                        <option value="IN_SHOP">
                            <xsl:if test="location = 'IN_SHOP'">
                                <xsl:attribute name="checked"/>
                            </xsl:if>
                            in shop
                        </option> 
                    </select>
                </div>
            </div>
            <button type="submit" class="btn btn-success">update</button>
        </form>
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
    
    <xsl:template match="rb:items" mode="update">
        <div class="control-group">
            <label class="control-label" for="inputName">Name</label>
            <div class="controls">
                <input type="text" id="inputName">
                    <xsl:choose>
                        <xsl:when test="./name">
                            <xsl:attribute name="value">
                                <xsl:value-of select="name"/>
                            </xsl:attribute>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:attribute name="placeholder">
                                provide name ... 
                            </xsl:attribute>
                        </xsl:otherwise>
                    </xsl:choose>
                </input>
            </div>
        </div> 
            <p class="quantity">
                <xsl:value-of select="quantity"/>
            </p>
            <p class="milk">
                <xsl:value-of select="milk"/>
            </p>
            <p class="size">
                <xsl:value-of select="size"/>
            </p>
    </xsl:template>    
    
    <xsl:template match="dap:link[@title='self']" mode="content">
        <div class="tab-pane active" id="self">
            <div class="order">
                <p class="status">
                    <xsl:choose>
                        <xsl:when test="../status = 'UNPAID'">
                            Your order is awaiting payment. <br/> You may also <a href="#cancel" data-toggle="tab">cancel</a> your order completely.
                        </xsl:when>
                        <xsl:otherwise>
                            <span class="alert">
                                <xsl:value-of select="../status"/>
                            </span>
                        </xsl:otherwise>
                    </xsl:choose>
                </p>    
                <xsl:apply-templates select="//rb:order"/>
            </div>
        </div>
    </xsl:template>
    
    <xsl:template match="dap:link[@title='cancel']" mode="content">
        <div class="tab-pane" id="cancel">
            <p>Are you sure you want to cancel/delete this order ?</p>
            <p>
                <xsl:apply-templates select="//rb:order"/>
            </p>
            <button class="btn" type="button" onclick="deleteOrder();">cancel</button>
            <script type="text/javascript">
            function deleteOrder() {
                $.ajax({
                    url: document.location,
                    type: 'DELETE',
                    context: document.getElementById("cancel")
                }).done(function() {
                   alert("deleted");
                });
            }
        </script>
        </div>
    </xsl:template>
     <xsl:template match="dap:link[@title='update']" mode="content">
        <div class="tab-pane" id="update">
            <xsl:apply-templates select="//rb:order" mode="update"/>
        </div>
    </xsl:template>
    
    <xsl:template match="dap:link" mode="content">                      
        <div class="tab-pane" id="{@title}">
            <button id="send" onclick="{@title}();" class="btn btn-success">
                <xsl:value-of select="@title"/>
            </button>         
        </div>
    </xsl:template>

    <xsl:template match="dap:link[@title='self']" mode="nav">
        <li class="active">
            <a href="#self" data-toggle="tab">
                <i class="icon-refresh"></i> self
            </a>
        </li>
    </xsl:template>
    <xsl:template match="dap:link[@title='update']" mode="nav">
        <li>
            <a href="#update" data-toggle="tab">
                <i class="icon-pencil"></i> update
            </a>
        </li>
    </xsl:template>
    <xsl:template match="dap:link[@title='cancel']" mode="nav">
        <li>
            <a href="#cancel" data-toggle="tab">
                <i class="icon-trash"></i> cancel
            </a>
        </li>
    </xsl:template>
    <xsl:template match="dap:link[@title='pay']" mode="nav">
        <li>
            <a href="#pay" data-toggle="tab">
                <i class="icon-shopping-cart"></i> pay
            </a>
        </li>
    </xsl:template>
    <xsl:template match="dap:link" mode="nav">
        <li> 
            <xsl:value-of select="@title"/>             
        </li>
    </xsl:template>
</xsl:stylesheet>
