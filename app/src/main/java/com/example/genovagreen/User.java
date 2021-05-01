package com.example.genovagreen;

public class User {
    private String username, email;

    public User(){}
    public User(String user, String email){
        this.username=user;
        this.email=email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
