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
        <a class="tabNormal" href="create.htm"><span>Add Advertiser</span></a>
        <a class="tabUnselected" href="list.htm"><span>View Advertiser</span></a>
    </div>
    <div id="boxes" class="ui-widget box Top">
        <spring-form:form method="POST" action="create.htm" modelAttribute="advertiser">
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
                        <spring-form:errors path="description"/>
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
                    <td style="width:200px;">Vertical:</td>
                    <td>
                        <spring-form:select path="vertical" items="${verticalList}" itemLabel="name" itemValue="id"/>
                        <spring-form:errors path="vertical"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:200px;">&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td style="width:200px;">&nbsp;</td>
                    <td>
                        <input type="submit" value="Add Advertiser"/>
                    </td>
                </tr>
            </table>
        </spring-form:form>
    </div>
</div>
