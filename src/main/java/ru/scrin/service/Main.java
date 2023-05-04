package ru.scrin.service;

import db.UsersEntity;
import ru.scrin.service.services.DAO;

public class Main {
    public static void main(String[] args) {
       DAO dao = new DAO();
        UsersEntity user = new UsersEntity();
        user.setUsername("vlad");
        user.setHashPassword("845");
       dao.saveTopic(user);
    }
}
