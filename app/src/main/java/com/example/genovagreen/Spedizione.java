package com.example.genovagreen;

import java.util.Comparator;

public class Spedizione {
    public String luogo, descrizione, organizzatore, data, ora, partecipanti;



    public Spedizione(){}

    public Spedizione(String luogo,String descrizione, String organizzatore, String data, String ora, String partecipanti){
        this.luogo=luogo;
        this.descrizione=descrizione;
        this.organizzatore=organizzatore;
        this.data=data;
        this.ora=ora;
        this.partecipanti=partecipanti;
    }


    public void setData(String data) {
        this.data = data;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public void setOrganizzatore(String organizzatore) {
        this.organizzatore = organizzatore;
    }

    public void setPartecipanti(String partecipanti) {
        this.partecipanti = partecipanti;
    }

    public String getPartecipanti() {
        return partecipanti;
    }

    public String getData() {
        return data;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getLuogo() {
        return luogo;
    }

    public String getOra() {
        return ora;
    }

    public String getOrganizzatore() {
        return organizzatore;
    }

}
