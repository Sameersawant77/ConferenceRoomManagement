<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="java.util.*,com.ConferenceRoomManagement.Entities.Room,com.ConferenceRoomManagement.Repository.RoomDAO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!--  NAVBAR  -->
<%@ include file="/views/navbar.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="CSS/roomrudPage.css">


</head>
<body>
	<%
	RoomDAO rd = new RoomDAO();
	List<Room> room = rd.getRooms();
	%>
	
	
	
	<!-- 
	<div>
		<a href="home"><i class="fa fa-rotate-left"> Back</i></a>
	</div>
	-->
	
	<div style="margin-top: 30px">
		<button onclick="showaddform()">
			<i class="fa fa-plus">Add Room</i>
		</button>
	</div>
	<div style="margin: 2% 6%; padding: 20px 40px;">
		<table class="table1">
			<tr>
				<td>Id</td>
				<td>Conference Name</td>
				<td>Capacity</td>
				<td>Status</td>
				<td>Edit</td>
				<td>delete</td>
			</tr>

			<c:forEach var="room" items="${rooms}">
				<tr>
					<td>${room.roomId}</td>
					<td>${room.roomName}</td>
					<td>${room.capacity}</td>
					<td>${room.status}</td>
					<td><form method="post" action="RoomServlet">
							<input type="hidden" name="action" value="update"><input
								type="hidden" name="id" value="${room.roomId}"><input
								type="submit" value="edit">
						</form></td>
					<td><form method="post" action="RoomCrudServlet">
							<input type="hidden" name="action" value="delete"><input
								type="hidden" name="id" value="${room.roomId}"><input
								type="submit" value="delete">
						</form></td>
				</tr>
			</c:forEach>

		</table>
	</div>
	<div id="form">
		<h2>Add Rooms</h2>

		<button onclick="closeform()" id="closecontainer" class="crossbtn">
			<i class="fa fa-times" aria-hidden="true"></i>
		</button>
		<div>
			<form action="RoomCrudServlet" method="post">
				<table class="table2">
					<tr>
						<td>Conference Name</td>
						<td>: <input type="text" name="name"
							placeholder="Enter conference room name ...."></td>
					</tr>
					<tr>
						<td>Capacity</td>
						<td>: <input type="text" name="capacity" id=""
							placeholder="Enter conference capacity ..."></td>
					</tr>
					<tr>
						<td>Status</td>
						<td>: <select name="status">
								<option value="active">active</option>
								<option value="inactive">inactive</option>
						</select></td>
					</tr>

				</table>
				<input type="hidden" name="action" value="add"> <br> <input
					type="submit" value="Add" style="align-content: center;"
					class="btn2">
			</form>
		</div>
	</div>



	<script>
		function showaddform() {
			var form = document.getElementById("form").style;
			form.display = "block";

		}
		function closeform() {
			document.getElementById("form").style.display = "none";
		}
		document.getElementById("formupdate").addEventListener("submit",
				function(event) {
					event.preventDefault();
				});
	</script>
</body>
</html>