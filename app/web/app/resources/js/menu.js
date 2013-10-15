//This is used to block and unblock UI on any AJAX calls.
$.blockUI.defaults.message = '<img src="../../resources/images/spinner3-black.gif"/>';
$.blockUI.defaults.baseZ = 2000;
$.blockUI.defaults.overlayCSS = {backgroundColor:'#fff', opacity:0};
$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);

$(document).ready(function() {

    $('#page-wrapper').corner();
    $('#boxes').corner("right bottom");

    function addMega() {
        $(this).addClass("hovering");
    }

    function removeMega() {
        $(this).removeClass("hovering");
    }

    var megaConfig = {
        interval: 200,
        sensitivity: 4,
        over: addMega,
        timeout: 200,
        out: removeMega
    };

    $("li.menuparent").hoverIntent(megaConfig)
});
