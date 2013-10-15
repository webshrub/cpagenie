<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring-tags" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="displaytag-el" uri="http://displaytag.sf.net/el" %>
<style type="text/css">
    body {
        font-size: 70.0%;
    }

    div#boxes table, div#leadModalDiv table {
        margin: 1em 0;
        border-collapse: collapse;
        width: 100%;
    }

    div#boxes table td, div#boxes table th, div#leadModalDiv table td {
        border: 1px solid #eee;
        padding: .4em;
        text-align: left;
    }
</style>
<div>
    <div>
        <a class="tabUnselected" href="impressionreport.htm"><span>Impression Report</span></a>
        <a class="tabNormal" href="leadreport.htm"><span>Lead Report</span></a>
    </div>
    <div id="boxes" class="ui-widget box Top">
        <spring-form:form method="POST" action="updateleadreport.htm" modelAttribute="leadReportFilter" id="leadReportForm">
            <table class="ui-widget ui-widget-content">
                <thead>
                <tr class="ui-widget-header">
                    <th>Advertiser</th>
                    <th>Campaign</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Fetch Data</th>
                </tr>
                </thead>
                <tr>
                    <td>
                        <spring-form:select path="advertiser" items="${advertiserList}" itemLabel="name" itemValue="id"
                                            id="leadReportAdvertiserSelect"/>
                        <spring-form:errors path="advertiser"/>
                    </td>
                    <td>
                        <spring-form:select path="campaign" items="${campaignList}" itemLabel="name" itemValue="id"
                                            id="leadReportCampaignSelect"/>
                        <spring-form:errors path="campaign"/>
                    </td>
                    <td>
                        <spring-form:input path="startDate" type="text" size="25" id="leadReportStartDate"/>
                        <spring-form:errors path="startDate"/>
                    </td>
                    <td>
                        <spring-form:input path="endDate" type="text" size="25" id="leadReportEndDate"/>
                        <spring-form:errors path="endDate"/>
                    </td>
                    <td>
                        <button type="submit">Fetch Data</button>
                    </td>
                </tr>
            </table>
        </spring-form:form>
        <table id="leadReportTable" class="ui-widget ui-widget-content">
            <thead>
            <tr class="ui-widget-header">
                <th>ID</th>
                <th>Capture Time</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Address1</th>
                <th>Address2</th>
                <th>City</th>
                <th>State</th>
                <th>Home Phone</th>
                <th>Work Phone</th>
                <th>Mobile Phone</th>
                <th>Status</th>
                <th>Edit</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
    <div id="leadModalDiv" title="Edit Lead">
        <p id="errorMessages"></p>
        <spring-form:form method="POST" action="../lead/update.htm" modelAttribute="lead" id="leadModalForm">
            <table class="ui-widget ui-widget-content">
                <tbody>
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
                    <td>Email:</td>
                    <td>
                        <spring-form:input path="email" type="text" size="25"/>
                    </td>
                </tr>
                <tr>
                    <td>Address1:</td>
                    <td>
                        <spring-form:input path="address1" type="text" size="25"/>
                    </td>
                </tr>
                <tr>
                    <td>Address2:</td>
                    <td>
                        <spring-form:input path="address2" type="text" size="25"/>
                    </td>
                </tr>
                <tr>
                    <td>City:</td>
                    <td>
                        <spring-form:input path="city" type="text" size="25"/>
                    </td>
                </tr>
                <tr>
                    <td>State:</td>
                    <td>
                        <spring-form:input path="state" type="text" size="25"/>
                    </td>
                </tr>
                <tr>
                    <td>Home Phone:</td>
                    <td>
                        <spring-form:input path="homePhone" size="25"/>
                    </td>
                </tr>
                <tr>
                    <td>Work Phone:</td>
                    <td>
                        <spring-form:input path="workPhone" size="25"/>
                    </td>
                </tr>
                <tr>
                    <td>Mobile Phone:</td>
                    <td>
                        <spring-form:input path="mobilePhone" size="25"/>
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
                        <button type="submit" name="delete"
                                onclick="return confirm('Are you sure you want to delete this lead');">
                            Delete
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
        </spring-form:form>
    </div>
</div>
