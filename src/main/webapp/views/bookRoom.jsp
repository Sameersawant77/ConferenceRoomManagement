<%@ page import="java.time.LocalDate" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Book a Room</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .time-slot-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 10px;
        }
        .time-slot-btn {
            width: 100%;
        }
    </style>
</head>
<body class="bg-secondary">
<%@ include file="/views/navbar.jsp" %>
    <div class="container d-flex justify-content-center align-items-center min-vh-100">
        <div class="card shadow-lg" style="width: 100%; max-width: 700px;">
            <div class="card-body p-5">
                <h1 class="card-title text-center mb-4">Book a Room</h1>
                <form action="RoomBookingServlet" method="post">
                    <div class="mb-3">
                        <label for="email" class="form-label">Email:</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>

                    <div class="mb-3">
                        <label for="bookingDate" class="form-label">Date of Booking:</label>
                        <input type="date" class="form-control" id="bookingDate" name="bookingDate" required>
                    </div>

                    <div class="mb-3">
                        <label for="room" class="form-label">Select Room:</label>
                        <select class="form-select" id="room" name="room" required>
                            <c:forEach var="room" items="${rooms}">
                                <option value="${room.roomId}">${room.roomName}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Select Time Slots:</label>
                        <div class="time-slot-grid">
                            <c:forEach var="hour" begin="10" end="17">
                                <input type="checkbox" class="btn-check" id="slot${hour}" name="slots" value="${hour}:00-${hour + 1}:00" autocomplete="off">
                                <label class="btn btn-outline-primary time-slot-btn" for="slot${hour}">${hour}:00 - ${hour + 1}:00</label>
                            </c:forEach>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary w-100">Book Now</button>
                </form>

                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger mt-3" role="alert">
                        ${errorMessage}
                    </div>
                </c:if>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const today = new Date();
            const minDate = today.toISOString().split('T')[0];
            const maxDate = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 7).toISOString().split('T')[0];

            document.getElementById('bookingDate').setAttribute('min', minDate);
            document.getElementById('bookingDate').setAttribute('max', maxDate);
        });
    </script>
</body>
</html>