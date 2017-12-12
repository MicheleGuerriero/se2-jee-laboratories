package it.polimi.seiiexamples.servlets;

import it.polimi.seiiexamples.beans.CarBean;
import it.polimi.seiiexamples.beans.ReservationBean;
import it.polimi.seiiexamples.entities.Car;
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

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		User u = (User) req.getSession().getAttribute("user");
		Reservation r = new Reservation();
		if (carService.getFromName(req.getParameter("carName")) != null) {
			r.setCar(carService.getFromName(req.getParameter("carName")));
			r.setUser(u);
			reservationService.insertReservation(r);
			req.setAttribute("message", "Reservation for car " + r.getCar().getCarName() + " successfully inserted.");
		} else {
			req.setAttribute("message", "Please specify a valid car name.");
		}

		req.setAttribute("userReservations", reservationService.getFromUser(u));
		req.setAttribute("cars", carService.getCarList());
		req.getRequestDispatcher("/login_ok.jsp").forward(req, resp);	
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User u = (User) req.getSession().getAttribute("user");
		Car c = (Car) carService.getFromName(req.getParameter("carName"));
		
		if(c != null) {
			if(reservationService.finalizeReservation(u, c) != null) {
				req.setAttribute("message", "The reservation had been successfully finalized.");
			} else {
				req.setAttribute("message", "You have not active reservation for the specified car.");
			}
		} else {
			req.setAttribute("message", "The specified car does not exists.");
		}
		
		req.setAttribute("userReservations", reservationService.getFromUser(u));
		req.setAttribute("cars", carService.getCarList());
		req.getRequestDispatcher("/login_ok.jsp").forward(req, resp);	
	}

}