package it.polimi.seiiexamples.servlets;

import it.polimi.seiiexamples.beans.CarBean;
import it.polimi.seiiexamples.beans.ReservationBean;
import it.polimi.seiiexamples.beans.UserBean;
import it.polimi.seiiexamples.entities.Car;
import it.polimi.seiiexamples.entities.Reservation;
import it.polimi.seiiexamples.entities.User;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
		if (requireLogin(req, resp)) {
			if (parameterExists("delete", req.getParameterMap(), true)) {
				deleteReservation(req, resp);
			} else {
				insertReservation(req, resp);
			}
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		if (parameterExists("id", req.getParameterMap(), true)) {
			if (parameterExists("delete", req.getParameterMap(), true) && requireLogin(req, resp)) {
				showDeleteForm(req, resp);
			} else {
				showReservation(req, resp);
			}
		} else if (parameterExists("user", req.getParameterMap(), true)) {
			showReservationByUser(req, resp);
		}  else if (parameterExists("car", req.getParameterMap(), true)) {
			showReservationByCar(req, resp);
		} else {
			showErrorPage("You must specify a reservation to show", req, resp);
		}
	}

	private void showReservation(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		Reservation r = reservationService.getFromId(req.getParameter("id"));
		req.setAttribute("reservation", r);

		req.setAttribute("car", r.getCar().getId());
		showJSP("reservation.jsp", req, resp);

	}

	private void showReservationByUser(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User u = userService.getUserFromId(Integer.parseInt(req.getParameter("user")));
		req.setAttribute("owner", u);

		List<Reservation> r = u.getReservations();
		req.setAttribute("reservations", r);

		showJSP("user_reservations.jsp", req, resp);
	}
	
	private void showReservationByCar(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Car c = carService.getFromName(req.getParameter("car"));
		req.setAttribute("car", c);

		List<Reservation> r = c.getCarReservations();
		req.setAttribute("reservations", r);

		showJSP("car_reservations.jsp", req, resp);
	}

	private void showDeleteForm(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Reservation r = reservationService.getFromId(req.getParameter("id"));
		if (r != null) {
			if (r.getUser().equals(getCurrentUser(req))) {
				req.setAttribute("reservation", r);
				showJSP("reservation_delete.jsp", req, resp);
			} else {
				showErrorPage("The specified reservation is not owned by you",
						"/reservation?user=" + getCurrentUser(req).getId(), req, resp);
			}
		} else {
			showErrorPage("The specified reservation does not exist",
					"/reservation?user=" + getCurrentUser(req).getId(), req, resp);
		}
	}

	private void insertReservation(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		Reservation r = new Reservation();
		r.setCar(carService.getFromName(req.getParameter("carName")));
		r.setUser(getCurrentUser(req));
		reservationService.insertReservation(r);
		
		User u = userService.getUserFromId((int) req.getSession().getAttribute("userId"));
		req.setAttribute("userReservations", reservationService.getFromUser(u));
		req.setAttribute("cars", carService.getCarList());
		this.showJSP("/login_ok.jsp", req, resp);

	}

	private void deleteReservation(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		if (parameterExists("id", req.getParameterMap(), true)) {
			Reservation r = reservationService.getFromId(req.getParameter("id"));
			if (r != null) {
				User u = getCurrentUser(req);
				if (r.getUser().equals(u)) {
					em.remove(r);

					resp.sendRedirect("/reservation?user=" + u.getId());
				} else {
					showErrorPage("The specified reservation is not owned by you",
							"/reservation?user=" + getCurrentUser(req).getId(), req, resp);
				}
			} else {
				showErrorPage("The specified reservation does not exist",
						"/reservation?user=" + getCurrentUser(req).getId(), req, resp);
			}
		} else {
			showErrorPage("You must specify a reservation to delete",
					"/reservation?user=" + getCurrentUser(req).getId(), req, resp);
		}
	}
}