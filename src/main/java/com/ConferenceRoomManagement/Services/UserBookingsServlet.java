package com.ConferenceRoomManagement.Services;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ConferenceRoomManagement.Entities.Booking;
import com.ConferenceRoomManagement.Repository.BookingDAO;
import com.ConferenceRoomManagement.Repository.RoomDAO;
import com.ConferenceRoomManagement.Repository.UserDAO;

/**
 * Servlet implementation class UserBookingsServlet
 */
@WebServlet("/UserBookingsServlet")
public class UserBookingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private BookingDAO bookingDAO = new BookingDAO();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserBookingsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = (int) request.getSession().getAttribute("userId");
		List<Booking> bookings = bookingDAO.getUserBookings(userId);
		UserDAO userDAO = new UserDAO();
		RoomDAO roomDAO = new RoomDAO();
		request.setAttribute("bookings", bookings);
		request.setAttribute("userDAO", userDAO);
		request.setAttribute("roomDAO", roomDAO);
        request.getRequestDispatcher("/views/userBookings.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
