
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
<link rel="stylesheet" href="./CSS/roomrudPage.css">
<style>
.img1 img {
	height: 200px;
	width: 250px;
}

.img h2 {
	margin: 0;
	text-align: center;
}

.bt {
	display: flex;
	justify-content: space-around;
}
/* #btn1,#btn2{
            background-color: transparent;
            border: none;
            font-size: 20px; 
        }         */
#btn1, #btn2 {
	background-color: rgb(206, 206, 206);
	color: black;
	border: none;
	padding: 5px;
	width: 100px;
	border-radius: 5px;
}

#btn1:hover, #btn2:hover {
	background-color: green;
	color: white;
}

.img {
	border: 1px solid rgb(203, 203, 203);
	padding: 20px;
	border-radius: 10px;
	box-shadow: 5px 5px 5px 5px rgb(229, 229, 229);
	text-align: center;
}

.imagecontainer {
	display: flex;
	flex-wrap: wrap;
	flex-direction: row;
	align-content: center;
	align-items: center;
	justify-content: center;
	gap: 30px;
	margin: 0 10%;
}

</style>
</head>
<body>
	<%
	RoomDAO rd = new RoomDAO();
	List<Room> room = rd.getRooms();
	String user = (String) session.getAttribute("username");
	String role = (String) session.getAttribute("role");
	
	%>
	
	<div>
		<a href="HomeServlet"><i class="fa fa-rotate-left"> Back</i></a>
	</div>
    
	<div class="imagecontainer">
		<c:forEach var="room" items="${rooms}">
			<div class="img">
				<c:choose>
					<c:when test="${room.capacity >= 5 && room.capacity <10}">
						<div class="img1">
							<img src="images/6.jpg" alt="">
						</div>
					</c:when>
					<c:when test="${room.capacity >= 10 && room.capacity <15}">
						<div class="img1">
							<img src="images/8.jpg" alt="">
						</div>
					</c:when>
					<c:when test="${room.capacity >= 15 && room.capacity <20}">
						<div class="img1">
							<img src="images/15.jpg" alt="">
						</div>
					</c:when>
					<c:otherwise>
						<div class="img1">
							<img src="images/25.jpg" alt="">
						</div>
					</c:otherwise>
				</c:choose>
				<div class="box1">
					<h3>${room.roomName}</h3>
					<h3>Capacity : ${room.capacity}</h3>
					<div class="bt1" id="showcrud" >
					<c:if test="${role == 'admin'}">
						<div class="bt">
							<form method="post" action="RoomServlet">
								<input type="hidden" name="action" value="update"> <input
									type="hidden" name="id" value="${room.roomId}"> <input
									type="submit" id="btn1" value="Update">
							</form>
							<form method="post" action="RoomCrudServlet">
								<input type="hidden" name="action" value="delete"> <input
									type="hidden" name="id" value="${room.roomId}"> <input
									type="submit" id="btn2" value="Delete">
							</form>
						</div>
						</c:if>
					</div>
					
				</div>
			</div>
		</c:forEach>
		<c:if test="${role == 'admin'}">
		<div class="img" id="showcrud1" onclick="showaddform()">
			<i class="fa fa-plus"></i>
		</div>
		</c:if>
	</div>

	<!-- <div style="margin: 2% 6%; padding: 20px 40px;">
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
				<td><form method="post" action="RoomServlet"><input type="hidden" name="action" value="update"><input type="hidden" name="id" value="${room.roomId}"><input type="submit" value="edit"></form></td>
				<td><form method="post" action="RoomCrudServlet"><input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="${room.roomId}"><input type="submit" value="delete"></form></td>
			</tr>
            </c:forEach>

		</table>
	 -->
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
	</script>
</body>
</html>