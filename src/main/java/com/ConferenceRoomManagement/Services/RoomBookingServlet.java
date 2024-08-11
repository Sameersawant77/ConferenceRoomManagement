package com.ConferenceRoomManagement.Services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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

/**
 * Servlet implementation class RoomBookingServlet
 */
@WebServlet("/RoomBookingServlet")
public class RoomBookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RoomDAO roomDAO = new RoomDAO();
    private BookingDAO bookingDAO = new BookingDAO();
    private UserDAO userDAO = new UserDAO();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomBookingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Room> rooms = roomDAO.getRooms();
        request.setAttribute("rooms", rooms);
        request.getRequestDispatcher("/views/roomBooking.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookingDateStr = request.getParameter("bookingDate");
        String roomIdStr = request.getParameter("room");
        String[] selectedSlots = request.getParameterValues("slots");
        request.getSession().setAttribute("userId", 1);
        int userId = (int) request.getSession().getAttribute("userId");

        if (bookingDateStr == null || roomIdStr == null || selectedSlots == null) {
            response.sendRedirect("/views/roomBooking.jsp");
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

        for (String slot : selectedSlots) {
            String[] times = slot.split("-");
            LocalDateTime startDateTime = LocalDateTime.of(bookingDate, LocalTime.parse(times[0]));
            LocalDateTime endDateTime = LocalDateTime.of(bookingDate, LocalTime.parse(times[1]));

            if (bookingDAO.isSlotAvailable(roomId, bookingDate, startDateTime, endDateTime)) {
                bookingDAO.bookSlot(roomId, bookingDate, startDateTime, endDateTime, userId);
            } else {
	            Booking existingBooking = bookingDAO.getBookingDetails(roomId, bookingDate, LocalTime.parse(times[0]), LocalTime.parse(times[1]));
	            User user = userDAO.getUserById(existingBooking.getUserId());
	            String errorMessage = String.format(
	                "Slot %s is already booked by user %s.",
	                slot,
	                user.getUsername()
	                );
	            request.setAttribute("errorMessage", errorMessage);
	            doGet(request, response);
	            return;
            }
        }

        response.sendRedirect("/views/roomBooking.jsp");
    }

}
