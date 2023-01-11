package com.example.lab4back1.util;

public class StatusObject {

    private String username;
    private boolean success;
    private String token;

    public StatusObject(boolean success, String token, String name) {
        this.success = success;
        this.token = token;
        this.username = name;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "StatusObject{" +
                "success=" + success +
                '}';
    }
}

