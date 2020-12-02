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
<title>Reservation</title>
<link href="css/bootstrap.min.css" rel="stylesheet" >
</head>
<body>
<%@ include file="header.jsp" %>

    <div class="container">

    <div class="row mb-2 justify-content-md-center">
         <div class="card flex-md-row mb-3 box-shadow h-md-250">
           <div class="card-body d-flex flex-column align-items-start">
			     <div class="row justify-content-md-center">
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
		     </div>
        </div>

   </div>        
            
     <div class="row mb-2">
       <div class="col-md-6">
         <div class="card flex-md-row mb-4 box-shadow h-md-250">
           <div class="card-body d-flex flex-column align-items-start">
             <strong class="d-inline-block mb-2 text-primary">Hotel</strong>
             <h3 class="mb-0">
               <c:out value='${cookie["hotelName"].value}'/>
             </h3>
             <p class="card-text mb-auto">This hotel is located in heart of city</p>

           </div>
           <img class="card-img-right flex-auto d-none d-md-block" data-src="holder.js/200x250?theme=thumb" alt="Thumbnail [200x250]" style="width: 200px; height: 250px;" src="img/hotel_paris.jpeg" data-holder-rendered="true">
         </div>
       </div>
       <div class="col-md-6">
         <div class="card flex-md-row mb-4 box-shadow h-md-250">
           <div class="card-body d-flex flex-column align-items-start">
             <strong class="d-inline-block mb-2 text-success">Room</strong>
             <h3 class="mb-0">
               <c:out value='${cookie["roomId"].value}'/>
             </h3>
             <div class="mb-1 text-muted"><c:out value='${cookie["roomPrice"].value}'/></div>
           </div>
           <img class="card-img-right flex-auto d-none d-md-block" data-src="holder.js/200x250?theme=thumb" alt="Thumbnail [200x250]" src="img/hotel_paris.jpeg" data-holder-rendered="true" style="width: 200px; height: 250px;">
         </div>
       </div>
     </div>
      </div>

<div class="container">
<div class="col-md-13 order-md-1">

		<hr class="mb-4">

          <form class="needs-validation" novalidate="">
          		<c:url var="BookLink" value="ReservationControllerServlet">
          			<c:param name="command" value="RESERVE_ROOM_CHOSEN" />
						<c:param name="hotelName" value='${cookie["hotelName"].value}'/> 
						<c:param name="hotelId" value='${cookie["hotelId"].value}'/> 
						<c:param name="roomId" value='${cookie["roomId"].value}'/>
						<c:param name="roomPrice" value='${cookie["roomPrice"].value}'/>
						<c:param name="checkIn" value='${cookie["checkIn"].value}'/>
						<c:param name="checkOut" value='${cookie["checkOut"].value}'/>
					
				</c:url>
            <div class="mb-3">

            </div>
            <a href="${BookLink}" class="btn btn-primary btn-lg active" role="button" >Book Room</a>

            
          </form>

		    </div>
		    </div>


</body>
</html>