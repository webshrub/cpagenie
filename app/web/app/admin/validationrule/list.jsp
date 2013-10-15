<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring-tags" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="displaytag-el" uri="http://displaytag.sf.net/el" %>
<style type="text/css">
    body {
        font-size: 70.0%;
    }

    div#boxes table, div#validationRuleModalDiv table {
        margin: 1em 0;
        border-collapse: collapse;
        width: 100%;
    }

    div#boxes table td, div#boxes table th, div#validationRuleModalDiv table td {
        border: 1px solid #eee;
        padding: .4em;
        text-align: left;
    }
</style>
<div>
    <div>
        <a class="tabUnselected" href="create.htm"><span>Add Validation Rule</span></a>
        <a class="tabNormal" href="list.htm"><span>View Validation Rule</span></a>
    </div>
    <div id="boxes" class="ui-widget box Top">
        <table id="validationRuleListTable" class="ui-widget ui-widget-content">
            <thead>
            <tr class="ui-widget-header">
                <th>ID</th>
                <th>Profane</th>
                <th>Description</th>
                <th>Edit</th>
            </tr>
            </thead>
            <tbody>
            <core:forEach items="${validationRuleList}" var="validationRule">
                <tr>
                    <td>${validationRule.id}</td>
                    <td>${validationRule.profane}</td>
                    <td>${validationRule.description}</td>
                    <td>
                        <a href="${validationRule.id}" name="validationRuleModalOpenLink">
                            <img src="../../resources/images/edit.gif" style="border-style:none;"/>
                        </a>
                    </td>
                </tr>
            </core:forEach>
            </tbody>
        </table>
    </div>
    <div id="validationRuleModalDiv" title="Edit Rule">
        <p id="errorMessages"></p>
        <spring-form:form method="POST" action="update.htm" modelAttribute="validationRule"
                          id="validationRuleModalForm">
            <table class="ui-widget ui-widget-content">
                <tbody>
                <tr>
                    <td>Profane:</td>
                    <td>
                        <spring-form:input path="profane" type="text" size="25"/>
                    </td>
                </tr>
                <tr>
                    <td>Description:</td>
                    <td>
                        <spring-form:input path="description" type="text" size="25"/>
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
                                <%--onclick="return confirm('Are you sure you want to delete '+$('#profane').val());">Delete--%>
                        <%--</button>--%>
                    </td>
                </tr>
                </tbody>
            </table>
        </spring-form:form>
    </div>
</div>
