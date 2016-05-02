define([ 'authService', 'ojs/ojcore', 'knockout', 'jquery', 'ojs/ojknockout', 'ojs/ojinputtext', 'ojs/ojinputnumber', 'ojs/ojbutton',
		'ojs/ojswitch', 'ojs/ojpopup' ], function(authService, oj, ko, $) {
	console.log('authService', authService);

	function newUserHandler(){
		$('#confirmPopup').ojPopup('open');
	}
	
	function mainContentViewModel() {
		var self = this;
		self.name = ko.observable();
		self.email = ko.observable();
		self.password = ko.observable();

		self.register = function(data, event) {
			console.log('data', data, 'event', event);
			if (data.name.length == 0) {
				authService.registerUser(self.name(), self.email(), self.password(), newUserHandler);
			}
		};
	}
	var vm = new mainContentViewModel();

	$(document).ready(function() {
	});
	return vm;
});
