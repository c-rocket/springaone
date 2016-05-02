'use strict';

define('authService', [ 'jquery' ], function($) {
	var currentUser = {};

	return {
		registerUser : function(username, email, pw, successHandler) {
			var data = JSON.stringify({
				username : username,
				email : email,
				pw : pw
			});
			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : 'POST',
				url : baseUrl + '/user/',
				dataType : 'json',
				data : data,
				success : function(user) {
					console.log('user', user);
					if (user) {
						currentUser.uid = user.USER_ID
						currentUser.name = user.USER_NAME
						currentUser.gravatar = user.USER_GRAVATAR
						currentUser.email = user.USER_EMAIL
						currentUser.signedIn = true
					} else {
						alert('Signup failed! Check email');
					}
					successHandler();
				},
				error : function() {
					alert('Error registering user');
				}
			})
		},

		login : function(email, password) {
			return $.getJSON(baseUrl + "login/" + email + "/" + password);
		},
		getCurrentUser : function() {
			return currentUser;
		},
		setCurrentUser : function(user) {
			currentUser.name = user.name;
			currentUser.gravatar = user.gravatar;
			currentUser.uid = user.uid;
			currentUser.email = user.email;
			currentUser.signedIn = user.signedIn;
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
});