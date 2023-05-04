package ru.scrin.service.services;

import db.HibernateUtil;
import db.UsersEntity;
import org.hibernate.Session;

public class DAO {

    public void saveTopic(UsersEntity user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }
}