<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel ="stylesheet" href="CSS/home.css">
</head>
<body>
	<div class="showmenu" id="show">
		<i class="fa fa-times" onclick="closemenu()" aria-hidden="true"
			style="color: red; font-size: 30px; font-weight: 300; position: absolute; right: 2%;"></i>
		<ul>
			<li><a href="home">Home</a></li>
			<li><a href="booking">Conference Room Booking</a></li>
			<li><a href="list">Booking List</a></li>
			<li><a href="showRoom">Room</a></li>
		</ul>
	</div>
	<div class="navbar">
		<nav style="display: flex; justify-content: flex-start; gap: 30%;">
			<button onclick="showmenu()" class="nav-btn">
				<i class="fa fa-bars"></i>
			</button>
			<h2>Montran Corporation India</h2>
			<h3>-Logout</h3>
		</nav>
	</div>
	<div class="bodycontent">
		<h2
			style="text-align: center; padding-top: 17%; font-size: 30px; color: rgb(0, 0, 0);">Welcome
			to the Conference System</h2>
	</div>
	<script>
		function showmenu() {
			document.getElementById("show").style.display = "block";
		}
		function closemenu() {
			document.getElementById("show").style.display = "none";
		}
	</script>
</body>
</html>