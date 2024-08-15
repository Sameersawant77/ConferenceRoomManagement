<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page
    import="java.util.*,com.ConferenceRoomManagement.Entities.Room,com.ConferenceRoomManagement.Repository.RoomDAO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Conference Room Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .room-card {
            border: 1px solid #dee2e6;
            border-radius: 10px;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
        }
        .room-image {
            height: 200px;
            object-fit: cover;
        }
        .add-room-card {
            cursor: pointer;
            height: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .add-room-icon {
            font-size: 3rem;
        }
    </style>
</head>
<body>
    <%@ include file="/views/navbar.jsp" %>
    
    <div class="container mt-4">
        
        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
            <c:forEach var="room" items="${rooms}">
                <div class="col">
                    <div class="card h-100 room-card">
                        <c:choose>
                            <c:when test="${room.capacity >= 5 && room.capacity < 10}">
                                <img src="images/6.jpg" class="card-img-top room-image" alt="Room Image">
                            </c:when>
                            <c:when test="${room.capacity >= 10 && room.capacity < 15}">
                                <img src="images/8.jpg" class="card-img-top room-image" alt="Room Image">
                            </c:when>
                            <c:when test="${room.capacity >= 15 && room.capacity < 20}">
                                <img src="images/15.jpg" class="card-img-top room-image" alt="Room Image">
                            </c:when>
                            <c:otherwise>
                                <img src="images/25.jpg" class="card-img-top room-image" alt="Room Image">
                            </c:otherwise>
                        </c:choose>
                        <div class="card-body">
                            <h5 class="card-title">${room.roomName}</h5>
                            <p class="card-text">Capacity: ${room.capacity}</p>
                            <c:if test="${role == 'admin'}">
                                <div class="d-flex justify-content-between">
                                    <form method="post" action="RoomServlet" class="me-2">
                                        <input type="hidden" name="action" value="update">
                                        <input type="hidden" name="id" value="${room.roomId}">
                                        <button type="submit" style="width: 150px" class="btn btn-secondary">Update</button>
                                    </form>
                                    <form method="post" action="RoomCrudServlet">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="id" value="${room.roomId}">
                                        <button type="submit" style="width: 150px" class="btn btn-secondary">Delete</button>
                                    </form>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:forEach>
            <c:if test="${role == 'admin'}">
                <div class="col">
                    <div class="card h-100 room-card add-room-card" onclick="showAddForm()">
                        <div class="card-body text-center">
                            <i class="fas fa-plus add-room-icon"></i>
                            <p class="mt-2">Add New Room</p>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>
    </div>

    <!-- Add Room Form Modal -->
    <div class="modal fade" id="addRoomModal" tabindex="-1" aria-labelledby="addRoomModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addRoomModalLabel">Add Room</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="RoomCrudServlet" method="post">
                        <div class="mb-3">
                            <label for="roomName" class="form-label">Conference Name</label>
                            <input type="text" class="form-control" id="roomName" name="name" placeholder="Enter conference room name" required>
                        </div>
                        <div class="mb-3">
                            <label for="capacity" class="form-label">Capacity</label>
                            <input type="number" class="form-control" id="capacity" name="capacity" placeholder="Enter conference capacity" required>
                        </div>
                        <div class="mb-3">
                            <label for="status" class="form-label">Status</label>
                            <select class="form-select" id="status" name="status" required>
                                <option value="active">Active</option>
                                <option value="inactive">Inactive</option>
                            </select>
                        </div>
                        <input type="hidden" name="action" value="add">
                        <button type="submit" class="btn btn-primary">Add Room</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
    <script>
        function showAddForm() {
            var addRoomModal = new bootstrap.Modal(document.getElementById('addRoomModal'));
            addRoomModal.show();
        }
    </script>
</body>
</html>