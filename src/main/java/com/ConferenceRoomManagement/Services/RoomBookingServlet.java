package com.ConferenceRoomManagement.Services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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
import com.ConferenceRoomManagement.Utils.MailUtil;

@WebServlet("/RoomBookingServlet")
public class RoomBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private RoomDAO roomDAO = new RoomDAO();
    private BookingDAO bookingDAO = new BookingDAO();
    private UserDAO userDAO = new UserDAO();
    MailUtil mailUtil = new MailUtil();
    
    public RoomBookingServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Room> rooms = roomDAO.getActiveRooms();
        request.setAttribute("rooms", rooms);
        request.getRequestDispatcher("/views/bookRoom.jsp").forward(request, response);
    }

    protected synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookingDateStr = request.getParameter("bookingDate");
        String roomIdStr = request.getParameter("room");
        String[] selectedSlots = request.getParameterValues("slots");
        String email = request.getParameter("email");
        int userId = (int) request.getSession().getAttribute("userId");

        if (bookingDateStr == null || roomIdStr == null || selectedSlots == null || email == null) {
        	request.getRequestDispatcher("/views/bookRoom.jsp").include(request, response);
            return;
        }

        LocalDate bookingDate = LocalDate.parse(bookingDateStr);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        String formattedBookingDate = bookingDate.format(dateFormatter);
        int roomId = Integer.parseInt(roomIdStr);
        Room room = roomDAO.getRoomById(roomId);

        if (room == null) {
            request.setAttribute("errorMessage", "Room not found.");
            doGet(request, response);
            return;
        }

        boolean allSlotsAvailable = true;
        StringBuilder bookedSlots = new StringBuilder();
        bookingDAO.deleteOldBookings();

        for (String slot : selectedSlots) {
            String[] times = slot.split("-");
            LocalDateTime startDateTime = LocalDateTime.of(bookingDate, LocalTime.parse(times[0]));
            LocalDateTime endDateTime = LocalDateTime.of(bookingDate, LocalTime.parse(times[1]));

            if (!bookingDAO.isSlotAvailable(roomId, bookingDate, startDateTime, endDateTime)) {
                allSlotsAvailable = false;
                Booking existingBooking = bookingDAO.getBookingDetails(roomId, bookingDate, LocalTime.parse(times[0]), LocalTime.parse(times[1]));
                User user = userDAO.getUserById(existingBooking.getUserId());

                // Get all bookings for this user on this date
                List<Booking> userBookings = bookingDAO.getBookingsByDate(bookingDate);
                String userBookedSlots = userBookings.stream()
                        .map(booking -> {
                            User bookedUser = userDAO.getUserById(booking.getUserId());
                            return String.format("%s-%s (Booked by %s)",
                                booking.getStartTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                                booking.getEndTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                                bookedUser != null ? bookedUser.getUsername() : "Unknown");
                        })
                        .collect(Collectors.joining(", "));
                
                
                
                String errorMessage = String.format(
                    "Slot %s is already booked by user %s. All slots booked on %s are %s",
                    slot,
                    user.getUsername(),
                    formattedBookingDate,
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
                bookingDAO.bookSlot(roomId, bookingDate, startDateTime, endDateTime, userId, email);
            }

             //Send confirmation emails
            mailUtil.sendConfirmationEmail(email, room.getRoomName(), formattedBookingDate, bookedSlots.toString());
            mailUtil.sendAdminNotificationEmail(room.getRoomName(), formattedBookingDate, bookedSlots.toString(), email);

            response.sendRedirect("UserBookingsServlet");
        }else {
        	request.setAttribute("errorMessage", "Error while Booking, Please Book Again.");
            doGet(request, response);
            return;
        }
    }

}