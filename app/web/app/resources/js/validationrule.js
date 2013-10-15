$(document).ready(function() {
    $('#validationRuleListTable').dataTable({
        "iDisplayLength": 10,
        "aLengthMenu": [
            [10, 20, 50],
            [10, 20, 50]
        ] ,
        "aoColumnDefs": [
            {   "bSortable": false,
                "aTargets": [3]
            }
        ]
    });

    $('a[name=validationRuleModalOpenLink]').click(function(e) {
        e.preventDefault();
        var id = $(this).attr('href');
        showValidationRuleModalDiv(id);
    });

    $("#validationRuleModalDiv").dialog({
        autoOpen: false,
        height: 300,
        width: 350,
        modal: true,
        close: function() {
            $('#errorMessages').text('').removeClass('ui-state-highlight');
            $(".ui-state-error").val('').removeClass('ui-state-error');
        }
    });

    $('#validationRuleModalForm').ajaxForm({
        type: 'POST',
        success:function(json) {
            if (json.status == 'success') {
                window.location.replace("list.htm");
            } else {
                updateErrorMessages($('#errorMessages'), json);
                renderValidationRuleModalDiv(json);
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

function showValidationRuleModalDiv(id) {
    $.getJSON("update.htm?id=" + id, function(json) {
        renderValidationRuleModalDiv(json);
        $('#validationRuleModalDiv').dialog('open');
    });
}

function renderValidationRuleModalDiv(json) {
    $('#profane').val(json.validationRule.profane);
    $('#description').val(json.validationRule.description);
}
