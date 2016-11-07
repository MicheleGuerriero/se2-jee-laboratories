package it.polimi.seiiexamples.beans;

import java.io.IOException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.seiiexamples.entities.User;

@Stateless
public class UserBean {

    @PersistenceContext(unitName = "EnjoyDatabase")
    private EntityManager em;
	
	public Boolean validate(String userMail, String password) {
		boolean result = false;
		Query q = this.em.createQuery("SELECT u FROM User u WHERE u.email = :email" + " AND u.password = :pass");
		q.setParameter("email", userMail);
		q.setParameter("pass", password);
		try {
			User user = (User) q.getSingleResult();
			if (userMail.equalsIgnoreCase(user.getEmail()) && password.equals(user.getPassword())) {
				result = true;
			}
		} catch (NoResultException e) {
			return null;
		} 
		return result;
	}

	public User getUserFromId(int id) {
		try {
			return (User) em.createQuery("SELECT u FROM User u WHERE u.id=:id").setParameter("id", id)
					.getSingleResult();

		} catch (NoResultException ex) {
			return null;
		}
	}

	public User getUserFromEmail(String e) {
		try {
			return (User) em.createQuery("SELECT u FROM User u WHERE u.email=:email")
					.setParameter("email", e).getSingleResult();

		} catch (NoResultException ex) {
			return null;
		}
	}
	
	public User insertUser(User u) throws IOException, ServletException {
		if (getUserFromEmail(u.getEmail()) == null) {
			em.persist(u);
			return u;
		} else {
			return null;
		}
	}
}

