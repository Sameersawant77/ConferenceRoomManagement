package com.ConferenceRoomManagement.Utils;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        
        System.out.println(requestURI);
        
        // Check if the request is for LoginServlet or home page
        if (requestURI.endsWith("LoginServlet") || requestURI.equals(contextPath + "/") || requestURI.equals(contextPath + "/home") || requestURI.equals(contextPath + "/showRoom")) {
            chain.doFilter(request, response);
            return;
        }
        
        HttpSession session = httpRequest.getSession(false);
        
        // Check if user is authenticated
        if (session != null && session.getAttribute("userId") != null) {
            chain.doFilter(request, response);
        } else {
        	request.setAttribute("errorMessage", "Please login first.");
			RequestDispatcher rd = request.getRequestDispatcher("/login");
			rd.forward(request, response);
        }
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
    }
}