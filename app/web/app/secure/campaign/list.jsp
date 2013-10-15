<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring-tags" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="displaytag-el" uri="http://displaytag.sf.net/el" %>
<style type="text/css">
    body {
        font-size: 70.0%;
    }

    div#boxes table, div#campaignModalDiv table {
        margin: 1em 0;
        border-collapse: collapse;
        width: 100%;
    }

    div#boxes table td, div#boxes table th, div#campaignModalDiv table td {
        border: 1px solid #eee;
        padding: .4em;
        text-align: left;
    }
</style>
<div>
    <div>
        <a class="tabUnselected" href="create.htm"><span>Add Campaign</span></a>
        <a class="tabNormal" href="list.htm"><span>View Campaign</span></a>
    </div>
    <div id="boxes" class="ui-widget box Top">
        <table id="campaignListTable" class="ui-widget ui-widget-content">
            <thead>
            <tr class="ui-widget-header">
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Email</th>
                <th>Advertiser</th>
                <th>Edit</th>
                <th>Preview</th>
                <th>Script</th>
            </tr>
            </thead>
            <tbody>
            <core:forEach items="${campaignList}" var="campaign">
                <tr>
                    <td>${campaign.id}</td>
                    <td>${campaign.name}</td>
                    <td>${campaign.description}</td>
                    <td>${campaign.email}</td>
                    <td>${campaign.advertiser.name}</td>
                    <td>
                        <a href="${campaign.id}" name="campaignModalOpenLink">
                            <img src="../../resources/images/edit.gif" style="border-style:none;"/>
                        </a>
                    </td>
                     <td>
                        <a href="${campaign.id}" class="formPreviewLink">
                            <img src="../../resources/images/edit.gif" style="border-style:none;"/>
                        </a>
                    </td>
                    <td>
                        <a href="${campaign.id}" class="sourceOptionsLink">
                            <img src="../../resources/images/edit.gif" style="border-style:none;"/>
                        </a>
                    </td>
                    
                </tr>
            </core:forEach>
            </tbody>
        </table>
    </div>
    <div id="campaignModalDiv" title="Edit Campaign">
        <p id="errorMessages"></p>
        <spring-form:form method="POST" action="update.htm" modelAttribute="campaign" id="campaignModalForm">
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
                    <td>Start Date:</td>
                    <td>
                        <spring-form:input path="startDate" type="text" size="25"/>
                    </td>
                </tr>
                <tr>
                    <td>End Date:</td>
                    <td>
                        <spring-form:input path="endDate" type="text" size="25"/>
                    </td>
                </tr>
                <tr>
                    <td>Email:</td>
                    <td>
                        <spring-form:input path="email" type="text" size="25"/>
                    </td>
                </tr>
                <tr>
                    <td>Cost Per Lead:</td>
                    <td>
                        <spring-form:input path="costPerLead" type="text" size="25"/>
                    </td>
                </tr>
                <tr>
                    <td>Total Budget:</td>
                    <td>
                        <spring-form:input path="totalBudget" type="text" size="25"/>
                    </td>
                </tr>
                <tr>
                    <td>Status:</td>
                    <td>
                        <spring-form:select path="status" items="${statusList}" itemLabel="name" itemValue="id"/>
                    </td>
                </tr>
                <tr>
                    <td>Response Type:</td>
                    <td>
                        <spring-form:select path="responseType" items="${responseTypeList}" itemLabel="name"
                                            itemValue="id"/>
                    </td>
                </tr>
                <tr>
                    <td>Success Response:</td>
                    <td>
                        <spring-form:input path="successResponse" type="text" size="25" id="successResponse"/>
                    </td>
                </tr>
                <tr>
                    <td>Failure Response:</td>
                    <td>
                        <spring-form:input path="failureResponse" type="text" size="25" id="failureResponse"/>
                    </td>
                </tr>
                <tr>
                    <td>Advertiser:</td>
                    <td>
                        <spring-form:select path="advertiser" items="${advertiserList}" itemLabel="name" itemValue="id"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button type="submit" name="addCampaignFieldButton" id="addCampaignFieldButton">Add Field
                        </button>
                    </td>
                    <td>
                        &nbsp;
                    </td>
                </tr>
                </tbody>
            </table>
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
                </tbody>
            </table>
            <table class="ui-widget ui-widget-content">
                <tr>
                    <td>
                        <button type="submit" name="update">Update</button>
                    </td>
                    <td>
                        <%--<button type="submit" name="delete"--%>
                                <%--onclick="return confirm('Are you sure you want to delete '+$('#name').val());"> Delete--%>
                        <%--</button>--%>
                    </td>
                </tr>
            </table>
            <input type="hidden" id="removeIds" value=""/>
        </spring-form:form>
    </div>
    <div id="formPreviewModal" title="Preview Campaign Form">
	    <div id="cpaginiecampaign">
	    </div>
    </div>
    <div id="scriptPreviewModal" title="CPAGenie Script Source">
    	<div id="scriptSource"></div>
    </div>
     <div id="sourceOptionsModal" title="CPAGenie Source Options">
       <spring-form:form method="POST" action="scriptPreview.htm" modelAttribute="campaign" id="sourceoptions">
       </spring-form:form>
    </div>
</div>