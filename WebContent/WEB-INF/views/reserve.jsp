<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ page import="java.util.ArrayList,java.util.List,model.Hotel" %>
    <%@ page import="java.util.ArrayList,java.util.List,model.Room" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ taglib prefix ="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hotel Booking</title>
<link href="css/bootstrap.min.css" rel="stylesheet" >
</head>
<body>
<%@ include file="header.jsp" %>

<div class="row justify-content-md-center">
          <input type="hidden" name="command" value = "LIST" />
            <div class="col-auto">
              <div class="form-group">
                <span>Location</span>
                	<div class="card">      
					  <div class="card-body w-auto p-3">
					    <p class="card-text"><c:out value='${cookie["city"].value}, ${cookie["country"].value}'/></p>
					  </div>
					</div>
              </div>
            </div>
            <div class="col-auto">
              <div class="form-group form-location">
                <span>Check-In</span>
                	<div class="card">      
					  <div class="card-body w-auto p-3">
					    <p class="card-text"><c:out value='${cookie["checkIn"].value}'/></p>
					  </div>
					</div>
              </div>
            </div>
            <div class="col-auto">
              <div class="form-group form-location">
                <span>Check-Out</span>
                	<div class="card">      
					  <div class="card-body w-auto p-3">
					    <p class="card-text"><c:out value='${cookie["checkOut"].value}'/></p>
					  </div>
					</div>
              </div>
            </div>
            </div>
<div class="card text-center">
  <div class="card-body w-auto p-3">
    <p class="card-text"><c:out value="Hotel ${hotel.name}"/></p>
  </div>

</div>
		    
		    
  <div class="container sticky-top w-50 p-3" >
<div class="row justify-content-center">
<div class="card-body">
 <div class="table-responsive">
							 
	<table class="table table-bordered table-striped" table-stripedid="dataTable2" width="100%" cellspacing="0">
        <thead class="thead-dark">
		    <tr>
		    
		      <th scope="col">Room Number</th>
			  <th scope="col">Price</th>
			  <th scope="col">Number of Beds</th>
			  <th scope="col">Room Capacity</th> <!-- Needs to be tailored to add room size -->
				<th scope="col">Interested? </th>
		    </tr>
		  </thead>
		  <tbody>
			  <c:forEach var="roommap" items="${ROOM_MAP}" >
			<!-- Setup a link for Reservation -->
					<c:url var="ReserveLink" value="ReservationControllerServlet">
						<c:param name="command" value = "SEND_CHECKOUT"/>
						<c:param name="hotelId" value="${hotel.id}"/> <!-- embedded ID --> 
						<c:param name="hotelName" value="${hotel.name}"/> 
						<c:param name="roomId" value="${roommap.key.id}"/>
						<c:param name="roomPrice" value="${roommap.key.price}"/>
						<c:param name="roomBedNo" value="${roommap.value.numBeds}"/>
						<c:param name="roomCapacity" value="${roommap.value.guestCapacity}"/>
						<c:set var="room" value="${roommap.key}" scope="request"/>
						<c:set var="roomInfo" value="${roommap.value}" scope="request"/>
					</c:url>
				<tr>

					<td><c:out value="${roommap.key.id}"/></td>
					<td><c:out value="${roommap.key.price}"/></td>
					<td><c:out value="${roommap.value.numBeds}"/></td>
					<td><c:out value="${roommap.value.guestCapacity}"/></td>
					<td><a href="${ReserveLink}">Reserve Room</a></tr>
     		</c:forEach>

   					</tbody>
                         </table>
                     </div>
                 </div>
    </div>
  </div>

</body>
</html>