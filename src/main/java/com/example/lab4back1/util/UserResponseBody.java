package com.example.lab4back1.util;

public class UserResponseBody {
    private String username;
    private String password;
    private Boolean status;

    public UserResponseBody(String name, String token, Boolean status) {
        this.username = name;
        this.password = token;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getStatus() {
        return status;
    }
}
