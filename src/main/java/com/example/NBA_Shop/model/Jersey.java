package com.example.NBA_Shop.model;

/**
 * Model of Jersey
 *
 * @author Luigi Spina
 * @version 1.0
 * @since 2021-06-04
 */

public class Jersey {

    private String jerseyUUID; //unique ID for Jerseys
    private String spielerName; //whose jersey is it
    private double preis; //price of Jersey

    public String getJerseyUUID() {
        return jerseyUUID;
    }

    public void setJerseyUUID(String jerseyUUID) {
        this.jerseyUUID = jerseyUUID;
    }


    public String getSpielerName() {
        return spielerName;
    }

    public void setSpielerName(String spielerName) {
        this.spielerName = spielerName;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }
}
