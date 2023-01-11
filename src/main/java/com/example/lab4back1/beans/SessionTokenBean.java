package com.example.lab4back1.beans;

import jakarta.ejb.Singleton;
import jakarta.ejb.Stateful;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;

@Singleton
public class SessionTokenBean {

    private HashMap<String, String> tokensMap = new HashMap<>();

    public String generateTokenForUser(String username) {
        String token = RandomStringUtils.random(10, true, true);
        tokensMap.put(username, token);
        return token;
    }

    public String getTokenForUser (String userName) {
        return tokensMap.getOrDefault(userName, null);
    }
}
