package com.wonders.xlab.healthcloud.entity.steward;

import javax.persistence.Embeddable;

/**
 * Created by Jeffrey on 15/7/7.
 */
@Embeddable
public class Location {

    private String address;

    private double longitude;

    private double latitude;

    public Location() {
    }

    public Location(String address, double longitude, double latitude) {
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
