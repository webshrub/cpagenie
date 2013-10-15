<%@ page session="false" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title>CPAGenie - Login</title>
    <link rel="stylesheet" type="text/css" href="resources/css/ws_login.css"/>
</head>
<body>
<form method="post" action="j_spring_security_check">
    <table width="100%" height="100%" class="textbody">
        <tr>
            <td valign="top" style="padding-top:154px;">
                <table width="472" height="242" border="0" cellpadding="0" cellspacing="0" align="center">
                    <tr height="34">
                        <td width="15" nowrap><img src="resources/images/loginbox_toplft.jpg"></td>
                        <td background="resources/images/loginbox_headback.jpg" class="textheaderwhite" colspan="3">CPAGenie - Login</td>
                        <td width="22" nowrap><img src="resources/images/loginbox_toprt.jpg"></td>
                    </tr>
                    <tr bgcolor="#FFFFFF">
                        <td background="resources/images/loginbox_lftback.gif"></td>
                        <td width="237" align="left" nowrap>
                            <table width="237" border="0" cellspacing="0" cellpadding="0">
                                <%if (request.getParameter("login_error") != null && request.getParameter("login_error").equals("true")) {%>
                                <tr height="40">
                                    <td align="center" valign="middle" colspan="2" class="texterror"><b>Either Username/Password is not correct or the User account is not active.</b></td>
                                </tr>
                                <%}%>
                                <tr height="30">
                                    <td width="88" valign="middle" class="textbody" style="padding-left:16px;" nowrap> Username:</td>
                                    <td valign="middle"><input type="text" size="16" name="j_username" id="j_username" value='' class="textbody"/></td>
                                </tr>
                                <tr height="30">
                                    <td class="textbody" valign="middle" style="padding-left:16px;">Password:</td>
                                    <td valign="middle"><input type="password" size="16" name="j_password" id="j_password" class="textbody"/></td>
                                </tr>
                                <tr height="30">
                                    <td>&nbsp;</td>
                                    <td align="right" style="padding-right:23px;"><input type="submit" value="Login" style="cursor: hand; width:89px; height:23px;" class="textbody"> <td>
                                </tr>
                            </table>
                        </td>
                        <td width="1" valign="top" style="padding-top:32px;" nowrap><img src="resources/images/loginbox_line.gif"> </td>
                        <td width="197" align="center" valign="bottom" style="padding-bottom:9px;" nowrap><img src="resources/images/logo_ws_small.jpg" alt="Logo Exponential"/></td>
                        <td background="resources/images/loginbox_rtback.gif"><img src="resources/images/pixel.gif" width="22" height="1"></td>
                    </tr>
                    <tr height="25">
                        <td><img src="resources/images/loginbox_botlft.gif"></td>
                        <td background="resources/images/loginbox_botback.gif" colspan="3"><img src="resources/images/pixel.gif" width="1" height="25"></td>
                        <td><img src="resources/images/loginbox_botrt.gif"></td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</form>
</body>
</html>