<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<title>A-One</title>
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0" />
<meta name="description" content="A-One Craiglist Demos">
<meta name="keywords" content="Oracle Q-One Demo">
<meta name="author" content="Oracle">
<meta charset="UTF-8">
<!-- REMOVE THESE LATER -->
<meta http-equiv='cache-control' content='no-cache'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>
<!-- REMOVE THESE LATER -->
<link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon" />

<!-- This is the main css file for the default Alta theme -->
<link rel="stylesheet" href="<c:url value='/resources/css/libs/oj/v2.0.0/alta/oj-alta-min.css'/>" type="text/css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/angularjs-toaster/0.4.9/toaster.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/0.2.1/css/material.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/0.2.1/css/ripples.css" />
<link rel="stylesheet" href="<c:url value='/resources/css/aone-jet.css'/>" />
<link rel="stylesheet" href="<c:url value='/resources/style/main.css'/>">

<!-- RequireJS configuration file -->
<script type="text/javascript">
	var require = {
		urlArgs : "bust=" + new Date().getTime()
	};
</script>
<script data-main="<c:url value='/resources/jet/main'/>" src="<c:url value='/resources/jet/libs/require/require-debug.js'/>"></script>
</head>
<body>
	<input type="hidden" value="<c:url value='/'/>" name="baseUrl" id="baseUrl" />
	<div id="globalBody" class="oj-web-applayout-offcanvas-wrapper oj-offcanvas-outer-wrapper">
		<div class="oj-offcanvas-inner-wrapper">
			<div class="oj-web-applayout-scrollable-wrapper">
				<div class="oj-web-applayout-scrollable oj-web-applayout-page">
					<div class="oj-web-applayout-header" style="background-color: #03a9f4;">
						<div class="oj-web-applayout-max-width oj-flex-bar oj-sm-align-items-center">
							<div style="background-color: #03a9f4;">
								<div class="oj-flex">
									<div class="oj-xl-8 oj-lg-8 oj-sm-8 oj-flex-item oj-flex">
										<a href="<c:url value='/'/>"> <img data-bind="attr: { src: baseUrl + 'resources/images/logo2.png'}"
											style="vertical-align: middle; border: 0">
										</a>
									</div>
									<div class="oj-xl-1 oj-lg-1 oj-sm-1 oj-flex-item oj-flex">
										<span>Browse Stuff</span>
									</div>
									<div data-bind="visible: !signedIn()" class="oj-xl-1 oj-lg-1 oj-sm-1 oj-flex-item oj-flex">
										<span>Login</span>
									</div>
									<div data-bind="visible: !signedIn()" class="oj-xl-1 oj-lg-1 oj-sm-1 oj-flex-item oj-flex">
										<span>Register</span>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- This is where your main page content will be loaded -->
					<div class="oj-web-applayout-max-width oj-web-applayout-content">
						<div class="oj-flex">
							<div class="oj-xl-12 oj-lg-12 oj-sm-12 oj-flex-item oj-flex">
								<div id="device-container">
									<div class="demo-scroll-container">

										<div class="demo-grid-sizes demo-flex-display">
											<div class="oj-flex oj-flex-items-pad">
												<!-- LEFT PANEL -->
												<div class="oj-sm-3 oj-md-3 oj-lg-4 oj-xl-4 oj-flex-item">

													<div class="button-container">
														<input id="filter-text" data-bind="ojComponent: {component: 'ojInputSearch',value: filterText}" placeholder="Search" />
													</div>
													<br />
													<ul id="listview" aria-label="list using array" class="list-group"
														data-bind="ojComponent: {component: 'ojListView', data: itemList, item: {template: 'item_template'}, selectionMode: 'single'}">
													</ul>

													<script type="text/html" id="item_template">
   						<div class="list-group-item" data-bind="click:loadItem">
							<div class="row-picture">
								<img class="circle" data-bind="attr: { src: USER_GRAVATAR}">
							</div>
							<div class="row-content">
								<h4 class="list-group-item-heading" data-bind="text:ITEM_TITLE"></h4>
								<span class="list-group-item-text">
									<span data-bind="text:ITEM_POST_DATE"></span>
								</span>
							</div>
							<div class="payment">
								<div data-bind="text:' ' + ITEM_PRICE"></div>
								<span data-bind="css: {label:true,status:true,sold:ITEM_STATUS=='sold',available:ITEM_STATUS=='available',cancelled:ITEM_STATUS=='cancelled'}, text: ITEM_STATUS"></span>
							</div>
						</div>
					</script>

												</div>
												<!-- RIGHT PANEL -->
												<div class="oj-sm-3 oj-md-5 oj-lg-6 oj-xl-8 oj-flex-item">
													<div class="oj-flex-items-pad details">
														<div data-bind="visible: ITEM_ID() !== undefined" style="display:none;">
															<!-- HEADER -->
															<div class="oj-flex">
																<div class="oj-sm-12 oj-md-8 oj-flex-item">
																	<h3 data-bind="text:ITEM_TITLE"></h3>
																	<span> <img class="img-circle gravatar" data-bind="attr: { src: USER_GRAVATAR}"> Posted by <span
																		data-bind="text:USER_NAME"></span> - <span data-bind="text:ITEM_POST_DATE"></span> &nbsp; <span
																		data-bind="css:{label:true, status:true, sold:ITEM_STATUS=='sold',available:ITEM_STATUS=='available',cancelled:ITEM_STATUS=='cancelled'}, text:ITEM_STATUS"></span>
																	</span>
																</div>
																<div class="oj-sm-12 oj-md-4 oj-flex-item">
																	<div class="price-tag">
																		<span data-bind="text:ITEM_PRICE"></span>
																	</div>

																	<div data-bind="visible: signedIn() &&  isItemPoster()  &&  isAvailable()">
																		<button id="adminMenuButton" data-bind="ojComponent: {component: 'ojButton', label: 'Admin', menu: '#adminMenu'}">
																		</button>
																		<!-- To handle menu item selection, use a select listener as shown, not a click listener. -->
																		<ul id="adminMenu" style="display: none" data-bind="ojComponent: {component: 'ojMenu'}">
																			<li id="editPost"><a href="#" data-bind="click:editItem"><span
																					class="oj-menu-item-icon oj-fwk-icon oj-fwk-icon-arrow-n"></span>Edit Post</a></li>
																			<li id="divider"></li>
																			<li id="cancelPost"><a href="#" data-bind="click:cancelItem"><span
																					class="oj-menu-item-icon demo-icon-font demo-palette-icon-24"></span>Cancel Post</a></li>
																		</ul>
																	</div>

																	<div data-bind="visible: signedIn() &&  !isItemPoster()  &&  isAvailable() &&!alreadyOffered()">
																		<button id="button"
																			data-bind="click: makeOffer, 
                       								ojComponent: { component: 'ojButton', label: 'Make an Offer' }">
																		</button>
																	</div>
																</div>
															</div>
															<!-- DESCRIPTION -->
															<div class="oj-flex">
																<div class="panel oj-flex-item">
																	<div class="panel-heading">
																		<h3 class="panel-title">Description</h3>
																	</div>
																	<div class="panel-body" data-bind="text:ITEM_DESC"></div>
																</div>
															</div>
															<div class="oj-flex">
																<div class="panel oj-flex-item ">
																	<div class="panel-heading">
																		<h3 class="panel-title">Offers</h3>
																	</div>
																	<div class="panel-body">
																		<ul id="offerView" aria-label="Orrers using array" class="list-group"
																			data-bind="ojComponent: {component: 'ojListView', data: offerList, item: {template: 'offer_template'}, selectionMode: 'none'}">
																		</ul>

																		<script type="text/html" id="offer_template">
   											<div class="offer-list list-group-item">
												<button data-bind="visible: OFFER_STATUS == 'accepted', 
                       									ojComponent: { component: 'ojButton', label: 'Sold', disabled:true }">
												</button>
												<button data-bind="visible: isItemPoster && isAvailable, click:acceptOffer,ojComponent: { component: 'ojButton', label: 'Accept - ' + OFFER_AMOUNT }"</button>
												<button data-bind="visible: isOfferMaker && isAvailable, click:cancelOffer, ojComponent: { component: 'ojButton', label: 'Cancel My Offer - ' + OFFER_AMOUNT }"></button>

												<img class="img-circle comment gravatar" data-bind="attr: { src: USER_GRAVATAR}" class="img-circle offer gravatar" />
												<span data-bind="text:USER_NAME"></span>
											</div>
										</script>
																	</div>
																</div>
															</div>
															<div class="oj-flex">
																<div class="panel oj-flex-item">
																	<div class="panel-heading">
																		<h3 class="panel-title">Comments</h3>
																	</div>
																	<div class="panel-body">
																		<ul id="commentView" aria-label="Comments using array" class="comment-list list-group"
																			data-bind="ojComponent: {component: 'ojListView', data: commentList, item: {template: 'comment_template'}, selectionMode: 'none'}">
																		</ul>
																		<script type="text/html" id="comment_template">
   												<div class="list-group-item">
													<img class="img-circle comment gravatar row" data-bind="attr: { src: USER_GRAVATAR}" class="img-circle offer gravatar"><span data-bind="USER_NAME"></span>
													<div class="well well-sm cmt row">
														<span class="cmt-title" data-bind="text:USER_NAME + ':'"></span> 
														<span class="cmt-time pull-right"> 
															<span data-bind="text:COMMENT_CREATE_DATE"></span>
														</span> <br> <span data-bind="text:COMMENT_TEXT"></span>
													</div>
												</div>
											</script>
																	</div>
																	<div class="panel-body">
																		<div id="form-container" class="oj-form-layout" data-bind="visible: isAvailable() && signedIn()">
																			<div class="oj-form row">
																				<div class="input-group-addon row">
																					<img data-bind="attr: { src: USER_GRAVATAR}" class="img-circle comment gravatar">
																				</div>
																				<div class="row" style="width: 300px">
																					<textarea id="text-area" data-bind="ojComponent: {component: 'ojTextArea', value: commentText}"
																						placeholder="Comment here..."></textarea>
																				</div>
																				<span class="pull-right row">
																					<button id="createCommentButton"
																						data-bind="ojComponent: { component: 'ojButton', label: 'comment' },click:createComment"></button>
																				</span>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>
														<div class="oj-flex" data-bind="visible: ITEM_ID() === undefined">
															<img data-bind="attr: { src: baseUrl + '/resources/images/forsale.jpg'}" style="width: 100%; height: 50%; padding-top: 30px">
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
