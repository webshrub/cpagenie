function loadjscssfile(filename, filetype){
 if (filetype=="js"){ //if filename is a external JavaScript file
  var fileref=document.createElement('script')
  fileref.setAttribute("type","text/javascript")
  fileref.setAttribute("src", filename)
 }
 else if (filetype=="css"){ //if filename is an external CSS file
  var fileref=document.createElement("link")
  fileref.setAttribute("rel", "stylesheet")
  fileref.setAttribute("type", "text/css")
  fileref.setAttribute("href", filename)
 }
 if (typeof fileref!="undefined") {
  document.getElementsByTagName("head")[0].appendChild(fileref);
 }
}

//loadjscssfile("http://localhost:8080/server/resources/js/jquery-1.4.2.js", "js") //dynamically load and add this .js file

$(document).ready(function() {
		var jsonk = '$json$';
		renderCampaignForm(JSON.parse(jsonk));
	 
});


function renderCampaignForm( jsonk ) {
	$("#cpaginieCampaign").empty();
	$("#cpaginieCampaign").html('<form id="cpaginieform" method="GET" action="http://'+ jsonk.serveraddress+'/server/submit" id="campaignForm" class="containerform">'+
			'<input type="hidden" name="campaignId" value='+jsonk.campaign.id+'>'+
			'<input type="hidden" name="sourceId" value='+jsonk.sourceId+'>'+
	           '<table id="campaignFieldsTable" class="containertable"> <tbody></tbody></table>'+
	           '<div><input class="cpasubmitbutton" type="submit" value="Submit"></div>'+
	           '<div id="cpaerror" style="color:red;display:none">Plese Fill in the required Fields </div>'+
      '</form>');
    $('#campaignFieldsTable >tbody').empty();
    for (var fieldPosition = 0; fieldPosition < jsonk.fields.length; fieldPosition++) {
        var field = jsonk.fields[fieldPosition];
        $('#campaignFieldsTable >tbody').append(createFormFields(fieldPosition, field, jsonk));
    }
    
    $('#cpaginieform').submit(function(){
    	var error = false;
    	$('.cparequiredFieldtext').each(function(){
    		if($(this).val() == '') {
    			error = true;
    			$(this).css('background-color','pink');
    		}else {
    			$(this).css('background-color','white');
    		}
    	});
    	if(error){
    		$("#cpaerror").show();
    		return false;
    	}else {
    		$("#cpaerror").hide();
    		return true;
    	}
    });
    
    $('.cparequiredFieldtext').click(function (){
    	$(this).css('background-color','white');
    });
    
    $('.cparequiredFieldtext').keypress(function (){
    	$(this).css('background-color','white');
    });
    
    
  
    
};


function createFormFields(fieldPosition, field, jsonk) {
	var classlabelvar = field.required ? "cparequiredFieldlabel" : "cpafieldlabel";
	var classtextvar = field.required ? "cparequiredFieldtext" : "cpafieldtext";
    var fieldDescriptionTd = '<td>' +
    		'<label class="'+classlabelvar+'"> '+ field.description+ ' </label>'+
            '</td>';
    var fieldParameterTd = '<td>' +
            '<input class="'+classtextvar+'" type="text" value="" name="' + field.parameter + '" id="' + field.parameter + '">' +
            '</td>';
    return '<tr>' + fieldDescriptionTd + fieldParameterTd+ '</tr>';
};