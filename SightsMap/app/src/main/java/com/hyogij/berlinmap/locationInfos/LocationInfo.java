package com.hyogij.berlinmap.locationInfos;

/*
 * Description : LocationInfo class to store information
 * Date : 2015.11.16
 * Author : hyogij@gmail.com
 */
public class LocationInfo {
    private double latitude = 0;
    private double longitude = 0;
    private String name = null;
    private String description = null;
    private String url = null;

    public LocationInfo() {
        super();
    }

    public LocationInfo(double latitude, double longitude, String name, String description, String url) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.description = description;
        this.url = url;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "LocationInfo [latitude=" + latitude + ", longitude=" + longitude + ", name=" + name
                + ", description=" + description + ", url=" + url + "]";
    }
}
