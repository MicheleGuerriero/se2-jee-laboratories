package it.polimi.seiiexamples.servlets;

import it.polimi.seiiexamples.beans.CarBean;
import it.polimi.seiiexamples.beans.ReservationBean;
import it.polimi.seiiexamples.entities.Car;
import it.polimi.seiiexamples.entities.User;

import java.io.IOException;
import java.util.List;

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
		try {
			Car c = new Car();
			c.setCarName(req.getParameter("carName"));
			c.setNumberOfSpots(Integer.parseInt(req.getParameter("nSpots")));
			if(carService.insertCar(c) != null){
				User u = userService.getUserFromId((int) req.getSession().getAttribute("userId"));
				req.setAttribute("userReservations", reservationService.getFromUser(u));
				req.setAttribute("cars", carService.getCarList());
				this.showJSP("/login_ok.jsp", req, resp);
			} else {
				this.showJSP("/error.jsp", req, resp);
			}
		} catch (IllegalArgumentException e) {
			req.setAttribute("nSpots", req.getParameter("nSpots"));
			req.setAttribute("carName", req.getParameter("carName"));
			req.setAttribute("error", e.getMessage());
			this.showJSP("/login_ok.jsp", req, resp);
		} 
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		List<Car> c = carService.getCarList();
		req.setAttribute("carList", c);
		this.showJSP("/car_list.jsp", req, resp);

	}

}