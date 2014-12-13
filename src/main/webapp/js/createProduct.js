
define(['jquery','popUpDialog'],function($,popUpDialog){

	var createProduct = function(displayArea, json){
		createProduct._parseJson(displayArea, json);
	}

	//parse product list and create mark up for each product
	createProduct._parseJson = function(element,json){

		var html = "";
		var row = Math.floor(json.length/3);
		var rem = json.length%3;

		var $element = $(element);
		$element.css('background', 'white');
		$element.append("<h1 classs='text-center'><u><i>Search Result</i></u></h1>");
		$element.append("<br><br>");
		$element.append(html);

		for(var i = 0; i < row; i++){
			html = 	'<div class="row">' +
			createProduct._createProductMarkUp(json[i*3]) + 
			createProduct._createProductMarkUp(json[i*3+1]) +
			createProduct._createProductMarkUp(json[i*3+2]) +
			'</div>';

			$element.append(html);
			$element.append('<div class="row"><div class="col-md-12 col-sm-12"><img src="../img/divider.png" /><br></div>');
		}

		if(rem > 0){
			html = 	'<div class="row">';
			for(var i = 0; i < rem; i++){
				html += createProduct._createProductMarkUp(json[row*3+i]);
			}
			html += '</div>';
			$element.append(html);	
		}

		// button on click view individual phone
 		$(".ViewProduct").click(function(event) {
 			event.preventDefault();

			var url = '/webapi/device/' + $(this).attr('id') + '/summaries';

 			popUpDialog(url);
 		});
 		$element.append('<div class="row"><div class="col-md-12 col-sm-12"><img src="../img/divider.png" /><br></div>');
 	}

	//turn json into HTML markup (individual product)
	// id of button represents ID in the database
	createProduct._createProductMarkUp = function(json){
		var html = 	'<div class="col-md-4 text-center phone">'+
		'<h5>' + json.name + '</h5>' + 
		'<img src="'+json.imgUrl+'" class="img-responsive center-block" /><br>'+
		'<button class="btn-custom ViewProduct" id="'+json.id+'">View Product</button>'+
		'</div>';

		return html;
	}

	var _createSpinner = function(){
		
		return {
		  lines: 9, // The number of lines to draw
		  length: 16, // The length of each line
		  width: 11, // The line thickness
		  radius: 32, // The radius of the inner circle
		  corners: 0.8, // Corner roundness (0..1)
		  rotate: 0, // The rotation offset
		  direction: 1, // 1: clockwise, -1: counterclockwise
		  color: '#000', // #rgb or #rrggbb or array of colors
		  speed: 1.8, // Rounds per second
		  trail: 41, // Afterglow percentage
		  shadow: false, // Whether to render a shadow
		  hwaccel: false, // Whether to use hardware acceleration
		  className: 'spinner', // The CSS class to assign to the spinner
		  zIndex: 2e9, // The z-index (defaults to 2000000000)
		  top: '50%', // Top position relative to parent
		  left: '50%' // Left position relative to parent
		};
	}

	return createProduct;
});