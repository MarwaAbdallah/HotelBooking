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

    <title>Sign In Page</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.0/examples/floating-labels/">

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this img -->
    <link href="css/floating-labels.css" rel="stylesheet">
  </head>

  <body>
  <div class="container w-50 p-3 ">
    <form class="form-signin" method="Post" action="admin">
    <input type="hidden" name="command" value="ADD_USER">
      <div class="text-center mb-4">
        <h1 class="h3 mb-3 font-weight-normal">Add User</h1>
			<div>
				<%
					String message = (String) request.getAttribute("message");
					if (message != null) {
				%>
				<p style="color: red"><i><%=message%></i></p>
				<%}%>
			</div>
      </div>

        <div class="mb-3">
          <label for="email">Email </label>
          <input type="email" class="form-control" id="email" name="email" placeholder="you@example.com">
          <div class="invalid-feedback">
            Please enter a valid User email address.
          </div>
        </div>

        <div class="mb-3">
          <label for="address">Password</label>
          <input type="text" class="form-control" id="password" name="password" placeholder="1234 Main St" required>
          <div class="invalid-feedback">
            Please enter User Password.
          </div>
        </div>

			<div class="checkbox mb-3">
				 <select class="custom-select mr-sm-2" id="inlineFormCustomSelect" name="role">
				        <c:forEach var="r" items="${LIST_ROLES}">
				            <option name="userRole" value="${r.role}">${r.role}</option>
				          </c:forEach>
				      </select>

			</div>

			<button class="btn btn-lg btn-primary btn-block" type="submit">Add User</button>
			<p class="mt-5 mb-3 text-muted text-center">© 2017-2018</p>
		</form>
  </div>

</body></html>