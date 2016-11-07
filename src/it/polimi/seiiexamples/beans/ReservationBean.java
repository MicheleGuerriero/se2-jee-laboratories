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
public class ReservationBean {

    @PersistenceContext(unitName = "EnjoyDatabase")
    private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Reservation> getFromUser(User u) {
		try {
			return (List<Reservation>) this.em.createQuery("SELECT r FROM Reservation r WHERE r.user=:u")
					.setParameter("u", u).getResultList();
		} catch (NoResultException ex) {
			return new ArrayList<Reservation>();
		} 
	}
	
	@SuppressWarnings("unchecked")
	public List<Reservation> getFromCar(Car c) {
		try {
			return (List<Reservation>) this.em.createQuery("SELECT r FROM Reservation r WHERE r.car=:c")
					.setParameter("c", c).getResultList();
		} catch (NoResultException ex) {
			return new ArrayList<Reservation>();
		} 
	}
	
	@SuppressWarnings("unchecked")
	public List<Reservation> getActiveFromCar(Car c) {
		try {
			return (List<Reservation>) this.em.createQuery("SELECT r FROM Reservation r WHERE r.car=:c AND r.status=true")
					.setParameter("c", c).getResultList();
		} catch (NoResultException ex) {
			return new ArrayList<Reservation>();
		} 
	}
	
	@SuppressWarnings("unchecked")
	public List<Reservation> getFinalizedFromCar(Car c) {
		try {
			return (List<Reservation>) this.em.createQuery("SELECT r FROM Reservation r WHERE r.car=:c AND r.status=false")
					.setParameter("c", c).getResultList();
		} catch (NoResultException ex) {
			return new ArrayList<Reservation>();
		} 
	}

	public Reservation getFromId(String id) {
		try {
			return (Reservation) this.em.createQuery("SELECT r FROM Reservation r WHERE r.resid=:id").setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}
	
	public void insertReservation(Reservation r)
			throws IOException, ServletException {
			r.setIsActive(1);
			em.persist(r);
	}
	
	public Reservation finalizeReservation(String resId)
			throws IOException, ServletException {
			Reservation r = getFromId(resId);
			if (r != null) {
				em.persist(r);
				return r;
			} else {
				return null;
			}
	}
	
}

