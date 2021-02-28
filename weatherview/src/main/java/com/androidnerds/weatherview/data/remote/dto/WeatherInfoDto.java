package com.androidnerds.weatherview.data.remote.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherInfoDto {

    @SerializedName("woeid")
    private int woeid;
    @SerializedName("title")
    private String title;
    @SerializedName("location_type")
    private String locationType;
    @SerializedName("latt_long")
    private String latLng;
    @SerializedName("timezone")
    private String timeZone;
    @SerializedName("time")
    private String time;
    @SerializedName("sun_rise")
    private String sunRise;
    @SerializedName("sun_set")
    private String sunSet;
    @SerializedName("timezone_name")
    private String timeZoneName;
    @SerializedName("consolidated_weather")
    private List<DayForecastDto> consolidatedWeather;

    public int getWoeid() {
        return woeid;
    }

    public void setWoeid(int woeid) {
        this.woeid = woeid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getLatLng() {
        return latLng;
    }

    public void setLatLng(String latLng) {
        this.latLng = latLng;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSunRise() {
        return sunRise;
    }

    public void setSunRise(String sunRise) {
        this.sunRise = sunRise;
    }

    public String getSunSet() {
        return sunSet;
    }

    public void setSunSet(String sunSet) {
        this.sunSet = sunSet;
    }

    public String getTimeZoneName() {
        return timeZoneName;
    }

    public void setTimeZoneName(String timeZoneName) {
        this.timeZoneName = timeZoneName;
    }

    public List<DayForecastDto> getConsolidatedWeather() {
        return consolidatedWeather;
    }

    public void setConsolidatedWeather(List<DayForecastDto> consolidatedWeather) {
        this.consolidatedWeather = consolidatedWeather;
    }
}
