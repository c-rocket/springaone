/**
 * Copyright (c) 2014, 2016, Oracle and/or its affiliates.
 * The Universal Permissive License (UPL), Version 1.0
 */
/**
 * Browse content module
 */
define([ 'moment','itemService', 'offerService', 'commentService', 'ojs/ojcore', 'knockout', 'jquery', 'ojs/ojknockout', 'ojs/ojmasonrylayout', 'ojs/ojbutton', 'ojs/ojswitch', 'ojs/ojchart',
		'ojs/ojlistview', 'ojs/ojdialog', 'ojs/ojinputtext', 'ojs/ojselectcombobox', 'ojs/ojarraytabledatasource', 'ojs/ojinputtext',
		'ojs/ojmenu' ], function(moment, itemService, offerService, commentService, oj, ko, $) {

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
	
	function acceptOffer(data,event){
		console.log("data",data,"event",event);
	}
	
	function cancelOffer(data,event){
		console.log("data",data,"event",event);
	}

	function loadItem(data, event) {
		console.log("signedIn", vm.signedIn(), "isItemPoster", vm.isItemPoster(),"isAvailable",vm.isAvailable());
		itemService.findItem(data.ITEM_ID).then(function(item){
			//load item information
			vm.ITEM_ID(item.ITEM_ID);
			vm.ITEM_TITLE(item.ITEM_TITLE);
			vm.USER_GRAVATAR(item.USER_GRAVATAR);
			vm.ITEM_POST_DATE(moment(item.ITEM_POST_DATE).fromNow());
			vm.USER_NAME(item.USER_NAME);
			vm.ITEM_STATUS(item.ITEM_STATUS);
			vm.ITEM_PRICE(item.ITEM_PRICE);
			vm.ITEM_DESC(item.ITEM_DESC);
			
			vm.isItemPoster(item.ITEM_POSTED_BY == vm.uid);
			vm.isSold(item.ITEM_STATUS == 'sold');
			
			vm.offerList.reset();
			offerService.findOffers(item.ITEM_ID).then(function(offers){
				$.each(offers, function(key, value) {
					if (value.USER_NAME == vm.currentUser) {
						vm.alreadyOffered(true);
						value.alreadyOffered(true);
					}
					value.isAvailable = item.ITEM_STATUS == 'available';
					value.isItemPoster = item.ITEM_POSTED_BY == vm.uid;
					value.isSold = item.ITEM_STATUS == 'sold';
					value.acceptOffer = acceptOffer;
					value.cancelOffer = cancelOffer;
					value.isOfferMaker = value.OFFER_ID == vm.iud;
				});
				vm.offerList.add(offers);
			});
			vm.commentList.reset();
			commentService.findComments(item.ITEM_ID).then(function(comments){
				console.log('comments',comments);
				$.each(comments, function(key, value) {
					value.COMMENT_CREATE_DATE = moment(value.COMMENT_CREATE_DATE).fromNow();
				});
				vm.commentList.add(comments);
			});
			
			vm.isAvailable(item.ITEM_STATUS == 'available');
			if (item.ITEM_POSTED_BY == vm.uid) {
				vm.isItemPoster(true)
			}else{
				vm.isItemPoster(false)
			}
		});
	}

	function loadItems(datasource) {
		datasource.reset();
		itemService.getAllItems().then(function(items) {
			$.each(items, function(key, value) {
				value.loadItem = loadItem;
				value.ITEM_POST_DATE = moment(value.ITEM_POST_DATE).fromNow();
			});
			datasource.add(items);
			vm.originalList = items;
		});
	}

	// Match based on text contained within title
	function filteredTitle(titleSearch, originalList) {
		if (!titleSearch) {
			return originalList;
		}
		var titleContainsFilter = function(item) {
			// ignore case by using all lowercase
			var title = item.ITEM_TITLE.toLowerCase();
			var search = titleSearch.toLowerCase()
			return title.indexOf(search) > -1;
		};
		return ko.utils.arrayFilter(originalList, titleContainsFilter);
	}

	function viewModel() {
		var self = this;
		// Create Observable text field for filter
		self.filterText = ko.observable();
		// Create observable data source for list view
		self.itemList = new oj.ArrayTableDataSource([], {
			idAttribute : "ITEM_ID"
		});
		// Apply custom filter to list view, we also want to have the original
		// list in the model to restore from
		self.originalList = [];
		self.filterText.subscribe(function(newValue) {
			self.itemList.reset(filteredTitle(newValue, originalList));
		});

		self.baseUrl = baseUrl;

		// setup observable authentication and default to false
		self.signedIn = ko.observable(false);
		self.isItemPoster = ko.observable(false);
		self.isAvailable = ko.observable(false);
		self.alreadyOffered = ko.observable(false);
		
		//create userId
		self.uid = ko.observable();

		// Item Admin functions
		self.editItem = function(data, event) {
			console.log("data", data);
		}
		
		self.cancelItem = function(data, event) {
			console.log("data", data);
		}

		// Offer Modal
		self.makeOffer = function(data, event) {
			console.log("data", data);
		}

		// setup form for submitting comments
		self.commentText = ko.observable(false);
		self.createComment = function(data, event) {
			console.log("data", data);
		}
		self.isSold = ko.observable();
		
		//setup item components
		self.offerList = new oj.ArrayTableDataSource([], {
			idAttribute : "OFFER_ID"
		});
		self.commentList = new oj.ArrayTableDataSource([], {
			idAttribute : "COMMENT_ID"
		});
		self.ITEM_ID = ko.observable();
		self.ITEM_TITLE = ko.observable();
		self.USER_GRAVATAR = ko.observable();
		self.ITEM_POST_DATE = ko.observable();
		self.USER_NAME = ko.observable();
		self.ITEM_STATUS = ko.observable();
		self.ITEM_DESC = ko.observable();
		self.ITEM_PRICE = ko.observable();
	}

	var vm = new viewModel();

	// Use JQuery on ready to define execution points when the view loads
	$(document).ready(function() {
		// when the page loads, pull down the item list
		loadItems(vm.itemList);
	});

	// define must return the view to bind on screen
	return vm;
});
