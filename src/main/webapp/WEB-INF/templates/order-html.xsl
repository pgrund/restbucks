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
    <!-- enable html5 compat -->
    <xsl:output method="html" doctype-system="about:legacy-compat"/>
    
    <!-- variable for base path (e.g.: test = '../../main/webapp') -->
    <xsl:variable name="base" select="''"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <html lang="en">
            <head>
                <meta charset="utf-8"/>
                <title>RESTBucks - RESTful Coffee Ordering</title>
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                <meta name="description" content="information about single RESTBucks order"/>
                <meta name="author" content="RESTBucks system"/>

                <!-- HTML5 shim, for IE6-8 support of HTML elements -->
                <!--[if lt IE 9]>
                  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
                <![endif]-->

                <!-- styles -->
                <link href="{$base}/css/bootstrap.css" rel="stylesheet"/>
                <style type="text/css">
                    body {
                    padding-top: 60px;
                    padding-bottom: 40px;
                    }
                    .sidebar-nav {
                    padding: 9px 0;
                    }
                </style>
                <link href="{$base}/css/bootstrap-responsive.css" rel="stylesheet"/>

                <!-- fav and touch icons 
                <link rel="shortcut icon" href="{$base}/images/favicon.ico"/>
                <link rel="apple-touch-icon" href="{$base}/images/apple-touch-icon.png"/>
                <link rel="apple-touch-icon" sizes="72x72" href="{$base}/images/apple-touch-icon-72x72.png"/>
                <link rel="apple-touch-icon" sizes="114x114" href="{$base}/images/apple-touch-icon-114x114.png"/>
                -->
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
                                        <a href="{$base}/restbucks.html#">Info</a>
                                    </li>
                                    <li>
                                        <a href="{$base}/restbucks.html#model">Model</a>
                                    </li>
                                    <li>
                                        <a href="{$base}/restbucks.html#hypermedia">Hypermedia</a>
                                    </li>
                                </ul>
                                <p class="navbar-text pull-right">Logged in as <a href="#">username</a></p>
                            </div><!--/.nav-collapse -->
                        </div>
                    </div>
                </div>

                <div class="container-fluid">
                    <div class="row-fluid">
                        <div class="span2">
                            <div class="well sidebar-nav">
                                <ul class="nav nav-list">
                                    <li class="nav-header">RESTBucks</li>                                    
                                    <li class="nav-header">
                                      <a href="https://github.com/pgrund/restbucks">source</a>
                                    </li>
                                </ul>
                            </div><!--/.well -->
                        </div><!--/span-->
                        <div class="span10">
                            <div class="hero-unit">
                                <h3>RESTBucks Order</h3>
                                <xsl:apply-templates select="//rb:order"/>
                            </div>
                            <div class="row-fluid tabbable">
                                <ul class="nav nav-tabs">
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
                <script src="{$base}/js/jquery.js"></script>
                <script src="{$base}/js/bootstrap-transition.js"></script>
                <script src="{$base}/js/bootstrap-alert.js"></script>
                <script src="{$base}/js/bootstrap-modal.js"></script>
                <script src="{$base}/js/bootstrap-dropdown.js"></script>
                <script src="{$base}/js/bootstrap-scrollspy.js"></script>
                <script src="{$base}/js/bootstrap-tab.js"></script>
                <script src="{$base}/js/bootstrap-tooltip.js"></script>
                <script src="{$base}/js/bootstrap-popover.js"></script>
                <script src="{$base}/js/bootstrap-button.js"></script>
                <script src="{$base}/js/bootstrap-collapse.js"></script>
                <script src="{$base}/js/bootstrap-carousel.js"></script>
                <script src="{$base}/js/bootstrap-typeahead.js"></script>

            </body>
        </html>
    </xsl:template>
    
    <xsl:template match="rb:order" >            
            <ul>
                <xsl:apply-templates select="rb:items"/>
            </ul>
            <p class="location">
                <xsl:value-of select="rb:location"/>
            </p>
    </xsl:template>
    
    <xsl:template match="rb:order" mode="update">
        <form action="{./dap:link[@title='update']/@href}" method="post" 
              id="updateOrder" class="form-horizontal">
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
                                <xsl:attribute name="selected"/>
                            </xsl:if>
                            take away
                        </option> 
                        <option value="IN_SHOP">
                            <xsl:if test="location = 'IN_SHOP'">
                                <xsl:attribute name="selected"/>
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
                <xsl:value-of select="rb:name"/>
            </p>
            <p class="quantity">
                <xsl:value-of select="rb:quantity"/>
            </p>
            <p class="milk">
                <xsl:value-of select="rb:milk"/>
            </p>
            <p class="size">
                <xsl:value-of select="rb:size"/>
            </p>
        </li>
    </xsl:template>
    
    <xsl:template match="rb:items" mode="update">
        <div class="control-group">
            <label class="control-label" for="name">Name</label>
            <div class="controls">
                <select id="name" name="name">                      
                    <option value="ESPRESSO">
                        <xsl:if test="rb:name = 'ESPRESSO'">
                            <xsl:attribute name="selected"/>
                        </xsl:if>
                        espresso
                    </option> 
                    <option value="LATTE">
                        <xsl:if test="rb:name = 'LATTE'">
                            <xsl:attribute name="selected"/>
                        </xsl:if>
                        latte
                    </option>
                    <option value="CAPUCCINO">
                        <xsl:if test="rb:name = 'CAPUCCINO'">
                            <xsl:attribute name="selected"/>
                        </xsl:if>
                        capuccino
                    </option>
                    <option value="FLAT_WHITE">
                        <xsl:if test="rb:name = 'FLAT_WHITE'">
                            <xsl:attribute name="selected"/>
                        </xsl:if>
                        flat white
                    </option>
                </select>
            </div>
        </div>         
        
         <div class="control-group">
            <label class="control-label" for="quantity">Quantity</label>
            <div class="controls">
                <input type="number" min="1" id="quantity" name="quantity">
                            <xsl:attribute name="value"><xsl:value-of select="./rb:quantity"/></xsl:attribute>
                </input>
            </div>
        </div> 
         
            <div class="control-group">
                <label class="control-label" for="milk">Milk</label>
                <div class="controls">
                    <select id="milk" name="milk">
                        <option value="SEMI">
                            <xsl:if test="rb:milk = 'SEMI'">
                                <xsl:attribute name="selected"/>
                            </xsl:if>
                            semi milk
                        </option> 
                        <option value="SKIM">
                            <xsl:if test="rb:milk = 'SKIM'">
                                <xsl:attribute name="selected"/>
                            </xsl:if>
                            skim milk
                        </option>                         
                        <option value="WHOLE">
                            <xsl:if test="rb:milk = 'WHOLE'">
                                <xsl:attribute name="selected"/>
                            </xsl:if>
                            whole milk
                        </option> 
                    </select>
                </div>
            </div>  
            
            <div class="control-group">
                <label class="control-label" for="size">Size</label>
                <div class="controls">
                    <select id="size" name="size">
                        <option value="SMALL">
                            <xsl:if test="rb:size = 'SMALL'">
                                <xsl:attribute name="selected"/>
                            </xsl:if>
                            small
                        </option> 
                       <option value="MEDIUM">
                            <xsl:if test="rb:size = 'MEDIUM'">
                                <xsl:attribute name="selected"/>
                            </xsl:if>
                            medium
                        </option>
                        <option value="LARGE">
                            <xsl:if test="rb:size = 'LARGE'">
                                <xsl:attribute name="selected"/>
                            </xsl:if>
                            large
                        </option>
                    </select>
                </div>
            </div>
    </xsl:template>    
    
    <xsl:template match="dap:link[@title='self']" mode="content">
        <div class="tab-pane active" id="self">
            <div class="order">
                <p class="status">
                    <xsl:choose>
                        <xsl:when test="../rb:status = 'UNPAID'">
                            Your order is awaiting payment. <br/> You may also <a href="#cancel" data-toggle="tab">cancel</a> your order completely.
                        </xsl:when>
                        <xsl:otherwise>
                            <span class="alert">
                                <xsl:value-of select="../rb:status"/>
                            </span>
                        </xsl:otherwise>
                    </xsl:choose>
                </p>                   
            </div>
        </div>
    </xsl:template>
    
    <xsl:template match="dap:link[@title='cancel']" mode="content">
        <div class="tab-pane" id="cancel">
            <div class="alert" id="alert.cancel">
                <button type="button" class="close" data-dismiss="alert">x</button>
                <strong>Warning!</strong> Are you sure you want to cancel/delete this order ?
            </div>                     
            <button class="btn" type="button" onclick="deleteOrder();">cancel</button>
            <script type="text/javascript">
            function deleteOrder() {
                $.ajax({
                    url: '<xsl:value-of select="//dap:link[@title='cancel']/@href"/>',
                    type: 'DELETE',
                    context: document.getElementById("cancel")
                }).done(function() {
                    $("#alert\\.cancel").text("resource deleted").addClass("alert-success");
                }).fail(function(jqXHR, textStatus, errorThrown){
                    $("#alert\\.cancel").text(textStatus).addClass("alert-error").prepend('<button type="button" class="close" data-dismiss="alert">x</button><strong>Error!</strong> ');
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
