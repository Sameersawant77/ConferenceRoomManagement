package com.ConferenceRoomManagement.Services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ConferenceRoomManagement.Entities.Booking;
import com.ConferenceRoomManagement.Entities.Room;
import com.ConferenceRoomManagement.Repository.BookingDAO;
import com.ConferenceRoomManagement.Repository.RoomDAO;
import com.ConferenceRoomManagement.Utils.MailUtil;

/**
 * Servlet implementation class ReBookServlet
 */
@WebServlet("/ReBookServlet")
public class ReBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private BookingDAO bookingDAO = new BookingDAO();
	private RoomDAO roomDAO = new RoomDAO();
	MailUtil mailUtil = new MailUtil();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReBookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        
        boolean reBooked = bookingDAO.reBook(bookingId);
        
        if (reBooked) {
        	Booking booking = bookingDAO.getBookingById(bookingId);
        	Room room = roomDAO.getRoomById(booking.getRoomId());
        	LocalDate bookingDate = booking.getBookingDate();
        	LocalDateTime startTime = booking.getStartTime();
        	LocalDateTime endTime = booking.getEndTime();

        	// Format booking date and times
        	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        	DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        	String formattedBookingDate = bookingDate.format(dateFormatter);
        	String formattedStartTime = startTime.format(timeFormatter);
        	String formattedEndTime = endTime.format(timeFormatter);

        	// Send confirmation email
        	mailUtil.reBookConfirmationEmail(
        	    booking.getEmail(),
        	    room.getRoomName(),
        	    formattedBookingDate,
        	    formattedStartTime,
        	    formattedEndTime
        	);
        	
        	mailUtil.reBookAdminNotificationEmail(
        	    room.getRoomName(),
        	    formattedBookingDate,
        	    formattedStartTime,
        	    formattedEndTime,
        	    booking.getEmail()
            );
        	
            response.sendRedirect(request.getContextPath() + "/UserBookingsServlet");
        } else {
            response.getWriter().println("Failed to Book Again.");
            request.getRequestDispatcher("/UserBookingsServlet").include(request, response);
        }
	}

}
