<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ taglib prefix ="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
  
<!DOCTYPE html>
<!-- saved from url=(0053)https://getbootstrap.com/docs/4.5/examples/jumbotron/ -->
<html lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Jekyll v4.1.1">
    <title>Jumbotron Template · Bootstrap</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.5/examples/jumbotron/">

    <!-- Bootstrap core CSS -->
<link href="./img/Jumbotron Template · Bootstrap_files/bootstrap.min.css" rel="stylesheet" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">

    <!-- Favicons -->
<link rel="apple-touch-icon" href="https://getbootstrap.com/docs/4.5/assets/img/favicons/apple-touch-icon.png" sizes="180x180">
<link rel="icon" href="https://getbootstrap.com/docs/4.5/assets/img/favicons/favicon-32x32.png" sizes="32x32" type="image/png">
<link rel="icon" href="https://getbootstrap.com/docs/4.5/assets/img/favicons/favicon-16x16.png" sizes="16x16" type="image/png">
<link rel="manifest" href="https://getbootstrap.com/docs/4.5/assets/img/favicons/manifest.json">
<link rel="mask-icon" href="https://getbootstrap.com/docs/4.5/assets/img/favicons/safari-pinned-tab.svg" color="#563d7c">
<link rel="icon" href="https://getbootstrap.com/docs/4.5/assets/img/favicons/favicon.ico">
<meta name="msapplication-config" content="/docs/4.5/assets/img/favicons/browserconfig.xml">
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

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>
    <!-- Custom styles for this template -->
    <link href="./img/Jumbotron Template · Bootstrap_files/jumbotron.css" rel="stylesheet">
  </head>
  <body>
	<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
		<span class="navbar-brand position-absolute">HotelBooker</span>
		<c:url var="SignIn" value="LoginServlet">
			<c:param name="action" value="REQUEST_SIGNIN" />
		</c:url>
		<c:url var="SignOut" value="LoginServlet">
			<c:param name="action" value="SIGNOUT" />
		</c:url>
		<c:url var="SignUp" value="RegisterServlet">
			<c:param name="action" value="REQUEST_SIGNUP" />
		</c:url>

		<div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
			<c:if test="${not empty usrPrincipal}">
			<
			<ul class="navbar-nav ml-auto">
				<li class="nav-item"><p></p></li>
				<li class="nav-item"><a class="nav-link" href="${SignOut}">Sign
						Out <c:out value='${usrPrincipal}'/></a></li>
			</ul>
			</c:if>
			<c:if test="${empty usrPrincipal}">
			<ul class="navbar-nav ml-auto">

				<li class="nav-item"><a class="nav-link" href="${SignIn}">Sign
						In</a></li>
				<li class="nav-item"><a class="nav-link" href="${SignUp}">Sign
						Up</a>
			</ul>
			</c:if>

		</div>

	</nav>


</body></html>