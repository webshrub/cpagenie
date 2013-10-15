<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring-tags" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="displaytag-el" uri="http://displaytag.sf.net/el" %>
<style type="text/css">
    body {
        font-size: 70.0%;
    }

    div#boxes table, div#advertiserModalDiv table {
        margin: 1em 0;
        border-collapse: collapse;
        width: 100%;
    }

    div#boxes table td, div#boxes table th, div#advertiserModalDiv table td {
        border: 1px solid #eee;
        padding: .4em;
        text-align: left;
    }
</style>
<div>
    <div>
        <a class="tabUnselected" href="create.htm"><span>Add Advertiser</span></a>
        <a class="tabNormal" href="list.htm"><span>View Advertiser</span></a>
    </div>
    <div id="boxes" class="ui-widget box Top">
        <table id="advertiserListTable" class="ui-widget ui-widget-content">
            <thead>
            <tr class="ui-widget-header">
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Email</th>
                <th>Vertical</th>
                <th>Edit</th>
            </tr>
            </thead>
            <tbody>
            <core:forEach items="${advertiserList}" var="advertiser">
                <tr>
                    <td>${advertiser.id}</td>
                    <td>${advertiser.name}</td>
                    <td>${advertiser.description}</td>
                    <td>${advertiser.email}</td>
                    <td>${advertiser.vertical.name}</td>
                    <td>
                        <a href="${advertiser.id}" name="advertiserModalOpenLink">
                            <img src="../../resources/images/edit.gif" style="border-style:none;"/>
                        </a>
                    </td>
                </tr>
            </core:forEach>
            </tbody>
        </table>
    </div>
    <div id="advertiserModalDiv" title="Edit Advertiser">
        <p id="errorMessages"></p>
        <spring-form:form method="POST" action="update.htm" modelAttribute="advertiser" id="advertiserModalForm">
            <table class="ui-widget ui-widget-content">
                <tbody>
                <tr>
                    <td>Name:</td>
                    <td>
                        <spring-form:input path="name" type="text" size="25"/>
                    </td>
                </tr>
                <tr>
                    <td>Description:</td>
                    <td>
                        <spring-form:input path="description" type="text" size="25"/>
                    </td>
                </tr>
                <tr>
                    <td>Email:</td>
                    <td>
                        <spring-form:input path="email" type="text" size="25"/>
                    </td>
                </tr>
                <tr>
                    <td>Vertical:</td>
                    <td>
                        <spring-form:select path="vertical" items="${verticalList}" itemLabel="name"
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
                                <%--onclick="return confirm('Are you sure you want to delete '+$('#name').val());">Delete--%>
                        <%--</button>--%>
                    </td>
                </tr>
                </tbody>
            </table>
        </spring-form:form>
    </div>
</div>