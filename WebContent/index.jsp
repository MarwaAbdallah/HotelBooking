<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ page import="java.util.ArrayList,java.util.List,model.Location" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ taglib prefix ="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Jekyll v4.1.1">
    <title>HManager</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.5/examples/floating-labels/">

    <!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet" >

  
  </head>
  <body>
<%@ include file="WEB-INF/views/header.jsp" %>
<div class="card mb-3">

  <div class="card-body">
  <div class="container sticky-top w-50 p-3" >
<div class="row justify-content-center">
      <div class="col-lg-12">
      <div>
        <form class="home-search" action="${pageContext.request.contextPath}/locations" method="post">
          <div class="row justify-content-md-center">
          <input type="hidden" name="command" value = "LIST_HOTELS" />
            <div class="col-auto">
              <div class="form-group">
              
                <span>Location</span>
                      <select class="custom-select mr-sm-2" id="inlineFormCustomSelect" name="location">
				        <c:forEach var="loc" items="${LOCATION_LIST}">
				            <option name="cityCountry" value="${loc.city},${loc.country}">${loc.city}, ${loc.country}</option>
				          </c:forEach>
				      </select>
              </div>
            </div>
            <div class="col-auto">
              <div class="form-group form-location">
                <span>Check-In</span>
                <input type="date" class="form-control" name="checkIn" placeholder="Ex: Choose your city or location">
                <a class="location-icon" href="#"> <i class="far fa-compass"></i> </a>
              </div>
            </div>
            <div class="col-auto">
              <div class="form-group form-location">
                <span>Check-Out</span>
                <input type="date" class="form-control" name="checkOut" placeholder="Ex: Choose your city or location">
                <a class="location-icon" href="#"> <i class="far fa-compass"></i> </a>
              </div>
            </div>
            </div>
            <div>
		    <div class="col-auto row justify-content-end">
		      <button type="submit" class="btn btn-primary mb-2">Search</button>
		    </div>
		    </div>
		  </form>
        </div>

      </div>
    </div>
  </div>
 </div>
 <div class="container">
   <img class="card-img-top" src="css/home-page-pic.jpg" alt="Card image cap">
</div>

 </div>
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>  
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>  
</body>
</html>
