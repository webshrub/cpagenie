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
        <a class="tabNormal" href="create.htm"><span>Add User</span></a>
        <a class="tabUnselected" href="list.htm"><span>View User</span></a>
        <a class="tabUnselected" href="assignadvertiser.htm"><span>Assign Advertiser</span></a>
        <a class="tabUnselected" href="assignrole.htm"><span>Assign Role</span></a>
    </div>
    <div id="boxes" class="ui-widget box Top">
        <spring-form:form method="POST" action="create.htm" modelAttribute="user">
            <table class="ui-widget ui-widget-content">
                <tr>
                    <td style="width:200px;">Username:</td>
                    <td>
                        <spring-form:input path="username" type="text" size="25"/>
                        <spring-form:errors path="username"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:200px;">Password:</td>
                    <td>
                        <spring-form:password path="password" size="25"/>
                        <spring-form:errors path="password"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:200px;">Confirm Password:</td>
                    <td>
                        <spring-form:password path="confirmPassword" size="25"/>
                        <spring-form:errors path="confirmPassword"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:200px;">Email:</td>
                    <td>
                        <spring-form:input path="email" type="text" size="25"/>
                        <spring-form:errors path="email"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:200px;">First Name:</td>
                    <td>
                        <spring-form:input path="firstName" type="text" size="25"/>
                        <spring-form:errors path="firstName"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:200px;">Last Name:</td>
                    <td>
                        <spring-form:input path="lastName" type="text" size="25"/>
                        <spring-form:errors path="lastName"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:200px;">Status:</td>
                    <td>
                        <spring-form:select path="status" items="${statusList}" itemLabel="name" itemValue="id"/>
                        <spring-form:errors path="status"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:200px;">&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td style="width:200px;">&nbsp;</td>
                    <td>
                        <input type="submit" value="Add User"/>
                    </td>
                </tr>
            </table>
        </spring-form:form>
    </div>
</div>
