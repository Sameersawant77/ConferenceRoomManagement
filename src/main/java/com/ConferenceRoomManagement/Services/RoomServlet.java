package com.ConferenceRoomManagement.Services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ConferenceRoomManagement.Entities.Room;
import com.ConferenceRoomManagement.Repository.RoomDAO;

/**
 * Servlet implementation class RoomServlet
 */
@WebServlet("/RoomServlet")
public class RoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println(id);
		RoomDAO rd = new RoomDAO();
		Room r = rd.getRoomById(id);
		out.println("<div id=\"form1\">\r\n"
				+ "		<h2>update Room </h2>\r\n"
				+ "		<div>\r\n"
				+ "			<form action=\"RoomCrudServlet\" method='post'>\r\n"
				+ "			\r\n"
				+ "				<table class=\"table2\">\r\n"
				+ "					<tr>\r\n"
				+ "						<td>Conference Name</td>\r\n"
				+ "						<td>: <input type=\"text\" name=\"name\" value='"+r.getRoomName()+"'></td>\r\n"
				+ "					</tr>\r\n"
				+ "					<tr>\r\n"
				+ "						<td>Capacity</td>\r\n"
				+ "						<td>: <input type=\"text\" name=\"capacity\" id=\"\"\r\n"
				+ "							placeholder=\"Enter conference capacity ...\" value='"+r.getCapacity()+"'></td>\r\n"
				+ "					</tr>\r\n"
				+ "					<tr>\r\n"
				+ "						<td>Status</td>\r\n"
				+ "						<td>: <select name=\"status\" value='"+r.getStatus()+"'>\r\n"
				+ "								<option value=\"active\">active</option>\r\n"
				+ "								<option value=\"inactive\">inactive</option>\r\n"
				+ "						</select></td>\r\n"
				+ "					</tr>\r\n"
				+ "\r\n"
				+ "				</table>\r\n"
				+ "				<input type=\"hidden\" name=\"action\" value=\"update\"> <input type='hidden' name ='id' value='"+id+"'><br>\r\n"
				+ "				<input type=\"submit\" value=\"submit\">\r\n"
				+ "			</form>\r\n"
				+ "		</div>\r\n"
				+ "	</div>");
	}

}
