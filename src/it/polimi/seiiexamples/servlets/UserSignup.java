package it.polimi.seiiexamples.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.seiiexamples.entities.User;

public class UserSignup extends GenericServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
			req.getRequestDispatcher("/new_user.jsp").forward(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		try {
			User u = new User();
			u.setName(req.getParameter("username"));
			u.setEmail(req.getParameter("email"));
			u.setPassword(req.getParameter("psw"));
			if(userService.isValidUser(u)) {
				if(userService.insertUser(u)){
					req.getRequestDispatcher("/user_ok.jsp").forward(req, resp);
				} else {
					req.setAttribute("error", "The email appears to be already registered.");
					req.getRequestDispatcher("/new_user.jsp").forward(req, resp);
				}
			} else {
				req.setAttribute("error", "One of the fields is not valid.");
				req.getRequestDispatcher("/new_user.jsp").forward(req, resp);
			}
		} catch (IllegalArgumentException e) {
			req.getRequestDispatcher("/new_user.jsp").forward(req, resp);
		} 
	}

}