package com.ConferenceRoomManagement.Services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ConferenceRoomManagement.Entities.Booking;
import com.ConferenceRoomManagement.Entities.Room;
import com.ConferenceRoomManagement.Entities.User;
import com.ConferenceRoomManagement.Repository.BookingDAO;
import com.ConferenceRoomManagement.Repository.RoomDAO;
import com.ConferenceRoomManagement.Repository.UserDAO;

@WebServlet("/RoomBookingServlet")
public class RoomBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private RoomDAO roomDAO = new RoomDAO();
    private BookingDAO bookingDAO = new BookingDAO();
    private UserDAO userDAO = new UserDAO();

    
    public RoomBookingServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Room> rooms = roomDAO.getRooms();
        request.setAttribute("rooms", rooms);
        request.getRequestDispatcher("/views/roomBooking.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookingDateStr = request.getParameter("bookingDate");
        String roomIdStr = request.getParameter("room");
        String[] selectedSlots = request.getParameterValues("slots");
        String email = request.getParameter("email");
        int userId = Integer.parseInt(request.getParameter("userId"));

        if (bookingDateStr == null || roomIdStr == null || selectedSlots == null || email == null) {
        	request.getRequestDispatcher("/views/roomBooking.jsp").include(request, response);
            return;
        }

        LocalDate bookingDate = LocalDate.parse(bookingDateStr);
        int roomId = Integer.parseInt(roomIdStr);
        Room room = roomDAO.getRoomById(roomId);

        if (room == null) {
            request.setAttribute("errorMessage", "Room not found.");
            doGet(request, response);
            return;
        }

        boolean allSlotsAvailable = true;
        StringBuilder bookedSlots = new StringBuilder();

        for (String slot : selectedSlots) {
            String[] times = slot.split("-");
            LocalDateTime startDateTime = LocalDateTime.of(bookingDate, LocalTime.parse(times[0]));
            LocalDateTime endDateTime = LocalDateTime.of(bookingDate, LocalTime.parse(times[1]));

            if (!bookingDAO.isSlotAvailable(roomId, bookingDate, startDateTime, endDateTime)) {
                allSlotsAvailable = false;
                Booking existingBooking = bookingDAO.getBookingDetails(roomId, bookingDate, LocalTime.parse(times[0]), LocalTime.parse(times[1]));
                User user = userDAO.getUserById(existingBooking.getUserId());

                // Get all bookings for this user on this date
                List<Booking> userBookings = bookingDAO.getUserBookingsForDate(user.getUserId(), bookingDate);
                String userBookedSlots = userBookings.stream()
                    .map(booking -> String.format("%s-%s",
                        booking.getStartTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                        booking.getEndTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))))
                    .collect(Collectors.joining(", "));

                String errorMessage = String.format(
                    "Slot %s is already booked by user %s. All slots booked by this user on %s: %s",
                    slot,
                    user.getUsername(),
                    bookingDate,
                    userBookedSlots
                );
                request.setAttribute("errorMessage", errorMessage);
                doGet(request, response);
                return;
            }

            bookedSlots.append(slot).append(", ");
        }

        if (allSlotsAvailable) {
            for (String slot : selectedSlots) {
                String[] times = slot.split("-");
                LocalDateTime startDateTime = LocalDateTime.of(bookingDate, LocalTime.parse(times[0]));
                LocalDateTime endDateTime = LocalDateTime.of(bookingDate, LocalTime.parse(times[1]));
                bookingDAO.bookSlot(roomId, bookingDate, startDateTime, endDateTime, userId);
            }

            // Send confirmation emails
//            sendConfirmationEmail(email, room.getRoomName(), bookingDate, bookedSlots.toString());
//            sendAdminNotificationEmail(room.getRoomName(), bookingDate, bookedSlots.toString(), email);

            //response.sendRedirect("/views/bookingConfirmation.jsp");
            request.getRequestDispatcher("/views/roomBooking.jsp").include(request, response);
        }
    }

}