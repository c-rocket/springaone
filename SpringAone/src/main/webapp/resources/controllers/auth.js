'use strict'

app.controller('AuthController', function($q, $scope, $rootScope, $location, toaster, User, Auth) {

	$scope.login = function(user) {

		Auth.login.get({
			email : user.email,
			pw : user.password
		}, function(resp) {
			if (resp.USER_ID != null) {
				user.uid = resp.USER_ID
				user.name = resp.USER_NAME
				user.gravatar = resp.USER_GRAVATAR
				user.email = resp.USER_EMAIL
				user.signedIn = true
				User.setCurrentUser(user)
				if (User.isSignedIn) {
					var currentuser = User.getCurrentUser()

					$rootScope.$broadcast('userEvent', user)
					toaster.pop('success', "Logged in!")
					$location.path(baseUrl + '/browse')
				} else {

					toaster.pop('error', "Login failed!")
					$scope.user = ''
				}
			}else {
				toaster.pop('error', "Login failed! Check Credentials")
			}
		});

	}; // ----end $scope.login
	$scope.register = function(user) {

		var newUser = {
			username : user.name,
			email : user.email,
			pw : user.password
		}
		User.registerUser(newUser).then(function(user) {
			if (user.data) {
				user.uid = user.data.USER_ID
				user.name = user.data.USER_NAME
				user.gravatar = user.data.USER_GRAVATAR
				user.email = user.data.USER_EMAIL
				user.signedIn = true
				User.setCurrentUser(user)
				if (User.isSignedIn) {
					var currentuser = User.getCurrentUser()
					$rootScope.$broadcast('userEvent', user)
					toaster.pop('success', "Registered!")
					$location.path(baseUrl + '/browse')
				} else {
					toaster.pop('error', "Signup failed!")
					$scope.user = ''
				}
			}
		});

	} // ------end $scope.register

	$scope.changePassword = function(user) {
		var payload = {
			oldpw : user.oldPass,
			newpw : user.newPass,
			email : user.email
		}
		User.changePassword(payload).success(function(status) {
			if (status == 401) {
				toaster.pop('failed', 'Password change failed! Retry')
			}else{
				toaster.pop('success', 'Password changed!')
				$scope.user = '';
			}
		})

	}

});
