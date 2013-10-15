$(document).ready(function() {
    $('#impressionReportTable').dataTable({
        "bFilter":false,
        "iDisplayLength": 10,
        "aLengthMenu": [
            [10, 20, 50],
            [10, 20, 50]
        ] ,
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": "impressionreport.htm",
        "fnServerData": function (sSource, aoData, fnCallback) {
            $.ajax({
                "dataType": "json",
                "type": "POST",
                "url": sSource,
                "data": aoData,
                "success": function(json) {
                    var sEcho = json.sEcho;
                    var iTotalRecords = json.iTotalRecords;
                    var iTotalDisplayRecords = json.iTotalDisplayRecords;
                    var aaData = [];
                    $(json.impressionReportRowList).each(function () {
                        aaData.push([this.runTime, this.impressions, this.submitCount, this.leadCount, this.costPerLead, this.revenue, this.conversionRate, this.revenuePerImpression, this.filterRate]);
                    });
                    var newJsonObject = {
                        "sEcho":sEcho,
                        "iTotalRecords":iTotalRecords,
                        "iTotalDisplayRecords":iTotalDisplayRecords,
                        "aaData":aaData
                    };
                    fnCallback(newJsonObject);
                },
                "error":function() {
                    alert("Error occurred while fetching impression report.");
                }
            });
        }
    });

    $('#impressionReportForm').ajaxForm({
        type: 'POST',
        success:function(json) {
            $('#impressionReportTable').dataTable().fnDraw(false);
        },
        error:function(json) {
            alert("Something bad happened while fetching impression report!!!");
        }
    });

    $('#impressionReportAdvertiserSelect').change(function(e) {
        var advertiserId = $('#impressionReportAdvertiserSelect option:selected').val();
        var options = '';
        $.getJSON("getcampaigns.htm?id=" + advertiserId, function(json) {
            $(json.campaignList).each(function() {
                options += '<option value="' + this.id + '">' + this.name + '</option>';
            });
            $('#impressionReportCampaignSelect').html(options);
        });
    });

    $('#impressionReportStartDate').datepicker({
        dateFormat:"yy-mm-dd"
    });

    $('#impressionReportStartDate').datepicker('setDate', new Date());

    $('#impressionReportEndDate').datepicker({
        dateFormat:"yy-mm-dd"
    });

    $('#impressionReportEndDate').datepicker('setDate', new Date());

    $('#leadReportTable').dataTable({
        "bFilter":false,
        "iDisplayLength": 10,
        "aLengthMenu": [
            [10, 20, 50],
            [10, 20, 50]
        ] ,
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": "leadreport.htm",
        "fnServerData": function (sSource, aoData, fnCallback) {
            $.ajax({
                "dataType": "json",
                "type": "POST",
                "url": sSource,
                "data": aoData,
                "success": function(json) {
                    var sEcho = json.sEcho;
                    var iTotalRecords = json.iTotalRecords;
                    var iTotalDisplayRecords = json.iTotalDisplayRecords;
                    var aaData = [];
                    $(json.leadList).each(function () {
                        aaData.push([this.id, this.captureTime, this.firstName, this.lastName, this.email, this.address1, this.address2, this.city, this.state, this.homePhone, this.workPhone, this.mobilePhone, this.status.name, this]);
                    });
                    var newJsonObject = {
                        "sEcho":sEcho,
                        "iTotalRecords":iTotalRecords,
                        "iTotalDisplayRecords":iTotalDisplayRecords,
                        "aaData":aaData
                    };
                    fnCallback(newJsonObject);
                },
                "error":function() {
                    alert("Error occurred while fetching lead report.");
                }
            });
        },
        "aoColumnDefs": [
            {   "bSortable": false,
                "aTargets": [13],
                "bUseRendered": false,
                "fnRender": function(obj) {
                    return '<a style="cursor:pointer" onclick="showLeadModalDiv(' + obj.aData[0] + '); return false;">' +
                            '<img src="../../resources/images/edit.gif" style="border-style:none;"/>' +
                            '</a>';
                }
            }
        ]
    });

    $('#leadReportForm').ajaxForm({
        type: 'POST',
        success:function(json) {
            $('#leadReportTable').dataTable().fnDraw(false);
        },
        error:function(json) {
            alert("Something bad happened while fetching lead report!!!");
        }
    });

    $('#leadReportAdvertiserSelect').change(function(e) {
        var advertiserId = $('#leadReportAdvertiserSelect option:selected').val();
        var options = '';
        $.getJSON("getcampaigns.htm?id=" + advertiserId, function(json) {
            $(json.campaignList).each(function() {
                options += '<option value="' + this.id + '">' + this.name + '</option>';
            });
            $('#leadReportCampaignSelect').html(options);
        });
    });

    $('#leadReportStartDate').datepicker({
        dateFormat:"yy-mm-dd"
    });

    $('#leadReportStartDate').datepicker('setDate', new Date());

    $('#leadReportEndDate').datepicker({
        dateFormat:"yy-mm-dd"
    });

    $('#leadReportEndDate').datepicker('setDate', new Date());

    $("#leadModalDiv").dialog({
        autoOpen: false,
        height: 500,
        width: 350,
        modal: true,
        close: function() {
            $('#errorMessages').text('').removeClass('ui-state-highlight');
            $(".ui-state-error").val('').removeClass('ui-state-error');
        }
    });

    $('#leadModalForm').ajaxForm({
        type: 'POST',
        success:function(json) {
            if (json.status == 'success') {
                window.location.replace("../report/leadreport.htm");
            } else {
                updateErrorMessages($('#errorMessages'), json);
                renderLeadModalDiv(json);
            }
        },
        error:function(json) {
            alert("Something bad happened!!!");
        }
    });
});

function updateErrorMessages(errorMessagesElement, json) {
    var errorMessages = '';
    $(json.errors).each(function() {
        $("#" + this.field).addClass('ui-state-error');
        errorMessages = errorMessages + this.defaultMessage + '\n';
    });
    errorMessagesElement.text(errorMessages).addClass('ui-state-highlight');
    setTimeout(function() {
        errorMessagesElement.removeClass('ui-state-highlight', 1500);
    }, 500);
}

function showLeadModalDiv(id) {
    $.getJSON("../lead/update.htm?id=" + id, function(json) {
        renderLeadModalDiv(json);
        $('#leadModalDiv').dialog('open');
    });
}

function renderLeadModalDiv(json) {
    $('#firstName').val(json.lead.firstName);
    $('#lastName').val(json.lead.lastName);
    $('#email').val(json.lead.email);
    $('#address1').val(json.lead.address1);
    $('#address2').val(json.lead.address2);
    $('#city').val(json.lead.city);
    $('#state').val(json.lead.state);
    $('#homePhone').val(json.lead.homePhone);
    $('#workPhone').val(json.lead.workPhone);
    $('#mobilePhone').val(json.lead.mobilePhone);
    var leadStatusId = json.lead.status.id;
    var options = '';
    $(json.statusList).each(function() {
        if (this.id == leadStatusId) {
            options += '<option value="' + this.id + '" selected>' + this.name + '</option>';
        } else {
            options += '<option value="' + this.id + '">' + this.name + '</option>';
        }
    });
    $('#status').html(options);
}


