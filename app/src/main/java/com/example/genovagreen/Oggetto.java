package com.example.genovagreen;


public class Oggetto {
    public String rifiuto;
    public String cassonetto;

    public Oggetto(){}

    public Oggetto(String rifiuto, String cassonetto){
        this.rifiuto=rifiuto;
        this.cassonetto=cassonetto;
    }

    public void setRifiuto(String rifiuto) {
        this.rifiuto = rifiuto;
    }
    public void setCassonetto(String cassonetto) {
        this.cassonetto = cassonetto;
    }
    public String getRifiuto(){
        return rifiuto;
    }
    public String getCassonetto(){
        return cassonetto;
    }
}
