<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring-tags" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="displaytag-el" uri="http://displaytag.sf.net/el" %>
<style type="text/css">
    body {
        font-size: 70.0%;
    }

    div#boxes table {
        margin: 1em 0;
        border-collapse: collapse;
        width: 100%;
    }

    div#boxes table td, div#boxes table th {
        border: 1px solid #eee;
        padding: .4em;
        text-align: left;
    }
</style>
<div>
    <div>
        <a class="tabUnselected" href="create.htm"><span>Add User</span></a>
        <a class="tabUnselected" href="list.htm"><span>View User</span></a>
        <a class="tabNormal" href="assignadvertiser.htm"><span>Assign Advertiser</span></a>
        <a class="tabUnselected" href="assignrole.htm"><span>Assign Role</span></a>
    </div>
    <div id="boxes" class="ui-widget box Top">
        <spring-form:form method="POST" action="assignadvertiser.htm" modelAttribute="userAdvertiserMapping">
            <table class="ui-widget ui-widget-content">
                <tbody>
                <tr>
                    <td style="width:200px;">User:</td>
                    <td>
                        <spring-form:select path="user" id="advertiserUserSelect">
                            <spring-form:option value="0" label="Select User"/>
                            <spring-form:options items="${userList}" itemLabel="username" itemValue="id"/>
                        </spring-form:select>
                        <spring-form:errors path="user"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:200px;">Advertisers:</td>
                    <td>
                        <table id="userAdvertiserListTable">
                            <tbody>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td style="width:200px;">&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td style="width:200px;">&nbsp;</td>
                    <td>
                        <input type="submit" value="Assign Advertiser"/>
                    </td>
                </tr>
                </tbody>
            </table>
        </spring-form:form>
    </div>
</div>
