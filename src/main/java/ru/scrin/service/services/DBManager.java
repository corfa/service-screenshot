package ru.scrin.service.services;

import db.HibernateUtil;
import db.UsersEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
public class DBManager {

    public void createUser(UsersEntity user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public UsersEntity getUser(String username){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query= session.createQuery("from UsersEntity where username=:username");
        query.setString("username",username);
        UsersEntity result= (UsersEntity) query.uniqueResult();

        session.getTransaction().commit();
        session.close();
        return result;

    }


}