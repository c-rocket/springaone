'use strict';

app.factory('Auth', [ '$resource', function($resource) {

	return {
		newUser : $resource(baseUrl + '/user', {}, {
			method : 'POST'
		}),

		login : $resource(baseUrl + '/login/:email/:pw', {}, {
			method : 'GET',
			isArray : false
		}),

	};
} ]);

app.factory('User', function($http) {

	var currentUser = {};

	var User = {

		getCurrentUser : function() {

			return currentUser;
		},

		registerUser : function(user) {

			return $http.post(baseUrl + '/user', user)

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

		changePassword : function(payload) {
			return $http.post(baseUrl + '/userpass', payload)
		}

	}

	return User;
});
