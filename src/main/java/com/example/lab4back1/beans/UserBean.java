package com.example.lab4back1.beans;

import com.example.lab4back1.dao.HitDao;
import com.example.lab4back1.dao.UserDao;
import com.example.lab4back1.model.User;
import com.google.common.hash.Hashing;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Singleton
public class UserBean {

    @EJB
    private HitDao hitDao;
    @EJB
    private UserDao userDao;

    public void addUser(User user){
        userDao.addUser(user);
    }

    public ArrayList<String> getUsers(){
        return userDao.getUsers();
    }

    public boolean isRegistered(String username, String password) {
        String hashPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        User user = userDao.getUserByUsername(username);
        return user != null && user.getPassword().equals(hashPassword);
    }

    public boolean isUsernameExists(String username){
        return getUsers().contains(username);
    }
}
