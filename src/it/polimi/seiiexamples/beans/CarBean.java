package it.polimi.seiiexamples.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.seiiexamples.entities.Car;
import it.polimi.seiiexamples.entities.Reservation;
import it.polimi.seiiexamples.entities.User;

@Stateless
public class CarBean {

	@PersistenceContext(unitName = "EnjoyDatabase")
	private EntityManager em;

	public Car getFromName(String carName) {
		try {
			return (Car) this.em.createQuery("SELECT c FROM Car c WHERE c.carName=:carName")
					.setParameter("carName", carName).getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Car> getCarList() {
		try {
			return (List<Car>) this.em.createQuery("SELECT c FROM Car c").getResultList();
		} catch (NoResultException ex) {
			return new ArrayList<Car>();
		}
	}

	public Car insertCar(Car c) throws IOException, ServletException {
		if (getFromName(c.getCarName()) == null) {
			em.persist(c);
			return c;
		} else {
			return null;
		}
	}
}
