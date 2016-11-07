package it.polimi.seiiexamples.servlets;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import it.polimi.seiiexamples.beans.UserBean;
import it.polimi.seiiexamples.entities.User;

public class GenericServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	protected UserBean userService;

	public GenericServlet() {
		super();
	}

	public User getCurrentUser(HttpServletRequest req) {
		if (req.getAttribute("user") == null) {
			Integer userId = (Integer) req.getSession().getAttribute("userId");
			if (userId != null) {
				req.setAttribute("user", userService.getUserFromId(userId));
			}
		}
		return (User) req.getAttribute("user");
	}
}