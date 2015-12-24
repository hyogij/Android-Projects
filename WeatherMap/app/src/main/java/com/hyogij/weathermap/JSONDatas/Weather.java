package com.hyogij.weathermap.JSONDatas;

import java.util.ArrayList;

/**
 * A class for Weather data
 */
public class Weather {
    public String cityName = null;
    public String title = null;
    public String link = null;
    public String description = null;
    public String pubDate = null;
    public String temp = null;

    public ArrayList<Forecast> forecastList = null;

    public Weather(String cityName, String title, String link, String description, String
            pubDate, String temp,
                   ArrayList<Forecast> forecastList) {
        this.cityName = cityName;
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
        this.temp = temp;
        this.forecastList = forecastList;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public ArrayList<Forecast> getForecastList() {
        return forecastList;
    }

    public void setForecastList(ArrayList<Forecast> forecastList) {
        this.forecastList = forecastList;
    }
}
