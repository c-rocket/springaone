/*---------modified for TMO--------*/

'use strict';

function offerService() {
	return {
		findOffers : function(itemId) {
			return $.getJSON(baseUrl + "offer/" + itemId);
		},
		deleteOffer : function(itemId, id, successHandler) {
			$.ajax({
				type : 'DELETE',
				url : baseUrl + '/offer/' + itemId,
				dataType : 'json',
				data : {
					taskId : id
				},
				success : successHandler,
				error : function() {
					alert('Error deleteing comment');
				}
			});
		},
		updateOffer : function(url, payload, successHandler) {
			$.ajax({
				type : 'PUT',
				url : url,
				dataType : 'json',
				data : payload,
				success : successHandler,
				error : function() {
					alert('Error updating offer');
				}
			});
		}
	}
}
