package com.example.lab4back1.dao;

import com.example.lab4back1.model.Hit;
import com.example.lab4back1.model.User;
import com.example.lab4back1.util.HibernateUtil;
import jakarta.ejb.Singleton;
import jakarta.ejb.TransactionManagement;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

@Singleton
@TransactionManagement(jakarta.ejb.TransactionManagementType.BEAN)
public class HitDao {
    public void addHit(Hit hit) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(hit);
        transaction.commit();
        session.close();
    }

    public List<Hit> getHits(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Hit> list = session
                .createQuery("select h from Hit h where h.user = :user", Hit.class)
                .setParameter("user", user)
                .getResultList();
        return (list != null) ? list : new ArrayList<>();
    }

    public void deleteHits(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from Hit x where x.user = :user")
                .setParameter("user", user)
                .executeUpdate();
        transaction.commit();
        session.close();
    }
}
