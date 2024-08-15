package com.ConferenceRoomManagement.Services;

import java.io.IOException;
//import java.io.PrintWriter;
import java.util.List;

//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ConferenceRoomManagement.Entities.Room;
//import com.ConferenceRoomManagement.Entities.Room.RoomStatus;
import com.ConferenceRoomManagement.Repository.RoomDAO;

/**
 * Servlet implementation class RoomCrudServlet
 */
@WebServlet("/RoomCrudServlet")
public class RoomCrudServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private RoomDAO rd;
    @Override
    public void init() {
    	rd = new RoomDAO();
    }
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomCrudServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.setContentType("text/html");
		List<Room> rooms = rd.getRooms();
        request.setAttribute("rooms", rooms);
        request.getRequestDispatcher("/views/showRoom.jsp").include(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if(action.equals("add")) {
			String name = request.getParameter("name");
			int capacity = Integer.parseInt(request.getParameter("capacity"));
			String status = request.getParameter("status");
			rd.addRoom(name, capacity, status);
			response.sendRedirect("showRoom");
		}
		else if(action.equals("delete")) {
			int id = Integer.parseInt(request.getParameter("id"));
			rd.deleteRoom(id);
			response.sendRedirect("showRoom");
		}
		else if(action.equals("update")) {
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			int capacity = Integer.parseInt(request.getParameter("capacity"));
			String status = request.getParameter("status");
			rd.updateRoom(id,name,capacity,status);
			response.sendRedirect("showRoom");			
		}
	}

}
