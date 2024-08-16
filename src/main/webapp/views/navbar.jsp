<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Conference Room Management</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .navbar-nav .nav-link {
            color: rgba(0,0,0,.55);
        }
        .navbar-nav .nav-link:hover {
            color: rgba(0,0,0,.7);
        }
    </style>
</head>
<body>
    <%
    String user = (String) session.getAttribute("username");
    String role = (String) session.getAttribute("role");
    %>

    <nav class="navbar navbar-expand-lg navbar-light bg-secondary">
        <div class="container-fluid">
            <a class="navbar-brand" href="home">Montran India</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <% if (user != null) { %>
                        <li class="nav-item">
                            <a class="nav-link" href="RoomBookingServlet">Book Room</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="UserBookingsServlet">My Bookings</a>
                        </li>
                    <% } %>
                    <% if ("admin".equals(role)) { %>
                        <li class="nav-item">
                            <a class="nav-link" href="AllBookingsServlet">All Bookings</a>
                        </li>
                    <% } %>
                    <li class="nav-item">
                        <a class="nav-link" href="showRoom">All Rooms</a>
                    </li>
                </ul>
                <div class="navbar-text">
                    <% if (user != null) { %>
                        Welcome <%= user %> | <a href="LogoutServlet" class="text-decoration-none text-dark">Logout</a>
                    <% } else { %>
                        <a href="login" class="text-decoration-none text-dark">Login</a>
                    <% } %>
                </div>
            </div>
        </div>
    </nav>

    <!-- Bootstrap JS and Popper.js -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>