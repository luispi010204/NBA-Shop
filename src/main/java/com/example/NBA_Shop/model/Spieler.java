package com.example.NBA_Shop.model;

/**
 * Model of Spieler
 *
 * @author Luigi Spina
 * @version 1.0
 * @since 2021-06-04
 */

public class Spieler {

    private String spielerUUID; //unique ID of the player
    private String vorname;
    private String nachname;
    private int alter;
    private Schuh schuh;
    private Jersey jersey;

    public String getSpielerUUID() {
        return spielerUUID;
    }

    public void setSpielerUUID(String spielerUUID) {
        this.spielerUUID = spielerUUID;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public int getAlter() {
        return alter;
    }

    public void setAlter(int alter) {
        this.alter = alter;
    }

    public Schuh getSchuh() {
        return schuh;
    }

    public void setSchuh(Schuh schuh) {
        this.schuh = schuh;
    }

    public Jersey getJersey() {
        return jersey;
    }

    public void setJersey(Jersey jersey) {
        this.jersey = jersey;
    }
}
