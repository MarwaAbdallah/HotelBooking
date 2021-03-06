<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ taglib prefix ="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="https://getbootstrap.com/docs/4.0/assets/img/favicons/favicon.ico">

    <title>Hotel Booking</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.0/examples/floating-labels/">

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this img -->
    <link href="css/floating-labels.css" rel="stylesheet">
  </head>

  <body>
	<div class="container w-50 p-3 ">
		<form class="form-signin" method="Post" action="admin">
			<input type="hidden" name="command" value="EDIT_USER">
			<div class="text-center mb-4">
				<h1 class="h3 mb-3 font-weight-normal">Edit User</h1>
				<div>
					<%
					String message = (String) request.getAttribute("message");
					if (message != null) {
				%>
					<p style="color: red">
						<i><%=message%></i>
					</p>
					<%}%>
				</div>
			</div>

			<div class="mb-3">
				<label for="email">Email </label> <input type="email"
					class="form-control" id="email" name="email"
					VALUE="${USER.email}">
				<div class="invalid-feedback">Please enter a valid User email
					address.</div>
			</div>

	<div class="mb-3">
		<label for="address">Enabled</label> 
		<select class="form-control "
			name="enabled" id="enabled">
			<option selected>${USER.enabled}</option>
			<option value="enabled">true</option>
			<option value="disabled">false</option>
		</select>
	</div>

	<div class="checkbox mb-3">
		<label for="address">Role</label> 
		<select class="form-control " name="role" id="role">
			<option selected>${USER.role}</option>
			<c:forEach var="r" items="${LIST_ROLES}">
				<option name="userRole" value="${r.role}">${r.role}</option>
			</c:forEach>
		</select>

	</div>

	<button class="btn btn-lg btn-primary btn-block" type="submit">Edit User</button>
	</form>
	</div>