<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<title></title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/angularjs-toaster/0.4.9/toaster.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/0.2.1/css/material.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/0.2.1/css/ripples.css" />

<link rel="stylesheet" href="<c:url value='/resources/style/main.css'/>">

</head>
<body ng-app="aOne">
	<input type="hidden" value="<c:url value='/'/>" id="baseUrl" />
	<!-- Toaster status: info, wait, error, success, warning -->
	<toaster-container toaster-options="{'time-out': 3000, 'close-button':true}"></toaster-container>
	<div ng-include="'<c:url value='/resources/views/nav.jsp'/>'"></div>

	<div class="jumbotron">
		<div ng-view=""></div>
	</div>

	<script src="https://code.jquery.com/jquery-2.1.3.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.js"></script>

	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.8/angular.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.8/angular-animate.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.8/angular-resource.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.8/angular-route.js"></script>

	<script src="https://cdn.firebase.com/js/client/2.1.0/firebase.js"></script>
	<script src="https://cdn.firebase.com/libs/angularfire/0.9.1/angularfire.min.js"></script>

	<script src="https://cdnjs.cloudflare.com/ajax/libs/angularjs-toaster/0.4.9/toaster.js"></script>

	<script src="<c:url value='/resources//lib/moment.min.js'/>"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/angular-moment/0.9.0/angular-moment.js"></script>

	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/0.2.1/js/material.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/0.2.1/js/ripples.js"></script>

	<script>
		$(document).ready(function() {
			$.material.init();
		});
	</script>

	<script type="text/javascript" src="<c:url value='/resources/app.js'/>"></script>

	<script src="<c:url value='/resources/controllers/nav.js'/>"></script>
	<script src="<c:url value='/resources/controllers/item.js'/>"></script>
	<script src="<c:url value='/resources/controllers/auth.js'/>"></script>
	<script src="<c:url value='/resources/controllers/browse.js'/>"></script>

	<script src="<c:url value='/resources/services/auth.js'/>"></script>
	<script src="<c:url value='/resources/services/item.js'/>"></script>
	<script src="<c:url value='/resources/services/comment.js'/>"></script>
	<script src="<c:url value='/resources/services/offer.js'/>"></script>


</body>
</html>
