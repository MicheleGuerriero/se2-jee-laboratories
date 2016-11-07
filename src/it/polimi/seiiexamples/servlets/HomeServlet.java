package it.polimi.seiiexamples.servlets;

import java.io.IOException;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class HomeServlet extends GenericServlet {
	private static final long serialVersionUID = 1L;

	public HomeServlet() {
		super();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		this.showJSP("/home.jsp", req, resp);
	}

}
