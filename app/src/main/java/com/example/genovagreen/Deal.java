package com.example.genovagreen;

public class Deal {
    private String rifiuto;
    private String cassonetto;

    public Deal(String rifiuto,String cassonetto){
        this.rifiuto=rifiuto;
        this.cassonetto=cassonetto;
    }

    public String getRifiuto(){
        return rifiuto;
    }
    public String getCassonetto(){
        return cassonetto;
    }
}
