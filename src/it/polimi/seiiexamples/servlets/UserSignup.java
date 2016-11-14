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
			if(userService.insertUser(u) != null){
				req.getSession().setAttribute("userId", u.getId());
				// req.setAttribute("user", u);
				req.getRequestDispatcher("/user_ok.jsp").forward(req, resp);

			} else {
				req.getRequestDispatcher("/error.jsp").forward(req, resp);
			}
		} catch (IllegalArgumentException e) {
			req.setAttribute("username", req.getParameter("username"));
			req.setAttribute("email", req.getParameter("email"));
			req.setAttribute("error", e.getMessage());
			req.getRequestDispatcher("/new_user.jsp").forward(req, resp);
		} 
	}

}