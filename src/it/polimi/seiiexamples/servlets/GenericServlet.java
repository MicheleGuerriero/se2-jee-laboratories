package it.polimi.seiiexamples.servlets;

import java.io.IOException;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.seiiexamples.beans.UserBean;
import it.polimi.seiiexamples.entities.User;

public class GenericServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	protected UserBean userService;

	public GenericServlet() {
		super();
	}

	public void showJSP(String jsp, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher(jsp).forward(req, resp);
	}

	public void showErrorPage(String error, String goBack, HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.setAttribute("error", (error == null ? "" : error));
		req.setAttribute("goBackUrl", (goBack == null ? "" : goBack));
		this.showJSP("/error.html", req, resp);
	}

	public void showErrorPage(String error, HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		this.showErrorPage(error, req.getHeader("Referer"), req, resp);
	}

	public Boolean parameterExists(String key, Map<String, String[]> input, Boolean not_empty) {
		if (!input.containsKey(key) || input.get(key).length == 0) {
			return false;
		} else if (not_empty && input.get(key)[0].isEmpty()) {
			return false;
		}
		return true;
	}

	public Boolean parameterExists(String key, Map<String, String[]> input) {
		return this.parameterExists(key, input, false);
	}

	public Boolean requireLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		if (getCurrentUser(req) == null) {
			this.showErrorPage("You must be logged in to view this page", "/", req, resp);
			return false;
		}
		return true;
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

	public String getCurrentHostUrl(HttpServletRequest req) {
		String temp = req.getScheme() + "://" + req.getHeader("Host");
		return temp + "/";
	}
}