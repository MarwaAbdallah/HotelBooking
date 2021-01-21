<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,java.util.List,model.Location"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<!-- saved from url=(0053)https://getbootstrap.com/docs/4.0/examples/dashboard/ -->
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon"
	href="https://getbootstrap.com/docs/4.0/assets/img/favicons/favicon.ico">

<title>Dashboard Template for Bootstrap</title>

<link rel="canonical"
	href="https://getbootstrap.com/docs/4.0/examples/dashboard/">

<!-- Bootstrap core CSS -->
<link
	href="css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="css/dashboard.css"
	rel="stylesheet">
<style type="text/css">/* Chart.js */
@
-webkit-keyframes chartjs-render-animation {
	from {opacity: 0.99
}

to {
	opacity: 1
}

}
@
keyframes chartjs-render-animation {
	from {opacity: 0.99
}

to {
	opacity: 1
}

}
.chartjs-render-monitor {
	-webkit-animation: chartjs-render-animation 0.001s;
	animation: chartjs-render-animation 0.001s;
}
</style>
</head>

<body>
<%@ include file="header.jsp" %>

  <div class="jumbotron">
    <div class="container">

      </div>
  </div>

	<div class="container-fluid">
		<div class="row">
			<nav class="col-md-2 d-none d-md-block bg-light sidebar">
				<div class="sidebar-sticky">
					<c:url var="GetHotelsMgmtAdminPage" value="admin">
						<c:param name="command" value="GET_HOTEL_MGMT_ADMIN" />
					</c:url>
					<c:url var="GetUsrMgmtAdminPage" value="admin">
						<c:param name="command" value="GET_USER_MGMT_ADMIN" />
					</c:url>
					<c:url var="GetAdminHomePage" value="admin">
						<c:param name="command" value="GET_HOME" />
					</c:url>
					<ul class="nav flex-column">
						<li class="nav-item"><a class="nav-link active"
							href="${GetAdminHomePage}">
								<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
									viewBox="0 0 24 24" fill="none" stroke="currentColor"
									stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
									class="feather feather-home">
									<path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path>
									<polyline points="9 22 9 12 15 12 15 22"></polyline></svg> Dashboard
								<span class="sr-only">(current)</span>
						</a></li>
              <li class="nav-item">
                <a class="nav-link" href="${GetUsrMgmtAdminPage}">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-users"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path><circle cx="9" cy="7" r="4"></circle><path d="M23 21v-2a4 4 0 0 0-3-3.87"></path><path d="M16 3.13a4 4 0 0 1 0 7.75"></path></svg>
                  Users
                </a>
              </li>
						<li class="nav-item"><a class="nav-link" href="${GetHotelsMgmtAdminPage}"><svg
								xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor"
								stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-layers">
									<polygon points="12 2 2 7 12 12 22 7 12 2"></polygon>
									<polyline points="2 17 12 22 22 17"></polyline>
									<polyline points="2 12 12 17 22 12"></polyline></svg> Hotels 
						</a></li>
					</ul>
				</div>
			</nav>

			<main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
				<div class="chartjs-size-monitor"
					style="position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;">
					<div class="chartjs-size-monitor-expand"
						style="position: absolute; left: 0; top: 0; right: 0; bottom: 0; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;">
						<div
							style="position: absolute; width: 1000000px; height: 1000000px; left: 0; top: 0"></div>
					</div>
					<div class="chartjs-size-monitor-shrink"
						style="position: absolute; left: 0; top: 0; right: 0; bottom: 0; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;">
						<div
							style="position: absolute; width: 200%; height: 200%; left: 0; top: 0"></div>
					</div>
				</div>

				<h2>Manage Users</h2>
				<%
					String message = (String) request.getAttribute("message");
					if (message != null) {
				%>
				<p style="color: blue"><i><%=message%></i></p>
				<%}%>
				<c:url var="AddUserLink" value="admin">
					<c:param name="command" value="GET_ADD_USER" />
				</c:url>
				<div class="mt-3">
				<a class="btn btn-lg btn-primary" href="${AddUserLink}" role="button">Add User</a>
				</div>
				<div class="table-responsive mt-5">
					<table class="table table-striped table-sm">
						<thead>
							<tr>
								<th>Email</th>
								<th>Role</th>
								<th>Enabled</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="userMap" items="${mapUserRole}">
								<!-- Setup a link for Reservation -->
								<c:url var="EditUserLink" value="admin">
									<c:param name="command" value="GET_EDIT_USER" />
									<c:param name="email" value="${userMap.key.email}" />
									<!-- embedded ID -->
								</c:url>
								<c:url var="DeleteUserLink" value="AdminServlet">
									<c:param name="command" value="DELETE_USER" />
									<c:param name="email" value="${userMap.key.email}" />
									<!-- embedded ID -->
								</c:url>
								<tr>
									<td><c:out value="${userMap.key.email}" /></td>
									<td><c:out value="${userMap.value.roleName}" /></td>
									<td><c:out value="${userMap.key.enabled}" /></td>
									<td><a href="${EditUserLink}">Edit</a> | 
									<a href="${DeleteUserLink}">Delete</a> </td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</main>
		</div>
	</div>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="js/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
		crossorigin="anonymous"></script>
	<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery-slim.min.js"><\/script>')</script>
	<script src="js/popper.min.js"></script>
	<script
		src="js/bootstrap.min.js"></script>

	<!-- Icons -->
	<script src="js/feather.min.js"></script>
	<script>
      feather.replace()
    </script>

	<!-- Graphs -->
	<script src="js/Chart.min.js"></script>
	<script>
      var ctx = document.getElementById("myChart");
      var myChart = new Chart(ctx, {
        type: 'line',
        data: {
          labels: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
          datasets: [{
            data: [15339, 21345, 18483, 24003, 23489, 24092, 12034],
            lineTension: 0,
            backgroundColor: 'transparent',
            borderColor: '#007bff',
            borderWidth: 4,
            pointBackgroundColor: '#007bff'
          }]
        },
        options: {
          scales: {
            yAxes: [{
              ticks: {
                beginAtZero: false
              }
            }]
          },
          legend: {
            display: false,
          }
        }
      });
    </script>


</body>
</html>