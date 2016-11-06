package com.rmathur.weather;

import com.orm.SugarRecord;

/**
 * Created by rohanmathur on 10/19/16.
 */

public class Location extends SugarRecord {

    private String zipcode;

    public Location() {
        this.zipcode = "";
    }

    public Location(String zipcode) {
        // set this object's zipcode
        this.zipcode = zipcode;
    }

    public String getZipCode() {
        return this.zipcode;
    }
}
