<%@ page import="java.util.List" %>
<%@ page import="com.ConferenceRoomManagement.Entities.Booking" %>
<%@ page import="com.ConferenceRoomManagement.Repository.UserDAO" %>
<%@ page import="com.ConferenceRoomManagement.Repository.RoomDAO" %>
<%@ page import="com.ConferenceRoomManagement.Entities.User" %>
<%@ page import="com.ConferenceRoomManagement.Entities.Room" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>All Bookings</title>
<!-- Bootstrap CSS -->
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<style>
.container {
    margin-top: 20px;
}
</style>
</head>
<body>
<%@ include file="/views/navbar.jsp" %>
<div class="container">
    <h1 class="text-center">All Bookings</h1>
    <table class="table table-striped table-bordered">
        <thead class="thead-dark">
            <tr>
                <th>Booking ID</th>
                <th>User Name</th>
                <th>Email</th>
                <th>Date</th>
                <th>Room Name</th>
                <th>Start Time</th>
                <th>End Time</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="booking" items="${bookings}">
            <c:set var="user" value="${userDAO.getUserById(booking.userId)}" />
            <c:set var="room" value="${roomDAO.getRoomById(booking.roomId)}" />
            <tr>
                <td>${booking.bookingId}</td>
                <td>${user.username}</td>
                <td>${booking.email}</td>
                <td>
		            <fmt:parseDate value="${booking.bookingDate}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
		            <fmt:formatDate value="${parsedDate}" type="date" pattern="MMM d, yyyy" />
		        </td>
		        <td>${room.roomName}</td>
		        <td>
		            <fmt:parseDate value="${booking.startTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedStartTime" type="both" />
		            <fmt:formatDate value="${parsedStartTime}" type="time" pattern="h:mm a" />
		        </td>
		        <td>
		            <fmt:parseDate value="${booking.endTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedEndTime" type="both" />
		            <fmt:formatDate value="${parsedEndTime}" type="time" pattern="h:mm a" />
		        </td>
                <td>${booking.status}</td>
                <td>
                    <c:if test="${booking.status != 'cancelled'}">
                        <form action="CancelBookingServlet" method="post" onsubmit="return confirmCancel()">
                            <input type="hidden" name="bookingId" value="${booking.bookingId}">
                            <button type="submit" class="btn btn-danger btn-sm">Cancel</button>
                        </form>
                    </c:if>
                    <c:if test="${booking.status == 'cancelled'}">
                        <button type="button" class="btn btn-danger btn-sm" disabled>Cancel</button>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
    function confirmCancel() {
        return confirm("Are you sure you want to cancel this booking?");
    }
</script>
</body>
</html>