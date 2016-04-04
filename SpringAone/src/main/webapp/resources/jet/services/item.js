define('item', [ 'jquery' ], function($) {
	var self = this;
	self.__isAmdLoaderPresent = function() {
		return (typeof define === 'function' && define['amd']);
	};
	self.getAllItems = function() {
		alert('getting Items');
		return $.getJSON(baseUrl + "item/");
	}
	return self;
});

/*---------modified for TMO--------*/
//
// 'use strict';
//
// function itemService() {
// return {
// getItems : function() {
// var items = getAllItems();
// var selectedItem = {};
//
// var getItems = {
// all : items,
//
// createItem : function(item) {
// $.ajax({
// headers : {
// 'Accept' : 'application/json',
// 'Content-Type' : 'application/json'
// },
// type : 'POST',
// url : baseUrl + '/item/',
// dataType : 'json',
// data : item,
// success : function() {
// console.log('item api returned', item)
// items.push(item)
// console.log('http recd', item)
//
// },
// error : function() {
// alert('Error creating item');
// }
// });
// },
// editItem : function(url, payload, successHandler) {
// $.ajax({
// type : 'PUT',
// url : url,
// dataType : 'json',
// data : payload,
// success : successHandler,
// error : function() {
// alert('Error updating offer');
// }
// });
// },
// updateItemArray : function(item) {
// for (var t = 0; t < getItems.all.length; t++) {
// if (getItems.all[t].ITEM_ID == item.ITEM_ID) {
// getItems.all[t] = item
// }
// }
// },
// setSelectedItem : function(item) {
// selectedItem = item;
// console.log('selectedItem within setSelectedItem is:', selectedItem)
// },
// getSelectedItem : function() {
// console.log('selected item within getSelectedItem', selectedItem)
// return selectedItem;
// }
// }
// return getItems;
// },
// getAllItems : function() {
// alert('getting Items');
// return $.getJSON(baseUrl + "item/");
// },
// newItem : function(item, successHandler) {
// $.ajax({
// headers : {
// 'Accept' : 'application/json',
// 'Content-Type' : 'application/json'
// },
// type : 'POST',
// url : baseUrl + '/item/',
// dataType : 'json',
// data : item,
// success : successHandler,
// error : function() {
// alert('Error creating item');
// }
// });
// },
// findItem : function(itemId) {
// return $.getJSON(baseUrl + "item/" + itemId);
// },
// deleteItem : function(itemId, successId) {
// $.ajax({
// type : 'DELETE',
// url : baseUrl + '/item/' + itemId,
// dataType : 'json',
// success : successHandler,
// error : function() {
// alert('Error deleteing comment');
// }
// });
// },
// updateItem : function(itemId, payload, successHandler) {
// $.ajax({
// type : 'PUT',
// url : baseUrl + '/item/' + itemId,
// dataType : 'json',
// data : payload,
// success : successHandler,
// error : function() {
// alert('Error updating offer');
// }
// });
// }
// }
// }
