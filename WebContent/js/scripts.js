function getCards(){
	var output;
	$.ajax({url:"./ws/card/getCards", success:function(cards){
		output = cards;
	}, async:false});
	updateJsonOutput();
	return output;
}

function updateJsonOutput(){
	$.ajax({url:"./ws/card/getCards", success:function(cards){
		$("#jsonOutput").val(cards);
	}, dataType:"text" });
}

function renderCards(){
	cards = getCards();
	console.log(cards)
	output = "";
	
	for(i = 0; i < cards.length; i++){
		output += HTMLCard(cards[i]);
		output += "<div class=\"panel-footer\">";
		output += "<p> cardId: " + cards[i].cardId;
		output += "<button type=\"button\" class=\"btn btn-danger btn-apagar\" cardId=\"" + cards[i].cardId + "\"><span class=\"glyphicon glyphicon-remove\"></span></button>";
		output += "<button type=\"button\" class=\"btn btn-warning btn-editar\" cardId=\"" + cards[i].cardId + "\"><span class=\"glyphicon glyphicon-edit\"></span></button>";
		output += "</p></div>"
		output += "</div><div style=\"padding-right:10px\"/>";
	}
	
	$("#cards").html(output);
	$(".card-title").quickfit({
			max : 30,
			width: 183
		}) 
		$(".card-label").quickfit({
			max : 25,
			min : 10
		})
	
}

function HTMLCard(card){
	var output = "";
	output += "<div class=\"panel panel-primary card-panel\">";
	output += "<div class=\"panel-heading card-panel-heading\">";
		output += "<div class=\"panel-title\"><h2 class=\"card-title\">" + card.cardWord + "</h2></div>";
	output += "</div>";
	
	output += "<div class=\"panel-body card-body\">";
	for(j = 0; j < card.censoredWords.length; j++){
		output += "<h3 class=\"card-censuredWord\"><span class=\"label label-danger card-label\"><p>" + card.censoredWords[j] + "</p></span></h3>";
	}
	output += "</div>";
	
	return output
}
$(document).ready(function(){
	$("#cards").on('click',".btn-apagar",function(e){
		var cardId = $(this).attr("cardId"); 
		$.ajax({url:"./Controller", type:"POST", data:{cmd:"apagar", cardId:cardId}, success: function(data){
		}, async:false});
		renderCards();
	});
	
	$("#cards").on('click',".btn-editar",function(e){
		var cardId = $(this).attr("cardId"); 
		
		var output;
		$.ajax({url:"./ws/card/getCard/" + cardId, success:function(card){
			output = card;
		}, async:false});
		$("#editCardId").val(output.cardId);
		$("#editCardWord").val(output.cardWord);
		for(i = 0; i < output.censoredWords.length; i++){
			$("#editCensuredWord"+i).val(output.censoredWords[i]);
		}
		
		$('#mainTabs a[href="#edit"]').tab('show');
		
	});	
});
