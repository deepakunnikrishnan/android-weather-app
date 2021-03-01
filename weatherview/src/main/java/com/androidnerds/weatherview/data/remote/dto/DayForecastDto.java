package com.androidnerds.weatherview.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class DayForecastDto {

    private long id;
    @SerializedName("weather_state_name")
    private String weatherStateName;
    @SerializedName("weather_state_abbr")
    private String weatherStateAbbreviation;
    @SerializedName("wind_direction_compass")
    private String windDirectionCompass;
    @SerializedName("created")
    private String createdTime;
    @SerializedName("applicable_date")
    private String applicableTime;
    @SerializedName("min_temp")
    private double minTemperature;
    @SerializedName("max_temp")
    private double maxTemperature;
    @SerializedName("the_temp")
    private double currentTemperature;
    @SerializedName("wind_speed")
    private double windSpeed;
    @SerializedName("wind_direction")
    private double windDirection;
    @SerializedName("air_pressure")
    private double airPressure;
    @SerializedName("humidity")
    private int humidity;
    @SerializedName("visibility")
    private double visibility;
    @SerializedName("predictability")
    private int predictability;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWeatherStateName() {
        return weatherStateName;
    }

    public void setWeatherStateName(String weatherStateName) {
        this.weatherStateName = weatherStateName;
    }

    public String getWeatherStateAbbreviation() {
        return weatherStateAbbreviation;
    }

    public void setWeatherStateAbbreviation(String weatherStateAbbreviation) {
        this.weatherStateAbbreviation = weatherStateAbbreviation;
    }

    public String getWindDirectionCompass() {
        return windDirectionCompass;
    }

    public void setWindDirectionCompass(String windDirectionCompass) {
        this.windDirectionCompass = windDirectionCompass;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getApplicableTime() {
        return applicableTime;
    }

    public void setApplicableTime(String applicableTime) {
        this.applicableTime = applicableTime;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }

    public double getAirPressure() {
        return airPressure;
    }

    public void setAirPressure(double airPressure) {
        this.airPressure = airPressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public int getPredictability() {
        return predictability;
    }

    public void setPredictability(int predictability) {
        this.predictability = predictability;
    }
}
