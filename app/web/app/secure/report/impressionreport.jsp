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
        <a class="tabNormal" href="impressionreport.htm"><span>Impression Report</span></a>
        <a class="tabUnselected" href="leadreport.htm"><span>Lead Report</span></a>
    </div>
    <div id="boxes" class="ui-widget box Top">
        <spring-form:form method="POST" action="updateimpressionreport.htm" modelAttribute="impressionReportFilter"
                          id="impressionReportForm">
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
                                            id="impressionReportAdvertiserSelect"/>
                        <spring-form:errors path="advertiser"/>
                    </td>
                    <td>
                        <spring-form:select path="campaign" items="${campaignList}" itemLabel="name" itemValue="id"
                                            id="impressionReportCampaignSelect"/>
                        <spring-form:errors path="campaign"/>
                    </td>
                    <td>
                        <spring-form:input path="startDate" type="text" size="25" id="impressionReportStartDate"/>
                        <spring-form:errors path="startDate"/>
                    </td>
                    <td>
                        <spring-form:input path="endDate" type="text" size="25" id="impressionReportEndDate"/>
                        <spring-form:errors path="endDate"/>
                    </td>
                    <td>
                        <button type="submit">Fetch Data</button>
                    </td>
                </tr>
            </table>
            <table id="impressionReportTable" class="ui-widget ui-widget-content">
                <thead>
                <tr class="ui-widget-header">
                    <th>Run Time</th>
                    <th>Impressions</th>
                    <th>Submit Count</th>
                    <th>Lead Count</th>
                    <th>Cost Per Lead</th>
                    <th>Revenue</th>
                    <th>Conversion Rate</th>
                    <th>Revenue Per Impression</th>
                    <th>Filter Rate</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </spring-form:form>
    </div>
</div>
