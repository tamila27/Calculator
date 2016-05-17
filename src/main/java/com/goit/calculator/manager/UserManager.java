package com.goit.calculator.manager;

import com.goit.calculator.model.User;
import com.goit.calculator.persistence.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by tamila on 5/16/16.
 */
public class UserManager {

    public static void main(String[] args) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        User user = new User();
        user.setPassword("2");
        user.setUserEmail("3");
        user.setUserLastName("4");
        user.setUserName("5");

        System.out.println("Creating Person: " + user.getUserName());
        session.persist(user);

        session.getTransaction().commit();
        session.close();
    }

    public static void registerUser(User user){
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        System.out.println("Creating Person: " + user.getUserName());
        session.persist(user);

        session.getTransaction().commit();
        session.close();
    }

    public static User getUserFromDB(String userName, String password){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<User> foundUsers = null;
        try {
            transaction = session.beginTransaction();

            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("userName", userName));
            criteria.add(Restrictions.eq("password", password));
            foundUsers = (List<User>) criteria.list();

            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        User result;
        if (foundUsers == null || foundUsers.isEmpty()) {
            result = null;
        } else if (foundUsers.size() > 1) {
            throw new RuntimeException("Too many records");
        } else {
            result = foundUsers.get(0);
        }

        return result;
    }
}
