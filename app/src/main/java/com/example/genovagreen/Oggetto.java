package com.example.genovagreen;


import java.util.Comparator;

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

    public static Comparator<Oggetto> comparator = new Comparator<Oggetto>() {
        @Override
        public int compare(Oggetto ogg1, Oggetto ogg2) {
            return (int) (ogg1.getRifiuto().compareTo(ogg2.getRifiuto()));
        }
    };
}
