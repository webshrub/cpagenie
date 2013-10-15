<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <title><tiles:insertAttribute name="title"/></title>
    <jsp:include page="includescripts.jsp"/>
</head>
<body class="front not-logged-in node-type-products body-node-1973 no-sidebars" id="springsource">
<div id="page-wrapper">
    <div id="page">
        <jsp:include page="header.jsp"/>
        <div id="container" class="clear-block">
            <div id="container-inner">
                <div id="container-content">
                    <div id="main" class="column">
                        <div id="squeeze" class="clear-block">
                            <p></p>

                            <div id="triColumn">
                                <tiles:insertAttribute name="body"/>
                            </div>
                            <div class="clear">&nbsp;</div>
                            <p></p>
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
