$(document).ready(function() {
    $('#userListTable').dataTable({
        "iDisplayLength": 10,
        "aLengthMenu": [
            [10, 20, 50],
            [10, 20, 50]
        ] ,
        "aoColumnDefs": [
            {   "bSortable": false,
                "aTargets": [6]
            }
        ]
    });

    $('a[name=userModalOpenLink]').click(function(e) {
        e.preventDefault();
        var id = $(this).attr('href');
        showUserModalDiv(id);
    });

    $("#userModalDiv").dialog({
        autoOpen: false,
        height: 300,
        width: 350,
        modal: true,
        close: function() {
            $('#errorMessages').text('').removeClass('ui-state-highlight');
            $(".ui-state-error").val('').removeClass('ui-state-error');
        }
    });

    $('#userModalForm').ajaxForm({
        type: 'POST',
        success:function(json) {
            if (json.status == 'success') {
                window.location.replace("list.htm");
            } else {
                updateErrorMessages($('#errorMessages'), json);
                renderUserModalDiv(json);
            }
        },
        error:function(json) {
            alert("Something bad happened!!!");
        }
    });

    $('#authorityUserSelect').change(function(e) {
        var userId = $('#authorityUserSelect option:selected').val();
        if (userId == 0) {
            $('#userAuthorityListTable').find('tbody').empty();
        } else {
            $.getJSON("getroles.htm?id=" + userId, function(json) {
                $('#userAuthorityListTable').find('tbody').empty();
                $(json.authorityList).each(function() {
                    var checked = isAuthorityChecked(this, json.userAuthoritySet);
                    $('#userAuthorityListTable').find('tbody').append(getAuthorityRow(this, checked));
                });
            });
        }
    });

    $('#advertiserUserSelect').change(function(e) {
        var userId = $('#advertiserUserSelect option:selected').val();
        if (userId == 0) {
            $('#userAdvertiserListTable').find('tbody').empty();
        } else {
            $.getJSON("getadvertisers.htm?id=" + userId, function(json) {
                $('#userAdvertiserListTable').find('tbody').empty();
                $(json.advertiserList).each(function() {
                    var checked = isAdvertiserChecked(this, json.userAdvertiserSet);
                    $('#userAdvertiserListTable').find('tbody').append(getAdvertiserRow(this, checked));
                });
            });
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

function showUserModalDiv(id) {
    $.getJSON("update.htm?id=" + id, function(json) {
        renderUserModalDiv(json);
        $('#userModalDiv').dialog('open');
    });
}

function renderUserModalDiv(json) {
    $('#username').val(json.user.username);
    $('#password').val("");
    $('#confirmPassword').val("");
    $('#email').val(json.user.email);
    $('#firstName').val(json.user.firstName);
    $('#lastName').val(json.user.lastName);
    var userStatusId = json.user.status.id;
    var options = '';
    $(json.statusList).each(function() {
        if (this.id == userStatusId) {
            options += '<option value="' + this.id + '" selected>' + this.name + '</option>';
        } else {
            options += '<option value="' + this.id + '">' + this.name + '</option>';
        }
    });
    $('#status').html(options);
}

function isAuthorityChecked(authority, userAuthoritySet) {
    var checked = false;
    $(userAuthoritySet).each(function() {
        if (this.id == authority.id) {
            checked = true;
        }
    });
    return checked;
}

function getAuthorityRow(authority, checked) {
    var checkedRow =
            '<tr>' +
                    '<td>' +
                    '<input type="checkbox" checked value="' + authority.id + '" name="authorities" id="authorities' + authority.id + '">' +
                    '<input type="hidden" value="on" name="_authorities"> ' + authority.authorityType.name + ' ' +
                    '</td>' +
                    '</tr>';
    var unCheckedRow =
            '<tr>' +
                    '<td>' +
                    '<input type="checkbox" value="' + authority.id + '" name="authorities" id="authorities' + authority.id + '">' +
                    '<input type="hidden" value="on" name="_authorities"> ' + authority.authorityType.name + ' ' +
                    '</td>' +
                    '</tr>';
    if (checked) {
        return checkedRow;
    } else {
        return unCheckedRow;
    }
}

function isAdvertiserChecked(advertiser, userAdvertiserSet) {
    var checked = false;
    $(userAdvertiserSet).each(function() {
        if (this.id == advertiser.id) {
            checked = true;
        }
    });
    return checked;
}

function getAdvertiserRow(advertiser, checked) {
    var checkedRow =
            '<tr>' +
                    '<td>' +
                    '<input type="checkbox" checked value="' + advertiser.id + '" name="advertisers" id="advertisers' + advertiser.id + '">' +
                    '<input type="hidden" value="on" name="_advertisers"> ' + advertiser.name + ' ' +
                    '</td>' +
                    '</tr>';
    var unCheckedRow =
            '<tr>' +
                    '<td>' +
                    '<input type="checkbox" value="' + advertiser.id + '" name="advertisers" id="advertisers' + advertiser.id + '">' +
                    '<input type="hidden" value="on" name="_advertisers"> ' + advertiser.name + ' ' +
                    '</td>' +
                    '</tr>';
    if (checked) {
        return checkedRow;
    } else {
        return unCheckedRow;
    }
}
