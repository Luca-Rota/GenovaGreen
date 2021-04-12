package com.example.genovagreen;

public class SpedPersonali {
    String id;
    String email;

    public SpedPersonali(){}
    public SpedPersonali(String id, String email){
        this.id=id;
        this.email=email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
