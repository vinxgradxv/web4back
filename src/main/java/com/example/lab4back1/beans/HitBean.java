package com.example.lab4back1.beans;

import com.example.lab4back1.dao.HitDao;
import com.example.lab4back1.dao.UserDao;
import com.example.lab4back1.model.Hit;
import com.example.lab4back1.util.HitValidator;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;

@Singleton
public class HitBean {

    @EJB
    private HitDao hitDao;

    @EJB
    private UserDao userDao;

    public void addHit(Hit hit, String username){
        LocalDateTime startTime = LocalDateTime.now(ZoneOffset.UTC);
        hit.setStatus(HitValidator.isHit(hit.getX(), hit.getY(), hit.getR()));
        hit.setCurrentTime(new Timestamp(new Date().getTime()));
        hit.setUser(userDao.getUserByUsername(username));
        hit.setScriptTime(Math.round(LocalDateTime.now().minusNanos(startTime.getNano()).getNano() * 0.001));
        hitDao.addHit(hit);
    }

    public ArrayList<Hit> getHits(String username){
        return (ArrayList<Hit>) hitDao.getHits(userDao.getUserByUsername(username));
    }

    public void deleteHits(String username){
        hitDao.deleteHits(userDao.getUserByUsername(username));
    }
}
