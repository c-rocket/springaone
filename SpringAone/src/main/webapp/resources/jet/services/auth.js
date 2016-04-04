'use strict';

function authService() {
	return {
		newUser : function(data, successHandler) {
			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'POST',
				url : baseUrl + '/user/',
				dataType : 'json',
				data : data,
				success : successHandler,
				error : function() {
					alert('Error posting user');
				}
			})
		},

		login : function(email, password) {
			return $.getJSON(baseUrl + "login/" + email + "/" + password);
		},
	};
};

function userService() {
	var currentUser = {};
	return {
		getCurrentUser : function() {
			return currentUser;
		},
		registerUser : function(user, successHandler) {
			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'POST',
				url : baseUrl + '/user',
				dataType : 'json',
				data : user,
				success : successHandler,
				error : function() {
					alert('Error posting user');
				}
			})
		},
		setCurrentUser : function(user) {
			currentUser.name = user.name;
			currentUser.gravatar = user.gravatar
			currentUser.uid = user.uid
			currentUser.email = user.email
			currentUser.signedIn = user.signedIn
		},
		isSignedIn : function() {
			if (currentUser.signedIn) {
				return true
			}
			return false;
		},
		changePassword : function(payload, successHandler) {
			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'POST',
				url : baseUrl + '/userpass',
				dataType : 'json',
				data : payload,
				success : successHandler,
				error : function() {
					alert('Error posting userpass');
				}
			});
		}
	};
}
