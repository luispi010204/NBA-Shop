package com.example.NBA_Shop.model;

import javax.validation.constraints.*;
import javax.ws.rs.FormParam;

/**
 * Model of Schuh
 *
 * @author Luigi Spina
 * @version 1.0
 * @since 2021-06-16
 */

public class Schuh {

    @FormParam("schuhUUID")
    @Pattern(regexp = "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String schuhUUID; //unique ID for shoes

    @FormParam("schuhName")
    @Size(min = 1, max = 30)
    private String schuhName; //whose shoes are it

    @FormParam("preis")
    @DecimalMin(value = "0.00")
    @DecimalMax(value = "1000.00")
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
