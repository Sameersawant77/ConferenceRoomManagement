package com.ConferenceRoomManagement.Services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ConferenceRoomManagement.Entities.User;
import com.ConferenceRoomManagement.Repository.UserDAO;




@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		UserDAO userDao = new UserDAO();
		User user = userDao.getUserByUsernameAndPassword(username, password);
		
		
		if(user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("userId",user.getUserId());
			session.setAttribute("username",user.getUsername());
			session.setAttribute("role",user.getRole().toString());
			RequestDispatcher rd = request.getRequestDispatcher("/home");
			rd.forward(request, response);
		}else {
			request.setAttribute("errorMessage", "Invalid credentials. Please try again.");
			RequestDispatcher rd = request.getRequestDispatcher("/views/login.jsp");
			rd.forward(request, response);
		}	
	}

	

}
