package com.example.genovagreen;

public class MySped {
    public String id, email;
    public MySped(){}
    public MySped(String id, String email){
        this.id=id;
        this.email=email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
