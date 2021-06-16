package com.example.NBA_Shop.model;

import javax.validation.constraints.*;
import javax.ws.rs.FormParam;


/**
 * Model of Spieler
 *
 * @author Luigi Spina
 * @version 1.0
 * @since 2021-06-16
 */

public class Spieler {

    @FormParam("spielerUUID")
    @Pattern(regexp = "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String spielerUUID; //unique ID of the player

    @FormParam("vorname")
    @NotEmpty
    @Size(min = 2, max = 30)
    private String vorname;

    @FormParam("nachname")
    @NotEmpty
    @Size(min = 2, max = 30)
    private String nachname;

    @FormParam("alter")
    @DecimalMin("0")
    @DecimalMax("99")
    private int alter;

    private Schuh schuh;

    private Jersey jersey;

    public Spieler() {
        setSpielerUUID(null);
        setVorname(null);
        setNachname(null);
        setAlter(0);
        setSchuh(null);
        setJersey(null);

    }

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
