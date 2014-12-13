/**
	JS developed by Lan Nguyen
	**/

	define(['jquery','createProduct'],function($,createProduct){

		var initializeControl = function(options){

			var $form = $(options.form);
			var $inputField = $(options.input);
			var displayArea = options.displayArea
			$form.submit(function(event) {
				event.preventDefault();

				$.ajax({
					url: initializeControl.constructUrl($inputField.val()),
					type: 'GET'
				})
				.done(function(json) {
					createProduct(displayArea,json);
				})
				.fail(function() {
					console.log("error");
				});
				
			});
		}

		initializeControl.constructUrl = function(searchTerm){
			return 'http://localhost:8080/webapi/';
			//return "someurl" + searchTerm;
		}

		return initializeControl;
	});