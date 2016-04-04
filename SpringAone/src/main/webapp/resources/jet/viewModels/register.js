define([ 'ojs/ojcore', 'knockout', 'jquery', 'ojs/ojknockout', 'ojs/ojinputtext', 'ojs/ojinputnumber', 'ojs/ojbutton', 'ojs/ojswitch',
		'ojs/ojpopup' ], function(oj, ko, $) {
	var baseUrl = '';

	function toaster(text) {
		vm.toastText(text)
		$('#toaster').ojPopup('open', '#form-container');
		setTimeout(function() {
			$('#toaster').ojPopup('close');
		}, 1500);
	}

	function applyConfigChanges(data, event) {
		console.log(data);
		var jsonData = JSON.stringify({
			server : data.hostname(),
			port : data.port(),
			username : data.username(),
			password : data.password(),
			sendingMessages : data.sendingMessages()
		});
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'PUT',
			url : baseUrl + '/system/config',
			dataType : 'json',
			data : jsonData,
			success : function(responseData) {
				console.log(responseData);
				getConfig();
				toaster('System Configuraiton Updated');
			},
			error : function() {
				alert('Error saving Config');
			}
		});
	}

	function mainContentViewModel() {
		var self = this;
		self.hostname = ko.observable();
		self.port = ko.observable();
		self.username = ko.observable();
		self.password = ko.observable();
		self.sendingMessages = ko.observable();
		self.toastText = ko.observable();

		self.applyChanges = applyConfigChanges;
	}
	var vm = new mainContentViewModel();

	function getConfig() {
		$.ajax({
			url : baseUrl + '/system/config'
		}).then(function(data) {
			vm.hostname(data.server);
			vm.port(data.port);
			vm.username(data.username);
			vm.password(data.password);
			vm.sendingMessages(data.sendingMessages);
		});
	}

	$(document).ready(function() {
		baseUrl = $('#baseUrl').val();
		getConfig();
	});
	return vm;
});
