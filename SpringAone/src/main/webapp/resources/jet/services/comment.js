'use strict';

function commentService() {
	return {
		addComment : function(itemId, payload, successHandler) {
			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'POST',
				url : baseUrl + '/comment/' + itemId,
				dataType : 'json',
				data : payload,
				success : successHandler,
				error : function() {
					alert('Error posting userpass');
				}
			});
		},
		findComments : function(itemId) {
			return $.getJSON(baseUrl + "comment/" + itemId);
		},
		deleteComment : function(itemId, id, successHandler) {
			$.ajax({
				type : 'DELETE',
				url : baseUrl + '/comment/' + itemId,
				dataType : 'json',
				data : {
					taskId : id
				},
				success : successHandler,
				error : function() {
					alert('Error deleteing comment');
				}
			});
		}
	};
}

app.factory('Comment', function($resource) {

	return $resource(baseUrl + '/comment/:itemId', {}, {
		'addComment' : function() {
			method: 'POST'
		},
		'findComments' : {
			method : 'GET',
			isArray : true
		},
		'deleteComment' : {
			method : 'DELETE',
			params : {
				taskId : '@id'
			}
		}

	});

});