package it.polimi.seiiexamples.beans;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import it.polimi.seiiexamples.entities.User;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Stateless
public class UserBean {
	
    @PersistenceContext(unitName = "EnjoyDatabase")
    private EntityManager em;

    public Integer validate(String userMail, String password) {
        if (!this.validateEmail(userMail)) {
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
        if (!u.getPassword().equals("")
                && !u.getName().equals("")
                && this.validateEmail(u.getEmail())) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean insertUser(User u) {
        if (getUserFromEmail(u.getEmail()) == null) {
            em.persist(u);
            return true;
        } else {
            return false;
        }
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
