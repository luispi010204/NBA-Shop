package com.example.NBA_Shop.model;

/**
 * Model of Schuh
 *
 * @author Luigi Spina
 * @version 1.0
 * @since 2021-06-04
 */

public class Schuh {
    private String schuhUUID; //unique ID for shoes
    private String schuhName; //whose shoes are it
    private double preis; //price of the shoes

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
