<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
.fa-user {
	font-size: 100px;
}

body {
	background-image: url(<c:url value="/images/back.jpg"/>);
	background-repeat: no-repeat;
	background-size: cover;
}

tr, td {
	padding: 7px;
}

input {
	outline: none;
}

.error-message {
	color: red;
	text-align: center;
	font-weight: bold;
}
</style>
</head>
<body>
<%@ include file="/views/navbar.jsp" %>
	<fieldset
		style="margin: 10% 35%; padding: 20px 40px; border: 0px solid rgb(59, 59, 59); background-color: rgba(74, 74, 74, 0.6); color: white;">
		<legend align="center">
			<i class="fa fa-user"></i>
		</legend>
		<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
		<form action="${pageContext.request.contextPath}/LoginServlet">
			<table>

				<tr>
					<td>Username</td>
					<td>: <input type="text" name="username"
						placeholder="Enter username ...." required></td>
				</tr>
				<tr>
					<td>Password</td>
					<td>: <input type="password" name="password"
						placeholder="Enter password ...." required></td>
				</tr>
			</table>
			<br> <input type="submit" value="submit"
				style="margin-left: 30%">
		</form>
		<% if (errorMessage != null) { %>
			<p class="error-message"><%= errorMessage %></p>
		<% } %>
	</fieldset>
</body>
</html>