<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<!-- saved from url=(0053)https://getbootstrap.com/docs/4.5/examples/jumbotron/ -->
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author"
	content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
<meta name="generator" content="Jekyll v4.1.1">
<title>Hotel Booking</title>

<link rel="canonical"
	href="https://getbootstrap.com/docs/4.5/examples/jumbotron/">

<!-- Bootstrap core CSS -->
<link href="/home/marwa/Desktop/bootstrap.min.css">

<!-- Favicons -->
<link rel="apple-touch-icon"
	href="https://getbootstrap.com/docs/4.5/assets/img/favicons/apple-touch-icon.png"
	sizes="180x180">
<link rel="icon"
	href="https://getbootstrap.com/docs/4.5/assets/img/favicons/favicon-32x32.png"
	sizes="32x32" type="image/png">
<link rel="icon"
	href="https://getbootstrap.com/docs/4.5/assets/img/favicons/favicon-16x16.png"
	sizes="16x16" type="image/png">
<link rel="manifest"
	href="https://getbootstrap.com/docs/4.5/assets/img/favicons/manifest.json">
<link rel="mask-icon"
	href="https://getbootstrap.com/docs/4.5/assets/img/favicons/safari-pinned-tab.svg"
	color="#563d7c">
<link rel="icon"
	href="https://getbootstrap.com/docs/4.5/assets/img/favicons/favicon.ico">
<meta name="msapplication-config"
	content="/docs/4.5/assets/img/favicons/browserconfig.xml">
<meta name="theme-color" content="#563d7c">


<style>
.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}
</style>
<!-- Custom styles for this template -->
<link href="/home/marwa/Desktop/jumbotron.css">
</head>
<body>
	<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
		<c:url var="UserHome" value="user">
		</c:url>
		<c:url var="SignIn" value="AuthenticationServlet">
			<c:param name="action" value="REQUEST_SIGNIN" />
		</c:url>
		<c:url var="SignOut" value="AuthenticationServlet">
			<c:param name="action" value="SIGNOUT" />
		</c:url>
		<c:url var="SignUp" value="RegisterServlet">
			<c:param name="action" value="REQUEST_SIGNUP" />
		</c:url>
		<c:url var="Home" value="HotelManagementSystem">

		</c:url>

		<ul class="navbar-nav ml-auto">
			<li class="nav-item"><a class="nav-link" href="${Home}">HotelBooker</a></li>
		</ul>

		<div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
			<c:if test="${not empty user}">

				<ul class="navbar-nav ml-auto">
					<li class="nav-item"><a class="nav-link" href="${UserHome}">Home</a></li>
					<li class="nav-item"><a class="nav-link" href="${SignOut}">Sign
							Out <c:out value='${user}' />
					</a></li>
				</ul>
			</c:if>
			<c:if test="${empty user}">
				<ul class="navbar-nav ml-auto">

					<li class="nav-item"><a class="nav-link" href="${SignIn}">Sign
							In</a></li>
					<li class="nav-item"><a class="nav-link" href="${SignUp}">Sign
							Up</a>
				</ul>
			</c:if>

		</div>

	</nav>


</body>
</html>