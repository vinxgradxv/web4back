package com.example.lab4back1.dao;

import com.example.lab4back1.model.User;
import com.example.lab4back1.util.HibernateUtil;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

@Stateless
@TransactionManagement(jakarta.ejb.TransactionManagementType.BEAN)
public class UserDao {

    public void addUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    public User getUserByUsername(String username) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        User user;
        try {
            user = (User) session.createQuery("from User where username = :username")
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception exception) {
            user = null;
        }
        return user;
    }

    public ArrayList<String> getUsers() {
        return (ArrayList<String>) HibernateUtil
                .getSessionFactory()
                .openSession()
                .createQuery("select username from User")
                .list();
    }

}
