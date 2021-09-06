package com.example.genovagreen;


import java.util.Comparator;

public class Object {
    public String rifiuto,cassonetto;

    public Object(){}

    public Object(String rifiuto, String cassonetto){
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

    public static Comparator<Object> comparator = new Comparator<Object>() {
        @Override
        public int compare(Object ogg1, Object ogg2) {
            return (int) (ogg1.getRifiuto().compareTo(ogg2.getRifiuto()));
        }
    };
}
