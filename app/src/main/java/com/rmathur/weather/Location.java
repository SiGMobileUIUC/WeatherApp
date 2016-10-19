package com.rmathur.weather;

import com.orm.SugarRecord;

/**
 * Created by rohanmathur on 10/19/16.
 */

public class Location extends SugarRecord {

    public int zipcode;

    public Location(int zipcode) {
        // set this object's zipcode
        this.zipcode = zipcode;
    }
}
