/**
 * Copyright (c) 2014, 2016, Oracle and/or its affiliates.
 * The Universal Permissive License (UPL), Version 1.0
 */
/**
 * Main content module
 */
define([ 'ojs/ojcore', 'knockout', 'jquery', 'ojs/ojknockout', 'ojs/ojmasonrylayout', 'ojs/ojbutton', 'ojs/ojswitch', 'ojs/ojchart',
		'ojs/ojlistview', 'ojs/ojdialog', 'ojs/ojinputtext', 'ojs/ojselectcombobox', 'ojs/ojarraytabledatasource', 'ojs/ojinputtext',
		'services/item' ], function(oj, ko, $) {

	function alertClick(data, event) {
		var alertDisplay = data.name;
		$.ajax({
			type : 'PUT',
			url : baseUrl + '/device/' + vm.currentId + '/alerts/' + data.value,
			dataType : 'json',
			success : function(jsonData) {
				toaster('Alert ' + alertDisplay + ' sent');
			},
			error : function() {
				alert('Error sending alert: ' + data.value);
			}
		});
		return true;
	}

	function toaster(text) {
		vm.toastText(text)
		$('#toaster').ojPopup('open', '#device-container');
		setTimeout(function() {
			$('#toaster').ojPopup('close');
		}, 1500);
	}

	function toggleEvent(data, event) {
		$.ajax({
			type : 'PUT',
			url : baseUrl + '/device/' + vm.currentId + '/events/' + data.value,
			dataType : 'json',
			success : function(jsonData) {
			},
			error : function() {
				alert('Error sending event: ' + data.value);
			}
		});
	}

	function deleteClick(data) {
		var id = data.currentId;
		vm.currentId = null;
		vm.title('Select/Create a Device');
		vm.id(null);
		vm.type(null);
		vm.image(null);

		vm.metrics([]);
		vm.alerts([]);
		vm.events([]);

		vm.lineSeriesValue([]);
		vm.lineGroupsValue([]);
		$("#deviceLayout").ojMasonryLayout("refresh");
		$.ajax({
			type : 'DELETE',
			url : baseUrl + '/device/' + id,
			dataType : 'json',
			success : function(jsonData) {
				loadDevices(vm.devicesDatasource);
			},
			error : function() {
				alert('Error deleteing DeviceID=' + id);
			}
		});

	}

	function deviceClick(data, event) {
		loadDevice(data.name);
	}

	function loadDevice(id) {
		$.getJSON(baseUrl + '/device/' + id).then(function(device) {
			console.log(device);
			vm.id(device.id);
			vm.type(device.resource);
			vm.title(device.resource + ': ' + device.id)
			vm.image('data:image/jpeg;base64,' + device.picture);
			vm.metrics.removeAll();
			var colorIndex = 0;
			$.each(device.metrics, function(key, value) {
				vm.metrics.push({
					name : key,
					value : value,
					sizeClass : 'oj-masonrylayout-tile-2x1 tile tile' + colorIndex
				});
				colorIndex = (colorIndex + 1) % 7;
			});
			vm.alerts.removeAll();
			$.each(device.alerts, function(key, value) {
				vm.alerts.push({
					name : value,
					value : key,
					buttonClick : alertClick
				});
			});
			vm.events.removeAll();
			$.each(device.events, function(key, value) {
				vm.events.push({
					name : value.display,
					value : key,
					toggleEvent : toggleEvent,
					switchValue : ko.observable(value.value)
				});
			});

			vm.lineSeriesValue.removeAll();
			for (var i = 0; i < device.chartSeries.length; i++) {
				vm.lineSeriesValue.push({
					name : device.chartSeries[i],
					items : device.chartValues[i]
				});
			}
			vm.lineGroupsValue.removeAll();
			$.each(device.chartLabels, function(key, value) {
				vm.lineGroupsValue.push(value);
			});
			vm.currentId = device.id;
			$("#metricsLayout").ojMasonryLayout("refresh");
			$("#eventsLayout").ojMasonryLayout("refresh");
		});
	}

	function updateDevice(id) {
		$.getJSON(baseUrl + '/device/' + id).then(function(device) {
			vm.metrics.removeAll();
			var colorIndex = 0;
			$.each(device.metrics, function(key, value) {
				vm.metrics.push({
					name : key,
					value : value,
					sizeClass : 'oj-masonrylayout-tile-2x1 tile tile' + colorIndex
				});
				colorIndex = (colorIndex + 1) % 7;
			});
			vm.lineSeriesValue.removeAll();
			for (var i = 0; i < device.chartSeries.length; i++) {
				vm.lineSeriesValue.push({
					name : device.chartSeries[i],
					items : device.chartValues[i]
				});
			}
			vm.currentId = device.id;
			$("#metricsLayout").ojMasonryLayout("refresh");
		});
	}

	function createClick(data, event) {
		vm.idInput(null);
		vm.secretInput(null);
		var devices = getDeviceTypes();
		$("#modalDialog1").ojDialog("open");
	}

	function createModalClose(data, event) {
		var id = data.idInput();
		var data = JSON.stringify({
			id : id,
			secret : data.secretInput(),
			type : data.typeSelect()[0]
		});
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : 'POST',
			url : baseUrl + '/device/',
			dataType : 'json',
			data : data,
			success : function(jsonData) {
				loadDevices(vm.devicesDatasource);
				loadDevice(id);
			},
			error : function() {
				alert('Error creating device DeviceID=' + data.idInput());
			}
		});
		$("#modalDialog1").ojDialog("close");
	}

	function cancelModalClose(data, event) {
		$("#modalDialog1").ojDialog("close");
	}
	function loadItems(datasource) {
		datasource.reset();
		$.getJSON(baseUrl + "item/").then(function(items) {
			datasource.add(items);
			vm.originalList = items;
		});
	}

	function getDeviceTypes() {
		vm.deviceTypes.removeAll();
		$.ajax({
			url : baseUrl + '/device/types'
		}).then(function(data) {
			$.each(data, function(index, value) {
				if (value.enabled != null)
					if (index == 0) {
						vm.typeSelect([ value.name ]);
					}
				vm.deviceTypes.push({
					value : value.name,
					label : value.display
				})
			});
		});
	}

	function viewModel() {
		var self = this;

		self.filterText = ko.observable();
		self.itemList = new oj.ArrayTableDataSource([], {
			idAttribute : "ITEM_ID"
		});
		self.filterText.subscribe(function(newValue) {
			self.itemList.reset(filteredTitle(newValue));
		});
		self.originalList = [];
		var filteredTitle = function(titleSearch) {
			if (!titleSearch) {
				return self.originalList;
			}
			var titleContainsFilter = function(item) {
				var title = item.ITEM_TITLE.toLowerCase();
				var search = titleSearch.toLowerCase()
				return title.indexOf(search) > -1;
			};
			var result = ko.utils.arrayFilter(self.originalList, titleContainsFilter);
			return result;
		}

		self.item = ko.observable();
		/*
		 * 
		 * 
		 * 
		 * 
		 */
		self.id = ko.observable();
		self.type = ko.observable();
		self.image = ko.observable();

		self.metrics = ko.observableArray([]);
		self.alerts = ko.observableArray([]);
		self.events = ko.observableArray([]);
		self.deleteClick = deleteClick;

		self.orientationValue = 'vertical';

		/* chart data */

		self.lineSeriesValue = ko.observableArray([]);
		self.lineGroupsValue = ko.observableArray([]);

		self.devicesDatasource = new oj.ArrayTableDataSource([], {
			idAttribute : "name"
		});

		self.createClick = createClick;

		self.createModalClose = createModalClose;
		self.cancelModalClose = cancelModalClose;
		self.typeSelect = ko.observable('');
		self.idInput = ko.observable('');
		self.secretInput = ko.observable('');
		self.toastText = ko.observable('');

		self.typesource = ko.observable();
		self.deviceTypes = ko.observableArray([]);
	}

	var vm = new viewModel();

	$(document).ready(function() {
		loadItems(vm.itemList);
		// console.log(item);
	});
	return vm;
});
