'use strict';

define('commentService',[ 'jquery' ], function($) {
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
});