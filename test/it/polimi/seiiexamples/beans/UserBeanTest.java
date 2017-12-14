/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.seiiexamples.beans;

import it.polimi.seiiexamples.entities.Car;
import it.polimi.seiiexamples.entities.User;
import it.polimi.seiiexamples.entities.Reservation;
import java.io.File;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author micheleguerriero
 */
@RunWith(Arquillian.class)
public class UserBeanTest {

    @EJB
    private UserBean userBean;

    @Deployment
    public static JavaArchive createDeployment() {

        return ShrinkWrap.create(JavaArchive.class).addClasses(UserBean.class, User.class, Reservation.class, Car.class)
                .addAsResource(new File("test/resources/persistence-test.xml"), "META-INF/persistence.xml");

    }

    @Test
    public void testValidate() {
        User u = new User();

        u.setEmail("test@mail.it");
        u.setName("testname");
        u.setPassword("testpassword");
        userBean.insertUser(u);

        Assert.assertEquals(new Integer(1), userBean.validate("test@mail.it", "testpassword"));
        Assert.assertEquals(new Integer(-2), userBean.validate("wrongemail", "testpassword"));
        Assert.assertEquals(new Integer(0), userBean.validate("test@mail.it", "wrongpassword"));
        Assert.assertEquals(new Integer(-1), userBean.validate("notexists@mail.it", "testpassword"));

    }
}
