package com.example.NBA_Shop.model;

public class Jersey {

    private String JerseyUUID;

    public String getJerseyUUID() {
        return JerseyUUID;
    }

    public void setJerseyUUID(String jerseyUUID) {
        JerseyUUID = jerseyUUID;
    }

    private String SpielerName;
    private double preis;

    public String getSpielerName() {
        return SpielerName;
    }

    public void setSpielerName(String spielerName) {
        SpielerName = spielerName;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }
}
