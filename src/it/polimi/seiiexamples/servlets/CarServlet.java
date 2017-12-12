package it.polimi.seiiexamples.servlets;

import it.polimi.seiiexamples.beans.CarBean;
import it.polimi.seiiexamples.beans.ReservationBean;
import it.polimi.seiiexamples.entities.Car;
import it.polimi.seiiexamples.entities.User;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CarServlet extends GenericServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private CarBean carService;

	@EJB
	private ReservationBean reservationService;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		User u = (User) req.getSession().getAttribute("user");

		if (carService.isValidCarInfor(req.getParameter("carName"), req.getParameter("nSpots"))) {
			Car c = new Car();
			c.setCarName(req.getParameter("carName"));
			c.setNumberOfSpots(Integer.parseInt(req.getParameter("nSpots")));
			if (carService.insertCar(c) != null) {
				req.setAttribute("message", "New cart successfully inserted.");
			} else {
				req.setAttribute("message", "A car with the specified name already exists.");
			}
		} else {
			req.setAttribute("message", "Invalid or missing car information");
		}
		
		req.setAttribute("userReservations", reservationService.getFromUser(u));
		req.setAttribute("cars", carService.getCarList());
		req.getRequestDispatcher("/login_ok.jsp").forward(req, resp);
	}
}