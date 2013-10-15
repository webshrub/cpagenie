$(document).ready(function() {
    $('#advertiserListTable').dataTable({
        "iDisplayLength": 10,
        "aLengthMenu": [
            [10, 20, 50],
            [10, 20, 50]
        ],
        "aoColumnDefs": [
            {   "bSortable": false,
                "aTargets": [5]
            }
        ]
    });

    $("#advertiserModalDiv").dialog({
        autoOpen: false,
        height: 300,
        width: 350,
        modal: true,
        close: function() {
            $('#errorMessages').text('').removeClass('ui-state-highlight');
            $(".ui-state-error").val('').removeClass('ui-state-error');
        }
    });

    $('a[name=advertiserModalOpenLink]').click(function(e) {
        e.preventDefault();
        var id = $(this).attr('href');
        showAdvertiserModalDiv(id);
    });

    $('#advertiserModalForm').ajaxForm({
        type: 'POST',
        success:function(json) {
            if (json.status == 'success') {
                window.location.replace("list.htm");
            } else {
                updateErrorMessages($('#errorMessages'), json);
                renderAdvertiserModalDiv(json);
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

function showAdvertiserModalDiv(id) {
    $.getJSON("update.htm?id=" + id, function(json) {
        renderAdvertiserModalDiv(json);
        $('#advertiserModalDiv').dialog('open');
    });
}

function renderAdvertiserModalDiv(json) {
    $('#name').val(json.advertiser.name);
    $('#description').val(json.advertiser.description);
    $('#email').val(json.advertiser.email);
    var advertiserVerticalId = json.advertiser.vertical.id;
    var options = '';
    $(json.verticalList).each(function() {
        if (this.id == advertiserVerticalId) {
            options += '<option value="' + this.id + '" selected>' + this.name + '</option>';
        } else {
            options += '<option value="' + this.id + '">' + this.name + '</option>';
        }
    });
    $('#vertical').html(options);
}
