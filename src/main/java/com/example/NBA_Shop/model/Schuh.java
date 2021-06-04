package com.example.NBA_Shop.model;

public class Schuh {
    private String schuhUUID;

    private String schuhName;
    private double preis;

    public String getSchuhUUID() {
        return schuhUUID;
    }

    public void setSchuhUUID(String schuhUUID) {
        this.schuhUUID = schuhUUID;
    }


    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public String getSchuhName() {
        return schuhName;
    }

    public void setSchuhName(String schuhName) {
        this.schuhName = schuhName;
    }
}
