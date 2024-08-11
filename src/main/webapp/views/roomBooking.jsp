<%@ page import="java.time.LocalDate" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book a Room</title>
    <script>
        function setMaxDate() {
            const today = new Date();
            const maxDate = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 7);
            const maxDateString = maxDate.toISOString().split('T')[0];
            document.getElementById('bookingDate').setAttribute('max', maxDateString);
        }
        
        window.onload = setMaxDate;
    </script>
</head>
<body>
    <h1>Book a Room</h1>
    <form action="RoomBookingServlet" method="post">
        <label for="userId">User ID:</label>
        <input type="text" id="userId" name="userId" required /><br><br>
        
        <label for="email">Email:</label>
    	<input type="email" id="email" name="email" required /><br><br>
        
        <label for="bookingDate">Date of Booking:</label>
        <input type="date" id="bookingDate" name="bookingDate" required /><br><br>

        <label for="room">Select Room:</label>
        <select id="room" name="room" required>
            <c:forEach var="room" items="${rooms}">
                <option value="${room.roomId}">${room.roomName}</option>
            </c:forEach>
        </select><br><br>

        <label>Select Time Slots:</label><br>
        <c:forEach var="hour" begin="10" end="17">
            <input type="checkbox" name="slots" value="${hour}:00-${hour + 1}:00" /> ${hour}:00 - ${hour + 1}:00<br>
        </c:forEach><br>

        <input type="submit" value="Book Now" />
    </form>
    
    <c:if test="${not empty errorMessage}">
        <p style="color: red;">${errorMessage}</p>
    </c:if>
</body>
</html>
