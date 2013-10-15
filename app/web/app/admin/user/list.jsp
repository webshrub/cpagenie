<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring-tags" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="displaytag-el" uri="http://displaytag.sf.net/el" %>
<style type="text/css">
    body {
        font-size: 70.0%;
    }

    div#boxes table, div#userModalDiv table {
        margin: 1em 0;
        border-collapse: collapse;
        width: 100%;
    }

    div#boxes table td, div#boxes table th, div#userModalDiv table td {
        border: 1px solid #eee;
        padding: .4em;
        text-align: left;
    }
</style>
<div>
    <div>
        <a class="tabUnselected" href="create.htm"><span>Add User</span></a>
        <a class="tabNormal" href="list.htm"><span>View User</span></a>
        <a class="tabUnselected" href="assignadvertiser.htm"><span>Assign Advertiser</span></a>
        <a class="tabUnselected" href="assignrole.htm"><span>Assign Role</span></a>
    </div>
    <div id="boxes" class="ui-widget box Top">
        <table id="userListTable" class="ui-widget ui-widget-content">
            <thead>
            <tr class="ui-widget-header">
                <th>ID</th>
                <th>Username</th>
                <th>Email</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Status</th>
                <th>Edit</th>
            </tr>
            </thead>
            <tbody>
            <core:forEach items="${userList}" var="user">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.status.name}</td>
                    <td>
                        <a href="${user.id}" name="userModalOpenLink">
                            <img src="../../resources/images/edit.gif" style="border-style:none;"/>
                        </a>
                    </td>
                </tr>
            </core:forEach>
            </tbody>
        </table>
    </div>
    <div id="userModalDiv" title="Edit User">
        <p id="errorMessages"></p>
        <spring-form:form method="POST" action="update.htm" modelAttribute="user" id="userModalForm">
            <table class="ui-widget ui-widget-content">
                <tbody>
                <tr>
                    <td>Username:</td>
                    <td>
                        <spring-form:input path="username" type="text" size="25" readonly="true"/>
                    </td>
                </tr>
                <tr>
                    <td>Email:</td>
                    <td>
                        <spring-form:input path="email" type="text" size="25"/>
                    </td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td>
                        <spring-form:password path="password" size="25"/>
                    </td>
                </tr>
                <tr>
                    <td>Confirm Password:</td>
                    <td>
                        <spring-form:password path="confirmPassword" size="25"/>
                    </td>
                </tr>
                <tr>
                    <td>First Name:</td>
                    <td>
                        <spring-form:input path="firstName" type="text" size="25"/>
                    </td>
                </tr>
                <tr>
                    <td>Last Name:</td>
                    <td>
                        <spring-form:input path="lastName" type="text" size="25"/>
                    </td>
                </tr>
                <tr>
                    <td>Status:</td>
                    <td>
                        <spring-form:select path="status" items="${statusList}" itemLabel="name"
                                            itemValue="id"/>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>
                        <button type="submit" name="update">Update</button>
                    </td>
                    <td>
                        <%--<button type="submit" name="delete"--%>
                                <%--onclick="return confirm('Are you sure you want to delete '+$('#username').val());">--%>
                            <%--Delete--%>
                        <%--</button>--%>
                    <%--</td>--%>
                </tr>
                </tbody>
            </table>
        </spring-form:form>
    </div>
</div>
