package it.polimi.seiiexamples.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import it.polimi.seiiexamples.beans.AdditionBean;

public class AddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private AdditionBean addBean;

	public AddServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setBufferSize(8192);

		PrintWriter out = response.getWriter();
		String first = request.getParameter("t1");
		String second = request.getParameter("t2");

		if (!StringUtils.isNumeric(first) || !StringUtils.isNumeric(second)) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/error");
			dispatcher.include(request, response);
		} else {
			int i = Integer.parseInt(first);
			int j = Integer.parseInt(second);
			addBean.setI(i);
			addBean.setJ(j);
			addBean.add();

			out.println("<html><body><h1>Here is the sum!</h1>" + "<br>The sum is: " + addBean.getK() + "<br>"
					+ "</body></html>" + "<form action=\"add\" method=\"get\">" + "Do another sum"
					+ "<input type=\"submit\">" + "</form><br></body>");
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/add.html");
		dispatcher.include(request, response);

	}
}
