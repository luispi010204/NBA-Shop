package com.example.NBA_Shop.model;

import javax.validation.constraints.*;
import javax.ws.rs.FormParam;

/**
 * Model of Jersey
 *
 * @author Luigi Spina
 * @version 1.0
 * @since 2021-07-03
 */

public class Jersey {

    @FormParam("jerseyUUID")
    @Pattern(regexp = "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String jerseyUUID; //unique ID for Jerseys

    @FormParam("spielerName")
    @Size(min = 1, max = 30)
    private String spielerName; //whose jersey is it

    @FormParam("preis")
    @DecimalMin(value = "0.00")
    @DecimalMax(value = "1000.00")
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
