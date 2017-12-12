package it.polimi.seiiexamples.beans;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;

import org.apache.commons.validator.routines.EmailValidator;

import it.polimi.seiiexamples.entities.User;

@Stateless
public class UserBean {
	
    @PersistenceContext(unitName = "EnjoyDatabase")
    private EntityManager em;
	
	public Integer validate(String userMail, String password) {
		if(!EmailValidator.getInstance().isValid(userMail)) {
			return -2;
		}
		Query q = this.em.createQuery("SELECT u FROM User u WHERE u.email = :email");
		q.setParameter("email", userMail);
		
		try {
			User user = (User) q.getSingleResult();
			if (password.equals(user.getPassword())) {
				return 1;
			} else {
				return 0;
			}
		} catch (NoResultException e) {
			return -1;
		} 
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
	
	public Boolean isValidUser(User u) {
		if(EmailValidator.getInstance().isValid(u.getEmail())
				&& !u.getName().equals("")
				&& !u.getPassword().equals("")) {
			return true;
		} else {
			return false;
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

