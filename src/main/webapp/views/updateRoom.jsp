<%@ page import="com.ConferenceRoomManagement.Entities.Room" %>
<%@ page import="com.ConferenceRoomManagement.Repository.RoomDAO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Conference Room Management</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container {
            margin-top: 50px;
            display: flex;
            justify-content: center;
        }
        .form-container {
        	min-width: 600px;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>
<%@ include file="/views/navbar.jsp" %>
    <div class="container">
        <div class="form-container">
            <h2 class="text-center">Update Room</h2>
            <form action="RoomCrudServlet" method="post">
                <div class="form-group">
                    <label for="name">Conference Name</label>
                    <input type="text" class="form-control" id="name" name="name" value="${r.roomName}" required>
                </div>
                <div class="form-group">
                    <label for="capacity">Capacity</label>
                    <input type="text" class="form-control" id="capacity" name="capacity" placeholder="Enter conference capacity ..." value="${r.capacity}" required>
                </div>
                <div class="form-group">
                    <label for="status">Status</label>
                    <select class="form-control" id="status" name="status">
                        <option value="active" ${r.status == 'active' ? 'selected' : ''}>Active</option>
                        <option value="inactive" ${r.status == 'inactive' ? 'selected' : ''}>Inactive</option>
                    </select>
                </div>
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id" value="${id}">
                <button type="submit" class="btn btn-primary btn-block">Submit</button>
            </form>
        </div>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
