$(document).ready(function() {
    $.fn.pulse = function(time) {
        if (!time) {
            time = 2000;
        }
        // this == jQuery object that contains selections
        $(this).css("background", "rgb(255,255,180)");
        this.fadeTo(time, 0.30, function() {
            $(this).fadeTo(time, 1);
            $(this).css("background", "rgb(255,255,255)");
        });
        return this;
    };

    $('#campaignListTable').dataTable({
        "iDisplayLength": 10,
        "aLengthMenu": [
            [10, 20, 50],
            [10, 20, 50]
        ] ,
        "aoColumnDefs": [
            {   "bSortable": false,
                "aTargets": [5]
            }
        ]
    });

    $('a[name=campaignModalOpenLink]').click(function(e) {
        e.preventDefault();
        $('#removeIds').val('');
        var id = $(this).attr('href');
        showCampaignModalDiv(id);
    });

    $('#startDate').datepicker({
        dateFormat:"yy-mm-dd"
    });

    $('#endDate').datepicker({
        dateFormat:"yy-mm-dd"
    });

    $("#campaignModalDiv").dialog({
        autoOpen: false,
        height: 550,
        width: 750,
        modal: true,
        close: function() {
            $('#errorMessages').text('').removeClass('ui-state-highlight');
            $(".ui-state-error").val('').removeClass('ui-state-error');
        }
    });
    
    $('#campaignModalForm').ajaxForm();

    $('#campaignModalForm').submit(function() {
        $(this).ajaxSubmit({
            type: 'POST',
            data: {'removeIds':$('#removeIds').val()} ,
            success:function(json) {
                if (json.status == 'success') {
                    window.location.replace("list.htm");
                } else {
                    updateErrorMessages($('#errorMessages'), json);
                    renderCampaignModalDiv(json);
                    $('#removeIds').val('');
                }
            },
            error:function(json) {
                alert("Something bad happened while updating the campaign!!!");
            }
        });
    });

    var fieldPosition = $('#campaignFieldsTable >tbody >tr').length;
    $('#addCampaignFieldButton').click(function(e) {
        e.preventDefault();
        fieldPosition = $('#campaignFieldsTable >tbody >tr').length;
        $.getJSON("addCampaignField.htm?fieldPosition=" + fieldPosition, function(json) {
            ($('#campaignFieldsTable >tbody')).append(getCampaignFieldToCreate(fieldPosition, json)).hide().pulse(500);
        });
    });
    
    initSourceOptions();
    initScriptPreview();
    initFormPreview();
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

function showCampaignModalDiv(id) {
    $.getJSON("update.htm?id=" + id, function(json) {
        renderCampaignModalDiv(json);
    });
    $('#campaignModalDiv').dialog('open');
}

function renderCampaignModalDiv(json) {
    $('#name').val(json.campaign.name);
    $('#description').val(json.campaign.description);
    $('#startDate').val(json.campaign.startDate);
    $('#endDate').val(json.campaign.endDate);
    $('#email').val(json.campaign.email);
    $('#costPerLead').val(json.campaign.costPerLead);
    $('#totalBudget').val(json.campaign.totalBudget);
    var campaignStatusId = json.campaign.status.id;
    var options = '';
    $(json.statusList).each(function() {
        if (this.id == campaignStatusId) {
            options += '<option value="' + this.id + '" selected>' + this.name + '</option>';
        } else {
            options += '<option value="' + this.id + '">' + this.name + '</option>';
        }
    });
    $('#status').html(options);
    var campaignResponseTypeId = json.campaign.responseType.id;
    options = '';
    $(json.responseTypeList).each(function() {
        if (this.id == campaignResponseTypeId) {
            options += '<option value="' + this.id + '" selected>' + this.name + '</option>';
        } else {
            options += '<option value="' + this.id + '">' + this.name + '</option>';
        }
    });
    $('#responseType').html(options);
    $('#successResponse').val(json.campaign.successResponse);
    $('#failureResponse').val(json.campaign.failureResponse);
    var campaignAdvertiserId = json.campaign.advertiser.id;
    options = '';
    $(json.advertiserList).each(function() {
        if (this.id == campaignAdvertiserId) {
            options += '<option value="' + this.id + '" selected>' + this.name + '</option>';
        } else {
            options += '<option value="' + this.id + '">' + this.name + '</option>';
        }
    });
    $('#advertiser').html(options);
    $('#campaignFieldsTable >tbody').empty();
    for (var fieldPosition = 0; fieldPosition < json.fields.length; fieldPosition++) {
        var field = json.fields[fieldPosition];
        ($('#campaignFieldsTable >tbody')).append(getCampaignFieldToUpdate(fieldPosition, field, json)).hide().pulse(500);
    }
}

function getCampaignFieldToUpdate(fieldPosition, field, json) {
    var supportedFieldTypeSelectTd = '<td><select name="fields[' + fieldPosition + '].field" id="fields[' + fieldPosition + '].field">';
    var options = '';
    var supportedFieldTypeId = field.field.id;
    $(json.supportedFieldList).each(function() {
        if (this.id == supportedFieldTypeId) {
            options += '<option value="' + this.id + '" selected>' + this.displayName + '</option>';
        } else {
            options += '<option value="' + this.id + '">' + this.displayName + '</option>';
        }
    });
    supportedFieldTypeSelectTd = supportedFieldTypeSelectTd + options + '</select></td>';

    var fieldDescriptionTd = '<td>' +
            '<input type="text" value="' + field.description + '" name="fields[' + fieldPosition + '].description" id="fields[' + fieldPosition + '].description">' +
            '</td>';

    var fieldParameterTd = '<td>' +
            '<input type="text" value="' + field.parameter + '" name="fields[' + fieldPosition + '].parameter" id="fields[' + fieldPosition + '].parameter">' +
            '</td>';

    var fieldTypeSelectTd = '<td><select name="fields[' + fieldPosition + '].fieldType" id="fields[' + fieldPosition + '].fieldType">';
    options = '';
    var fieldTypeId = field.fieldType.id;
    $(json.fieldTypeList).each(function() {
        if (this.id == fieldTypeId) {
            options += '<option value="' + this.id + '" selected>' + this.name + '</option>';
        } else {
            options += '<option value="' + this.id + '">' + this.name + '</option>';
        }
    });
    fieldTypeSelectTd = fieldTypeSelectTd + options + '</select></td>';

    var fieldValidationTypeSelectTd = '<td><select name="fields[' + fieldPosition + '].fieldValidationType" id="fields[' + fieldPosition + '].fieldValidationType">';
    options = '';
    var fieldValidationTypeId = field.fieldValidationType.id;
    $(json.fieldValidationTypeList).each(function() {
        if (this.id == fieldValidationTypeId) {
            options += '<option value="' + this.id + '" selected>' + this.name + '</option>';
        } else {
            options += '<option value="' + this.id + '">' + this.name + '</option>';
        }
    });
    fieldValidationTypeSelectTd = fieldValidationTypeSelectTd + options + '</select></td>';

    var fieldRemoveLinkTd = '<td>' +
            '<a style="cursor:pointer" onclick="removeCampaignField(this,' + field.id + ');"> <img src="../../resources/images/delete.png" style="border-style:none;"/> </a>' +
            '</td>';

    return '<tr>' + supportedFieldTypeSelectTd + fieldDescriptionTd + fieldParameterTd + fieldTypeSelectTd + fieldValidationTypeSelectTd + fieldRemoveLinkTd + '</tr>';
}

function getCampaignFieldToCreate(fieldPosition, json) {
    var options = '';
    var supportedFieldTypeSelectTd = '<td><select name="fields[' + fieldPosition + '].field" id="fields[' + fieldPosition + '].field">';
    $(json.supportedFieldList).each(function () {
        options += '<option value="' + this.id + '">' + this.displayName + '</option>';
    });
    supportedFieldTypeSelectTd = supportedFieldTypeSelectTd + options + '</select></td>';

    var fieldDescriptionTd = '<td>' +
            '<input type="text" value="" name="fields[' + fieldPosition + '].description" id="fields[' + fieldPosition + '].description">' +
            '</td>';

    var fieldParameterTd = '<td>' +
            '<input type="text" value="" name="fields[' + fieldPosition + '].parameter" id="fields[' + fieldPosition + '].parameter">' +
            '</td>';

    options = '';
    var fieldTypeSelectTd = '<td><select name="fields[' + fieldPosition + '].fieldType" id="fields[' + fieldPosition + '].fieldType">';
    $(json.fieldTypeList).each(function () {
        options += '<option value="' + this.id + '">' + this.name + '</option>';
    });
    fieldTypeSelectTd = fieldTypeSelectTd + options + '</select></td>';

    var fieldValidationTypeSelectTd = '<td><select name="fields[' + fieldPosition + '].fieldValidationType" id="fields[' + fieldPosition + '].fieldValidationType">';
    options = '';
    $(json.fieldValidationTypeList).each(function () {
        options += '<option value="' + this.id + '">' + this.name + '</option>';
    });
    fieldValidationTypeSelectTd = fieldValidationTypeSelectTd + options + '</select></td>';
    
    var fieldRemoveLinkTd = '<td>' +
            '<a style="cursor:pointer" onclick="removeCampaignField(this);"> <img src="../../resources/images/delete.png" style="border-style:none;"/> </a>' +
            '</td>';

    return '<tr>' + supportedFieldTypeSelectTd + fieldDescriptionTd + fieldParameterTd + fieldTypeSelectTd + fieldValidationTypeSelectTd + fieldRemoveLinkTd + '</tr>';
}

function removeCampaignField(element, fieldId) {
    //Check the row deleted is from update screen. Just hide it because this is required in controller layer. 
    if (fieldId != undefined) {
        //Used to pass the deleted rowIds to spring
        appendRemoveIds(fieldId);
        //Rows are only hidden  from UI. Later on spring controller deletes these rows from database.
        $(element).parent().parent().css("background", "rgb(255,255,180)").fadeTo(500, 0, function () {
            $(this).hide();
        });
    } else {
        //Rearrange all the elements to remove hole between rowIds. This is required since Spring tries to bind null values of these removed rows.
        $(element).parent().parent().css("background", "rgb(255,255,180)").fadeTo(500, 0, function () {
            //Remove the clicked row
            $(this).remove();
            var rowCount = $('#campaignFieldsTable >tbody >tr').length;
            for (var i = 0; i < rowCount; i++) {
                var row = $('#campaignFieldsTable >tbody >tr:eq(' + i + ')');

                var fieldElement = row.find('td:eq(0) select');
                fieldElement.attr('id', 'fields[' + i + '].field');
                fieldElement.attr('name', 'fields[' + i + '].field');

                var descriptionElement = row.find('td:eq(1) input');
                descriptionElement.attr('id', 'fields[' + i + '].description');
                descriptionElement.attr('name', 'fields[' + i + '].description');

                var parameterElement = row.find('td:eq(2) input');
                parameterElement.attr('id', 'fields[' + i + '].parameter');
                parameterElement.attr('name', 'fields[' + i + '].parameter');

                var fieldTypeElement = row.find('td:eq(3) select');
                fieldTypeElement.attr('id', 'fields[' + i + '].fieldType');
                fieldTypeElement.attr('name', 'fields[' + i + '].fieldType');

                var fieldValidationTypeElement = row.find('td:eq(4) select');
                fieldValidationTypeElement.attr('id', 'fields[' + i + '].fieldValidationType');
                fieldValidationTypeElement.attr('name', 'fields[' + i + '].fieldValidationType');
            }
        });
    }
}

function appendRemoveIds(fieldId) {
    if (fieldId != undefined) {
        var previousIds = $('#removeIds').val();
        var newIds = previousIds + ':' + fieldId;
        if (previousIds == '') {
            newIds = fieldId;
        } else {
            newIds = previousIds + ':' + fieldId;
        }
        $('#removeIds').val(newIds);
    }
}


/***
 * Source Options Links 
 */

function initSourceOptions() {
    
    $('.sourceOptionsLink').click(function(e) {
        e.preventDefault();
        $('#removeIds').val('');
        var id = $(this).attr('href');
        showSourceOptions(id);
    });

    $("#sourceOptionsModal").dialog({
        autoOpen: false,
        height: 100,
        width: 750,
        modal: true,
        close: function() {
           
        }
    });
	
}


function showSourceOptions(id){
	$.getJSON("sourceOptions.htm?id=" + id, function(json) {
    	renderSourceOptions(json, id);
    });
    $('#sourceOptionsModal').dialog('open');
}

function renderSourceOptions(json, id){
	$('#sourceoptions').empty();
	 for (var fieldPosition = 0; fieldPosition < json.sourceOptions.length; fieldPosition++) {
	        var sourceOption = json.sourceOptions[fieldPosition];
	        $('#sourceoptions').append('<input type="radio" name="sourceoption" value="'+sourceOption.id+'"> '+sourceOption.name+'</input><br/>');
	    }
	// $('input:radio[name=sourceoption]')[0].checked = true;
	 $('input:radio[name=sourceoption]').click(function(){
		 var sourceId = $('input:radio[name=sourceoption]:checked').val();
		 $('#sourceOptionsModal').dialog('close');
		  showScriptPreview(id, sourceId);
	 });
	 
}


/*** Source Option End***/

/**
 * Script Preview Start
 * 
 */

function initScriptPreview(){
	 $('.scriptPreviewLink').click(function(e) {
	        e.preventDefault();
	        $('#removeIds').val('');
	        var id = $(this).attr('href');
	        showScriptPreview(id);
	    });

	    $("#scriptPreviewModal").dialog({
	        autoOpen: false,
	        height: 100,
	        width: 750,
	        modal: true,
	        close: function() {
	           
	        }
	    });
}

function showScriptPreview(id, sourceId) {
    $.getJSON("scriptPreview.htm?id=" + id, function(json) {
    	if(sourceId){
    		json.sourceId = sourceId;
    	}
    	renderScriptPreview(json);
    });
    $('#scriptPreviewModal').dialog('open');
}

function renderScriptPreview(json) {
	$("#scriptSource").empty();
	$("#scriptSource").html('<p>http://'+json.serverAddress+'/server/cpagenie.js?campaignId='+ json.campaignId +'&sourceId='+ json.sourceId+'&containerId='+json.containerId+'</p>');
}

/*** Script Preview End **/

/**
 * 
 * Form Preview Start
 */

function initFormPreview(){
    $('.formPreviewLink').click(function(e) {
        e.preventDefault();
        $('#removeIds').val('');
        var id = $(this).attr('href');
        showFormPreview(id);
    });

    $("#formPreviewModal").dialog({
        autoOpen: false,
        height: 550,
        width: 750,
        modal: true,
        close: function() {
           
        }
    });
}

function showFormPreview(id) {
    $.getJSON("formPreview.htm?id=" + id, function(json) {
    	renderFormPreview(json);
    });
    $('#formPreviewModal').dialog('open');
}

function renderFormPreview(json) {
	$("#cpaginiecampaign").empty();
	$("#cpaginiecampaign").html('<form method="POST" action="update4.htm" id="previewCampaignModalForm" class="containerform">'+
	           '<table id="previewcampaignFieldsTable" class="containertable"> <tbody></tbody></table>'+
      '</form>');
    $('#previewcampaignFieldsTable >tbody').empty();
    for (var fieldPosition = 0; fieldPosition < json.fields.length; fieldPosition++) {
        var field = json.fields[fieldPosition];
        ($('#previewcampaignFieldsTable >tbody')).append(createFormFields(fieldPosition, field, json)).hide().pulse(500);
    }
}

function createFormFields(fieldPosition, field, json) {
    var fieldDescriptionTd = '<td>' +
    		'<label class="cpafieldlabel"> '+ field.description+ ' </label>'+
            '</td>';

    var fieldParameterTd = '<td>' +
            '<input class="cpafieldtext" type="text" value="" name="' + field.parameter + '" id="' + field.parameter + '">' +
            '</td>';

    return '<tr>' + fieldDescriptionTd + fieldParameterTd+ '</tr>';
}

/*** Form Preview End **/
