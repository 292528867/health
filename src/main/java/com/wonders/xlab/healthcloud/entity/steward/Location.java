package com.wonders.xlab.healthcloud.entity.steward;

import javax.persistence.Embeddable;

/**
 * Created by Jeffrey on 15/7/7.
 */
@Embeddable
public class Location {

    private String address;

    private long longitude;

    private long latitude;

    public Location() {
    }

    public Location(String address, long longitude, long latitude) {
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

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }
}
