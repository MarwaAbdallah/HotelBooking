<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ page import="java.util.ArrayList,java.util.List,model.Location" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ taglib prefix ="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HManager</title>
<link href="css/bootstrap.min.css" rel="stylesheet" >
</head>
<body>
  <div class="container sticky-top w-50 p-3" >
<div class="row justify-content-center">
 <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped" table-stripedid="dataTable2" width="100%" cellspacing="0">
	                                      <thead class="thead-dark">
										    <tr>

										      <th scope="col">City</th>
										      <th scope="col">Country</th>
												
										    </tr>
										  </thead>
										  <tbody>
											  <c:forEach var="loc" items="${LOCATION_LIST}" >
											  			<!-- Setup a link for EDITing each User -->

			            								<tr>

			            									<td><c:out value="${loc.city}"/></td>
			            									<td><c:out value="${loc.country}"/></td>

			            								</tr>
				            				 </c:forEach>

		            					</tbody>
                                    </table>
                                     <table class="table table-bordered table-striped" table-stripedid="dataTable2" width="100%" cellspacing="0">
	                                      <thead class="thead-dark">
										    <tr>
										      <th scope="col">Image</th>
											  <th scope="col">Hotel</th>
										    </tr>
										  </thead>
										  <tbody>
											  <c:forEach var="loc" items="${LOCATION_LIST}" >
											  			<!-- Setup a link for EDITing each User -->

			            								<tr>
			            								<td><img  src="img/hotel_paris.jpeg" width="193" height="130"></td>
			            									<td>
			            									<table>
			            										<tr>
				            										<td> <c:out value="${loc.city}"/></td>
				            						
			            										</tr>
			            										<tr>
			            										<td><c:out value="${loc.country}"/></td>
			            										</tr>
			            									</table>
			            									</td>
			            									
			            									

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