package com.example.NBA_Shop.model;

public class Jersey {

    private String JerseyUUID;

    private String spielerName;
    private double preis;

    public String getJerseyUUID() {
        return JerseyUUID;
    }

    public void setJerseyUUID(String jerseyUUID) {
        JerseyUUID = jerseyUUID;
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
