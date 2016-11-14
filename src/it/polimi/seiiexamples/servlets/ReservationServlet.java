package it.polimi.seiiexamples.servlets;

import it.polimi.seiiexamples.beans.CarBean;
import it.polimi.seiiexamples.beans.ReservationBean;
import it.polimi.seiiexamples.entities.Reservation;
import it.polimi.seiiexamples.entities.User;

import java.io.IOException;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReservationServlet extends GenericServlet {
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "EnjoyDatabase")
	private EntityManager em;

	@EJB
	private ReservationBean reservationService;

	@EJB
	private CarBean carService;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		insertReservation(req, resp);
	}

	protected void insertReservation(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		Reservation r = new Reservation();
		r.setCar(carService.getFromName(req.getParameter("carName")));
		r.setUser(getCurrentUser(req));
		reservationService.insertReservation(r);

		User u = userService.getUserFromId((int) req.getSession().getAttribute("userId"));
		req.setAttribute("userReservations", reservationService.getFromUser(u));
		req.setAttribute("cars", carService.getCarList());
		req.getRequestDispatcher("/login_ok.jsp").forward(req, resp);

	}
}