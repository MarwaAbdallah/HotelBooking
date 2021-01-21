<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,java.util.List,model.Location"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HManager</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="header.jsp" %>
<div class="mt-4">
</div>
	<div class="row justify-content-md-center">
		<input type="hidden" name="command" value="LIST" />
		<div class="col-auto">
			<div class="form-group">
				<span>Location</span>
				<div class="card">
					<div class="card-body w-auto p-3">
						<p class="card-text">
						<c:if test="${cookie.containsKey('city')}">
							<c:out
								value='${cookie["city"].value}, ${cookie["country"].value}' />
						</c:if>
						<c:if test="${!cookie.containsKey('city')}">
							<c:out value="${city}, ${country}"/>
						</c:if>
						</p>
					</div>
				</div>
			</div>
		</div>
		<div class="col-auto">
			<div class="form-group form-location">
				<span>Check-In</span>
				<div class="card">
					<div class="card-body w-auto p-3">
						<p class="card-text">
							<c:if test="${cookie.containsKey('checkIn')}">
								<c:out
									value='${cookie["checkIn"].value}' />
							</c:if>
							<c:if test="${!cookie.containsKey('checkIn')}">
								<c:out value="${checkIn}" />
							</c:if>
						</p>
					</div>
				</div>
			</div>
		</div>
		<div class="col-auto">
			<div class="form-group form-location">
				<span>Check-Out</span>
				<div class="card">
					<div class="card-body w-auto p-3">
						<p class="card-text">
							<c:if test="${cookie.containsKey('checkOut')}">
								<c:out value='${cookie["checkOut"].value}' />
							</c:if>
							<c:if test="${!cookie.containsKey('checkOut')}">
								<c:out value="${checkOut}" />
							</c:if>
							
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>



	<div class="container sticky-top w-50 p-3">
		<div class="row justify-content-center">
			<div class="card-body">
				<div class="table-responsive">

					<table class="table table-bordered table-striped"
						table-stripedid="dataTable2" width="100%" cellspacing="0">
						<thead class="thead-dark">
							<tr>
								<th scope="col">Image</th>
								<th scope="col">Hotel</th>
								<th scope="col">Available Rooms</th>
								<th scope="col">Minimum Price</th>
								<!-- Needs to be tailored to add room size -->
								<th scope="col">NumStars</th>
								<th scope="col">Reserve</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="maphotel" items="${HOTELMAP}">
								<!-- Setup a link for Reservation -->
								<c:url var="ReserveLink" value="HotelControllerServlet">
									<c:param name="command" value="LIST_ROOMS" />
									<c:param name="hotelId" value="${maphotel.key.id}" />
									<!-- embedded ID -->
								</c:url>
								<tr>
									<td><img src="img/hotel_paris.jpeg" width="193"
										height="130"></td>
									<td><c:out value="${maphotel.key.name}" /></td>
									<td><c:out value="${maphotel.value.count}" /></td>
									<td><c:out value="${maphotel.value.minPrice}" /></td>
									<td><c:out value="${maphotel.key.numStars}" /></td>
									<td><a href="${ReserveLink}">Reserve</a>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>



</body>
</html>