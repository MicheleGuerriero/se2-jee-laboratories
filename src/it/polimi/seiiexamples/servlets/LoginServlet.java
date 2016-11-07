package it.polimi.seiiexamples.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.seiiexamples.entities.User;

public class LoginServlet extends GenericServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String n = request.getParameter("userEmail");
		String p = request.getParameter("psw");
		
		Integer validationResult= userService.login(n, p);
		
		if (validationResult == 1) {
			User u = userService.getUserFromEmail(n);
			request.getSession().setAttribute("user", u);

			RequestDispatcher rd = request.getRequestDispatcher("login_ok.jsp");
			rd.forward(request, response);
		} else if(validationResult == -2) {
			out.print("Invalid email.");
			RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
			rd.include(request, response);
		} else if(validationResult == 0){
			out.print("Wrong password for user with email " + n + ".");
			RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
			rd.include(request, response);
		} else {
			out.print("User not found, please register first.");
			RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
			rd.include(request, response);
		}

		out.close();
	}

}
