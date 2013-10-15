<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring-tags" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <a class="tabNormal" href="create.htm"><span>Add Campaign</span></a>
        <a class="tabUnselected" href="list.htm"><span>View Campaign</span></a>
    </div>
    <div id="boxes" class="ui-widget box Top">
        <spring-form:form method="POST" action="create.htm" modelAttribute="campaign">
            <table class="ui-widget ui-widget-content">
                <tr>
                    <td style="width:200px;">Name:</td>
                    <td>
                        <spring-form:input path="name" type="text" size="25"/>
                        <spring-form:errors path="name"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:200px;">Description:</td>
                    <td>
                        <spring-form:input path="description" type="text" size="25"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:200px;">Start Date:</td>
                    <td>
                        <spring-tags:bind path="startDate">
                            <input type="text"
                                   value="<fmt:formatDate value="${startDate}" type="date" pattern="yyyy-MM-dd"/>"
                                   id="startDate" name="startDate"/>
                        </spring-tags:bind>
                        <spring-form:errors path="startDate"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:200px;">End Date:</td>
                    <td>
                        <spring-tags:bind path="endDate">
                            <input type="text"
                                   value="<fmt:formatDate value="${endDate}" type="date" pattern="yyyy-MM-dd"/>"
                                   id="endDate" name="endDate"/>
                        </spring-tags:bind>
                        <spring-form:errors path="endDate"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:200px;">Email:</td>
                    <td>
                        <spring-form:input path="email" type="text" size="25" id="email"/>
                        <spring-form:errors path="email"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:200px;">Cost Per Lead:</td>
                    <td>
                        <spring-form:input path="costPerLead" type="text" size="25" id="costPerLead"/>
                        <spring-form:errors path="costPerLead"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:200px;">Total Budget:</td>
                    <td>
                        <spring-form:input path="totalBudget" type="text" size="25" id="totalBudget"/>
                        <spring-form:errors path="totalBudget"/>
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
                    <td style="width:200px;">Response Type:</td>
                    <td>
                        <spring-form:select path="responseType" items="${responseTypeList}" itemLabel="name"
                                            itemValue="id"/>
                        <spring-form:errors path="responseType"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:200px;">Success Response:</td>
                    <td>
                        <spring-form:input path="successResponse" type="text" size="25" id="successResponse"/>
                        <spring-form:errors path="successResponse"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:200px;">Failure Response:</td>
                    <td>
                        <spring-form:input path="failureResponse" type="text" size="25" id="failureResponse"/>
                        <spring-form:errors path="failureResponse"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:200px;">Advertiser:</td>
                    <td>
                        <spring-form:select path="advertiser" items="${advertiserList}" itemLabel="name" itemValue="id"/>
                        <spring-form:errors path="advertiser"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:200px;">
                        <button type="submit" name="addCampaignFieldButton" id="addCampaignFieldButton">Add Field
                        </button>
                    </td>
                    <td>
                        &nbsp;
                    </td>
                </tr>
            </table>
            <%--This is a hack to use description here to show error messages--%>
            <spring-form:errors path="description"/>
            <table id="campaignFieldsTable" class="ui-widget ui-widget-content">
                <thead>
                <tr class="ui-widget-header">
                    <th>Field</th>
                    <th>Description</th>
                    <th>Parameter</th>
                    <th>Field Type</th>
                    <th>Validation Type</th>
                    <th>Delete</th>
                </tr>
                </thead>
                <tbody>
                <core:forEach items="${fields}" varStatus="fieldRow">
                    <tr>
                        <td>
                            <spring-tags:bind path="fields[${fieldRow.index}].field">
                                <select name="<core:out value="${status.expression}"/>"
                                        id="<core:out value="${status.expression}"/>">
                                    <core:forEach var="supportedFieldVar" items="${supportedFieldList}">
                                        <option value="${supportedFieldVar.id}"
                                                <core:if
                                                        test="${supportedFieldVar.id == status.value}">selected</core:if>>
                                            <core:out value="${supportedFieldVar.displayName}"/>
                                        </option>
                                    </core:forEach>
                                </select>
                            </spring-tags:bind>
                        </td>
                        <td>
                            <spring-tags:bind path="fields[${fieldRow.index}].description">
                                <input type="text" name="<core:out value="${status.expression}"/>"
                                       id="<core:out value="${status.expression}"/>"
                                       value="<core:out value="${status.value}"/>"/>
                            </spring-tags:bind>
                        </td>
                        <td>
                            <spring-tags:bind path="fields[${fieldRow.index}].parameter">
                                <input type="text" name="<core:out value="${status.expression}"/>"
                                       id="<core:out value="${status.expression}"/>"
                                       value="<core:out value="${status.value}"/>"/>
                            </spring-tags:bind>
                        </td>
                        <td>
                            <spring-tags:bind path="fields[${fieldRow.index}].fieldType">
                                <select name="<core:out value="${status.expression}"/>"
                                        id="<core:out value="${status.expression}"/>">
                                    <core:forEach var="fieldTypeVar" items="${fieldTypeList}">
                                        <option value="${fieldTypeVar.id}"
                                                <core:if test="${fieldTypeVar.id == status.value}">selected</core:if>>
                                            <core:out value="${fieldTypeVar.name}"/>
                                        </option>
                                    </core:forEach>
                                </select>
                            </spring-tags:bind>
                        </td>
                        <td>
                            <spring-tags:bind path="fields[${fieldRow.index}].fieldValidationType">
                                <select name="<core:out value="${status.expression}"/>"
                                        id="<core:out value="${status.expression}"/>">
                                    <core:forEach var="fieldValidationTypeVar" items="${fieldValidationTypeList}">
                                        <option value="${fieldValidationTypeVar.id}"
                                                <core:if
                                                        test="${fieldValidationTypeVar.id == status.value}">selected</core:if>>
                                            <core:out value="${fieldValidationTypeVar.name}"/>
                                        </option>
                                    </core:forEach>
                                </select>
                            </spring-tags:bind>
                        </td>
                        <td>
                            <a style="cursor:pointer" onclick="removeCampaignField(this);">
                                <img src="../../resources/images/delete.png" style="border-style:none;"/>
                            </a>
                        </td>
                    </tr>
                </core:forEach>
                </tbody>
            </table>
            <table class="ui-widget ui-widget-content">
                <tr>
                    <td>
                        <input type="submit" name="addCampaignButton" value="Add Campaign"/>
                    </td>
                </tr>
            </table>
        </spring-form:form>
    </div>
</div>
