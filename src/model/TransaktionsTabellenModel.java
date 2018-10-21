/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Ratte
 */
public class TransaktionsTabellenModel {

    String id;
    String aktie;
    String stueckzahl;
    String ocoDatum;
    String kursVerkauf;
    String verkaufsdatum;
    String kursKauf;
    String kaufdatum;

    public TransaktionsTabellenModel(String id, String aktie, String stueckzahl, String ocoDatum, String kursVerkauf, String verkaufsdatum, String kursKauf, String kaufdatum) {
        this.id = id;
        this.aktie = aktie;
        this.stueckzahl = stueckzahl;
        this.ocoDatum = ocoDatum;
        this.kursVerkauf = kursVerkauf;
        this.verkaufsdatum = verkaufsdatum;
        this.kursKauf = kursKauf;
        this.kaufdatum = kaufdatum;
    }

    public TransaktionsTabellenModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAktie() {
        return aktie;
    }

    public void setAktie(String aktie) {
        this.aktie = aktie;
    }

    public String getStueckzahl() {
        return stueckzahl;
    }

    public void setStueckzahl(String stueckzahl) {
        this.stueckzahl = stueckzahl;
    }

    public String getOcoDatum() {
        return ocoDatum;
    }

    public void setOcoDatum(String ocoDatum) {
        this.ocoDatum = ocoDatum;
    }

    public String getKursVerkauf() {
        return kursVerkauf;
    }

    public void setKursVerkauf(String kursVerkauf) {
        this.kursVerkauf = kursVerkauf;
    }

    public String getVerkaufsdatum() {
        return verkaufsdatum;
    }

    public void setVerkaufsdatum(String verkaufsdatum) {
        this.verkaufsdatum = verkaufsdatum;
    }

    public String getKursKauf() {
        return kursKauf;
    }

    public void setKursKauf(String kursKauf) {
        this.kursKauf = kursKauf;
    }

    public String getKaufdatum() {
        return kaufdatum;
    }

    public void setKaufdatum(String kaufdatum) {
        this.kaufdatum = kaufdatum;
    }

}
