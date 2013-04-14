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
                      <a class="brand" href="#">Order</a>
                      <div class="nav-collapse">
                        <ul class="nav">
                          <li class="active"><a href="#">Home</a></li>
                          <li><a href="/relations.html">Relations</a></li>
                          <li><a href="/model.html">Model</a></li>
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
                          <li class="nav-header">Actions Explained</li>
                          <xsl:for-each select="//dap:link">
                              <li class="nav-header">
                                  <a href="{@rel}"><xsl:value-of select="@title"/></a>
                              </li>
                          </xsl:for-each>
                        </ul>
                      </div><!--/.well -->
                    </div><!--/span-->
                    <div class="span9">
                      <div class="hero-unit">
                        <h1>RESTBucks Order Information</h1>
                        <xsl:apply-templates select="//rb:order"/>
                        <p><a class="btn btn-primary btn-large">Learn more ...</a></p>
                      </div>
                      <div class="row-fluid">
                          <ul class="tabs">
                            <li><a href="#home" data-toggle="tab">Home</a></li>
                            <li><a href="#profile" data-toggle="tab">Profile</a></li>
                            <li><a href="#messages" data-toggle="tab">Messages</a></li>
                            <li><a href="#ettings" data-toggle="tab">Settings</a></li>
                            <xsl:apply-templates select="//dap:link" mode="nav"/>
                          </ul>
                          <div class="tab-content">
                            <div class="tab-pane active" id="home">...</div>
                            <div class="tab-pane" id="profile">...</div>
                            <div class="tab-pane" id="messages">...</div>
                            <div class="tab-pane" id="settings">...</div>
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
        <div class="order">
            <span class="status">
                <xsl:value-of select="rb:status"/>
            </span>
            <p class="location">
                <xsl:value-of select="rb:location"/>
            </p>
            <ul>
                <xsl:apply-templates select="rb:items"/>
            </ul>
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
    
    <xsl:template match="dap:link" mode="content">
         <div class="tab-pane" id="{@title}">
             <xsl:choose>
                <xsl:when test="@title = 'update'">
                    <textarea id="order" name="order"></textarea>
                    <script type="text/javascript">
                        function update() {
                        alert(document.getElementById('order').value + "\n"+
                        document.getElementById("order-href").href;
                        }
                    </script>
                </xsl:when>
                <xsl:when test="@title = 'cancel'">
                    <script type="text/javascript">
                        function cancel() {
                        alert("cancel");
                        }
                    </script>
                </xsl:when>
                <xsl:when test="@title = 'self'">
                    <script type="text/javascript">
                        function self() {
                        window.location = document.getElementById("self-href").href;
                        }
                    </script>
                </xsl:when>
                <xsl:when test="@title = 'pay'">
                    <textarea id="payment" name="payment">
                        <xsl:value-of select="rb:cost"/>
                    </textarea>
                    <script type="text/javascript">
                        function pay() {
                        alert(document.getElementById('payment').value + "\n"+
                        document.getElementById("pay-href").href;
                        }
                    </script>
                </xsl:when>
                <xsl:otherwise>
                    sonst
                </xsl:otherwise>
            </xsl:choose>
            <button id="send" onclick="{@title}();">
                <xsl:value-of select="@title"/>
            </button>
         </div>
    </xsl:template>

    <xsl:template match="dap:link" mode="nav">
        <li>
                <a href="#{@title}" data-toggle="tab">
                    <xsl:choose>
                        <xsl:when test="@title">
                            <xsl:value-of select="@title"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="substring-after(@rel,'.com/')"/>
                        </xsl:otherwise>    
                    </xsl:choose>
                </a>
        </li>
    <!--
            
        </li>
        -->
    </xsl:template>
</xsl:stylesheet>
